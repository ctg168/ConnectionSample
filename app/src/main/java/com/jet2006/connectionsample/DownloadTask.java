package com.jet2006.connectionsample;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask {
    private Thread mDownloadTask;
    private String downloadContent;
    private int mCurrentId;

    public int getmCurrentId() {
        return mCurrentId;
    }

    public void setmCurrentId(int mCurrentId) {
        this.mCurrentId = mCurrentId;
    }


    public DownloadTask(int id, final String downloadUri, final String storeFileName, final DownloadStatusListener downloadListener) {
        this.mDownloadListener = downloadListener;
        this.mCurrentId = id;

        mDownloadTask = new Thread() {
            private int fileSize;
            private String fileData;
            private int downloadRate;

            @Override
            public void run() {
                RandomAccessFile randomAccessFile = null;
                InputStream inputStream = null;

                try {

                    URL url = new URL(downloadUri);
                    //URL url = new URL(URLEncoder.encode(downloadUri, "UTF-8"));


                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(15 * 1000);
                    conn.connect();

                    randomAccessFile = new RandomAccessFile(storeFileName, "rwd");
                    inputStream = conn.getInputStream();
                    byte[] buffer = new byte[4096];
                    int length = -1;
                    while ((length = inputStream.read(buffer)) != -1) {
                        randomAccessFile.write(buffer, 0, length);
                    }
                    conn.disconnect();
                    Complete();
                }
                catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

    }

    public void Start() {
        mDownloadTask.start();
        mDownloadListener.onPreDownload(mCurrentId);
    }

    public void Progress(int currentProgress) {
        mDownloadListener.onProgress(mCurrentId, currentProgress);
    }

    private void Complete() {
        mDownloadListener.onComplete(mCurrentId);
    }


    private DownloadStatusListener mDownloadListener;

    public DownloadStatusListener getmDownloadListener() {
        return mDownloadListener;
    }

    public DownloadTask setmDownloadListener(DownloadStatusListener mDownloadListener) {
        this.mDownloadListener = mDownloadListener;
        return this;
    }

    public String getDownloadContent() {
        return downloadContent;
    }

    public void setDownloadContent(String downloadContent) {
        this.downloadContent = downloadContent;
    }
}