package social.application.mainpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import social.application.R;
import social.application.entity.LiveContent;
import social.application.services.liveStory.LiveContentSupportService;

public class MainMenuStoryFragment extends Fragment {

    public static final String STORY_KEY = "story";

    private FrameLayout rootView;

    private LiveContent liveContent;

    public MainMenuStoryFragment() {
        // Required empty public constructor
    }
    public static MainMenuStoryFragment newInstance(LiveContent liveContent) {
        MainMenuStoryFragment fragment = new MainMenuStoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(STORY_KEY, liveContent);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (FrameLayout)inflater.inflate(R.layout.fragment_main_menu_story, container, false);

        liveContent = (LiveContent) getArguments().getSerializable(STORY_KEY);

        /*Container*/
        final RelativeLayout liveContentContainer = new RelativeLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                LinearLayout.LayoutParams.MATCH_PARENT );
        layoutParams.setMargins(0, 0, 0, 0);
        liveContentContainer.setLayoutParams(layoutParams);

        LiveContentSupportService.addLiveContentLayoutToParent(liveContent, liveContentContainer);
        rootView.addView(liveContentContainer);
        return rootView;

    }
}
