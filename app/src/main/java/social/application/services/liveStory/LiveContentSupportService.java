package social.application.services.liveStory;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import social.application.entity.LiveContent;
import social.application.mainpage.adapters.MainMenuStoryPagerAdapter;
import social.application.services.CommonDisplayUtil;
import social.application.Story.story;

/**
 * Created by Chappy on 2018.06.22..
 */

public class LiveContentSupportService {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static FirebaseStorage storage = FirebaseStorage.getInstance("gs://socialapplication02.appspot.com");

    private static List<LiveContent> liveContents;

    private static Context context;

    public static void saveLiveContent(LiveContent liveContent) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        DatabaseReference liveContentsRef = database.getReference("users/" + userId + "/liveContents");
        liveContentsRef.child(String.valueOf(liveContent.getId())).setValue(liveContent);
    }

    public static LiveContent toLiveContent(Map<String, Object> liveContentMap){
        LiveContent liveContent = new LiveContent();
        liveContent.setId(String.valueOf(liveContentMap.get("id")));
        liveContent.setTitle(String.valueOf(liveContentMap.get("title")));
        liveContent.setContentURI(String.valueOf(liveContentMap.get("contentURI")));
        liveContent.setContentType(LiveContent.ContentType.valueOf(String.valueOf(liveContentMap.get("contentType"))));

        if(liveContentMap.get("tags") != null) {
            liveContent.setTags((List) liveContentMap.get("tags"));
        }

        return liveContent;
    }

    public static String saveLivePictureImage(byte[] data){

        StorageReference storageRef = storage.getReference("uploads/liveContents");
        String imageName = "img_" + new Date().getTime() + ".jpg";
        StorageReference livePictureImageRef = storageRef.child(imageName);

        UploadTask uploadTask = livePictureImageRef.putBytes(data);
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

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Image successfully uploaded!", Toast.LENGTH_SHORT);
            }
        });
        return livePictureImageRef.getPath();

    }

    public static String saveLiveVideo(Uri videoUri){
        File videoFile = new File(videoUri.toString());
        String fileName = videoFile.getName();

        StorageReference storageRef = storage.getReference("uploads/liveContents");
        StorageReference liveVideoImageRef = storageRef.child(fileName);

        UploadTask uploadTask = liveVideoImageRef.putFile(videoUri);
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

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Video successfully uploaded!", Toast.LENGTH_SHORT);
            }
        });
        return liveVideoImageRef.getPath();

    }

    public static void addAllLiveContentsToPagerAdapter(final MainMenuStoryPagerAdapter adapter, Context ctx){
        context = ctx;

        liveContents = new ArrayList<LiveContent>();

        DatabaseReference allUserRef = database.getReference("users");
        allUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.removeAllItems();

                for (DataSnapshot userSnapShot: dataSnapshot.getChildren()) {
                    Map<String, Object> userChildren = (Map<String, Object>)userSnapShot.getValue();
                    Map<String, Object> liveContentsMap = (Map<String, Object>)userChildren.get("liveContents");
                    if (liveContentsMap != null) {
                        for (Map.Entry<String, Object> liveContentEntry : liveContentsMap.entrySet()) {
                            if(liveContentEntry.getValue() != null) {
                                Map<String, Object> liveContentMap = (Map<String, Object>) liveContentEntry.getValue();
                                LiveContent liveContent = toLiveContent(liveContentMap);
                                liveContents.add(liveContent);
                                adapter.addStoryFragment(liveContent);
                                Log.d("INFO:", liveContent.toString());
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void addLiveContentLayoutToParent(final LiveContent liveContent, ViewGroup parent){

        Integer id = liveContent.getId().hashCode();

        /*Title TextView*/
        TextView titleTextView = new TextView(context);
        titleTextView.setTextColor(Color.parseColor("#ffffff"));
        titleTextView.setBackgroundColor(Color.parseColor("#33000000"));
        RelativeLayout.LayoutParams titleLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleTextView.setPadding(CommonDisplayUtil.getDipValue(8, context), CommonDisplayUtil.getDipValue(2, context), 0, CommonDisplayUtil.getDipValue(2, context));
        titleTextView.setLayoutParams(titleLayoutParams);
        titleTextView.setText(liveContent.getTitle());

        /*Tags TextView*/
        TextView locationTextView = new TextView(context);
        locationTextView.setId(id + 1);
        locationTextView.setTextColor(Color.parseColor("#ffffff"));
        locationTextView.setBackgroundColor(Color.parseColor("#33000000"));
        locationTextView.setPadding(CommonDisplayUtil.getDipValue(8, context), CommonDisplayUtil.getDipValue(1, context),
                CommonDisplayUtil.getDipValue(8, context), CommonDisplayUtil.getDipValue(2, context));
        RelativeLayout.LayoutParams locationLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        locationLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        locationTextView.setText(liveContent.getTags().toString());
        locationTextView.setLayoutParams(locationLayoutParams);

        if(liveContent.getContentType() != null && liveContent.getContentType().equals(LiveContent.ContentType.IMAGE)) {

            /* ImageView */
            ImageView backgroundImageView = new ImageView(context);
            RelativeLayout.LayoutParams backgroundImageLayoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            backgroundImageLayoutParams.setMargins(0, 0, 0, 0);
            backgroundImageView.setLayoutParams(backgroundImageLayoutParams);
            setLivePictureBackgroundImage(liveContent, backgroundImageView);

            parent.addView(backgroundImageView);

        } else if(liveContent.getContentType() != null && liveContent.getContentType().equals(LiveContent.ContentType.VIDEO)){

            /* VideoView */
            VideoView videoView = new VideoView(context);
            RelativeLayout.LayoutParams videoViewLayoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            videoViewLayoutParams.setMargins(0, 0, 0, 0);
            videoView.setLayoutParams(videoViewLayoutParams);
            videoView.setVideoURI(Uri.parse(liveContent.getContentURI()));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });

            parent.addView(videoView);
            videoView.start();
        }
        /*Assigning things to Parent view*/

        parent.addView(titleTextView);
        parent.addView(locationTextView);

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, story.class);
                context.startActivity(intent);
            }
        });


//        parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, EventViewerActivity.class);
//                intent.putExtra("liveContent", liveContent);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
    }


    public static void setLivePictureBackgroundImage(LiveContent liveContent, final ImageView imageView){
        StorageReference livePictureImageRef = storage.getReference(liveContent.getContentURI());

        final long ONE_MEGABYTE = 4024 * 4024;
        livePictureImageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Drawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                imageView.setBackground(image);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("ERROR", exception.getStackTrace().toString());
            }
        });
    }



}
