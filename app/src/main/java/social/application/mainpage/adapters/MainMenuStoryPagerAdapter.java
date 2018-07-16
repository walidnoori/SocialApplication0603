package social.application.mainpage.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import social.application.entity.LiveContent;
import social.application.mainpage.MainMenuStoryFragment;

/**
 * Created by Chappy on 2018.06.02..
 */

public class MainMenuStoryPagerAdapter extends FragmentStatePagerAdapter {

    public MainMenuStoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    List<MainMenuStoryFragment> storyFragments = new ArrayList<MainMenuStoryFragment>();

    @Override
    public Fragment getItem(int position) {
        return storyFragments.get(position);
    }

    @Override
    public int getCount() {
        return storyFragments.size();
    }

    public void removeAllItems(){
        storyFragments.clear();
    }

    public void addStoryFragment(LiveContent liveContent){
        storyFragments.add(MainMenuStoryFragment.newInstance(liveContent));
        this.notifyDataSetChanged();
    }

}
