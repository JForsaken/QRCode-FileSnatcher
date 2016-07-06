package projects.QRCode.service;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by joseph on 2016-07-03.
 */
public class InternetService {

    public static final int PORT = 8080;

    public static String getIP(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ipAddress);
    }

    public static String encode(String str) {
        byte [] octets = new byte[0];
        try {
            octets = str.getBytes( "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(octets, Base64.URL_SAFE);
    }

    public static String decode(String str) {
        byte [] octets = Base64 . decode(str, Base64.URL_SAFE );
        try {
            return new String(octets, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
