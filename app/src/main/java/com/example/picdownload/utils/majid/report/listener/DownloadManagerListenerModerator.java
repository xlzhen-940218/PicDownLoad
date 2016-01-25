package com.example.picdownload.utils.majid.report.listener;

/**
 * Created by Majid Golshadi on 4/21/2014.
 */
public class DownloadManagerListenerModerator {

    private DownloadManagerListener downloadManagerListener;

    public DownloadManagerListenerModerator(DownloadManagerListener listener){
        downloadManagerListener = listener;
    }

    public void OnDownloadStarted(long taskId,String downloadUrl) {
        if (downloadManagerListener != null) {
            downloadManagerListener.OnDownloadStarted(taskId,downloadUrl);
        }
    }

    public void OnDownloadPaused(long taskId,String downloadUrl) {
        if (downloadManagerListener != null) {
            downloadManagerListener.OnDownloadPaused(taskId,downloadUrl);
        }
    }

    public void onDownloadProcess(long taskId, double percent, long downloadedLength,String downloadUrl) {
        if (downloadManagerListener != null) {
            downloadManagerListener.onDownloadProcess(taskId, percent, downloadedLength,downloadUrl);
        }
    }

    public void OnDownloadFinished(long taskId,String downloadUrl) {
        if (downloadManagerListener != null) {
            downloadManagerListener.OnDownloadFinished(taskId,downloadUrl);
        }
    }

    public void OnDownloadRebuildStart(long taskId,String downloadUrl) {
        if (downloadManagerListener != null) {
            downloadManagerListener.OnDownloadRebuildStart(taskId,downloadUrl);
        }
    }


    public void OnDownloadRebuildFinished(long taskId,String downloadUrl) {
        if (downloadManagerListener != null) {
            downloadManagerListener.OnDownloadRebuildFinished(taskId,downloadUrl);
        }
    }

    public void OnDownloadCompleted(long taskId,String downloadUrl) {
        if (downloadManagerListener != null) {
            downloadManagerListener.OnDownloadCompleted(taskId,downloadUrl);
        }
    }
    
    public void ConnectionLost(long taskId,String downloadUrl){
    	if (downloadManagerListener != null) {
			downloadManagerListener.connectionLost(taskId,downloadUrl);
		}
    }
}
