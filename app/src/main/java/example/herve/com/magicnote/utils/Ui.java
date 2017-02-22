package example.herve.com.magicnote.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created           :Herve on 2017/2/22.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2017/2/22
 * @ projectName     :MagicNote
 * @ version
 */
public class Ui {

    public static int dp2px(Context mContext,float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }
}
