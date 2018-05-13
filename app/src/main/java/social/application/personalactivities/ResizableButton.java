package social.application.personalactivities;

import android.content.Context;
import android.util.AttributeSet;

public class ResizableButton extends android.support.v7.widget.AppCompatButton {

    public ResizableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

}
