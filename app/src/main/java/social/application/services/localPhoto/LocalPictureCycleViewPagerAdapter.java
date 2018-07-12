package social.application.services.localPhoto;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import social.application.entity.Event;
import social.application.entity.LocalPicture;
import social.application.services.events.EventSupportService;

/**
 * Created by Chappy on 2018.06.19..
 */

public class LocalPictureCycleViewPagerAdapter extends PagerAdapter {

    List<LocalPicture> localPictures = new ArrayList<LocalPicture>();
    Context context;
    LayoutInflater layoutInflater;

    public LocalPictureCycleViewPagerAdapter(Context context){
        this.context =  context;
        layoutInflater = LayoutInflater.from(context);
//        initElements();
    }

    public LocalPictureCycleViewPagerAdapter(List<LocalPicture> localPictures, Context context){
        this.localPictures = localPictures;
        this.context =  context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return localPictures.size();
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
        LocalPicture localPicture = localPictures.get(position);

        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(layoutParams);

        LocalPictureSupportService.addLocalPictureLayoutToParent(localPicture, relativeLayout);

        container.addView(relativeLayout);
        return relativeLayout;
    }

    public void removeAllItems(){
        localPictures.clear();
    }

    public void addItem(LocalPicture localPicture){
        localPictures.add(localPicture);
        this.notifyDataSetChanged();
    }
}


