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
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import social.application.R;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;


public class PersonalLiveStoryActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private VideoView mVideoView;
    private ImageView mImageView;
    private TextView mVideoButton;
    private TextView mPictureButton;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    private StorageReference storageRef = storage.getReference();

    public static final int REQUEST_EXTERNAL_PERMISSION_CODE = 123;
    public static final String[] PERMISSIONS_EXTERNAL_STORAGE = {
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
    };

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

        verifyStoragePermissions(PersonalLiveStoryActivity.this);

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
            uploadVideo(videoUri);
            mVideoView.setVideoURI(videoUri);
            mVideoView.start();
        }else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            mVideoView.setVisibility(View.GONE);
            mImageView.setVisibility(View.VISIBLE);
            Bundle extras = intent.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            uploadPicture(imageBitmap);
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

    public void uploadVideo(Uri file){
        StorageReference videoRef = storageRef.child("videos/video"+System.currentTimeMillis()+".mp4");
        UploadTask uploadTask = videoRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    public void uploadPicture(Bitmap imageBitMap){

        StorageReference imagesRef = storageRef.child("images/picture"+System.currentTimeMillis()+".jpg");

        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte [] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
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
