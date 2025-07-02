package org.emoflon.gips.gipsl.ui.handler;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler2;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.formatting.IContentFormatterFactory;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.editor.model.XtextDocumentUtil;
import org.emoflon.gips.gipsl.ui.internal.GipslActivator;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class FormatGipslHandler extends AbstractHandler implements IHandler2 {

	@Inject
	@Named(Constants.FILE_EXTENSIONS)
	private String fileExtension;

	@Inject(optional = true)
	private IContentFormatterFactory contentFormatterFactory;

	@Inject(optional = true)
	private XtextDocumentUtil xtextDocumentUtil;

	@Inject(optional = true)
	private XtextDocumentProvider provider;

	private IContentFormatter formatter;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (!(HandlerUtil.getCurrentSelectionChecked(event) instanceof IStructuredSelection selection))
			return null;

		Stream<IFile> filesToBeFormatted = StreamSupport.stream((Spliterator<Object>) selection.spliterator(), false) //
				.flatMap(this::collectFiles) //
				.filter(Objects::nonNull) //
				.filter(this::isNotDerived) //
				.filter(this::isGipslFile) //
				.distinct();

		Job job = new Job("Format") {
			@Override
			protected IStatus run(IProgressMonitor pMonitor) {
				List<IFile> files = filesToBeFormatted.toList();
				SubMonitor monitor = SubMonitor.convert(pMonitor, files.size());

				if (formatter == null && contentFormatterFactory != null) {
					formatter = contentFormatterFactory != null
							? contentFormatterFactory.createConfiguredFormatter(null, null)
							: null;
				}

				List<Exception> aggregatedExceptions = new LinkedList<>();
				for (IFile file : files) {
					monitor.checkCanceled();

					Display.getDefault().syncExec(() -> {
						try {
							formatFile(monitor.slice(1), file);
						} catch (Exception e) {
							aggregatedExceptions.add(new Exception(file.getFullPath().toOSString(), e));
						}
					});
				}

				if (aggregatedExceptions.isEmpty())
					return Status.OK_STATUS;

				String errorMessage = String.format("Unable to format %d file(s)", aggregatedExceptions.size());
				MultiStatus status = new MultiStatus(GipslActivator.PLUGIN_ID, IStatus.ERROR, errorMessage);

				for (Exception e : aggregatedExceptions) {
					IStatus childStatus = Status.error(e.getMessage(), e.getCause());
					status.add(childStatus);
				}

				return status;
			}
		};
		job.setPriority(Job.SHORT);
		job.setSystem(false);
		job.setUser(true);
		job.schedule();

		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
//		this.setBaseEnabled(formatter != null);
	}

	private void formatFile(IProgressMonitor monitor, IFile file) throws CoreException, ExecutionException {
		if (formatter != null) {
			if (xtextDocumentUtil != null) { // check if it's already loaded somewhere
				IDocument document = xtextDocumentUtil.getXtextDocument(file);
				if (document != null) {
					formatter.format(document, new Region(0, document.getLength()));
					return; // done!
				}
			}

			if (provider != null) { // we need to load it ourselves
				IEditorInput fileInput = new FileEditorInput(file);
				try {
					provider.connect(fileInput);
					IDocument document = provider.getDocument(fileInput);

					if (document != null) {
						formatter.format(document, new Region(0, document.getLength()));
						provider.aboutToChange(fileInput);
						provider.saveDocument(monitor, fileInput, document, true);
						provider.changed(fileInput);
						return; // done!
					}
				} finally {
					provider.disconnect(fileInput);
				}
			}
		}

		// alternative
		var editor = IDE.openEditor(getWorkbenchPage(), file, true);
		if (editor instanceof XtextEditor textEditor) {
			var formatAction = textEditor.getAction("Format");
			if (formatAction != null) {
				formatAction.run();
			} else {
				throw new ExecutionException("No Formatter found");
			}
		} else {
			throw new ExecutionException("No XtextEditor found");
		}
	}

	private IWorkbenchPage getWorkbenchPage() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	}

	private Stream<IFile> collectFiles(Object selection) {
		Iterator<IFile> iterator = buildIterator(selection);
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
	}

	private Iterator<IFile> buildIterator(Object element) {
		if (element instanceof IFile file)
			return Collections.singleton(file).iterator();

		if (element instanceof IContainer folder) {
			try {
				var childs = folder.members();
				return new SubIterator<Object, IFile>(Arrays.asList(childs).iterator(), this::buildIterator);
			} catch (CoreException e) {

			}
		}

		if (element instanceof IAdaptable adaptable) {
			var resource = adaptable.getAdapter(IResource.class);
			if (resource != null && resource != element) // avoid infinite recursion
				return buildIterator(resource);
		}

		return Collections.emptyIterator();
	}

	private boolean isGipslFile(IFile file) {
		return this.fileExtension.equalsIgnoreCase(file.getFileExtension());
	}

	private boolean isNotDerived(IFile file) {
		return !file.isDerived();
	}

	private static class SubIterator<I, T> implements Iterator<T> {

		private Iterator<? extends I> childs;
		private Function<I, Iterator<T>> mapper;
		private Iterator<T> subIterator;

		public SubIterator(Iterator<? extends I> childs, Function<I, Iterator<T>> mapper) {
			this.childs = Objects.requireNonNull(childs, "childs");
			this.mapper = Objects.requireNonNull(mapper, "mapper");
		}

		@Override
		public boolean hasNext() {
			if (subIterator != null && subIterator.hasNext())
				return true;

			if (!childs.hasNext())
				return false;

			subIterator = mapper.apply(childs.next());
			return subIterator.hasNext();
		}

		@Override
		public T next() {
			return subIterator.next();
		}
	}

}
