package social.application.services.events;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import social.application.R;
import social.application.events.EventViewerActivity;
import social.application.events.EventsActivity;
import social.application.events.adapters.CycleViewPagerAdapter;
import social.application.mainpage.adapters.MainMenuEventsPagerAdapter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Chappy on 2018.05.20..
 */

public  class EventSupportService {

    private static List<Event> events;

    private static Context context;

    private static ViewGroup parentView;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static FirebaseStorage storage = FirebaseStorage.getInstance("gs://socialapplication02.appspot.com");

    private static final String DEFAULT_EVENT_IMAGE_URI = "uploads/events/images/concert_small.jpeg";


    public static void saveEvent(Event event){
        DatabaseReference eventsRef = database.getReference("events");
        eventsRef.child(String.valueOf(event.getId())).setValue(event);
    }

    public static void addAllEventsToPagerAdapter(final MainMenuEventsPagerAdapter adapter, Context ctx){
        context = ctx;

        events = new ArrayList<Event>();
        DatabaseReference eventsRef = database.getReference("events");
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.removeAllItems();

                for (DataSnapshot eventSnapShot: dataSnapshot.getChildren()) {
                    Map<String, Object> eventMap = (Map<String, Object>)eventSnapShot.getValue();
                    Event event = toEvent(eventMap);
                    events.add(event);
                    adapter.addEventFragment(event);
                    Log.d("INFO:", event.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void addAllEventsToCycleViewPagerAdapter(final CycleViewPagerAdapter adapter, Context ctx){
        context = ctx;

        events = new ArrayList<Event>();
        DatabaseReference eventsRef = database.getReference("events");
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
        titleTextView.setPadding(CommonEventsUtil.getDipValue(8, context), CommonEventsUtil.getDipValue(2, context), 0, CommonEventsUtil.getDipValue(2, context));
        titleTextView.setLayoutParams(titleLayoutParams);
        titleTextView.setText(event.getTitle());

        /*Location TextView*/
        TextView locationTextView = new TextView(context);
        locationTextView.setId(id + 1);
        locationTextView.setTextColor(Color.parseColor("#ffffff"));
        locationTextView.setBackgroundColor(Color.parseColor("#33000000"));
        locationTextView.setPadding(CommonEventsUtil.getDipValue(8, context), CommonEventsUtil.getDipValue(1, context),
                CommonEventsUtil.getDipValue(8, context), CommonEventsUtil.getDipValue(2, context));
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
        dateTextView.setPadding(CommonEventsUtil.getDipValue(8, context), CommonEventsUtil.getDipValue(2, context),
                CommonEventsUtil.getDipValue(8, context), CommonEventsUtil.getDipValue(1, context));
        RelativeLayout.LayoutParams dateLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dateLayoutParams.addRule(RelativeLayout.ABOVE);
        dateLayoutParams.addRule(RelativeLayout.ABOVE, id + 1);
        dateTextView.setLayoutParams(dateLayoutParams);

//        /*Container*/
//        final RelativeLayout eventContainer = new RelativeLayout(context);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CommonEventsUtil.getDipValue(170, context), CommonEventsUtil.getDipValue(210, context));
//        layoutParams.setMargins(0, 0, CommonEventsUtil.getDipValue(2, context), 0);
//        eventContainer.setLayoutParams(layoutParams);
//
//        setEventViewBackgroundImage(event, eventContainer);

        parent.addView(titleTextView);
        parent.addView(locationTextView);
        parent.addView(dateTextView);

        parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, EventViewerActivity.class);
                intent.putExtra("event", event);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                return false;
            }
        });

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventsActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

//        parent.addView(eventContainer);
    }

    public static void setEventViewBackgroundImage(Event event, final View view){
        StorageReference eventImageRef = storage.getReference(event.getImageURI());

        final long ONE_MEGABYTE = 4024 * 4024;
        eventImageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
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

//    public static void addCycleViewPagerItemToViewGroup(ViewGroup parentViewGroup, Event event, Context context){
//
//        Integer id = event.getId().hashCode();
//
//        /*Title TextView*/
//        TextView titleTextView = new TextView(context);
//        titleTextView.setTextColor(Color.parseColor("#ffffff"));
//        titleTextView.setBackgroundColor(Color.parseColor("#33000000"));
//        RelativeLayout.LayoutParams titleLayoutParams =
//                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        titleTextView.setPadding(CommonEventsUtil.getDipValue(8, context), CommonEventsUtil.getDipValue(2, context), 0, CommonEventsUtil.getDipValue(2, context));
//        titleTextView.setLayoutParams(titleLayoutParams);
//        titleTextView.setText(event.getTitle());
//
//        /*Location TextView*/
//        TextView locationTextView = new TextView(context);
//        locationTextView.setId(id + 1);
//        locationTextView.setTextColor(Color.parseColor("#ffffff"));
//        locationTextView.setBackgroundColor(Color.parseColor("#33000000"));
//        locationTextView.setPadding(CommonEventsUtil.getDipValue(8, context), CommonEventsUtil.getDipValue(1, context),
//                CommonEventsUtil.getDipValue(8, context), CommonEventsUtil.getDipValue(2, context));
//        RelativeLayout.LayoutParams locationLayoutParams =
//                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        locationLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        locationTextView.setText(getShortPlaceInformation(event.getLocation()));
//        locationTextView.setLayoutParams(locationLayoutParams);
//
//        /*Date TextView*/
//        TextView dateTextView = new TextView(context, null, R.style.RecommendedEventsDescriptionTextStyle);
//        SimpleDateFormat sdfDate = new SimpleDateFormat("MM.dd.");
//        dateTextView.setText(sdfDate.format(new Date(event.getDateTime())).toString());
//        dateTextView.setTextColor(Color.parseColor("#ffffff"));
//        dateTextView.setBackgroundColor(Color.parseColor("#33000000"));
//        dateTextView.setPadding(CommonEventsUtil.getDipValue(8, context), CommonEventsUtil.getDipValue(2, context),
//                CommonEventsUtil.getDipValue(8, context), CommonEventsUtil.getDipValue(1, context));
//        RelativeLayout.LayoutParams dateLayoutParams =
//                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        dateLayoutParams.addRule(RelativeLayout.ABOVE);
//        dateLayoutParams.addRule(RelativeLayout.ABOVE, id + 1);
//        dateTextView.setLayoutParams(dateLayoutParams);
//
//        parentViewGroup.addView(titleTextView);
//        parentViewGroup.addView(locationTextView);
//        parentViewGroup.addView(dateTextView);
//    }


}
