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
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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
        public Builder(Context ctxt){
            this.ctxt = ctxt;
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
            return new SuperDialog(ctxt);
        }
    }

    private Builder mBuilder = null;

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
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_super_dialog, null);
        ImageView iv;
        setContentView(view);
    }
}
