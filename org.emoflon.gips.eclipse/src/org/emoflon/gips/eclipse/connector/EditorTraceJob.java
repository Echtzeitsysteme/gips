package org.emoflon.gips.eclipse.connector;

import java.util.Objects;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

public class EditorTraceJob extends Job {

	public static interface IEditorTraceJob {
		IStatus run(SubMonitor monitor);
	}

	private IEditorTraceJob job;

	public EditorTraceJob() {
		super("GIPS trace visualisation task");
	}

	public void setup(IEditorTraceJob job) {
		this.job = Objects.requireNonNull(job, "job");
	}

	@Override
	protected IStatus run(IProgressMonitor eclipseMonitor) {
		return job.run(SubMonitor.convert(eclipseMonitor));
	}

}