package projects.QRCode.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import projects.QRCode.R;
import projects.QRCode.service.UserService;
import projects.QRCode.dal.UserRepository;

public class ScannerActivity extends AppCompatActivity {

    static final int REQUEST_SCANNER = 1;

    private UserRepository userRepository = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        userRepository = new UserRepository(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_SCANNER && resultCode == RESULT_OK) {
            userRepository.addUser(UserService.fromJson(data.getStringExtra("SCAN_RESULT")));
        }
    }
}
