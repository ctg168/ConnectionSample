package com.jet2006.connectionsample;

public interface DownloadStatusListener {
    //    void onComplete(int id);
//    void onFailed(int id, int errorCode, String errorMessage);
//    void onProgress(int id, long totalBytes, long downloadedBytes, int progress);
//    void onPreDownload(int id);
//    void onPause(int id);
//    void onResume(int id);
//    void onCancel(int id);
//
    void onComplete(int id);
    void onFailed(int id, int errorCode, String errorMessage);
    void onProgress(int id, int progress);
    void onPreDownload(int id);
}
