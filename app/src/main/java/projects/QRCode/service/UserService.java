package projects.QRCode.service;


import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import projects.QRCode.data.User;

/**
 * Created by joseph on 2016-07-03.
 */
public class UserService {

    public static User getCurrentUser(Context context) {
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        return new User("test", InternetService.getIP(context),
                String.valueOf(InternetService.PORT));
    }

    public static User fromJson(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gSon = gsonBuilder.create();
        return gSon.fromJson(json, User.class);
    }

    public static String toJson(User user) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gSon = gsonBuilder.create();
        return gSon.toJson(user);
    }
}
