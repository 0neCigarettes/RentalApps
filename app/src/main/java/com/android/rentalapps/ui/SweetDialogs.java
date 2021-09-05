package com.android.rentalapps.ui;

import android.app.Activity;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.android.rentalapps.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetDialogs {
    public interface onDialogClosed{
        void onClosed(String string);
    }

    public static void confirmDialog(Activity context, String Title , String body, String suksesBody, onDialogClosed listener) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(Title);
        dialog.setContentText(body);
        dialog.setCancelText("Tidak");
        dialog.setConfirmText("Ya");
        dialog.showCancelButton(true);
        dialog.setCancelable(false);
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.dismissWithAnimation();
            listener.onClosed(suksesBody);
        });

        dialog.show();

        Button btnC = (Button) dialog.findViewById(R.id.confirm_button);
        Button btnCc = (Button) dialog.findViewById(R.id.cancel_button);

        btnC.setBackground(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dialog_buton));
        btnC.setScaleX((float) 1);
        btnC.setScaleY((float) 0.8);
        btnC.setTextSize((float) 16.5);

        btnCc.setBackground(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dialog_buton));
        btnCc.setScaleX((float) 1);
        btnCc.setScaleY((float) 0.8);
        btnCc.setTextSize((float) 16.5);
    }

    public static void commonSuccess(Activity context, String body, boolean close) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setCancelable(false);
        dialog.setTitleText("Berhasil Memuat permintaan");
        dialog.setContentText(body);

        dialog.setConfirmText("OK");
        dialog.setConfirmClickListener(sweetAlertDialog -> {

            if(close)
                sweetAlertDialog.dismissWithAnimation();
        });
        dialog.show();

        Button btnC = (Button) dialog.findViewById(R.id.confirm_button);

        btnC.setBackground(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dialog_buton));
        btnC.setScaleX((float) 1);
        btnC.setScaleY((float) 0.8);
        btnC.setTextSize((float) 16.5);
    }

    public static void commonWarning(Activity context, String title, String content, boolean close) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setCancelable(true);
        dialog.setTitleText(title);
        dialog.setContentText(content);
        dialog.setConfirmText("OK");
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.dismissWithAnimation();
            if(close)
                context.finish();
        });
        dialog.show();

        Button btnC = (Button) dialog.findViewById(R.id.confirm_button);

        btnC.setBackground(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dialog_buton));
        btnC.setScaleX((float) 1);
        btnC.setScaleY((float) 0.8);
        btnC.setTextSize((float) 16.5);
    }

    public static void commonError(Activity context, String content, boolean close) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog.setCancelable(false);
        dialog.setTitleText("Gagal memuat permintaan");
        dialog.setContentText(content);
        dialog.setConfirmText("OK");
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.dismissWithAnimation();
            if(close)
                context.finish();
        });
        dialog.show();

        Button btnC = (Button) dialog.findViewById(R.id.confirm_button);

        btnC.setBackground(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dialog_buton));
        btnC.setScaleX((float) 1);
        btnC.setScaleY((float) 0.8);
        btnC.setTextSize((float) 16.5);
    }

    public static void commonSuccessWithIntent(Activity context, String body, onDialogClosed listener) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setCancelable(false);
        dialog.setTitleText("Sukses !");
        dialog.setContentText(body);
        dialog.setConfirmText("OK");
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.dismissWithAnimation();
            listener.onClosed("Sukses");
        });
        dialog.show();

        Button btnC = (Button) dialog.findViewById(R.id.confirm_button);

        btnC.setBackground(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dialog_buton));
        btnC.setScaleX((float) 1);
        btnC.setScaleY((float) 0.8);
        btnC.setTextSize((float) 16.5);
    }

    public static void commonWarningWithIntent(Activity context,String title, String body, onDialogClosed listener) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setCancelable(false);
        dialog.setTitleText(title);
        dialog.setContentText(body);
        dialog.setConfirmText("OK");
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.dismissWithAnimation();
            listener.onClosed("Sukses");
        });
        dialog.show();

        Button btnC = (Button) dialog.findViewById(R.id.confirm_button);

        btnC.setBackground(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dialog_buton));
        btnC.setScaleX((float) 1);
        btnC.setScaleY((float) 0.8);
        btnC.setTextSize((float) 16.5);

    }

    public static void endpointError(Activity context) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog.setCancelable(false);
        dialog.setTitleText("Oops!");
        dialog.setContentText("Internet tidak stabil atau server mengalami gangguan, silahkan coba lagi");
        dialog.setConfirmText("OK");
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.dismissWithAnimation();
            context.finishAffinity();
        });
        try {
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }

        Button btnC = (Button) dialog.findViewById(R.id.confirm_button);

        btnC.setBackground(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dialog_buton));
        btnC.setScaleX((float) 1);
        btnC.setScaleY((float) 0.8);
        btnC.setTextSize((float) 16.5);
    }
}
