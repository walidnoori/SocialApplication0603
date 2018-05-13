package social.application.explore;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import social.application.R;

/**
 * Created by Chappy on 2018.04.01..
 */

public class ExplorePostAdapter extends BaseAdapter {

    List<ExploreObject> items;

    public ExplorePostAdapter(){
        items = new ArrayList<ExploreObject>();
        loadItems();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.explore_grid_item, null);
        LinearLayout itemContainer = itemView.findViewById(R.id.explore_grid_container);
        TextView title = (TextView) itemView.findViewById(R.id.explore_object_title);
        title.setText(this.items.get(position).getTitle());


        if(position == 1) {
            itemContainer.setMinimumHeight(200);
        }

        return itemView;
    }

    public void loadItems(){
        for(int i = 0; i < 30; i++){
            items.add(new ExploreObject(i, "Post " + (i+1), i ));
        }
    }
}
