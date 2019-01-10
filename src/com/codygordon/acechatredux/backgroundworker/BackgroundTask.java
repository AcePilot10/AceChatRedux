package com.codygordon.acechatredux.backgroundworker;

public class BackgroundTask implements Runnable {

	private DoInBackground task;
	private IBackgroundWorker worker;

	public BackgroundTask(DoInBackground task, IBackgroundWorker worker) {
		this.task = task;
		this.worker = worker;
	}

	@Override
	public void run() {
		try {
		task.execute();
		worker.onComplete();
		} catch(Exception e) {
			worker.onError(e);
		}
	}
	
	public interface DoInBackground {
		void execute();
	}
}