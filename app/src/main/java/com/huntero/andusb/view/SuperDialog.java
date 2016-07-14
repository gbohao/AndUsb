package com.huntero.andusb.view;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huntero.andusb.R;

/**
 * TODO: document your custom view class.
 */
public class SuperDialog extends Dialog {

    public static class Builder {
        private Context ctxt;
        private String msg;
        private Drawable icon;
        private int iconId;
        private int themeResId;

        public Builder(Context ctxt){
            this.ctxt = ctxt;
        }
        public Builder(Context ctxt, int themeResId){
            this.ctxt = ctxt;
            this.themeResId = themeResId;
        }

        public Builder message(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder message(int msgId) {
            this.msg = this.ctxt.getString(msgId);
            return this;
        }

        public Builder icon(int resId) {
            iconId = resId;
            return this;
        }

        public Builder icon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        public SuperDialog create() {
            SuperDialog dialog = null;
            if (themeResId != 0) {
                dialog = new SuperDialog(ctxt, themeResId);
            } else {
                dialog = new SuperDialog(ctxt);
            }

            if (!TextUtils.isEmpty(this.msg)) {
                dialog.setMessage(this.msg);
            }
            if (iconId != 0) {
                dialog.setIcon(iconId);
            }
            if (icon != null) {
                dialog.setIcon(icon);
            }
            return dialog;
        }
    }

    ImageView iv;
    TextView tv;
    Handler mHandler;

    protected SuperDialog(Context context) {
        this(context, 0);
    }

    protected SuperDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.setCancelable(true);
        this.setOnCancelListener(null);
        create(context);
    }

    protected SuperDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        create(context);
    }

    private void create(Context context) {
        mHandler = new Handler();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_super_dialog, null);
        iv = (ImageView) view.findViewById(R.id.dialog_icon);
        tv = (TextView) view.findViewById(R.id.dialog_msg);

        setContentView(view);
    }

    public void setMessage(String msg) {
        if (tv != null) {
            tv.setText(msg);
        }
    }

    public void setMessage(int resId) {
        if (tv != null) {
            tv.setText(resId);
        }
    }

    public void setIcon(int resId) {
        if (iv != null) {
            iv.setImageResource(resId);
        }
    }

    public void setIcon(Drawable icon) {
        if (iv != null) {
            iv.setImageDrawable(icon);
        }
    }

    public void dismissAfter(final long millis) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("SuperDialog", "run: dismiss after " + millis);
                SuperDialog.this.dismiss();
            }
        }, millis);
    }
}
