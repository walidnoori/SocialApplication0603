package social.application.services.events;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import social.application.R;
import social.application.events.EventViewerActivity;
import social.application.events.EventsActivity;
import social.application.events.adapters.EventCycleViewPagerAdapter;
import social.application.mainpage.adapters.MainMenuEventsPagerAdapter;
import social.application.entity.Event;
import social.application.services.CommonDisplayUtil;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Chappy on 2018.05.20..
 */

public  class EventSupportService {

    private static List<Event> events;

    private static Context context;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static FirebaseStorage storage = FirebaseStorage.getInstance("gs://socialapplication02.appspot.com");

    private static final String DEFAULT_EVENT_IMAGE_URI = "uploads/events/images/concert_small.jpeg";


    public static void saveEvent(Event event){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        DatabaseReference eventsRef = database.getReference("users/" + userId + "/events");
        eventsRef.child(String.valueOf(event.getId())).setValue(event);
    }

    public static void addAllEventsToPagerAdapter(final MainMenuEventsPagerAdapter adapter, Context ctx){
        context = ctx;

        events = new ArrayList<Event>();
        DatabaseReference allUserRef = database.getReference("users");
        allUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.removeAllItems();

                for (DataSnapshot userSnapShot: dataSnapshot.getChildren()) {
                    Map<String, Object> userChildren = (Map<String, Object>) userSnapShot.getValue();
                    Map<String, Object> eventsMap = (Map<String, Object>) userChildren.get("events");

                    if (eventsMap != null){
                        for (Map.Entry<String, Object> eventEntry : eventsMap.entrySet()) {
                            if(eventEntry.getValue() != null) {
                                Map<String, Object> eventMap = (Map<String, Object>) eventEntry.getValue();
                                Event event = toEvent(eventMap);
                                events.add(event);
                                adapter.addEventFragment(event);
                                Log.d("INFO:", event.toString());
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

    public static void addAllEventsToCycleViewPagerAdapter(final EventCycleViewPagerAdapter adapter, Context ctx){
        context = ctx;

        events = new ArrayList<Event>();
        DatabaseReference allUserRef = database.getReference("users");
        allUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.removeAllItems();

                for (DataSnapshot userSnapShot: dataSnapshot.getChildren()) {
                    Map<String, Object> userChildren = (Map<String, Object>)userSnapShot.getValue();
                    Map<String, Object> eventsMap = (Map<String, Object>)userChildren.get("events");
                    if (eventsMap != null) {
                        for (Map.Entry<String, Object> eventEntry : eventsMap.entrySet()) {
                            if(eventEntry.getValue() != null) {
                                Map<String, Object> eventMap = (Map<String, Object>) eventEntry.getValue();
                                Event event = toEvent(eventMap);
                                events.add(event);
                                adapter.addItem(event);
                                Log.d("INFO:", event.toString());
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

    public static void addTrendingEventsToCycleViewPagerAdapter(final EventCycleViewPagerAdapter adapter, Context ctx){
        context = ctx;

        events = new ArrayList<Event>();
        DatabaseReference allUserRef = database.getReference("users");
        allUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.removeAllItems();

                for (DataSnapshot userSnapShot: dataSnapshot.getChildren()) {
                    if(events.size() < 5) {

                        Map<String, Object> userChildren = (Map<String, Object>) userSnapShot.getValue();
                        Map<String, Object> eventsMap = (Map<String, Object>) userChildren.get("events");
                        if (eventsMap != null) {
                            for (Map.Entry<String, Object> eventEntry : eventsMap.entrySet()) {
                                if (eventEntry.getValue() != null) {
                                    Map<String, Object> eventMap = (Map<String, Object>) eventEntry.getValue();
                                    Event event = toEvent(eventMap);
                                    events.add(event);

                                    Log.d("INFO:", event.toString());
                                }
                            }
                        }
                    }
                }

                for(Event event : events){
                    adapter.addItem(event);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void addAllUserEventsToCycleViewPagerAdapter(final EventCycleViewPagerAdapter adapter, Context ctx){
        context = ctx;
        events = new ArrayList<Event>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        DatabaseReference eventsRef = database.getReference("users/" + userId + "/events");
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.removeAllItems();

                for (DataSnapshot eventSnapShot: dataSnapshot.getChildren()) {
                    Map<String, Object> eventMap = (Map<String, Object>)eventSnapShot.getValue();
                    Event event = toEvent(eventMap);
                    events.add(event);
                    adapter.addItem(event);
                    Log.d("INFO:", event.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void addEventLayoutToParent(final Event event, ViewGroup parent){

        Integer id = event.getId().hashCode();

        /*Title TextView*/
        TextView titleTextView = new TextView(context);
        titleTextView.setTextColor(Color.parseColor("#ffffff"));
        titleTextView.setBackgroundColor(Color.parseColor("#33000000"));
        RelativeLayout.LayoutParams titleLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleTextView.setPadding(CommonDisplayUtil.getDipValue(8, context), CommonDisplayUtil.getDipValue(2, context), 0, CommonDisplayUtil.getDipValue(2, context));
        titleTextView.setLayoutParams(titleLayoutParams);
        titleTextView.setText(event.getTitle());

        /*Location TextView*/
        TextView locationTextView = new TextView(context);
        locationTextView.setId(id + 1);
        locationTextView.setTextColor(Color.parseColor("#ffffff"));
        locationTextView.setBackgroundColor(Color.parseColor("#33000000"));
        locationTextView.setPadding(CommonDisplayUtil.getDipValue(8, context), CommonDisplayUtil.getDipValue(1, context),
                CommonDisplayUtil.getDipValue(8, context), CommonDisplayUtil.getDipValue(2, context));
        RelativeLayout.LayoutParams locationLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        locationLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        locationTextView.setText(getShortPlaceInformation(event.getLocation()));
        locationTextView.setLayoutParams(locationLayoutParams);

        /*Date TextView*/
        TextView dateTextView = new TextView(context, null, R.style.RecommendedEventsDescriptionTextStyle);
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM.dd.");
        dateTextView.setText(sdfDate.format(new Date(event.getDateTime())).toString());
        dateTextView.setTextColor(Color.parseColor("#ffffff"));
        dateTextView.setBackgroundColor(Color.parseColor("#33000000"));
        dateTextView.setPadding(CommonDisplayUtil.getDipValue(8, context), CommonDisplayUtil.getDipValue(2, context),
                CommonDisplayUtil.getDipValue(8, context), CommonDisplayUtil.getDipValue(1, context));
        RelativeLayout.LayoutParams dateLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dateLayoutParams.addRule(RelativeLayout.ABOVE);
        dateLayoutParams.addRule(RelativeLayout.ABOVE, id + 1);
        dateTextView.setLayoutParams(dateLayoutParams);

        /* ImageView */
        ImageView backgroundImageView = new ImageView(context);
        RelativeLayout.LayoutParams backgroundImageLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        backgroundImageLayoutParams.setMargins(0, 0, 0, 0);
        backgroundImageView.setLayoutParams(backgroundImageLayoutParams);
        setEventViewBackgroundImage(event, backgroundImageView);

        /*Assigning things to Parent view*/
        parent.addView(backgroundImageView);
        parent.addView(titleTextView);
        parent.addView(locationTextView);
        parent.addView(dateTextView);

        parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, EventsActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                return false;
            }
        });

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventViewerActivity.class);
                intent.putExtra("event", event);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public static void setEventViewBackgroundImage(Event event, final ImageView imageView){
        StorageReference eventImageRef = storage.getReference(event.getImageURI());

        final long ONE_MEGABYTE = 4024 * 4024;
        eventImageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Drawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                imageView.setBackground(image);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("ERROR", exception.getStackTrace().toString());
            }
        });
    }

    public static Event toEvent(Map<String, Object> eventMap){
        Event event = new Event();
        event.setId(String.valueOf(eventMap.get("id")));
        event.setDescription(String.valueOf(eventMap.get("description")));
        event.setTitle(String.valueOf(eventMap.get("title")));
        event.setDateTime(Long.valueOf(String.valueOf(eventMap.get("dateTime"))));
        event.setLocation(String.valueOf(eventMap.get("location")));

        if(eventMap.get("tags") != null) {
            event.setTags((List) eventMap.get("tags"));
        }

        if(eventMap.get("taggedUsers") != null){
            event.setTaggedUsers((List) eventMap.get("taggedUsers"));
        }

        if(eventMap.get("imageURI") != null){
            event.setImageURI(String.valueOf(eventMap.get("imageURI")));
        } else{
            event.setImageURI(DEFAULT_EVENT_IMAGE_URI);
        }

        return event;
    }

    public static String getShortPlaceInformation(String placeText){
        String result = new String(placeText);
        result = result.replace("Place: ", "");
        String[] placePieces =  result.split(",");
        result = result.replace("," + placePieces[placePieces.length-1], "");
        return result;
    }

    public static String saveEventImage(byte[] data){

        StorageReference storageRef = storage.getReference("uploads/events/images");
        String imageName = "img_" + new Date().getTime() + ".jpg";
        StorageReference eventImageRef = storageRef.child(imageName);

        UploadTask uploadTask = eventImageRef.putBytes(data);
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
        return eventImageRef.getPath();

    }

}
