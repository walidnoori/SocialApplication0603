package social.application.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

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
                case 2:
                    return new ProfileFragment();
                default:
                    return new MainPageFragment();
            }
        }
    }

    public ViewPager getViewPager() {
        return viewPager;
    }


//    public static class InfoDialog extends DialogFragment {
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the Builder class for convenient dialog construction
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage("This is a test text")
//                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // FIRE ZE MISSILES!
//                        }
//                    });
//            return builder.create();
//        }
//    }
}
