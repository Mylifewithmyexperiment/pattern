package com.elitecorelib.advertisements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.elitecore.eliteconnectlibrary.R;
import com.elitecorelib.advertisements.pojo.InLine;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.elitecorelib.core.services.ConnectionManagerTaskNew;
import com.elitecorelib.core.utility.ElitelibUtility;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by harshesh.soni on 12/11/2018.
 */

public class AdvertisementAcitivity extends Activity implements AdsCompleteListner, ConnectionManagerCompleteListner {

    private String MODULE = "AdvertisementAcitivity";
    private String uriPath = "";
    private File rootFile = null;
    private static final String TEST_FILE_NAME = "Test.txt";
    private ProgressDialog mProgressDialog;
    private VideoView mVideoView;
    private SeekBar seekBar;
    private Bundle mBundle;
    private String ScreenName, closedButton, apiURL;
    private String mStringCompleteUrl = "", mStringMidpointUrl = "";
    private String urlName;
    private boolean isFlag50 = false;
    private int halfDuration = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("App");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            ScreenName = mBundle.getString("screen_name");
            closedButton = mBundle.getString("closed_button");
            apiURL = mBundle.getString("api_url");
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        AdsBaseParser mAdsBaseParser = new AdsBaseParser(this, apiURL);
        mAdsBaseParser.parse();

    }

    @Override
    public void getInLine(InLine inLine, int requestId) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();

        String mStringUrl = inLine.getVideo().getMediaFiles().getMediaFile().getURL();
        String mStringStartUrl = inLine.getTrackingEvents().getTracking().getURL();
        mStringCompleteUrl = inLine.getTrackingEvents().getTracking().getCOMPLETE_URL();
        mStringMidpointUrl = inLine.getTrackingEvents().getTracking().getMIDPOINT_URL();
        uriPath = mStringUrl;
        EliteSession.eLog.d(MODULE, "Media url : " + uriPath);
        EliteSession.eLog.d(MODULE, "Media start url : " + mStringStartUrl);
        EliteSession.eLog.d(MODULE, "Media complate url : " + mStringCompleteUrl);
        EliteSession.eLog.d(MODULE, "Media Midpoint url : " + mStringMidpointUrl);

        urlName = URLUtil.guessFileName(uriPath, null, null);
        EliteSession.eLog.d(MODULE, "Media name : " + urlName);
        callApi(mStringStartUrl, 10001);
        new DownloadData().execute();
    }

    public void callApi(String url, int reqCode) {
        JSONObject jsonObject = new JSONObject();
        try {
            ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(this, reqCode);
            task.execute(jsonObject.toString(), url);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error : " + e.getMessage());
        }
    }

    @Override
    public void onConnnectionManagerTaskComplete(String result, int requestId) {
        if (requestId == 10001) {
            EliteSession.eLog.e(MODULE, "start url call " + result + " :: " + requestId);
        } else if (requestId == 10002) {
            EliteSession.eLog.e(MODULE, "complete url call " + result + " :: " + requestId);
            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();
        } else if (requestId == 10003) {
            EliteSession.eLog.e(MODULE, "Midpoint url call " + result + " :: " + requestId);
        }
    }

    public File createCacheDirectory(File baseDir, String newDir) {
        if (baseDir == null) {
            return null;
        }
        File directory = new File(baseDir, newDir);
        directory.mkdirs();
        if (directory.isDirectory()) {
            return directory;
        } else {
            return null;
        }
    }

    public boolean testCacheDirectory(File directory) {
        if (directory == null || !directory.isDirectory()) {
            return false;
        }
        try {
            final byte[] inData = "test".getBytes("UTF-8");
            byte[] outData = new byte[inData.length];
            File testFile = new File(directory, TEST_FILE_NAME);

            FileOutputStream fos = new FileOutputStream(testFile);
            fos.write(inData);
            fos.flush();
            fos.close();

            if (directory.listFiles() == null) {
//                DeviceLog.debug("Failed to list files in directory " + directory.getAbsolutePath());
                return false;
            }

            FileInputStream fis = new FileInputStream(testFile);
            int readCount = fis.read(outData, 0, outData.length);
            fis.close();

            if (!testFile.delete()) {
//                DeviceLog.debug("Failed to delete testfile " + testFile.getAbsoluteFile());
                return false;
            }

            if (readCount != outData.length) {
//                DeviceLog.debug("Read buffer size mismatch");
                return false;
            }

            String result = new String(outData, "UTF-8");

            if (result.equals("test")) {
                return true;
            } else {
//                DeviceLog.debug("Read buffer content mismatch");
                return false;
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while testing cache directory " + e.getMessage());
            return false;
        }
    }

    private void downloadFile() {
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                File externalCache = createCacheDirectory(getExternalCacheDir(), "BSNLWiFi");
                if (testCacheDirectory(externalCache)) {
                    rootFile = externalCache;
                    EliteSession.eLog.d(MODULE, "using external cache directory: " + externalCache.getAbsolutePath());
                }
            } else {
                EliteSession.eLog.d(MODULE, "External media not mounted");
            }

            String path1 = rootFile.getPath() + "/" + urlName;
            File mFile1 = new File(path1);
            if (!mFile1.exists()) {
                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                int count;

                URL url = new URL(uriPath);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                EliteSession.eLog.d(MODULE, "Lenght of file: " + lenghtOfFile);
                EliteSession.eLog.d(MODULE, "file directory name: " + rootFile.getName());
                File mypath = new File(rootFile, urlName);
                EliteSession.eLog.d(MODULE, "file directory name : " + mypath.getPath());
                InputStream input = new BufferedInputStream(url.openStream());
                FileOutputStream output = new FileOutputStream(mypath);

                byte data[] = new byte[1024];

                while ((count = input.read(data)) > 0) {
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            }
        } catch (IOException e) {
            EliteSession.eLog.d(MODULE, "Error...." + e.toString());
        }
    }


    class DownloadData extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            try {
                downloadFile();
                return "";
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(String feed) {
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();

            Uri uri1 = Uri.parse(rootFile.getPath() + "/" + urlName);
            viewCreated(uri1);
        }
    }

    public void viewCreated(Uri mUri) {

        int mainBackgroundColor = Color.parseColor("#e1e4ea");
//        int labelTextColor = Color.parseColor("#FF4500");
//        int messageBackgroundColor = Color.parseColor("#3300FF");
//        int messageTextColor = Color.parseColor("#1b1b1c");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float density = metrics.density;
        int minMarginSize = Math.round(density * 8);
//        int paddingSize = minMarginSize * 2;
//        int maxMarginSize = minMarginSize * 4;

        RelativeLayout.LayoutParams labelLayoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mVideoView = new VideoView(this);
        mVideoView.setId(1);
        labelLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mVideoView.setLayoutParams(labelLayoutParams);
        mVideoView.setVideoURI(mUri);
        mVideoView.requestFocus();
        mVideoView.setZOrderOnTop(true);
        mVideoView.start();


        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {

                seekBar.setMax(mVideoView.getDuration());
                halfDuration = mVideoView.getDuration() / 2;
                seekBar.postDelayed(onEverySecond, 1000);
            }
        });

        seekBar = new SeekBar(this);
        seekBar.setId(3);
        seekBar.setMax(100);
        seekBar.bringToFront();
        seekBar.setProgress(1);
        seekBar.setVisibility(View.VISIBLE);
        seekBar.setBackgroundColor(Color.TRANSPARENT);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        seekBar.setLayoutParams(lp);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
            }

            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                // TODO Auto-generated method stub
                EliteSession.eLog.d(MODULE, "Progress : " + arg1 + " :: " + halfDuration);
                if(halfDuration > 0) {
                    if (arg1 > halfDuration) {
                        if (isFlag50 == false) {
                            EliteSession.eLog.d(MODULE, "Progress flag half is : " + isFlag50 + " :: " + arg1);
                            isFlag50 = true;
                            callApi(mStringMidpointUrl, 10003);
                        }
                    }
                }
                if (arg2) {
                    // this is when actually seekbar has been seeked to a new position
                    mVideoView.seekTo(arg1);
                }
            }
        });

        final ImageView message = new ImageView(this);
        message.setId(2);
        RelativeLayout.LayoutParams messageLayoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        messageLayoutParams.setMargins(20, 20, 20, 20);
        messageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        message.setLayoutParams(messageLayoutParams);
//        message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//        message.setText("Skip");
        message.setBackgroundResource(R.mipmap.ic_closed);
        message.setMinimumHeight(80);
        message.setMinimumWidth(80);
        message.setMaxHeight(80);
        message.setMaxWidth(80);
        message.bringToFront();
//        message.setTextColor(messageTextColor);

        if (closedButton.equals("false")) {
            message.setVisibility(View.INVISIBLE);
        }

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer vmp) {
//                message.setVisibility(View.VISIBLE);
                callApi(mStringCompleteUrl, 10002);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi(mStringCompleteUrl, 10002);
            }
        });

        RelativeLayout main = new RelativeLayout(this);
        RelativeLayout.LayoutParams mainLayoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        main.setLayoutParams(mainLayoutParams);
        mainLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 3);

        main.setBackgroundColor(mainBackgroundColor);
        main.addView(mVideoView);
        main.addView(message);
        main.addView(seekBar);

        setContentView(main);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    private Runnable onEverySecond = new Runnable() {

        @Override
        public void run() {

            if (seekBar != null) {
                seekBar.setProgress(mVideoView.getCurrentPosition());
            }

            if (mVideoView.isPlaying()) {
                seekBar.postDelayed(onEverySecond, 1000);
            }

        }
    };
}