package social.application.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import social.application.R;
import social.application.login.Login;
import social.application.login.Login;
import social.application.main.MainActivity;
import social.application.signup.SignUp;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    private static final int PICT_IMAGE_REQUEST = 2;

    private CircleImageView mChooseImage;
    private Uri imgUri;

    List<Integer> lstImages = new ArrayList<>();


    TextView text11;
    ImageButton backBtn;
    TextView signOutButton;
    View rootView;
    FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        //initview();


        return rootView;


    }


    public void init() {

        backBtn = rootView.findViewById(R.id.navigate_from_profile_to_main_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getViewPager().setCurrentItem(1);
            }
        });

        signOutButton = rootView.findViewById(R.id.signout);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        mChooseImage = rootView.findViewById(R.id.imageView6);
        mChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        text11 = rootView.findViewById(R.id.textView11);
        text11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfiniteViewPage();
            }
        });

    }
/*
    public void initview(){
        lstImages.add(R.drawable.foll6);
        lstImages.add(R.drawable.foll7);
        lstImages.add(R.drawable.foll8);
        lstImages.add(R.drawable.foll9);
        lstImages.add(R.drawable.foll10);
    }
    */

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICT_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICT_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            mChooseImage.setImageURI(imgUri);
        }
    }

    public void openInfiniteViewPage() {
        Intent intent = new Intent(this.getContext(), infiniteView.class);
        startActivity(intent);
    }
}