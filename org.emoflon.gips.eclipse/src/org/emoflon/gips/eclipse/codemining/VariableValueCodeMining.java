package org.emoflon.gips.eclipse.codemining;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.codemining.ICodeMiningProvider;
import org.eclipse.jface.text.codemining.LineContentCodeMining;

public class VariableValueCodeMining extends LineContentCodeMining {

	private final String text;

	public VariableValueCodeMining(int offset, String text, ICodeMiningProvider provider) {
		super(new Position(offset, text.length()), true, provider, null);
		this.text = Objects.requireNonNull(text);
	}

	@Override
	protected CompletableFuture<Void> doResolve(ITextViewer viewer, IProgressMonitor monitor) {
		return CompletableFuture.runAsync(() -> {
			super.setLabel(text);
		});
	}
}
