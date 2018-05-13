package social.application.personalactivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import social.application.R;


public class PersonalActivitiesFragment extends DialogFragment {

    View dialogLayout;
    ResizableButton liveStoryBtn;
    ResizableButton eventBtn;
    ResizableButton textBtn;
    ResizableButton photoVideoBtn;

    public PersonalActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogLayout = inflater.inflate(R.layout.fragment_personal_activities, null);

        LinearLayout linearLayout = dialogLayout.findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("Personal Activities");
        builder.setView(dialogLayout);

        initViewElements();

        return builder.create();
    }

    public void initViewElements(){
        liveStoryBtn = dialogLayout.findViewById(R.id.personal_live_story_btn);
        eventBtn = dialogLayout.findViewById(R.id.personal_event_btn);
        textBtn = dialogLayout.findViewById(R.id.personal_text_btn);
        photoVideoBtn = dialogLayout.findViewById(R.id.personal_photo_video_btn);
        initButtonOnClickListeners();
    }

    public void initButtonOnClickListeners(){
        liveStoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificActivity(new PersonalLiveStoryActivity());
            }
        });

        eventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificActivity(new PersonalEventActivity());
            }
        });

        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificActivity(new PersonalTextActivity());
            }
        });

        photoVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificActivity(new PersonalPhotoVideoActivity());
            }
        });
    }

    public void startSpecificActivity(Activity activity){
        Intent intent = new Intent(this.getContext(), activity.getClass());
        startActivity(intent);
    }


}
