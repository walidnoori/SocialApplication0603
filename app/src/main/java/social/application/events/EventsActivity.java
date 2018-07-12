package social.application.events;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import social.application.R;
import social.application.events.adapters.EventCycleViewPagerAdapter;
import social.application.services.events.EventSupportService;


public class EventsActivity extends AppCompatActivity {

    HorizontalInfiniteCycleViewPager yourEventsViewPager;

    HorizontalInfiniteCycleViewPager trendingEventsViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        initViews();
    }

    public void initViews(){
        int roundInPixel = 100;
        initYourEvents();
        initTrendingEvents();
    }

    public void initYourEvents(){
        yourEventsViewPager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.your_events_horizontal_cycle_view);
        EventCycleViewPagerAdapter yourEventsAdapter = new EventCycleViewPagerAdapter(getApplicationContext());
        EventSupportService.addAllUserEventsToCycleViewPagerAdapter(yourEventsAdapter, getApplicationContext());
        yourEventsViewPager.setAdapter(yourEventsAdapter);
    }

    public void initTrendingEvents(){
        HorizontalInfiniteCycleViewPager trendingEventsViewPager;
        yourEventsViewPager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.trending_events_horizontal_cycle_view);
        EventCycleViewPagerAdapter trendingEventsAdapter = new EventCycleViewPagerAdapter(getApplicationContext());
        EventSupportService.addTrendingEventsToCycleViewPagerAdapter(trendingEventsAdapter, getApplicationContext());
        yourEventsViewPager.setAdapter(trendingEventsAdapter);
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
