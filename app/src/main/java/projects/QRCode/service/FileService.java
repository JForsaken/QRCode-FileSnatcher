package projects.QRCode.service;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

/**
 * Created by joseph on 2016-07-06.
 */
public class FileService {

    public static String[] getFilesNames() {
        String path = Environment.getExternalStorageDirectory().toString();
        File folder = new File(path);
        return folder.list();
    }

    public static FileInputStream getFileInputStream(String name) {
        String path = Environment.getExternalStorageDirectory().toString();
        File file = new File(path, name);
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return fis;
    }

    public static String toJson(String[] files) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gSon = gsonBuilder.create();
        return gSon.toJson(files);
    }

    public static String getFileType(FileInputStream fis) {
        try {
            return URLConnection.guessContentTypeFromStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
