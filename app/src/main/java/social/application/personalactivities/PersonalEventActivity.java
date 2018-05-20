package social.application.personalactivities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import social.application.R;
import social.application.events.EventsActivity;
import social.application.services.events.Event;
import social.application.services.events.EventSupportService;


public class PersonalEventActivity extends AppCompatActivity {

    private static TextView dateText;

    private static TextView timeText;

    private static TextView placeText;

    private static EditText titleEditText;

    private static EditText descriptionEditText;

    private static Event editedEvent;

    private static int PLACE_PICKER_REQUEST = 1;

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
        editedEvent.setDate(new Date());
    }

    public void initViews(){
        dateText = (TextView)findViewById(R.id.personal_event_date_text);
        timeText = (TextView)findViewById(R.id.personal_event_time_text);
        placeText = (TextView)findViewById(R.id.personal_event_place_text);

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
            c.setTime(editedEvent.getDate());
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);

            SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");
            timeText.setText(sdfTime.format(c.getTime()).toString());
            editedEvent.setDate(c.getTime());
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
            c.setTime(editedEvent.getDate());
            c.set(year, month, day);
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy.MM.dd.");
            dateText.setText(sdfDate.format(c.getTime()).toString());
            editedEvent.setDate(c.getTime());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String placeStr = String.format("Place: %s, %s", place.getName(), place.getAddress());
                placeText.setText(placeStr);
            }
        }
    }

    public void navigateToEvents(View v){
        Event event = new Event();
        event.setId(generateEventId());
        event.setTitle(titleEditText.getText().toString());
        event.setDescription(descriptionEditText.getText().toString());
        event.setLocation(placeText.getText().toString());
        EventSupportService.addEvent(event);

        Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
        startActivity(intent);
    }

    public String generateEventId(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid() + "event" + new Date().getTime();
    }
}
