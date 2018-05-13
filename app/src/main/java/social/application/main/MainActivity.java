package social.application.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import social.application.R;
import social.application.dm.DMFragment;
import social.application.mainpage.MainPageFragment;
import social.application.profile.ProfileFragment;


public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_test);

        FragmentPagerAdapter fragmentPagerAdapter = new SwipeFragmentPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setCurrentItem(1);
    }

    public static class SwipeFragmentPagerAdapter extends FragmentPagerAdapter {
        public SwipeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DMFragment();
                case 1:
                    return new MainPageFragment();
                default:
                    return new ProfileFragment();
            }
        }
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
