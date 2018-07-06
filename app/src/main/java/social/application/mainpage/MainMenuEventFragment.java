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
import social.application.services.CommonDisplayUtil;
import social.application.entity.Event;
import social.application.services.events.EventSupportService;

import static social.application.services.events.EventSupportService.setEventViewBackgroundImage;


public class MainMenuEventFragment extends Fragment {

    public static final String EVENT_KEY = "event";

    private FrameLayout rootView;

    private Event event;

    public MainMenuEventFragment() {
        // Required empty public constructor
    }

    public static MainMenuEventFragment newInstance(Event event) {
        MainMenuEventFragment fragment = new MainMenuEventFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT_KEY, event);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (FrameLayout)inflater.inflate(R.layout.fragment_main_menu_event, container, false);

        event = (Event) getArguments().getSerializable(EVENT_KEY);

        /*Container*/
        final RelativeLayout eventContainer = new RelativeLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( CommonDisplayUtil.getDipValue(170, getContext()),
                                                                                CommonDisplayUtil.getDipValue(240, getContext()));
            layoutParams.setMargins(0, 0, 0, 0);
        eventContainer.setLayoutParams(layoutParams);

        EventSupportService.addEventLayoutToParent(event, eventContainer);
        setEventViewBackgroundImage(event, eventContainer);

        rootView.addView(eventContainer);
        return rootView;
    }

}
