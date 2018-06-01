package social.application.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.flags.impl.DataUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import social.application.R;
import social.application.services.events.Event;
import social.application.services.events.EventSupportService;


public class EventViewerActivity extends AppCompatActivity {

    private Event event;

    private TextView titleText;

    private TextView descriptionText;

    private TextView locationText;

    private TextView dateText;

    private TextView timeText;

    private TextView tagsText;

    private TextView taggedUsersText;

    private ImageView eventImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_viewer);

        initEvent();
        initViews();
        initViewValues();
    }

    public void initEvent(){
        Intent intent = getIntent();
        event = (Event) intent.getSerializableExtra("event");
    }

    public void initViews(){
        titleText = (TextView)findViewById(R.id.event_viewer_event_title);
        descriptionText = (TextView)findViewById(R.id.event_viewer_event_description);
        locationText = (TextView)findViewById(R.id.event_viewer_event_location);
        dateText = (TextView)findViewById(R.id.event_viewer_event_date);
        timeText = (TextView)findViewById(R.id.event_viewer_event_time);
        tagsText = (TextView)findViewById(R.id.event_viewer_event_tags);
        taggedUsersText = (TextView)findViewById(R.id.event_viewer_event_tagged_users);
        eventImage = (ImageView)findViewById(R.id.event_viewer_event_img);
    }

    public void initViewValues(){
        if(event.getTitle() != null) {
            titleText.setText(event.getTitle());
        }
        if(event.getDescription() != null) {
            descriptionText.setText(event.getDescription());
        }
        if(event.getLocation() != null) {
            locationText.setText(event.getLocation());
        }
        if(event.getDateTime() != null) {
            dateText.setText(getEventDateAsString());
            timeText.setText(getEventTimeAsString());
        }
        if(event.getTags() != null && !event.getTags().isEmpty() && !event.getTags().get(0).equals("")) {
            tagsText.setText(joinStrings(" ", event.getTags()));
        }
        if(event.getTaggedUsers() != null && !event.getTaggedUsers().isEmpty() && !event.getTaggedUsers().get(0).equals("")) {
            taggedUsersText.setText(joinStrings(" ", event.getTaggedUsers()));
        }
        EventSupportService.setEventViewBackgroundImage(event, eventImage);
    }

    public String getEventDateAsString(){
        Date date = new Date(event.getDateTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.");
        String result = sdf.format(date).toString();
        return result;
    }

    public String getEventTimeAsString(){
        return (new SimpleDateFormat("hh:mm").format(new Date(event.getDateTime()))).toString();
    }

    public static String joinStrings(String conjunction, List<String> list)
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String item : list)
        {
            if (first)
                first = false;
            else
                sb.append(conjunction);
            sb.append(item);
        }
        return sb.toString();
    }

}
