package social.application.mainpage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import social.application.R;
import social.application.services.events.Event;
import social.application.services.events.EventSupportService;


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

        EventSupportService.createEventLayout(event, rootView);

        return rootView;
    }

}
