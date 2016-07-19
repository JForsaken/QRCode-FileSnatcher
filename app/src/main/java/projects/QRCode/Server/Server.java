package projects.QRCode.server;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import fi.iki.elonen.NanoHTTPD;
import projects.QRCode.dal.UserRepository;
import projects.QRCode.data.User;
import projects.QRCode.service.FileService;
import projects.QRCode.service.InternetService;
import projects.QRCode.service.UserService;

/**
 * Created by joseph on 2016-07-02.
 */
public class Server extends NanoHTTPD {

    private static final String MIME_JSON = "application/json";
    private LinkedBlockingQueue blockingQueue;
    private UserRepository userRepository;

    public Server(Context context) {
        super(InternetService.PORT);

        userRepository = new UserRepository(context);

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


            if (uri.contains(".mp3")) {
                return new NanoHTTPD.Response(Response.Status.OK, "audio/mp3", new BufferedInputStream(new FileInputStream(uri)));
            }

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
                return handleGet(uri, headers, parms);
            }

            if(Method.POST.equals(method)) {
                return handlePost(session, uri, headers, parms);
            }

            if (Method.DELETE.equals(method)) {
                return handleDelete(uri, headers, parms);
            }

            throw new Resources.NotFoundException();

        } while(false);
    }

    private Response handleGet(String uri, Map<String, String> headers, Map<String, String> parms) throws FileNotFoundException {
        final String action = uri.substring(1).toLowerCase().split("/")[0];

        switch(action) {
            case "file":

                String fileName = uri.split("/")[1];
                FileInputStream fis = FileService.getFileInputStream(fileName);
                String MIME_TYPE = FileService.getFileType(fis);

                return new NanoHTTPD.Response(Response.Status.OK, MIME_TYPE, new BufferedInputStream(fis));
            case "files":
                return new NanoHTTPD.Response(Response.Status.OK, MIME_JSON, FileService.toJson(FileService.getFilesNames()));
            case "friends":
                return new NanoHTTPD.Response(Response.Status.OK, MIME_JSON, UserService.toJson(userRepository.getAllUser()));
            case "stillalive":
                break;
        }

        /*if (isLongLivedRequest(headers)) {
            try {
                    Object o = blockingQueue.poll(40, TimeUnit.SECONDS);

                if (o != null) {
                    return new Response(Response.Status.OK, MIME_PLAINTEXT, "");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        return new Response(Response.Status.NO_CONTENT, MIME_PLAINTEXT, "");
    }

    private Response handlePost(IHTTPSession session, String uri, Map<String, String> headers, Map<String, String> parms) throws IOException, ResponseException {
        final String action = uri.substring(1).toLowerCase().split("/")[0];

        switch(action) {
            case "online":

                Integer contentLength = Integer.parseInt(headers.get( "content-length" ));
                byte[] buf = new byte[contentLength];
                String username = null;

                try
                {
                    session.getInputStream().read( buf, 0, contentLength );
                    username = new String(buf);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }

                User user = userRepository.getUser(username);
                user.setOnline(true);
                userRepository.updateUser(user);

                return new NanoHTTPD.Response("OK");
            case "notify":

                //CREER UNE NOTIFICAITON

                break;
        }

        return null;
    }

    private Response handleDelete(String uri, Map<String, String> headers, Map<String, String> parms) throws IOException, ResponseException {
        final String action = uri.substring(1).toLowerCase().split("/")[0];

        switch(action) {
            case "notify":

                //CREER UNE NOTIFICAITON

                break;
        }

        return null;
    }



    private boolean isLongLivedRequest(Map<String, String> headers) {
        return headers.containsValue("Keep-Alive");
    }

    private class NotFoundException extends RuntimeException {
    }
}
