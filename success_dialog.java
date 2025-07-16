package com.cornerstone.buildingbrands.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.cornerstone.buildingbrands.R;

public class SuccessDialogHelper {
    
    public enum DialogType {
        SUCCESS_GREEN,
        SUCCESS_RED,
        SCAN_RESULT
    }
    
    public static void showSuccessDialog(Context context, String title, String message, 
                                       DialogType type, final DialogCallback callback) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        
        // Get views
        View container = dialog.findViewById(R.id.dialog_container);
        TextView tvTitle = dialog.findViewById(R.id.tv_dialog_title);
        TextView tvMessage = dialog.findViewById(R.id.tv_dialog_message);
        
        // Set content
        tvTitle.setText(title);
        tvMessage.setText(message);
        
        // Set background color based on type
        switch (type) {
            case SUCCESS_GREEN:
                container.setBackgroundResource(R.drawable.dialog_background_green);
                break;
            case SUCCESS_RED:
                container.setBackgroundResource(R.drawable.dialog_background_red);
                break;
            case SCAN_RESULT:
                container.setBackgroundResource(R.drawable.dialog_background_white);
                break;
        }
        
        // Animate dialog
        Animation scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        container.startAnimation(scaleUp);
        
        dialog.show();
        
        // Auto dismiss after 2 seconds
        new Handler().postDelayed(() -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onDismiss();
                }
            }
        }, 2000);
    }
    
    public static void showPasswordResetSuccess(Context context, final DialogCallback callback) {
        showSuccessDialog(context, 
            "Success!", 
            "A password request change has been sent to your email!",
            DialogType.SUCCESS_GREEN,
            callback);
    }
    
    public static void showPasswordChangedSuccess(Context context, final DialogCallback callback) {
        showSuccessDialog(context,
            "Success!",
            "Your password has been changed!",
            DialogType.SUCCESS_GREEN,
            callback);
    }
    
    public static void showApprovalEmailSuccess(Context context, final DialogCallback callback) {
        showSuccessDialog(context,
            "Success!",
            "You will be emailed once decision for approval is made.",
            DialogType.SUCCESS_GREEN,
            callback);
    }
    
    public static void showRequestSentSuccess(Context context, final DialogCallback callback) {
        showSuccessDialog(context,
            "",
            "Request was sent!",
            DialogType.SUCCESS_RED,
            callback);
    }
    
    public static void showScanResultPopulated(Context context, final DialogCallback callback) {
        showSuccessDialog(context,
            "",
            "Scan Result Populated",
            DialogType.SCAN_RESULT,
            callback);
    }
    
    public interface DialogCallback {
        void onDismiss();
    }
}