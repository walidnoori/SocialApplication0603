package social.application.services.LiveStory;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import social.application.entity.LivePicture;
import social.application.events.adapters.EventCycleViewPagerAdapter;
import social.application.events.adapters.LivePictureCycleViewPagerAdapter;
import social.application.mainpage.adapters.MainMenuStoryPagerAdapter;
import social.application.services.CommonDisplayUtil;
import social.application.Story.story;

/**
 * Created by Chappy on 2018.06.22..
 */

public class LivePictureSupportService {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static FirebaseStorage storage = FirebaseStorage.getInstance("gs://socialapplication02.appspot.com");

    private static List<LivePicture> livePictures;

    private static Context context;

    public static void saveLivePicture(LivePicture livePicture){
        DatabaseReference livePicturesRef = database.getReference("livePictures");
        livePicturesRef.child(String.valueOf(livePicture.getId())).setValue(livePicture);
    }

    public static LivePicture toLivePicture(Map<String, Object> livePictureMap){
        LivePicture livePicture = new LivePicture();
        livePicture.setId(String.valueOf(livePictureMap.get("id")));
        livePicture.setTitle(String.valueOf(livePictureMap.get("title")));
        livePicture.setImageURI(String.valueOf(livePictureMap.get("imageURI")));

        if(livePictureMap.get("tags") != null) {
            livePicture.setTags((List) livePictureMap.get("tags"));
        }

        return livePicture;
    }

    public static String saveLivePictureImage(byte[] data){

        StorageReference storageRef = storage.getReference("uploads/livePictures/images");
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
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
        return livePictureImageRef.getPath();

    }


    public static void addAllLivePicturesToPagerAdapter(final MainMenuStoryPagerAdapter adapter, Context ctx){
        context = ctx;

        livePictures = new ArrayList<LivePicture>();
        DatabaseReference livePicturesRef = database.getReference("livePictures");
        livePicturesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.removeAllItems();

                for (DataSnapshot livePictureSnapShot: dataSnapshot.getChildren()) {
                    Map<String, Object> livePictureMap = (Map<String, Object>)livePictureSnapShot.getValue();
                    LivePicture livePicture = toLivePicture(livePictureMap);
                    livePictures.add(livePicture);
                    adapter.addStoryFragment(livePicture);
                    Log.d("INFO:", livePicture.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void addAllLivePicturesToCycleViewPagerAdapter(final LivePictureCycleViewPagerAdapter adapter, Context ctx){
        context = ctx;

        livePictures = new ArrayList<LivePicture>();
        DatabaseReference livePicturesRef = database.getReference("livePictures");
        livePicturesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.removeAllItems();

                for (DataSnapshot livePictureSnapShot: dataSnapshot.getChildren()) {
                    Map<String, Object> livePictureMap = (Map<String, Object>)livePictureSnapShot.getValue();
                    LivePicture livePicture = toLivePicture(livePictureMap);
                    livePictures.add(livePicture);
                    adapter.addItem(livePicture);
                    Log.d("INFO:", livePicture.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void addLivePictureLayoutToParent(final LivePicture livePicture, ViewGroup parent){

        Integer id = livePicture.getId().hashCode();

        /*Title TextView*/
        TextView titleTextView = new TextView(context);
        titleTextView.setTextColor(Color.parseColor("#ffffff"));
        titleTextView.setBackgroundColor(Color.parseColor("#33000000"));
        RelativeLayout.LayoutParams titleLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleTextView.setPadding(CommonDisplayUtil.getDipValue(8, context), CommonDisplayUtil.getDipValue(2, context), 0, CommonDisplayUtil.getDipValue(2, context));
        titleTextView.setLayoutParams(titleLayoutParams);
        titleTextView.setText(livePicture.getTitle());

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
        locationTextView.setText(livePicture.getTags().toString());
        locationTextView.setLayoutParams(locationLayoutParams);

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
//                intent.putExtra("livePicture", livePicture);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
    }


    public static void setLivePictureViewBackgroundImage(LivePicture livePicture, final View view){
        StorageReference livePictureImageRef = storage.getReference(livePicture.getImageURI());

        final long ONE_MEGABYTE = 4024 * 4024;
        livePictureImageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Drawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                view.setBackground(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("ERROR", exception.getStackTrace().toString());
            }
        });
    }



}
