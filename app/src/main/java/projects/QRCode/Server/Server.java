package projects.QRCode.server;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import fi.iki.elonen.NanoHTTPD;
import projects.QRCode.service.InternetService;

/**
 * Created by joseph on 2016-07-02.
 */
public class Server extends NanoHTTPD {

    private static final String MIME_JSON = "application/json";
    private LinkedBlockingQueue blockingQueue = new LinkedBlockingQueue();

    public Server() {
        super(InternetService.PORT);

        try {
            start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            Method method = session.getMethod();
            String uri = session.getUri();
            Map<String, String> headers = session.getHeaders();
            Map<String, String> parms = session.getParms();

            return serve(session, uri, method, headers, parms);

        } catch (IOException ioe) {
            return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
        } catch (ResponseException re) {
            return new Response(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
        } catch (NotFoundException nfe) {
            return new NanoHTTPD.Response(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Not Found");
        } catch (Exception ex) {
            return new Response(Response.Status.INTERNAL_ERROR, MIME_HTML, "<html><body><h1>Error</h1>" + ex.toString() + "</body></html>");
        }
    }

    private Response serve(IHTTPSession session, String uri, Method method, Map<String, String> headers, Map<String, String> parms)  throws IOException, ResponseException {

        do {
            if(Method.GET.equals(method)) {
                return handleGet(session, headers, parms);
            }

            if(Method.POST.equals(method)) {
                return handlePost(session);
            }

            throw new Resources.NotFoundException();

        } while(false);
    }

    private Response handleGet(IHTTPSession session, Map<String, String> headers, Map<String, String> parms) {
        int c = 0;

        if (isLongLivedRequest(headers)) {
            try {
                Object o = blockingQueue.poll(20, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            c = 1;
        }

        return null;
    }

    private Response handlePost(IHTTPSession session) throws IOException, ResponseException {

        return null;
    }

    private boolean isLongLivedRequest(Map<String, String> headers) {
        //return headers.containsValue("long-lived");
        return true;
    }

    private class NotFoundException extends RuntimeException {
    }
}
