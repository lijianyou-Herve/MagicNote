package example.herve.com.magicnote.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import example.herve.com.magicnote.R;

/**
 * Created           :Herve on 2017/2/22.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2017/2/22
 * @ projectName     :MagicNote
 * @ version
 */
public class DialogUtils {

    /**
     * 我知道了
     *
     * @param ctx
     * @param message
     */
    public static void showDialog(final Context ctx, final String message, final DialogInterface.OnClickListener listener) {
        showDialog(ctx, ctx.getString(R.string.prompt), message, ctx.getString(R.string.i_know), null, listener, false);
    }

    public static void showDialog(final Context ctx, final String titleName, final String message, final String confirmButtonText, final String cancleButtonText, final DialogInterface.OnClickListener listener, boolean isShowCancelButton) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
            dialog.setTitle(titleName).setMessage(message).setPositiveButton(confirmButtonText, listener);
            if (isShowCancelButton) {
                dialog.setNegativeButton(cancleButtonText, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            Log.e("DialogUtils", e.toString());
        }
    }
}
