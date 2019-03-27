package com.sterlite.dccmappfordealersterlite.activity.Kyc;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import in.mayanknagwanshi.imagepicker.imagePicker.ImagePicker;
import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.activity.kycdetail.KYCDetailActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityKycBinding;
import com.sterlite.dccmappfordealersterlite.model.KYCSuccess;


public class KycActivity extends BaseActivity implements KycContract.View, View.OnClickListener {
    KycContract.Presenter<KycContract.View> presenter;
    ActivityKycBinding binding;

    int PERMISSION_CODE = 1002;
    String imagePath;
    private String id;
    private File f;
    ImagePicker imagePicker;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        init();
        presenter = new KycPresenter<>();
        presenter.onAttach(this);
        Log.e("kyc:userid", DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "")+"H");

    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kyc);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_kyc), true);
        KeyboardVisibilityEvent.registerEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
                    binding.toolbar.appBar.setExpanded(false);

                }
            }
        });
        AppUtils.addTextChangedListener(binding.edtIdNumber, binding.iedtIdNumber);
        AppUtils.addTextFocusChangeListener(binding.edtIdNumber, binding.scrollMainContainer, binding.toolbar.appBar);
        setClickListeners();

    }

    private void setClickListeners() {
        binding.btnAccept.setOnClickListener(this);
        binding.ivSelectedImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAccept:
                if (isValidData()) {
                    id = binding.edtIdNumber.getText().toString();
                   // presenter.uploadImage(imagePath);
                    presenter.validateIdNumber(id,imagePath);
                }
                break;
            case R.id.ivSelectedImage:
                if (AppUtils.AskAndCheckPermission(this, AppUtils.PERMISSION_STORAGE, PERMISSION_CODE)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    /*imagePicker = new ImagePicker();
                    imagePicker.withActivity(this);
                    imagePicker.withCompression(false);
                    imagePicker.chooseFromCamera(true);
                    imagePicker.start();*/
                }
                break;

        }
    }

    private boolean isValidData() {
        if (imagePath == null) {
            AppUtils.showToast(this, getString(R.string.msg_caputure_image));
            return false;
        } else if (binding.edtIdNumber.getText() == null || TextUtils.isEmpty(binding.edtIdNumber.getText().toString().trim())) {
            binding.iedtIdNumber.setError(getString(R.string.val_enter_number));
            binding.scrollMainContainer.scrollTo((int) binding.iedtIdNumber.getX(), 0);
            return false;
        }
        return true;

    }

    @Override
    public void gotoKycSuccessScreen() {
        Intent intent = new Intent(this, KYCDetailActivity.class);
        intent.putExtra(KYCDetailActivity.ARG_KYC_NUMBER, id);
        startActivity(intent);
        finish();
    }


   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            imagePath = null;
            imagePath = imagePicker.getImageFilePath(data);
            Log.e("imagePAth",imagePath+"");

            //imagePicker.getImageFilePath()
            onCaptureImageResult(data);
            Log.e("imagePAth",imagePath+"");

         *//*   imagePath = getPath(this, Uri.parse(data.getDataString()));
            Log.e("imagePAth",imagePath+"");
            Log.e("intent",data+"");
*//*
        *//*    File filepath = new File(data.getDataString());
            Uri apkURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", filepath);
            Log.e("APKURI",apkURI+"");
*//*
           // imagePath=FileProvider.getUriForFile(this,getApplicationContext().getPackageName()+".provider",filepath);
            if (imagePath == null)
            {




                // imagePath=FileProvider.getUriForFile(this,getApplicationContext().getPackageName()+".provider",data.getDataString())

              //  Uri apkURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", data.getData());
            }
             //
            AppUtils.setImageUrl(KycActivity.this, imagePath, binding.ivSelectedImage, null, false, null, true);

        }

    }*/

    private void setScanSucessFul() {
        if (Constants.IS_DUMMY_MODE) {
            KYCSuccess kycSuccess = DummyLists.getKYCSuccessObject(this);
//            binding.tvLblCustomerUidNumber.setText(kycSuccess.getUID());
//            binding.tvTitleCustomerConsent.setText(kycSuccess.getTitle());
//            binding.tvDescription.setText(kycSuccess.getDescription());
//            binding.relDetailsContainer.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onDestroy() {
        hideProgressBar();
        presenter.onDetach();
        super.onDestroy();
    }


    @Override
    public void showInvalidDialog() {
       // CustomAlertDialog.showAlertDialog(this, getString(R.string.app_name), getString(R.string.msg_invalid_number), getString(R.string.btn_ok), null, null, null, true);
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.msg_invalid_number)
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(true)
                .show();
    }


    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            String path = getDataColumn(context, uri, null, null);
            if (path != null) {
                return path;
            } else {
                try {
                    Bitmap bm = null;
                    InputStream is = getContentResolver().openInputStream(uri);
                    File file= new File(getExternalFilesDir(""), "profile.png");
                    BufferedInputStream bis = new BufferedInputStream(is, 8192);
                    bm = BitmapFactory.decodeStream(bis);
                    bm.compress(Bitmap.CompressFormat.PNG, 100, new BufferedOutputStream(new FileOutputStream(file)));
                    binding.ivSelectedImage.setImageBitmap(bm);
                    return file.getPath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }



    private String onCaptureImageResult(Intent data) {
        /*imgPreview.setVisibility(View.VISIBLE);
        tvPreview.setVisibility(View.GONE);
*/
        //ll2.setVisibility(View.GONE);
        //  filePath = data.getData().getPath();
        Bitmap bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
         String filePath = destination.getPath();
        imagePath =filePath;

        Log.e("CaptureImage"," : "+filePath);
        File sourceFile = new File(filePath);
         Long file_size = Long.parseLong(String.valueOf(sourceFile.length()/1024));
        Log.e("CaptureImage SIZE"," : "+file_size);
        AppUtils.setImageUrl(KycActivity.this, filePath, binding.ivSelectedImage, null, false, null, true);

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
            return filePath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;

        //  imgPreview.setImageBitmap(bm);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                imagePath=onCaptureImageResult(data);
              //  AppUtils.setImageUrl(KycActivity.this, imagePath, binding.ivSelectedImage, null, false, null, true);
                AppUtils.setImageUrl(KycActivity.this, imagePath, binding.ivSelectedImage, null, false, null, true);


            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }

}
