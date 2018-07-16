package social.application.mainpage;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import social.application.R;
import social.application.Story.story;
import social.application.explore.ExploreActivity;
import social.application.main.MainActivity;
import social.application.mainpage.adapters.MainMenuEventsPagerAdapter;
import social.application.mainpage.adapters.MainMenuExplorePagerAdapter;
import social.application.mainpage.adapters.MainMenuFollowingPagerAdapter;
import social.application.mainpage.adapters.MainMenuStoryPagerAdapter;
import social.application.personalactivities.PersonalActivitiesFragment;
import social.application.services.liveStory.LiveContentSupportService;
import social.application.services.events.EventSupportService;


public class MainPageFragment extends Fragment {

    private ImageButton dmBtn;

    private ImageButton profileBtn;

    private TextView personalActivitiesBtn;

    private View rootView;

    private ViewPager storyViewPager;

    private PagerAdapter storyViewPagerAdapter;

    private ViewPager eventViewPager;

    private PagerAdapter eventViewPagerAdapter;

    private ViewPager exploreViewPager;

    private PagerAdapter exploreViewPagerAdapter;

    private ViewPager followingsViewPager;

    private PagerAdapter followingsViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_page, container, false);
        super.onCreate(savedInstanceState);


        init();
        return rootView;
    }

    public void init(){
        initEvents();
        initStories();
        initExplore();
        initFollowings();
        initViewElements();
        initBtnOnClickListeners();
    }

    public void initEvents(){
        eventViewPagerAdapter = new MainMenuEventsPagerAdapter(getActivity().getSupportFragmentManager());
        eventViewPager = (ViewPager)rootView.findViewById(R.id.main_page_events_container);
        eventViewPager.setAdapter(eventViewPagerAdapter);
        EventSupportService.addAllEventsToPagerAdapter((MainMenuEventsPagerAdapter) eventViewPagerAdapter, getContext());
    }

    public void initStories(){
        storyViewPagerAdapter = new MainMenuStoryPagerAdapter(getActivity().getSupportFragmentManager());
        storyViewPager = (ViewPager)rootView.findViewById(R.id.main_live_story_view_pager);
        storyViewPager.setAdapter(storyViewPagerAdapter);
        LiveContentSupportService.addAllLiveContentsToPagerAdapter((MainMenuStoryPagerAdapter) storyViewPagerAdapter, getContext());
    }

    public void initExplore(){
        exploreViewPager = (ViewPager)rootView.findViewById(R.id.main_live_explore_view_pager);
        exploreViewPagerAdapter = new MainMenuExplorePagerAdapter(getActivity().getSupportFragmentManager());
        exploreViewPager.setAdapter(exploreViewPagerAdapter);
    }

    public void initFollowings(){
        followingsViewPager = (ViewPager)rootView.findViewById(R.id.main_live_followings_view_pager);
        followingsViewPagerAdapter = new MainMenuFollowingPagerAdapter(getActivity().getSupportFragmentManager());
        followingsViewPager.setAdapter(followingsViewPagerAdapter);
    }

    public void initViewElements() {
        dmBtn = (ImageButton) rootView.findViewById(R.id.dm_btn);
        profileBtn = (ImageButton) rootView.findViewById(R.id.profile_btn);
        personalActivitiesBtn = (TextView) rootView.findViewById(R.id.personal_actions_btn);
    }

    public void initBtnOnClickListeners() {
        dmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getViewPager().setCurrentItem(0);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getViewPager().setCurrentItem(2);
            }
        });

        storyViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificActivity(new story());
            }
        });

        exploreViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificActivity(new ExploreActivity());
            }
        });

//
        personalActivitiesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLiveStoryDialog();
            }
        });
    }

    public void startSpecificActivity(Activity activity) {
        Intent intent = new Intent(this.getContext(), activity.getClass());
        startActivity(intent);
    }

    public void showLiveStoryDialog() {
        FragmentManager fm = ((MainActivity) getActivity()).getSupportFragmentManager();
        PersonalActivitiesFragment personalActivitiesFragment = new PersonalActivitiesFragment();
        personalActivitiesFragment.show(fm, "test");
    }


}

