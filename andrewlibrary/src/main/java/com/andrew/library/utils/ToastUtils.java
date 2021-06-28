package com.andrew.library.utils;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.andrew.library.base.AndrewApplication;


/**
 * ToastUtils
 */

public class ToastUtils {

    private static Context context = AndrewApplication.instance;
    private static Toast toast;

    public static void show(int resId) {
        show(context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        show(context.getResources().getText(resId), duration);
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /*public static void showDebug(CharSequence text) {
        if (BuildConfig.DEBUG) {
            show(text, Toast.LENGTH_SHORT);
        }
    }*/

    public static void show(CharSequence text, int duration) {
        text = TextUtils.isEmpty(text == null ? "" : text.toString()) ? "请检查您的网络！" : text;
        if (Looper.getMainLooper() == Looper.myLooper()) {
            if (toast == null) {
                toast = Toast.makeText(context, text, duration);
            } else {
                toast.setText(text);
            }
            toast.show();
        } else {
            Looper.prepare();
            toast = Toast.makeText(context, text, duration);
            toast.show();
            Looper.loop();
            Looper mLooper = Looper.myLooper();
            if (mLooper != null) {
                mLooper.quit();
            }
        }

    }

    public static void show(int resId, Object... args) {
        show(String.format(context.getResources().getString(resId), args),
                Toast.LENGTH_SHORT);
    }

    public static void show(String format, Object... args) {
        if (TextUtils.isEmpty(format)){
            return;
        }
        show(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration, Object... args) {
        show(String.format(context.getResources().getString(resId), args),
                duration);
    }

    public static void show(String format, int duration, Object... args) {
        if (TextUtils.isEmpty(format)){
            return;
        }
        show(String.format(format, args), duration);
    }
}
