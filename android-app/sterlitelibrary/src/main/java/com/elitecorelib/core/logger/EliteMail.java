package com.elitecorelib.core.logger;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.listner.OnMailTaskCompleteListner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Chirag Parmar on 4/19/2016.
 */
public class EliteMail {

    private static EliteMail instance = null;
    private final String MODULE = "EliteMail";
    private MailSender gMailSender;
    private File zipLocation, zipFolder;
    private Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
    private static String mUserName = "", mPassword = "";
    OnMailTaskCompleteListner mailTaskCompleteListner;
    public String AppName;

    /*
     * Make Singleton class for batter performance */

    public static EliteMail getMailInstance() {

        if (instance == null) {
            instance = new EliteMail();
        }
        return instance;
    }

    /**
     * UserAuthntication
     *
     * @param auth_username
     * @param auth_password
     */
    public void userAuthontication(String auth_username, String auth_password, OnMailTaskCompleteListner mailTaskCompleteListner) {
        mUserName = auth_username;
        mPassword = auth_password;
        this.mailTaskCompleteListner = mailTaskCompleteListner;
    }

    /**
     * Mail property
     *
     * @param to

     */
    public void sendMail(String[] to) {
        int stringId = LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationInfo().labelRes;
        AppName = LibraryApplication.getLibraryApplication().getLibraryContext().getString(stringId);
        String mail_subject="",mail_body = "";
        try {
            mail_subject = AppName.replace(" ", "") + " Logs";
            TelephonyManager tm = (TelephonyManager) LibraryApplication.getLibraryApplication().getLibraryContext().getSystemService(Context.TELEPHONY_SERVICE);
            mail_body = "This logs zip sent by end user IMEI "+tm.getDeviceId()+" for "+ AppName.replace(" ", "");
        }catch (Exception e){
            EliteSession.eLog.e(e.getMessage());
        }

        if (mUserName.compareTo("") != 0 && mPassword.compareTo("") != 0) {
            new Mailasync(to,mUserName,mail_subject,mail_body).execute();
        } else {
            Toast.makeText(context, "User authontication required!!!", Toast.LENGTH_LONG).show();
            EliteSession.eLog.e(MODULE, "Username & Password required.");
            EliteSession.eLog.e(MODULE, "Please invoke userAuthontication method before call sendMail method.");
            EliteSession.eLog.d(MODULE, "Username & Password required.");
            EliteSession.eLog.d(MODULE, "Please invoke userAuthontication method before call sendMail method.");

        }
    }

    private class Mailasync extends AsyncTask<Void, Void, Void> {

        private boolean isSuccess;
        private int count = 0;
        private String[] toAddrress;
        private String fromAddrress, mSubject, mBody;

        private Mailasync(String[] to, String from, String subject, String body) {
            this.toAddrress = to;
            this.fromAddrress = from;
            this.mSubject = subject;
            this.mBody = body;
        }

        @Override
        protected Void doInBackground(Void... params) {

            gMailSender = new MailSender(mUserName, mPassword);
            gMailSender.setTo(toAddrress); // load array to setTo function
            gMailSender.setFrom(fromAddrress); // who is sending the email
            gMailSender.setSubject(mSubject);
            gMailSender.setBody(mBody);

            try {

//                int stringId = LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationInfo().labelRes;
//                String AppName = LibraryApplication.getLibraryApplication().getLibraryContext().getString(stringId);
//                zipFolder = new File(Environment.getExternalStorageDirectory() + File.separator + CoreConstants.LOGGER_FOLDER_NAME + "_" + AppName.replace(" ", "") + File.separator);
                zipFolder = new File(Environment.getExternalStorageDirectory() + File.separator + AppName.replace(" ", "") + File.separator);

                EliteSession.eLog.d(MODULE, "Folder location :"+zipFolder);
                zipLocation = new File(Environment.getExternalStorageDirectory(), AppName.replace(" ", "")+".zip");
                EliteSession.eLog.d(MODULE, "Zip location :"+zipLocation);
                try {
                    if (zipFolder.exists() && zipFolder.isDirectory()) {
                        EliteSession.eLog.d(MODULE, "Zip directory exist");
                        zipFileAtPath(zipFolder.getPath(), zipLocation.getPath());
                        gMailSender.addAttachment(new String[]{zipLocation.getPath()});

                    }
                    EliteSession.eLog.d(MODULE, "attachment added,sending mail");
                    isSuccess = gMailSender.send();
                    EliteSession.eLog.d(MODULE, "Mail Success  : "+isSuccess);

                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // path to file you want to attach
            if (isSuccess) {
                // success
                if (zipLocation.exists())
                    zipLocation.delete();

                EliteSession.eLog.d(MODULE, "Email was sent successfully.");
                mailTaskCompleteListner.onMailTaskComplete(true);
            } else {
                // failure
                mailTaskCompleteListner.onMailTaskComplete(false);
                EliteSession.eLog.d(MODULE, "Email was not sent.");
            }
        }
    }

    public boolean zipFileAtPath(String sourcePath, String toLocation) {
        // ArrayList<String> contentList = new ArrayList<String>();
        final int BUFFER = 2048;


        File sourceFile = new File(sourcePath);
        try {
            EliteSession.eLog.d(MODULE, "File created");
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(toLocation);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            if (sourceFile.isDirectory()) {
                EliteSession.eLog.d(MODULE, "Source file is directory");
                zipSubFolder(out, sourceFile, sourceFile.getParent().length());
            } else {
                byte data[] = new byte[BUFFER];
                EliteSession.eLog.d(MODULE, "Source file is not adirectory");
                FileInputStream fi = new FileInputStream(sourcePath);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(getLastPathComponent(sourcePath));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
 *
 * Zips a subfolder
 *
 */

    private void zipSubFolder(ZipOutputStream out, File folder,
                              int basePathLength) throws IOException, FileNotFoundException {

        final int BUFFER = 2048;

        File[] fileList = folder.listFiles();
        BufferedInputStream origin = null;
        for (File file : fileList) {
            if (file.isDirectory()) {
                EliteSession.eLog.d(MODULE, "file is directory");
                zipSubFolder(out, file, basePathLength);
            } else {
                byte data[] = new byte[BUFFER];
                String unmodifiedFilePath = file.getPath();
                String relativePath = unmodifiedFilePath
                        .substring(basePathLength);
                Log.i("ZIP SUBFOLDER", "Relative Path : " + relativePath);
                FileInputStream fi = new FileInputStream(unmodifiedFilePath);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(relativePath);
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
        }
    }

    /*
     * gets the last path component
     *
     * Example: getLastPathComponent("downloads/example/fileToZip");
     * Result: "fileToZip"
     */
    public String getLastPathComponent(String filePath) {
        String[] segments = filePath.split("/");
        String lastPathComponent = segments[segments.length - 1];
        return lastPathComponent;
    }
}
