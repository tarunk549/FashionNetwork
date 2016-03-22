package screens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.roposonetwork.R;

import utils.Singleton;
import views.RoposoButton;

/**
 * Created by kumartarun on 21/03/16.
 */
public class ProfileDescription extends Activity {
    TextView storyTitle, storyDescription, storyAuthor;
    ImageView storyDp;
    RoposoButton storyFollowButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_description);
        storyTitle = (TextView) findViewById(R.id.title);
        storyDescription = (TextView) findViewById(R.id.description);
        storyAuthor = (TextView) findViewById(R.id.author);
        storyDp = (ImageView) findViewById(R.id.dp);
        storyFollowButton = (RoposoButton) findViewById(R.id.statusButton);
        Intent intent = getIntent();
        storyTitle.setText(intent.getStringExtra("title"));
        storyDescription.setText(intent.getStringExtra("description"));
        storyAuthor.setText(intent.getStringExtra("author"));
        storyDp.setImageBitmap((Bitmap) intent.getParcelableExtra("dpBitMap"));
        storyFollowButton.setIdValue(intent.getStringExtra("profileIdValue"));
        storyFollowButton.init(ProfileDescription.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Singleton singleton = Singleton.getInstance(this);
        singleton.unLoad();
    }

}
