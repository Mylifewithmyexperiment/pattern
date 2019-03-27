package com.sterlite.dccmappfordealersterlite.Utils;

/**
 * Created by elitecore on 31/10/18.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.WindowManager;

/**
 *
 * @author Kirtan.Patel
 *
 */
public class BaseAlertDialog {
    public static AlertDialog createDialog(Context context){
        return createDialog(context,"OK","Cancel");
    }
    public static AlertDialog createDialog(Context context,String positiveButtonName,String negativeButtonString){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setPositiveButton(positiveButtonName, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        alertDialogBuilder.setNegativeButton(negativeButtonString, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //alertDialog.getWindow().setLayout((int)(BaseUtility.getScreenWidth()*0.9), ViewGroup.LayoutParams.WRAP_CONTENT);
        return alertDialog;
    }
    public static AlertDialog createDialog(Context context,String title,String positiveButtonName,String negativeButtonString){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setPositiveButton(positiveButtonName, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        alertDialogBuilder.setNegativeButton(negativeButtonString, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
       // alertDialog.getWindow().setLayout((int)(BaseUtility.getScreenWidth()*0.9), ViewGroup.LayoutParams.WRAP_CONTENT);
        return alertDialog;
    }
    public static AlertDialog createDialog(Context context,String title,String message,Integer icon,String positiveButtonName,String negativeButtonString){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setPositiveButton(positiveButtonName, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        alertDialogBuilder.setNegativeButton(negativeButtonString, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setIcon(icon);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
     //   alertDialog.getWindow().setLayout((int)(BaseUtility.getScreenWidth()*0.9), ViewGroup.LayoutParams.WRAP_CONTENT);
        return alertDialog;
    }

}
