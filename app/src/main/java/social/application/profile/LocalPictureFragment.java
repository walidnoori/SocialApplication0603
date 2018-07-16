package social.application.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import social.application.R;
import social.application.entity.LocalPicture;
import social.application.services.CommonDisplayUtil;
import social.application.services.localPhoto.LocalPictureSupportService;

public class LocalPictureFragment extends Fragment {

    public static final String LOCAL_PICTURE_KEY = "localPicture";

    private FrameLayout rootView;

    private LocalPicture localPicture;

    public LocalPictureFragment() {
        // Required empty public constructor
    }
    public static LocalPictureFragment newInstance(LocalPicture localPicture) {
        LocalPictureFragment fragment = new LocalPictureFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LOCAL_PICTURE_KEY, localPicture);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (FrameLayout)inflater.inflate(R.layout.local_picture_fragment, container, false);

        localPicture = (LocalPicture) getArguments().getSerializable(LOCAL_PICTURE_KEY);

        /*Container*/
        final RelativeLayout localPictureContainer = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams( CommonDisplayUtil.getDipValue(170, getContext()),
                CommonDisplayUtil.getDipValue(240, getContext()));
        layoutParams.setMargins(0, 0, 0, 0);
        localPictureContainer.setLayoutParams(layoutParams);

        LocalPictureSupportService.addLocalPictureLayoutToParent(localPicture, localPictureContainer);

        rootView.addView(localPictureContainer);
        return rootView;

    }
}
