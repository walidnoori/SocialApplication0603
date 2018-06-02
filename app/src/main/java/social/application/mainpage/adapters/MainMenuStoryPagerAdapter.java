package social.application.mainpage.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import social.application.mainpage.MainMenuEventFragment;
import social.application.mainpage.MainMenuStoryFragment;
import social.application.services.events.Event;

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
        return new MainMenuStoryFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    public void removeAllItems(){
        storyFragments.clear();
    }

}
