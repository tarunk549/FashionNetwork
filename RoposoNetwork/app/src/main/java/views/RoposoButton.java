package views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import utils.Constants;
import utils.Observer;
import utils.Singleton;
import utils.Story;

/**
 * Created by kumartarun on 21/03/16.
 */
public class RoposoButton extends Button implements View.OnClickListener , Observer{

    String idValue;
    public RoposoButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Singleton.getInstance((Activity)context).getSubject().attach(this);
        setOnClickListener(this);
    }

    public void onNotify(String notificationString) {
        Singleton singleton = Singleton.getInstance((Activity)this.getContext());
        if(singleton.getStoryById(idValue).getAuthor().getUserId().equals(notificationString)) {
            if (this.getText().toString().equals(Constants.FOLLOW_BUTTON_TEXT)) {
                this.setText(Constants.UN_FOLLOW_BUTTON_TEXT);
                singleton.getStoryById(idValue).getAuthor().setIsFollowing(true);
            }
            else {
                this.setText(Constants.FOLLOW_BUTTON_TEXT);
                singleton.getStoryById(idValue).getAuthor().setIsFollowing(false);
            }
        }
    }

    public void setIdValue(String idValue){
        this.idValue = idValue;
    }

    public void init(Context context){
        Singleton singleton = Singleton.getInstance((Activity)context);
        if (singleton.getStoryById(idValue).getAuthor().getIsFollowing()){
            this.setText(Constants.UN_FOLLOW_BUTTON_TEXT);
        } else{
            this.setText(Constants.FOLLOW_BUTTON_TEXT);
        }
    }

    /*@Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Singleton.removeListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Singleton.addListener(this);
    }*/

    public String getIdValue(){
        return idValue;
    }

    @Override
    public void onClick(View v) {
        Story story ;
        Singleton singleton = Singleton.getInstance((Activity)v.getContext());
        /*story =  singleton.getStoryById(idValue);
        if (singleton.getStoryById(idValue).getAuthor().getIsFollowing()){
            story.getAuthor().setIsFollowing(false);
            this.setText(Constants.FOLLOW_BUTTON_TEXT);
        } else{
            story.getAuthor().setIsFollowing(true);
            this.setText(Constants.UN_FOLLOW_BUTTON_TEXT);
        }*/
//        Singleton.notifyAllFor(idValue,story.getAuthor().getIsFollowing());
        singleton.getSubject().notify(singleton.getStoryById(idValue).getAuthor().getUserId());
    }

    /*@Override
    public void update(String id, boolean isFollow) {

        if (idValue.equalsIgnoreCase(id)) {
            this.setText(isFollow?Constants.UN_FOLLOW_BUTTON_TEXT:Constants.FOLLOW_BUTTON_TEXT);
        }
    }*/
}
