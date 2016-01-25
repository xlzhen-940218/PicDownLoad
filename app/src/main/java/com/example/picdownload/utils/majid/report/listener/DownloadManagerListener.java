package com.example.picdownload.utils.majid.report.listener;

/**
 * Created by Majid Golshadi on 4/20/2014.
 */
public interface DownloadManagerListener {

    void OnDownloadStarted(long taskId,String downloadUrl);

    void OnDownloadPaused(long taskId,String downloadUrl);

    void onDownloadProcess(long taskId, double percent, long downloadedLength,String downloadUrl);

    void OnDownloadFinished(long taskId,String downloadUrl);

    void OnDownloadRebuildStart(long taskId,String downloadUrl);

    void OnDownloadRebuildFinished(long taskId,String downloadUrl);

    void OnDownloadCompleted(long taskId,String downloadUrl);
    
    void connectionLost(long taskId,String downloadUrl);

}
