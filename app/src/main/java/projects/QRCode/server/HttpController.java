package projects.QRCode.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import fi.iki.elonen.NanoHTTPD;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by joseph on 2016-07-02.
 */
public class HttpController {

    private OkHttpClient longPollingClient;
    private OkHttpClient httpClient;
    private ConnectionPool connectionPool;

    public HttpController() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        connectionPool = new ConnectionPool(5, 45, TimeUnit.SECONDS);
        builder.connectionPool(connectionPool);
        longPollingClient = builder.build();
        httpClient = new OkHttpClient();
    }

    public Response executeLongPolling(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = longPollingClient.newCall(request).execute();
        return response;
    }

    public Response executeGET(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        return response;
    }
}
