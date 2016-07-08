package projects.QRCode.task;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.Response;
import projects.QRCode.data.User;
import projects.QRCode.server.HttpController;

/**
 * Created by joseph on 2016-07-07.
 */
public class FriendsTask extends AsyncTask {
    HttpController httpController;

    @Override
    protected Object doInBackground(Object[] objects) {
        User user = (User) objects[0];
        String url = user.getIP() + ":" + user.getPort() + "/friends";
        Response response;
        String friends = null;

        try {
            response = httpController.executeGET(url);
            friends = response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        httpController = new HttpController();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        onProgressUpdate(o);
    }
}
