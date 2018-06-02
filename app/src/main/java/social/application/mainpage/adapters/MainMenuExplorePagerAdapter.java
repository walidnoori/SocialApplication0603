package social.application.mainpage.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import social.application.mainpage.MainMenuExploreFragment;
import social.application.mainpage.MainMenuStoryFragment;

/**
 * Created by Chappy on 2018.06.02..
 */

public class MainMenuExplorePagerAdapter extends FragmentStatePagerAdapter {

    public MainMenuExplorePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new MainMenuExploreFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }




}
