package social.application.personalactivities;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import social.application.R;
import social.application.entity.LocalPicture;
import social.application.services.liveStory.LivePictureSupportService;
import social.application.services.localPhoto.LocalPictureSupportService;


public class PersonalPhotoVideoActivity extends AppCompatActivity {
    private static final int PICT_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private TextView mButtonUpload;

    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private StorageReference localPicturesStorageRef;
    private DatabaseReference localPictureDatabaseRef;

    private StorageTask mUploadTask;

    private Uri mImageUri;

    private LocalPicture localPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_photo_video);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);

        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        localPicturesStorageRef = FirebaseStorage.getInstance().getReference("uploads/localPictures");
        localPictureDatabaseRef = FirebaseDatabase.getInstance().getReference("localPictures");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(PersonalPhotoVideoActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();

                }else {
                    uploadFile();
                }
            }
        });
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICT_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICT_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
            mImageView.setImageURI(mImageUri);
//            Picasso.with(this).load(mImageUri).into(mImageView);

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadFile(){

        localPicture = new LocalPicture();
        localPicture.setImageURI(mImageUri.toString());
        localPicture.setId("localPicture" + System.currentTimeMillis());
        localPicture.setName(mEditTextFileName.getText().toString());

        if(mImageUri != null) {
            Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            localPicture.setImageURI(LocalPictureSupportService.saveLocalPictureImage(data));
            Toast.makeText(PersonalPhotoVideoActivity.this, "Upload successful!", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

        LocalPictureSupportService.saveLocalPicture(localPicture);

    }


}
