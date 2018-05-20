package social.application.mainpage;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import social.application.R;
import social.application.Story.story;
import social.application.events.EventsActivity;
import social.application.explore.ExploreActivity;
import social.application.following.FollowingActivity;
import social.application.Story.story;
import social.application.main.MainActivity;
import social.application.personalactivities.PersonalActivitiesFragment;


public class MainPageFragment extends Fragment {

    private ImageButton dmBtn;
    private ImageButton profileBtn;
    private ImageButton liveBtn;
    private ImageButton eventsBtn;
    private ImageButton exploreBtn;
    private ImageButton followingBtn;
    private TextView personalActivitiesBtn;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_main_page, container, false);
        super.onCreate(savedInstanceState);
        initViewElements();
        return rootView;
    }

    public void initViewElements(){
        dmBtn = (ImageButton) rootView.findViewById(R.id.dm_btn);
        profileBtn = (ImageButton) rootView.findViewById(R.id.profile_btn);
        liveBtn = (ImageButton) rootView.findViewById(R.id.live_story_btn);
//        eventsBtn = (ImageButton) rootView.findViewById(R.id.events_btn);
        exploreBtn = (ImageButton) rootView.findViewById(R.id.explore_btn);
        followingBtn = (ImageButton) rootView.findViewById(R.id.follows_btn);
        personalActivitiesBtn = (TextView) rootView.findViewById(R.id.personal_actions_btn);
        initBtnOnClickListeners();
    }

    public void initBtnOnClickListeners() {
        dmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getViewPager().setCurrentItem(0);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getViewPager().setCurrentItem(2);
            }
        });

        liveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificActivity(new story());
            }
        });

//        eventsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startSpecificActivity(new EventsActivity());
//            }
//        });

        exploreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificActivity(new ExploreActivity());
            }
        });

        followingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificActivity(new FollowingActivity());
            }
        });

        personalActivitiesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLiveStoryDialog();
            }
        });
    }

    public void startSpecificActivity(Activity activity){
        Intent intent = new Intent(this.getContext(), activity.getClass());
        startActivity(intent);
    }

    public void showLiveStoryDialog(){
        FragmentManager fm = ((MainActivity)getActivity()).getSupportFragmentManager();
        PersonalActivitiesFragment personalActivitiesFragment = new PersonalActivitiesFragment();
        personalActivitiesFragment.show(fm, "test");
    }


}

