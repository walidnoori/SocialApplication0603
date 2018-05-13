package social.application.following;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import social.application.R;
import social.application.dm.DMFragment;
import social.application.main.MainActivity;
import social.application.mainpage.MainPageFragment;
import social.application.profile.ProfileFragment;


public class FollowingActivity extends AppCompatActivity {

    private ViewPager viewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

      //  FragmentPagerAdapter fragmentPagerAdapter = new SwipeFragmentPagerAdapter(getSupportFragmentManager());
      //  viewPager = (ViewPager) findViewById(R.id.followings_pager);
       // viewPager.setAdapter(fragmentPagerAdapter);
       // viewPager.setCurrentItem(0);
    }
/*
    public static class SwipeFragmentPagerAdapter extends FragmentPagerAdapter {
        public SwipeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PostsFragment();
                case 1:
                    return new StoriesFramgent();
                default:
                    return new PostsFragment();
            }
        }
*/
       // @Override
      /*  public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Posts";
                case 1:
                    return "Stories";
                default:
                    return null;
            }
        }
    }
*/
   /* public ViewPager getViewPager() {
        return viewPager;
    }*/

}
