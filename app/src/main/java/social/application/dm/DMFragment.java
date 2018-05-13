package social.application.dm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import social.application.R;
import social.application.main.MainActivity;


public class DMFragment extends Fragment {

    ImageButton backBtn;
    View rootView;
    private ImageView messageImageView;


    public DMFragment() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public void initViews(){
        messageImageView = (ImageView)rootView.findViewById(R.id.image_view);
        messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMessageActivity();
            }
        });
    }

    public void startMessageActivity(){
        Intent intent = new Intent(this.getContext(), Message_content.class);
        startActivity(intent);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_dm, container, false);
        init();
        initViews();
        return rootView;


    }







    public void init(){
        backBtn = (ImageButton) rootView.findViewById(R.id.navigate_from_dm_to_main_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getViewPager().setCurrentItem(1);
            }
        });
    }

}
