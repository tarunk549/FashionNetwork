package utils;

import android.app.Activity;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by kumartarun on 21/03/16.
 */
public class Singleton {

    public interface OnUpdateListener {
        public void update(String id,boolean isFollow);
    }

    private static final ArrayList<OnUpdateListener> listeners = new ArrayList<>();
    private Subject subject = new Subject();
    static Singleton singleton = null;

    private Activity activity;
    private UserAttributes userAttributes = new UserAttributes();
    private StoryAttributes storyAttributes = new StoryAttributes();
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Story> stories = new ArrayList<Story>();

    public static Singleton getInstance(Activity activity) {
        if(singleton == null) {
            singleton = new Singleton();
        }
        singleton.activity = activity;
        return singleton;
    }

    public Boolean load() {
        Boolean success = false;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(activity.getAssets().open("json_data.json")));
            String mLine, jsonString = "";
            while ((mLine = reader.readLine()) != null) {
                jsonString += mLine;
            }
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                if(!jsonObject.optString(userAttributes.USER_NAME).equals("")) {
                    User user = new User();
                    user.setUserName(jsonObject.optString(userAttributes.USER_NAME));
                    user.setAboutUser(jsonObject.optString(userAttributes.USER_ABOUT));
                    user.setCreatedOn(jsonObject.optLong(userAttributes.USER_CREATED_ON));
                    user.setFollowerCount(jsonObject.optInt(userAttributes.USER_FOLLOWERS));
                    user.setFollowingCount(jsonObject.optInt(userAttributes.USER_FOLLOWING));
                    user.setIsFollowing(jsonObject.optBoolean(userAttributes.USER_IS_FOLLOWING));
                    user.setUserHandle(jsonObject.optString(userAttributes.USER_HANDLE));
                    user.setUserId(jsonObject.optString(userAttributes.USER_ID));
                    user.setUserImageUrl(jsonObject.optString(userAttributes.USER_IMAGE));
                    user.setUserProfileUrl(jsonObject.optString(userAttributes.USER_URL));
                    users.add(user);
                }
                else if(!jsonObject.optString(storyAttributes.STORY_TYPE).equals("")) {
                    Story story = new Story();
                    story.setTitle(jsonObject.optString(storyAttributes.STORY_TITLE));
                    story.setDb(jsonObject.optString(storyAttributes.STORY_DB));
                    story.setSi(jsonObject.optString(storyAttributes.STORY_SI));
                    story.setComment_count(jsonObject.optInt(storyAttributes.STORY_COMMENT_COUNT));
                    story.setLikes_count(jsonObject.optInt(storyAttributes.STORY_LIKE_COUNT));
                    story.setLike_flag(jsonObject.optBoolean(storyAttributes.STORY_LIKE_FLAG));
                    story.setDescription(jsonObject.optString(storyAttributes.STORY_DESCRIPTION));
                    story.setUrl(jsonObject.optString(storyAttributes.STORY_URL));
                    story.setId(jsonObject.optString(storyAttributes.STORY_ID));
                    story.setVerb(jsonObject.optString(storyAttributes.STORY_VERB));
                    story.setType(jsonObject.optString(storyAttributes.STORY_TYPE));
                    stories.add(story);
                }
            }
            assignStoryAuthors();
            success = true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    private Boolean assignStoryAuthors() {
        Boolean assigned = true;
        try {
            for(Story story : stories) {
                String storyDb = story.getDb();
                Boolean userAssignedForStory = false;
                for (User user : users) {
                    if (userAssignedForStory)
                        break;
                    if (user.getUserId().equals(storyDb)) {
                        story.setAuthor(user);
                        userAssignedForStory = true;
                    }
                }
                assigned = assigned && userAssignedForStory;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return assigned;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Story> getStoriess() {
        return stories;
    }

    public Story getStoryById(String storyId){
        Story storyById = null;
        for(Story story : stories) {
            if (story.getId().equalsIgnoreCase(storyId)) {
                storyById = story;
                break;
            }
        }
        return storyById;
    }

    public Boolean unLoad() {
        Boolean unLoaded = false;
        BufferedWriter writer = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray jsonArray = new JSONArray();
            for(User user : users)
                jsonArray.put(new JSONObject(objectMapper.writeValueAsString(user)));
            for(Story story : stories)
                jsonArray.put(new JSONObject(objectMapper.writeValueAsString(story)));
            String jsonString = jsonArray.toJSONObject(jsonArray).toString();
            activity.deleteFile("json_data.json");
            writer = new BufferedWriter(
                    new OutputStreamWriter(activity.openFileOutput("json_data.json",activity.getApplicationContext().MODE_PRIVATE)));
            writer.write(jsonString);
            writer.flush();
            writer.close();
            unLoaded = true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return unLoaded;
    }

    public static void addListener(OnUpdateListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(OnUpdateListener listener) {
        listeners.remove(listener);
    }

    public static void notifyAllFor(String id,boolean isFollow) {

        for (OnUpdateListener listener:listeners) {
            listener.update(id,isFollow);
        }
    }

    public Subject getSubject() {
        return subject;
    }

}