package social.application.mainpage;

import android.content.Context;
import android.util.AttributeSet;

public class ResizableImageButton extends android.support.v7.widget.AppCompatImageButton {

    public ResizableImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

}
