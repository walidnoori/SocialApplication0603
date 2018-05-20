package social.application.services.events;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Chappy on 2018.05.20..
 */

public  class EventSupportService {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static void testWrite(){
        DatabaseReference myRef = database.getReference("events");
        myRef.setValue("Hello, World!");
    }

    public static void addEvent(Event event){

        DatabaseReference eventsRef = database.getReference("events");
        eventsRef.child(String.valueOf(event.getId())).setValue(event);
    }

    public static void getAllEvents(){
        DatabaseReference eventsRef = database.getReference("events");
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot eventSnapShot: dataSnapshot.getChildren()) {
                   Log.d("INFO:",  eventSnapShot.getValue(Event.class).toString());
                }
            return;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
