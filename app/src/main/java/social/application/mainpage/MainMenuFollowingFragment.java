package social.application.mainpage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import social.application.R;
import social.application.following.FollowingActivity;

public class MainMenuFollowingFragment extends Fragment {

    private View rootView;

    public MainMenuFollowingFragment() {
        // Required empty public constructor
    }

    public static MainMenuFollowingFragment newInstance() {
        MainMenuFollowingFragment fragment = new MainMenuFollowingFragment();
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
        rootView = inflater.inflate(R.layout.fragment_main_menu_following, container, false);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), FollowingActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
