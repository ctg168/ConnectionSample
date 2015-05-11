package com.jet2006.connectionsample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements DownloadStatusListener {

    private TextView textView;
    private ProgressBar progressBar;
    private Button btnStart;
    private int taskId = 0;

    DownloadTask task;


    //String fileName = "http://192.168.1.27:9600/logs/file2.txt"; //utf-8编码的文件
    //String fileName = "http://192.168.1.27:9600/logs/file.txt"; //ansi编码的文件
    //String fileName = "http://192.168.1.27:9600/logs/cde.flv";
    //String fileName = "http://192.168.1.27:9600/logs/河南省行政机关公务员培训学分制管理办法(试行).Pdf";
    String fileName = "http://192.168.1.27:9600/logs/c.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final String storeFileName = this.getBaseContext().getExternalCacheDir().toString() + "/" + "aaa.jpg";

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new DownloadTask(taskId, fileName,storeFileName, MainActivity.this);
                task.Start();
                btnStart.setEnabled(false);
            }

        });
    }

    public void WriteMsg(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(msg);
                btnStart.setEnabled(true);
            }
        });
    }

    @Override
    public void onComplete(int id) {
        WriteMsg("download complete");
        System.out.println("MainActivity.onComplete");
    }

    @Override
    public void onFailed(int id, int errorCode, String errorMessage) {
        WriteMsg("download failed");
        System.out.println("MainActivity.onFailed");
    }

    @Override
    public void onProgress(int id, final int progress) {
        WriteMsg(String.valueOf(progress));
        System.out.println("MainActivity.onProgress" + progress);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progress);
            }
        });

    }

    @Override
    public void onPreDownload(int id) {
        WriteMsg("PreDownload");
        System.out.println("MainActivity.onPreDownload");
    }


}
