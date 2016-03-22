package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import screens.ProfileDescription;
import screens.StoryList;
import utils.Singleton;
import utils.Story;
import views.RoposoButton;
import com.roposonetwork.R;

/**
 * Created by kumartarun on 21/03/16.
 */
public class CardAdapter extends BaseAdapter{

    List<Story> cardsCollection;
    Context context;

    public CardAdapter(Context context, ArrayList<Story> cardsCollection){
        this.cardsCollection = new ArrayList<Story>();
        this.cardsCollection = cardsCollection;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        StoryList.CardViewHolder cardViewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.card_view, null);
            cardViewHolder = new StoryList.CardViewHolder();
            cardViewHolder.titleText = (TextView) convertView.findViewById(R.id.cardTitle);
            cardViewHolder.descriptionText = (TextView) convertView.findViewById(R.id.cardDescription);
            cardViewHolder.authorText = (TextView) convertView.findViewById(R.id.cardAuthor);
            cardViewHolder.dpImageView = (ImageView) convertView.findViewById(R.id.dp);
            cardViewHolder.followButton = (RoposoButton) convertView.findViewById(R.id.statusButton);
            convertView.setTag(cardViewHolder);
        }
        else {
            cardViewHolder = (StoryList.CardViewHolder) convertView.getTag();
        }

        Singleton singleton = Singleton.getInstance((Activity)context);
        Story story = singleton.getStoriess().get(position);

        Bitmap image = null;
        try {
            URL url = new URL(story.getSi());
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardViewHolder.titleText.setText(story.getTitle());
        cardViewHolder.descriptionText.setText(story.getDescription());
        cardViewHolder.authorText.setText(story.getAuthor().getUserName());
        cardViewHolder.dpImageView.setImageBitmap(image);
        cardViewHolder.followButton.setIdValue(story.getId());
        cardViewHolder.followButton.init(context);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileDescription.class);
                intent.putExtra("profileIdValue", cardsCollection.get(position).getId());
                intent.putExtra("title", cardsCollection.get(position).getTitle());
                intent.putExtra("description", cardsCollection.get(position).getDescription());
                intent.putExtra("author", cardsCollection.get(position).getAuthor().getUserName());
                intent.putExtra("dpBitMap", cardsCollection.get(position).getSi());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return cardsCollection.size();
    }

    @Override
    public Story getItem(int position) {
        return cardsCollection.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}