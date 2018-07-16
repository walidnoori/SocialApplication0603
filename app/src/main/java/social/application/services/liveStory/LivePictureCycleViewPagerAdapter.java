package social.application.services.liveStory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import social.application.entity.LiveContent;

/**
 * Created by Chappy on 2018.06.19..
 */

public class LivePictureCycleViewPagerAdapter extends PagerAdapter {

    List<LiveContent> liveContents = new ArrayList<LiveContent>();
    Context context;
    LayoutInflater layoutInflater;

    public LivePictureCycleViewPagerAdapter(Context context){
        this.context =  context;
        layoutInflater = LayoutInflater.from(context);
//        initElements();
    }

    public LivePictureCycleViewPagerAdapter(List<LiveContent> liveContents, Context context){
        this.liveContents = liveContents;
        this.context =  context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return liveContents.size();
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
        LiveContent liveContent = liveContents.get(position);

        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(layoutParams);

        LiveContentSupportService.addLiveContentLayoutToParent(liveContent, relativeLayout);
        container.addView(relativeLayout);
        return relativeLayout;
    }

    public void removeAllItems(){
        liveContents.clear();
    }

    public void addItem(LiveContent liveContent){
        liveContents.add(liveContent);
        this.notifyDataSetChanged();
    }



}


