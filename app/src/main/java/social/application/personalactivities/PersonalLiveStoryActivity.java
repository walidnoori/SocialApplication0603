package social.application.personalactivities;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.Date;

import social.application.R;
import social.application.entity.LiveContent;
import social.application.services.liveStory.LiveContentSupportService;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class PersonalLiveStoryActivity extends AppCompatActivity{

    private static final int REQUEST_VIDEO_CAPTURE = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private VideoView mVideoView;

    private ImageView mImageView;

    private TextView mVideoButton;

    private TextView mPictureButton;

    private TextView saveButton;

    private TextView cancelButton;

    private EditText titleEditText;

    private EditText tagsEditText;

    private Uri actualVideoUri;

    private LiveContent.ContentType actualContentType;

    public static final int REQUEST_EXTERNAL_PERMISSION_CODE = 123;
    public static final String[] PERMISSIONS_EXTERNAL_STORAGE = {
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_live_story);

        initViews();
        verifyStoragePermissions(PersonalLiveStoryActivity.this);
    }

    public void initViews(){
        mImageView = (ImageView) findViewById(R.id.cameraImageView);
        mVideoView = (VideoView) findViewById(R.id.cameraVideoView);

        mPictureButton = (TextView) findViewById(R.id.cameraButton);
        mPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        mVideoButton = (TextView) findViewById(R.id.cameraVideoButton);
        mVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });

        saveButton = (TextView) findViewById(R.id.save_live_story_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   saveLiveContent();
            }
        });

        cancelButton = (TextView) findViewById(R.id.cancel_live_story_btn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelLiveContent();
            }
        });

        titleEditText = (EditText) findViewById(R.id.live_story_title);
        tagsEditText = (EditText) findViewById(R.id.live_story_tags);

        cancelLiveContent();
    }

    public void saveLiveContent(){
        LiveContent liveContent = new LiveContent();
        liveContent.setId(generateLiveContentId());
        liveContent.setTitle(titleEditText.getText().toString());
        liveContent.setTags(Arrays.asList(tagsEditText.getText().toString().split(" ")));

        if(actualContentType == null){
            return;
        }

        if(actualContentType.equals(LiveContent.ContentType.IMAGE) && mImageView.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            liveContent.setContentURI(LiveContentSupportService.saveLivePictureImage(data));
            liveContent.setContentType(actualContentType);

        } else if (actualContentType.equals(LiveContent.ContentType.VIDEO) && actualVideoUri != null){
            File video = new File(actualVideoUri.toString());
            liveContent.setContentURI(LiveContentSupportService.saveLiveVideo(actualVideoUri));
            liveContent.setContentType(actualContentType);
        }

        LiveContentSupportService.saveLiveContent(liveContent);
    }

    public void cancelLiveContent(){
        mImageView.setVisibility(View.GONE);
        mVideoView.setVisibility(View.GONE);

        mPictureButton.setVisibility(View.VISIBLE);
        mVideoButton.setVisibility(View.VISIBLE);

        saveButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
    }

    private String generateLiveContentId(){
        return "liveContent" + FirebaseAuth.getInstance().getCurrentUser().getUid() + new Date().getTime();
    }

    private void dispatchTakeVideoIntent() {
        actualContentType = LiveContent.ContentType.VIDEO;
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            Uri uri = Uri.fromFile(getVideoFile());
            takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            takeVideoIntent.putExtra("android.intent.extra.durationLimit", 3);
            takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private void dispatchTakePictureIntent() {
        actualContentType = LiveContent.ContentType.IMAGE;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

            onMediaSelected();
            verifyStoragePermissions(this);
            mImageView.setVisibility(View.GONE);
            mVideoView.setVisibility(View.VISIBLE);
            mVideoView.setVideoURI(actualVideoUri);
            mVideoView.start();

        }else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            onMediaSelected();
            mVideoView.setVisibility(View.GONE);
            mImageView.setVisibility(View.VISIBLE);
            Bundle extras = intent.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            mImageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
        }
    }

    public void onMediaSelected(){
        mPictureButton.setVisibility(View.GONE);
        mVideoButton.setVisibility(View.GONE);

        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
    }

    public File getVideoFile(){
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"Camera");
        if(!folder.exists()){
            folder.mkdir();
        }

        File video_file = new File(folder,"video" + new Date().getTime() + ".mp4");
        actualVideoUri = Uri.fromFile(video_file);
        return video_file;
    }




    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_EXTERNAL_STORAGE,
                    REQUEST_EXTERNAL_PERMISSION_CODE
            );
        }
    }
}
