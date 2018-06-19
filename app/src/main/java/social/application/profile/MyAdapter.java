package social.application.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import social.application.R;

public class MyAdapter extends PagerAdapter {
    List<Integer>  lstImages;
    Context context;
    LayoutInflater layoutInflater;

    public MyAdapter (List<Integer> lstImages, Context context){
        this.lstImages = lstImages;
        this.context =  context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lstImages.size();
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
        View view = layoutInflater.inflate(R.layout.card_item, container, false);
        container.addView(view);
        return view;

    }
}
