package com.codygordon.acechatredux.backgroundworker;

public class BackgroundWorker {
	
	public static void runInBackground(BackgroundTask task) {
		new Thread(task).start();
	}
}