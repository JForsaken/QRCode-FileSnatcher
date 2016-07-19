package projects.QRCode.data;

/**
 * Created by joseph on 2016-07-03.
 */
public class User {
    private String username;
    private String IP;
    private String port;
    private boolean online;

    public User(String username, String IP, String port) {
        this.username = username;
        this.IP = IP;
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String toString() {

        String textOnline;

        if (online) {
            textOnline = "online";
        }
        else {
            textOnline = "offline";
        }

        return String.format("%1$s %2$s", username, textOnline);
    }

}
