package social.application.mainpage.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import social.application.mainpage.MainMenuFollowingFragment;
import social.application.mainpage.MainMenuStoryFragment;

/**
 * Created by Chappy on 2018.06.02..
 */

public class MainMenuFollowingPagerAdapter extends FragmentStatePagerAdapter {

    public MainMenuFollowingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new MainMenuFollowingFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

}
