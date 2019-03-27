package com.sterlite.dccmappfordealersterlite.adapter;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemInvoiceBinding;
import com.sterlite.dccmappfordealersterlite.model.bssrest.BillingDetailData;

/**
 * Created by elitecore on 31/10/18.
 */

public class InvoiceInformationAdapter extends BaseMainAdpter {
    int PERMISSION_CODE = 1002;

    private ArrayList<BillingDetailData> billingDetailData = new ArrayList<>();

    String DOWNLOAD_INVOICE;
    public InvoiceInformationAdapter(Context context) {
        init(context, billingDetailData,null);
    }

    public InvoiceInformationAdapter(Context context, OnRecyclerViewItemClickListener<BillingDetailData> onRecycleItemClickListener) {
        init(context, billingDetailData, onRecycleItemClickListener);
    }

    public void addItems(ArrayList<BillingDetailData> billingDetailData) {
        this.billingDetailData.clear();
        this.billingDetailData.addAll(billingDetailData);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        billingDetailData.remove(position);
        notifyDataSetChanged();
    }

    public BillingDetailData get(int position) {
        return billingDetailData.get(position);
    }

    public ArrayList getAll() {
        return billingDetailData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            ItemInvoiceBinding itemBinding = ItemInvoiceBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final BillingDetailData data = billingDetailData.get(position);

            viewHolder.binding.tvNumber.setText(data.getTransactionId());
            viewHolder.binding.tvInvoiceNumber.setText(data.getBillNumber());
            try {


/*                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(data.getBillDueDate()));
               */ viewHolder.binding.tvDueDate.setText(data.getFormattedBillDueDate());
                //calendar.setTimeInMillis(Long.parseLong(data.getBillDate()));
               // Date invDate = new SimpleDateFormat("dd-MMM-yyyy").parse(data.getFormattedBillDate());

                viewHolder.binding.tvInvoiceDate.setText(data.getFormattedBillDate());
                viewHolder.binding.tvStatus.setText(data.getBillStatus());
                viewHolder.binding.tvTotalAmount.setText(String.valueOf(data.getTotalAmount()));
           /* viewHolder.binding.tvDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("responseDownloadInvoice","on click pdf");

                    downloadInvoice(data.getBillNumber());
                }
            });*/
                viewHolder.binding.tvDownload.setClickable(true);
                viewHolder.binding.tvDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DOWNLOAD_INVOICE="http://"+Constants.BSS_Adapter_IP+":18080/bssadapter/services/ManageBillingAccount/downloadBillForApp?strBillNumber="+data.getBillNumber();

                        Log.e("responseDownloadInvoice", "on click pdf");
                        new MakeNetworkCall().execute(data.getBillNumber());
                        //downloadInvoice(data.getBillNumber());
                    }
                });
            }catch (Exception e){

            }
        }

    }

    public void downloadInvoice(final String billNumber) {
     // String billNumber1="REG0000000010014";
        String DOWNLOAD_INVOICE="http://"+Constants.BSS_Adapter_IP+":18080/bssadapter/services/ManageBillingAccount/downloadBillForApp?strBillNumber="+billNumber;
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(DOWNLOAD_INVOICE);

          //  url = new URL("http://www.google.com");

            urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setRequestMethod("GET");
           // urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setConnectTimeout(100000);
            urlConnection.setReadTimeout(100000);
            urlConnection.setDoInput(true);
            urlConnection.connect();
          // int responseCode = urlConnection.getResponseCode();

          //  if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream is;
            Log.e("bytearray", urlConnection.getInputStream()+" ");

            byte[] bytes = IOUtils.toByteArray(urlConnection.getInputStream());
                Log.e("CatalogClient", bytes+" ");
                responseDownloadInvoice(bytes,billNumber);
         //   }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }

     /*  SterliteDCCM.getDataManager().downloadBill(billNumber, new ApiHelper.OnApiCallback<byte[]>() {
                @Override
                public void onSuccess(byte[] baseResponse) {
                    Log.e("base2",baseResponse+" ");
                    if (baseResponse!=null){
                        responseDownloadInvoice(baseResponse,billNumber);
                    }
                }

                @Override
                public void onFailed(int code, String message) {

                }
            });*/
        /*byte[] bytes=AppApiHelper2.getWebServices(AppApiHelper2.BSS_ADAPTER_BASE_URL).downloadBill(billNumber);
        responseDownloadInvoice(bytes,billNumber);*/

/*        AppApiHelper2.getWebServices(AppApiHelper2.BSS_ADAPTER_BASE_URL).downloadBill(billNumber, new ApiHelper.OnApiCallback<byte[]>() {
            @Override
            public void onSuccess(byte[] resp) {
                Log.e("resp:DIVYA:",resp+"");
                String test = new String(resp);
                Log.e("test:DIVYA:",test+"");

                responseDownloadInvoice(resp,billNumber);


            }

            @Override
            public void onFailed(int code, String message) {
                Log.e("resp:error:",message+"");


            }
        });*/


        }



    public void responseDownloadInvoice(final byte[] respObject,final String billNumber) throws RuntimeException{


            /*if (true) {
                if(true)) {*/
            if (AppUtils.AskAndCheckPermission((Activity) context, AppUtils.PERMISSION_STORAGE, PERMISSION_CODE)) {
                final NotificationManager mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


                final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                mBuilder.setContentTitle(billNumber+".pdf" + " Download").setContentText("Download in progress").setSmallIcon(R.drawable.ic_file_pdf);
                int PROGRESS_MAX = 100;
                int PROGRESS_CURRENT = 0;
                mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (respObject != null) {
                                //mBuilder.setChannelId("channel_1");
                                NotificationChannel mChannel;
                                String CHANNEL_ID = "channel_1";
                               // String str = new String(respObject.getBytes(), "UTF-8");
                             //   byte[] bytes = respObject.getBytes("UTF-8");
                             //   String strBinaryPDFString = respObject;
                                final String fileName = billNumber.trim() + ".pdf";
                                String path = Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator + "MTN" + File.separator;



                                File createfile = new File(path);
                                createfile.mkdirs();
                                File outputFile = new File(createfile, fileName);//creating temporary file in phone Memory
                                new FileOutputStream(outputFile);
                                File filepath = new File(path + fileName);
                                OutputStream pdffos = new FileOutputStream(filepath);
                                pdffos.write(respObject);
                                pdffos.close();
                           //     pdffos.flush();
                                mBuilder.setContentText("Download complete").setProgress(0, 0, false);


                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                String mimeType = "application/pdf";
                                //intent.setDataAndType("", mimeType);
                                //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                Uri apkURI = FileProvider.getUriForFile(
                                        context,
                                        context.getApplicationContext()
                                                .getPackageName() + ".provider", filepath);
                                intent.setDataAndType(apkURI, mimeType);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                                mBuilder.setContentIntent(pendingIntent);

                                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    mChannel = new NotificationChannel(CHANNEL_ID, context.getString(R.string.app_name), importance);
                                    // Configure the notification channel.
                                    mChannel.setDescription("notification");
                                    mNotifyManager.createNotificationChannel(mChannel);
                                }
                                mBuilder.setChannelId(CHANNEL_ID);
                                mNotifyManager.notify(001, mBuilder.build());

                            }
                        }
                        catch (RuntimeException e) {
                            Log.e( "RunTimeException:::" , e.getMessage());

                        }catch (Exception e) {
                            Log.e( "Exception:::" , e.getMessage());

                        }
                    }
                }).start();
                    Log.e("responseDownloadInvoice","start");


                Log.e("responseDownloadInvoice","completed");

            }


    }


    public void responseDownloadInvoice1(final String respObject,final String billNumber) throws RuntimeException{


            /*if (true) {
                if(true)) {*/
        if (AppUtils.AskAndCheckPermission((Activity) context, AppUtils.PERMISSION_STORAGE, PERMISSION_CODE)) {
            final NotificationManager mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle(billNumber+".pdf" + " Download").setContentText("Download in progress").setSmallIcon(R.drawable.ic_file_pdf);
            int PROGRESS_MAX = 100;
            int PROGRESS_CURRENT = 0;
            mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (respObject != null) {
                            //mBuilder.setChannelId("channel_1");
                            NotificationChannel mChannel;
                            String CHANNEL_ID = "channel_1";
                            // String str = new String(respObject.getBytes(), "UTF-8");

                           /*
                            Log.e("Divya::Bytes:",bytes+"");*/
                           // byte[] bytes = Base64.decode(respObject.toString(),Base64.);

                            String strBinaryPDFString = respObject;
                            byte[] bytes = respObject.getBytes("UTF-8");
                            //byte[] bytes=(byte[])respObject;

                            final String fileName = billNumber.trim() + ".pdf";
                            String path = Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator + "MTN" + File.separator;


                            File createfile = new File(path);
                            createfile.mkdirs();
                            File outputFile = new File(createfile, fileName);//creating temporary file in phone Memory
                            new FileOutputStream(outputFile);
                            File filepath = new File(path + fileName);
                            OutputStream out = new FileOutputStream(filepath);
                            out.write(respObject.getBytes());
                            out.flush();
                            out.close();
                            mBuilder.setContentText("Download complete").setProgress(0, 0, false);


                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            String mimeType = "application/pdf";
                            //intent.setDataAndType("", mimeType);
                            //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri apkURI = FileProvider.getUriForFile(
                                    context,
                                    context.getApplicationContext()
                                            .getPackageName() + ".provider", filepath);
                            intent.setDataAndType(apkURI, mimeType);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                            mBuilder.setContentIntent(pendingIntent);

                            int importance = NotificationManager.IMPORTANCE_DEFAULT;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                mChannel = new NotificationChannel(CHANNEL_ID, context.getString(R.string.app_name), importance);
                                // Configure the notification channel.
                                mChannel.setDescription("notification");
                                mNotifyManager.createNotificationChannel(mChannel);
                            }
                            mBuilder.setChannelId(CHANNEL_ID);
                            mNotifyManager.notify(001, mBuilder.build());

                        }
                    }
                    catch (RuntimeException e) {
                        Log.e( "RunTimeException:::" , e.getMessage());

                    }catch (Exception e) {
                        Log.e( "Exception:::" , e.getMessage());

                    }
                }
            }).start();
            Log.e("responseDownloadInvoice","start");


            Log.e("responseDownloadInvoice","completed");

        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemInvoiceBinding binding;

        public ViewHolder(ItemInvoiceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }


   private class MakeNetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg) {

            InputStream is = null;
           // String DOWNLOAD_INVOICE="http://"+Constants.BSS_Adapter_IP+":18080/bssadapter/services/ManageBillingAccount/downloadBillForApp?strBillNumber="+billNumber1;
            String billNumber= arg[0];
            downloadInvoice(arg[0]);
            return null;
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("rESPONSE", "Result: " + result);
        }
    }


}





