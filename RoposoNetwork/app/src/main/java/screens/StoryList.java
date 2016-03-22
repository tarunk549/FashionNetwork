package screens;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.CardAdapter;
import utils.Singleton;
import utils.Story;
import views.RoposoButton;
import com.roposonetwork.R;


/**
 * Created by kumartarun on 21/03/16.
 */
public class StoryList extends Activity {
    ListView storyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_listview);
        storyList = (ListView) findViewById(R.id.storyList);
        ArrayList<Story> arrayList = new ArrayList<Story>();

        Singleton singleton = Singleton.getInstance(this);
        Boolean isLoaded = singleton.load();
        if(isLoaded){
            arrayList = singleton.getStoriess();
        }

        CardAdapter cardAdapter = new CardAdapter(StoryList.this, arrayList);
        storyList.setAdapter(cardAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Singleton singleton = Singleton.getInstance(this);
        singleton.unLoad();
    }

    public static class CardViewHolder{
        public TextView titleText;
        public TextView descriptionText;
        public TextView authorText;
        public ImageView dpImageView;
        public RoposoButton followButton;
    }
}
