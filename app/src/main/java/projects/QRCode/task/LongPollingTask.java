package projects.QRCode.task;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import projects.QRCode.server.HttpController;

/**
 * Created by joseph on 2016-07-03.
 */
public class LongPollingTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] objects) {

        OkHttpClient longPollingClient;
        ConnectionPool connectionPool;
        Response response = null;
        Request request;


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //Création du connection pool pour permettre la requête HTTP de rester ouverte selon un temps précis
        connectionPool = new ConnectionPool(5, 45, TimeUnit.SECONDS);
        builder.connectionPool(connectionPool);
        //Création du client qui permet de faire une requête HTTP
        longPollingClient = builder.build();

        do {

            try {
                //Création d'une nouvelle requête sur le url désiré
                request = new Request.Builder().url("http://127.0.0.1:8080/files?id=2").build();
                //Exécution de la requête est attente de la réponse du serveur
                response = longPollingClient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Si nous avons un réponse quelconque nous éffectuons une action précise
            if (response != null) {

                if (response.code() == 200) {

                } else if (response.code() == 408) {

                }
            }

        }
        //Effectuer un long polling tant que le serveur demandé est présent
        while (response != null && response.code() != 404);


        return null;
    }
}
