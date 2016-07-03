package projects.QRCode.client;

import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import projects.QRCode.R;
import projects.QRCode.server.Server;

public class MainActivity extends AppCompatActivity {
    private Server server;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();

        server = new Server();

        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        byte[] ipAddress = BigInteger.valueOf(wm.getConnectionInfo().getIpAddress()).toByteArray();

        try {
            InetAddress myaddr = InetAddress.getByAddress(ipAddress);
            StringTokenizer hostaddr = new StringTokenizer(myaddr.getHostAddress(), ".");
            String ipaddr = "";

            while (hostaddr.hasMoreTokens()) {
                ipaddr = hostaddr.nextToken()+ "." + ipaddr;
            }

            TextView txtIP = (TextView) findViewById(R.id.txtIP);
            txtIP.setText(ipaddr);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
