package social.application.events;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import social.application.R;
import social.application.events.adapters.CycleViewPagerAdapter;
import social.application.services.events.EventSupportService;


public class EventsActivity extends AppCompatActivity {

    ImageView eventImage01;
    ImageView eventImage02;
    ImageView eventImage03;
    ImageView eventImage04;
    ImageView eventImage05;
    ImageView eventImage06;
    ImageView eventImage07;
    ImageView eventImage08;

    HorizontalInfiniteCycleViewPager yourEventsViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        initViews();
    }

    public void initViews(){
//        eventImage01 = (ImageView) findViewById(R.id.event_img_01);
//        eventImage02 = (ImageView) findViewById(R.id.event_img_02);
//        eventImage03 = (ImageView) findViewById(R.id.event_img_03);
//        eventImage04 = (ImageView) findViewById(R.id.event_img_04);
        eventImage05 = (ImageView) findViewById(R.id.event_img_05);
        eventImage06 = (ImageView) findViewById(R.id.event_img_06);
        eventImage07 = (ImageView) findViewById(R.id.event_img_07);
        eventImage08 = (ImageView) findViewById(R.id.event_img_08);

        int roundInPixel = 100;

//        eventImage01.setImageDrawable( new BitmapDrawable(getResources(), getRoundedCornerBitmap( ((BitmapDrawable)eventImage01.getDrawable()).getBitmap(), roundInPixel )));
//        eventImage02.setImageDrawable( new BitmapDrawable(getResources(), getRoundedCornerBitmap( ((BitmapDrawable)eventImage02.getDrawable()).getBitmap(), roundInPixel )));
//        eventImage03.setImageDrawable( new BitmapDrawable(getResources(), getRoundedCornerBitmap( ((BitmapDrawable)eventImage03.getDrawable()).getBitmap(), roundInPixel )));
//        eventImage04.setImageDrawable( new BitmapDrawable(getResources(), getRoundedCornerBitmap( ((BitmapDrawable)eventImage04.getDrawable()).getBitmap(), roundInPixel )));
        eventImage05.setImageDrawable( new BitmapDrawable(getResources(), getRoundedCornerBitmap( ((BitmapDrawable)eventImage05.getDrawable()).getBitmap(), roundInPixel )));
        eventImage06.setImageDrawable( new BitmapDrawable(getResources(), getRoundedCornerBitmap( ((BitmapDrawable)eventImage06.getDrawable()).getBitmap(), roundInPixel )));
        eventImage07.setImageDrawable( new BitmapDrawable(getResources(), getRoundedCornerBitmap( ((BitmapDrawable)eventImage07.getDrawable()).getBitmap(), roundInPixel )));
        eventImage08.setImageDrawable( new BitmapDrawable(getResources(), getRoundedCornerBitmap( ((BitmapDrawable)eventImage08.getDrawable()).getBitmap(), roundInPixel )));

        initYourEvents();
    }

    public void initYourEvents(){
        yourEventsViewPager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.your_events_horizontal_cycle_view);
        CycleViewPagerAdapter yourEventsAdapter = new CycleViewPagerAdapter(getApplicationContext());
        EventSupportService.addAllEventsToCycleViewPagerAdapter(yourEventsAdapter, getApplicationContext());
        yourEventsViewPager.setAdapter(yourEventsAdapter);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


}
