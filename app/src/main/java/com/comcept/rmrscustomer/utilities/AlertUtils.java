package com.comcept.rmrscustomer.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Utilities functions for android alert and snack bar showing
 */
public class AlertUtils {

    private static final String TAG = "AlertUtils";

    private static String cActionOK = "OK";
    private static String cActionCancel = "Cancel";
    private static String cUpdate = "Update";

    public static void initActionText(@NonNull String ok, @NonNull String cancel) {
        cActionOK = ok;
        cActionCancel = cancel;
    }

    public static void showConfirmDialog(Context context, String message, DialogInterface.OnClickListener positiveListener) {
        showConfirmDialog(context, null, message, positiveListener, null);
    }

    public static void showConfirmDialog(Context context, String title, String message, DialogInterface.OnClickListener positiveListener) {
        showConfirmDialog(context, title, message, positiveListener, null);
    }

    public static void showConfirmDialog(Context context, String message, DialogInterface.OnClickListener positiveListener,
                                         DialogInterface.OnClickListener negativeListener) {
        showConfirmDialog(context, null, message, positiveListener, negativeListener);
    }

    public static void showConfirmDialog(Context context, @Nullable String title, String message,
                                         DialogInterface.OnClickListener positiveListener,
                                         @Nullable DialogInterface.OnClickListener negativeListener) {
        showAlertDialog(context, title, message, cActionOK, cActionCancel,
                positiveListener, negativeListener);
    }

    public static void showAlertDialog(Context context, String message) {
        showAlertDialog(context, null, message, null);
    }

    public static void showAlertDialog(Context context, String title, String message) {
        showAlertDialog(context, title, message, null);
    }

    public static void showAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        showAlertDialog(context, title, message, cActionOK, listener);
    }

    public static void showAlertDialogUpdate(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        showAlertDialog(context, title, message, cUpdate, listener);
    }

    public static void showAlertDialog(Context context, String message, DialogInterface.OnClickListener listener) {
        createAlertDialogBuilderInternal(context, message, cActionOK, listener)
                .show();
    }

    public static void showAlertDialog(Context context, @Nullable String title, String message,
                                       String positive, DialogInterface.OnClickListener listener) {

        AlertDialog.Builder builder = createAlertDialogBuilderInternal(context, message, positive, listener);
        if (title != null) {
            builder.setTitle(title);
        }
        builder.show();
    }

    public static void showAlertDialog(Context context, @Nullable String title, @NonNull String message,
                                       @NonNull String positive,
                                       @Nullable String negative,
                                       DialogInterface.OnClickListener positiveListener,
                                       DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = createAlertDialogBuilderInternal(context, message, positive, positiveListener);
        if (title != null) {
            builder.setTitle(title);
        }
        if (negative != null) {
            builder.setNegativeButton(negative, negativeListener);
        }
        builder.show();
    }

    private static AlertDialog.Builder createAlertDialogBuilderInternal(Context context,
                                                                        @NonNull String message,
                                                                        @NonNull String positiveButton,
                                                                        DialogInterface.OnClickListener positiveListener) {
        return new AlertDialog.Builder(context).setMessage(message)
                .setPositiveButton(positiveButton, positiveListener)
                .setCancelable(false);
    }

    public static void showToastLongMessage(Context context, String message) {
        if (context != null)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToastShortMessage(Context context, String message) {
        if (context != null)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }





    public static String capitalizeString(String str) {
        String retStr = str;
        try { // We can face index out of bound exception if the string is null
            retStr = str.substring(1, 2).toUpperCase() + str.substring(2);
        }catch (Exception e){}
        return retStr;
    }
}