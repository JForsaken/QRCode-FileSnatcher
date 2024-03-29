package projects.QRCode.dal;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.Arrays;

import projects.QRCode.data.User;

/**
 * Created by joseph on 2016-07-03.
 */
public class UserRepository {
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;
    private GsonBuilder gsonBuilder = null;
    private Gson gSon = null;

    public UserRepository(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        gsonBuilder = new GsonBuilder();
        gSon = gsonBuilder.create();

        init();
    }

    public User[] getAllUser() {
        String users = sharedPreferences.getString("friends", null);
        User[] userArray = gSon.fromJson(users, User[].class);
        return userArray;
    }

    public void addUser(User user) {

        if (!userExist(user)) {
            User[] userArray = getAllUser();
            User[] users = Arrays.copyOf(userArray, userArray.length + 1);
            users[users.length - 1] = user;
            editor.putString("friends", gSon.toJson(users));
            editor.commit();
        }
    }

    public void updateUser(User user) {
        User[] users = getAllUser();

        for (int i = 0; i < users.length; i++) {
            if (users[i].getUsername().equals(user.getUsername())) {
                users[i].setOnline(user.isOnline());
            }
        }
    }

    public boolean userExist(User user) {
        User[] users = getAllUser();

        for (int i = 0; i < users.length; i++) {
            if (users[i].getUsername().equals(user.getUsername())) {
                return true;
            }
        }

        return false;
    }

    private void init() {
        String users = sharedPreferences.getString("friends", null);

        if (users == null) {
            User[] userArray = new User[0];
            users = gSon.toJson(userArray);
            editor.putString("friends", users);
            editor.commit();
        }
    }

    public User getUser(String username) {
        User[] userArray = getAllUser();

        for (int i = 0; i < userArray.length; i++) {
            if (userArray[i].getUsername().equals(username)) {
                return userArray[i];
            }
        }

        return null;
    }
}
