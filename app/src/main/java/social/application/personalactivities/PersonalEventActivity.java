package social.application.personalactivities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import social.application.R;
import social.application.events.EventsActivity;
import social.application.main.MainActivity;
import social.application.services.events.Event;
import social.application.services.events.EventSupportService;


public class PersonalEventActivity extends AppCompatActivity {

    private static int PLACE_PICKER_REQUEST = 1;

    private static final int READ_REQUEST_CODE = 42;

    private static TextView dateText;

    private static TextView timeText;

    private static TextView placeText;

    private static EditText titleEditText;

    private static EditText descriptionEditText;

    private static EditText tagsEditText;

    private static EditText taggedUsers;

    private static ImageView selectedImage;

    private static Uri selectedImageUri;

    private static LinearLayout imageOptionsLinearLayout;

    private static LinearLayout emptyImageLinearLayout;

    private static Event editedEvent;

    private static Date eventDate;

    private static final String DEFAULT_EVENT_IMAGE_URI = "uploads/events/images/concert_small.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_event);

        init();
    }

    public void init(){
        initViews();
        initEditedEvent();
    }

    public void initEditedEvent(){
        editedEvent = new Event();
        eventDate = new Date();
    }

    public void initViews(){
        dateText = (TextView)findViewById(R.id.personal_event_date_text);
        timeText = (TextView)findViewById(R.id.personal_event_time_text);
        placeText = (TextView)findViewById(R.id.personal_event_place_text);
        tagsEditText = (EditText)findViewById(R.id.personal_event_tags);
        taggedUsers = (EditText)findViewById(R.id.personal_event_tagged_users);
        selectedImage = (ImageView)findViewById(R.id.personal_event_image);
        imageOptionsLinearLayout = (LinearLayout)findViewById(R.id.personal_event_image_options_layout);
        emptyImageLinearLayout = (LinearLayout)findViewById(R.id.personal_event_empty_image_layout);

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy.MM.dd.");
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");

        dateText.setText(sdfDate.format(new Date()));
        timeText.setText(sdfTime.format(new Date()));

        titleEditText = (EditText) findViewById(R.id.personal_event_title);
        descriptionEditText = (EditText) findViewById(R.id.personal_event_title);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar c = Calendar.getInstance();
            c.setTime(eventDate);
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);

            SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");
            timeText.setText(sdfTime.format(c.getTime()).toString());
            eventDate = c.getTime();
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.setTime(eventDate);
            c.set(year, month, day);
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy.MM.dd.");
            dateText.setText(sdfDate.format(c.getTime()).toString());
            eventDate = c.getTime();
        }
    }

    public void showPlacePicker(View v){
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void onSelectImage(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    public void saveEvent(View view){
        Event event = new Event();
        event.setId(generateEventId());
        event.setTitle(titleEditText.getText().toString());
        event.setDescription(descriptionEditText.getText().toString());
        if(placeText.getText().toString().equals("Add location")){
            event.setLocation(placeText.getText().toString());
        }
        event.setDateTime(eventDate.getTime());
        event.setTags(Arrays.asList(tagsEditText.getText().toString().split(" ")));
        event.setTaggedUsers(Arrays.asList(taggedUsers.getText().toString().split(" ")));

        if(selectedImage.getDrawable() != null) {
            selectedImage.setDrawingCacheEnabled(true);
            selectedImage.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) selectedImage.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            event.setImageURI(EventSupportService.saveEventImage(data));
        }

        EventSupportService.saveEvent(event);

        navigateToEvents();
    }

    public void navigateToEvents(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public String generateEventId(){
        return "event" + FirebaseAuth.getInstance().getCurrentUser().getUid() + new Date().getTime();
    }

    public void onDeleteImage(View view){
        selectedImage.setImageURI(null);
        selectedImage.setVisibility(View.GONE);
        imageOptionsLinearLayout.setVisibility(View.GONE);
        emptyImageLinearLayout.setVisibility(View.VISIBLE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String placeStr = String.format("Place: %s", place.getAddress());
                placeText.setText(placeStr);
            }
        }else if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedImageUri = data.getData();
                Log.i("INFO", "Selected image Uri: " + selectedImageUri.toString());
                selectedImage.setImageURI(data.getData());
                selectedImage.setVisibility(View.VISIBLE);
                imageOptionsLinearLayout.setVisibility(View.VISIBLE);
                emptyImageLinearLayout.setVisibility(View.GONE);
            }
        }
    }




}
