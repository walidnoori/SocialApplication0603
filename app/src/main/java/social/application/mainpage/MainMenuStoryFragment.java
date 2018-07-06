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
import social.application.entity.LivePicture;
import social.application.services.LiveStory.LivePictureSupportService;
import social.application.services.CommonDisplayUtil;

public class MainMenuStoryFragment extends Fragment {

    public static final String STORY_KEY = "story";

    private FrameLayout rootView;

    private LivePicture livePicture;

    public MainMenuStoryFragment() {
        // Required empty public constructor
    }
    public static MainMenuStoryFragment newInstance(LivePicture livePicture) {
        MainMenuStoryFragment fragment = new MainMenuStoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(STORY_KEY, livePicture);
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

        livePicture = (LivePicture) getArguments().getSerializable(STORY_KEY);

        /*Container*/
        final RelativeLayout livePictureContainer = new RelativeLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( CommonDisplayUtil.getDipValue(170, getContext()),
                                                                                CommonDisplayUtil.getDipValue(240, getContext()));
        layoutParams.setMargins(0, 0, 0, 0);
        livePictureContainer.setLayoutParams(layoutParams);

        LivePictureSupportService.addLivePictureLayoutToParent(livePicture, livePictureContainer);
        LivePictureSupportService.setLivePictureViewBackgroundImage(livePicture, livePictureContainer);

        rootView.addView(livePictureContainer);
        return rootView;

    }
}
