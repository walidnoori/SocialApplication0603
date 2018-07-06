package social.application.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import social.application.R;
import social.application.explore.ExploreActivity;
import social.application.following.FollowingActivity;

public class MainMenuExploreFragment extends Fragment {

    private static final Integer NUM_PAGES = 5;

    private View rootView;

    public MainMenuExploreFragment() {
        // Required empty public constructor
    }

    public static MainMenuExploreFragment newInstance() {
        MainMenuExploreFragment fragment = new MainMenuExploreFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_maun_menu_explore, container, false);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), ExploreActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
