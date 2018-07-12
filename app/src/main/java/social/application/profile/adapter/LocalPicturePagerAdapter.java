package social.application.profile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import social.application.entity.Event;
import social.application.entity.LivePicture;
import social.application.entity.LocalPicture;
import social.application.mainpage.MainMenuEventFragment;
import social.application.mainpage.MainMenuStoryFragment;
import social.application.profile.LocalPictureFragment;

/**
 * Created by Chappy on 2018.06.02..
 */

public class LocalPicturePagerAdapter extends FragmentStatePagerAdapter {

    public LocalPicturePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    List<LocalPictureFragment> localPictureFragments = new ArrayList<LocalPictureFragment>();

    @Override
    public Fragment getItem(int position) {
        return localPictureFragments.get(position);
    }

    @Override
    public int getCount() {
        return localPictureFragments.size();
    }

    public void removeAllItems(){
        localPictureFragments.clear();
    }

    public void addStoryFragment(LocalPicture localPicture){
        localPictureFragments.add(LocalPictureFragment.newInstance(localPicture));
        this.notifyDataSetChanged();
    }

}
