package com.sterlite.dccmappfordealersterlite.Utils;

/**
 * Created by elitecore on 31/10/18.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

/**
 *
 * @author Kirtan.Patel
 *
 */
public class AlertDialogManager {

    public void showAlertDialog(Context context, String title, String message,String type) {
        AlertDialog alertDialog = BaseAlertDialog.createDialog(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
    //    alertDialog.setIcon(getResourceIcon(type));
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
    public void showAlertDialog(final Context context, String title, String message,String type,final Intent intent) {
        AlertDialog alertDialog = BaseAlertDialog.createDialog(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // alertDialog.setIcon(getResourceIcon(type));

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(intent);
            }
        });
        alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(intent);
            }
        });
        alertDialog.show();
    }
    public void showAlertDialog(final Context context, String title, String message,String type,String button1Title,OnClickListener b1OnClickListener ,String button2Title,OnClickListener b2OnClickListener) {
        AlertDialog alertDialog = BaseAlertDialog.createDialog(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
       // alertDialog.setIcon(getResourceIcon(type));
        alertDialog.setButton(button1Title, b1OnClickListener);
        alertDialog.setButton2(button2Title,b2OnClickListener);
        alertDialog.show();
    }
  /*  public int getResourceIcon(String type){
        if (type != null && !type.isEmpty()) {
            if(type.equals(BaseConstants.SUCCESS)){
                return R.drawable.image_success_light;
            }else if(type.equals(BaseConstants.ERROR)){
                return R.drawable.image_error_light;
            }else if(type.equals(BaseConstants.DELETE)){
                return R.drawable.image_delete_light;
            }else if(type.equals(BaseConstants.DOWNLOAD)){
                return R.drawable.image_download_light;
            }
        }
        return 0;
    }*/
}