package projects.QRCode.server;

import java.io.IOException;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by joseph on 2016-07-02.
 */
public class HttpController {

    private OkHttpClient longPollingClient;
    private ConnectionPool connectionPool;

    public HttpController() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(connectionPool);
        longPollingClient = builder.build();
    }

    public Response executeLongPolling(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = longPollingClient.newCall(request).execute();
        return response;
    }
}
