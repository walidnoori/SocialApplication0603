package social.application.services;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Chappy on 2018.06.19..
 */

public class CommonDisplayUtil {

    public static int getDipValue(int value, Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }


}
