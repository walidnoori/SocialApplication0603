package social.application.events.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import social.application.comparator.EventDescComparator;
import social.application.entity.Event;
import social.application.services.events.EventSupportService;

/**
 * Created by Chappy on 2018.06.19..
 */

public class EventCycleViewPagerAdapter extends PagerAdapter {

    List<Event> events = new ArrayList<Event>();
    Context context;
    LayoutInflater layoutInflater;

    public EventCycleViewPagerAdapter(Context context){
        this.context =  context;
        layoutInflater = LayoutInflater.from(context);
//        initElements();
    }

    public EventCycleViewPagerAdapter(List<Event> events, Context context){
        this.events = events;
        this.context =  context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Event event = events.get(position);

        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(layoutParams);

        EventSupportService.addEventLayoutToParent(event, relativeLayout);
        container.addView(relativeLayout);
        return relativeLayout;
    }

    public void removeAllItems(){
        events.clear();
    }

    public void addItem(Event event){
        events.add(event);
        Collections.sort(events, new EventDescComparator());
        this.notifyDataSetChanged();
    }

    public void initElements(){
        Event event1 = new Event();
        event1.setTitle("Color Festival");
        event1.setLocation("Debrecen, Piac st. 1");
        event1.setDateTime(new Date().getTime());

        Event event2 = new Event();
        event2.setTitle("Old timer show");
        event2.setLocation("Debrecen, Piac st. 2");
        event2.setDateTime(new Date().getTime());

        Event event3 = new Event();
        event3.setTitle("National holiday");
        event3.setLocation("Debrecen, Piac st. 3");
        event3.setDateTime(new Date().getTime());

        Event event4 = new Event();
        event4.setTitle("Wedding");
        event4.setLocation("Debrecen, Piac st. 4");
        event4.setDateTime(new Date().getTime());

        addItem(event1);
        addItem(event2);
        addItem(event3);
        addItem(event4);
    }


}


