package com.dapeng.utils_lib.device;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-14
 * <p>
 * Summary: 剪切板工具类
 */
public class ClipboardUtil {

    /**
     * 复制到剪切板
     */
    public static void copyText(Context context, CharSequence text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
    }

    /**
     * 得到 剪切板复制的文字
     */
    public static CharSequence getTextFromClipboard(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData primaryClip = clipboardManager.getPrimaryClip();
        if (primaryClip != null && primaryClip.getItemCount() > 0) {
            return primaryClip.getItemAt(0).coerceToText(context);
        }
        return "";
    }

}
