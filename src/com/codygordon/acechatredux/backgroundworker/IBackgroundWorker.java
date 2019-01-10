package com.codygordon.acechatredux.backgroundworker;

public interface IBackgroundWorker {
	void onComplete();
	void onError(Exception e);
}