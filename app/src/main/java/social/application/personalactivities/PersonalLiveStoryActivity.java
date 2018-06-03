package social.application.personalactivities;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import social.application.R;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;


public class PersonalLiveStoryActivity extends AppCompatActivity implements View.OnClickListener{

    static final int REQUEST_VIDEO_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    VideoView mVideoView;
    ImageView mImageView;
    TextView mVideoButton;
    TextView mPictureButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_live_story);

        mVideoView = (VideoView) findViewById(R.id.cameraVideoView);
        mImageView = (ImageView) findViewById(R.id.cameraImageView);
        mVideoButton = (TextView) findViewById(R.id.cameraVideoButton);
        mPictureButton = (TextView) findViewById(R.id.cameraButton);


        mVideoButton.setOnClickListener(this);
        mPictureButton.setOnClickListener(this);
        mImageView.setVisibility(View.GONE);
        mVideoView.setVisibility(View.GONE);

    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            Uri uri = Uri.fromFile(getFile());
            takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            //verifyStoragePermissions(this);
            mImageView.setVisibility(View.GONE);
            mVideoView.setVisibility(View.VISIBLE);
            Uri videoUri = Uri.fromFile(getFile());
            mVideoView.setVideoURI(videoUri);
            mVideoView.start();
        }else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            mVideoView.setVisibility(View.GONE);
            mImageView.setVisibility(View.VISIBLE);
            Bundle extras = intent.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    public File getFile(){
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"Camera");
        if(!folder.exists()){
            folder.mkdir();
        }

        File video_file = new File(folder,"myVideo.mp4");

        return video_file;
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();

        if(i == R.id.cameraVideoButton){
            dispatchTakeVideoIntent();
        }else if(i == R.id.cameraButton){
            dispatchTakePictureIntent();
        }
    }
}
