package projects.QRCode.client;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import projects.QRCode.R;
import projects.QRCode.dal.UserRepository;
import projects.QRCode.server.Server;
import projects.QRCode.service.InternetService;
import projects.QRCode.service.UserService;
import projects.QRCode.task.LongPollingTask;

public class MainActivity extends AppCompatActivity {
    private Server server;

    private static final int REQUEST_SCANNER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        server = new Server();
        TextView txtIP = (TextView) findViewById(R.id.txtIP);
        txtIP.setText(InternetService.getIP(this));

        AsyncTask task = new LongPollingTask();
        task.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SCANNER && resultCode == RESULT_OK) {
            UserRepository userRepository = new UserRepository(this);
            userRepository.addUser(UserService.fromJson(data.getStringExtra("SCAN_RESULT")));
        }
    }

    public void openScannner(View view) {
        Intent intent = new Intent(this, ScannerActivity.class);
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, REQUEST_SCANNER);
    }
}
