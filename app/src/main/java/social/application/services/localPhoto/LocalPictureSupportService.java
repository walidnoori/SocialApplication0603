package social.application.services.localPhoto;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import social.application.Story.story;
import social.application.entity.LocalPicture;
import social.application.services.CommonDisplayUtil;

/**
 * Created by Chappy on 2018.06.22..
 */

public class LocalPictureSupportService {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static FirebaseStorage storage = FirebaseStorage.getInstance("gs://socialapplication02.appspot.com");

    private static List<LocalPicture> localPictures;

    private static Context context;

    public static void saveLocalPicture(LocalPicture localPictures){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference localPicturesRef = database.getReference("users/" + userId + "/localPictures");
        localPicturesRef.child(String.valueOf(localPictures.getId())).setValue(localPictures);
    }

    public static LocalPicture toLocalPicture(Map<String, Object> localPictureMap){
        LocalPicture localPicture = new LocalPicture();
        localPicture.setId(String.valueOf(localPictureMap.get("id")));
        localPicture.setImageURI(String.valueOf(localPictureMap.get("imageURI")));

        return localPicture;
    }

    public static String saveLocalPictureImage(byte[] data){

        StorageReference storageRef = storage.getReference("uploads/localPictures");
        String imageName = "img_" + new Date().getTime() + ".jpg";
        StorageReference localPictureImageRef = storageRef.child(imageName);

        UploadTask uploadTask = localPictureImageRef.putBytes(data);
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
        return localPictureImageRef.getPath();

    }

    public static void addAllLocalPicturesToCycleViewPagerAdapter(final LocalPictureCycleViewPagerAdapter adapter, Context ctx){
        context = ctx;
        localPictures = new ArrayList<LocalPicture>();

        DatabaseReference allUserRef = database.getReference("users");
        allUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.removeAllItems();

                for (DataSnapshot userSnapShot: dataSnapshot.getChildren()) {
                    Map<String, Object> userChildren = (Map<String, Object>)userSnapShot.getValue();
                    Map<String, Object> localPicturesMap = (Map<String, Object>)userChildren.get("localPictures");
                    if(localPicturesMap != null) {
                        for (Map.Entry<String, Object> localPictureEntry : localPicturesMap.entrySet()) {

                            if(localPictureEntry.getValue() != null){
                                Map<String, Object> localPictureMap = (Map<String, Object>) localPictureEntry.getValue();
                                LocalPicture localPicture = toLocalPicture(localPictureMap);
                                localPictures.add(localPicture);
                                adapter.addItem(localPicture);
                                Log.d("INFO:", localPicture.toString());
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        DatabaseReference localPicturesRef = database.getReference("localPictures");
//        localPicturesRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                adapter.removeAllItems();
//
//                for (DataSnapshot localPictureSnapShot: dataSnapshot.getChildren()) {
//                    Map<String, Object> localPictureMap = (Map<String, Object>)localPictureSnapShot.getValue();
//                    LocalPicture localPicture = toLocalPicture(localPictureMap);
//                    localPictures.add(localPicture);
//                    adapter.addItem(localPicture);
//                    Log.d("INFO:", localPicture.toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public static void addLocalPictureLayoutToParent(final LocalPicture localPicture, ViewGroup parent){

        Integer id = localPicture.getId().hashCode();

         /* ImageView */
        ImageView backgroundImageView = new ImageView(context);
        RelativeLayout.LayoutParams backgroundImageLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        backgroundImageLayoutParams.setMargins(0, 0, 0, 0);
        backgroundImageView.setLayoutParams(backgroundImageLayoutParams);
        backgroundImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        setImageToImageView(localPicture, backgroundImageView);

        /*Title TextView*/
        TextView titleTextView = new TextView(context);
        titleTextView.setTextColor(Color.parseColor("#ffffff"));
        titleTextView.setBackgroundColor(Color.parseColor("#33000000"));
        RelativeLayout.LayoutParams titleLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleTextView.setPadding(CommonDisplayUtil.getDipValue(8, context), CommonDisplayUtil.getDipValue(2, context), 0, CommonDisplayUtil.getDipValue(2, context));
        titleTextView.setLayoutParams(titleLayoutParams);
        titleTextView.setText(localPicture.getName());

        /*Assigning things to Parent view*/
        parent.addView(titleTextView);
        parent.addView(backgroundImageView);

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, story.class);
                context.startActivity(intent);
            }
        });
    }


    public static void setImageToImageView(LocalPicture localPicture, final ImageView imageView){
        StorageReference localPictureImageRef = storage.getReference(localPicture.getImageURI());

        final long ONE_MEGABYTE = 4024 * 4024;
        localPictureImageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Drawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                imageView.setImageDrawable(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("ERROR", exception.getStackTrace().toString());
            }
        });
    }



}
