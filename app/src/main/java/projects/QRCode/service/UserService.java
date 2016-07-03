package projects.QRCode.service;


import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import projects.QRCode.data.User;

/**
 * Created by joseph on 2016-07-03.
 */
public class UserService {

    public static User getCurrentUser(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName = myDevice.getName();

        return new User(deviceName, String.valueOf(ipAddress), "8080");
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
