package social.application.mainpage.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import social.application.mainpage.MainMenuEventFragment;
import social.application.entity.Event;

/**
 * Created by Chappy on 2018.06.02..
 */

public class MainMenuEventsPagerAdapter extends FragmentStatePagerAdapter {
    public MainMenuEventsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    List<MainMenuEventFragment> eventFragments = new ArrayList<MainMenuEventFragment>();

    @Override
    public Fragment getItem(int position) {
        return eventFragments.get(position);
    }

    @Override
    public int getCount() {
        return eventFragments.size();
    }

    public void addEventFragment(Event event){
        eventFragments.add(MainMenuEventFragment.newInstance(event));
        this.notifyDataSetChanged();
    }

    public void removeAllItems(){
        eventFragments.clear();
    }
}

