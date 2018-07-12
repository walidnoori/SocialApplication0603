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

import social.application.entity.LivePicture;
import social.application.services.liveStory.LivePictureSupportService;

/**
 * Created by Chappy on 2018.06.19..
 */

public class LivePictureCycleViewPagerAdapter extends PagerAdapter {

    List<LivePicture> livePictures = new ArrayList<LivePicture>();
    Context context;
    LayoutInflater layoutInflater;

    public LivePictureCycleViewPagerAdapter(Context context){
        this.context =  context;
        layoutInflater = LayoutInflater.from(context);
//        initElements();
    }

    public LivePictureCycleViewPagerAdapter(List<LivePicture> livePictures, Context context){
        this.livePictures = livePictures;
        this.context =  context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return livePictures.size();
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
        LivePicture livePicture = livePictures.get(position);

        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(layoutParams);

        LivePictureSupportService.addLivePictureLayoutToParent(livePicture, relativeLayout);
        container.addView(relativeLayout);
        return relativeLayout;
    }

    public void removeAllItems(){
        livePictures.clear();
    }

    public void addItem(LivePicture livePicture){
        livePictures.add(livePicture);
        this.notifyDataSetChanged();
    }



}


