package projects.QRCode.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import projects.QRCode.R;
import projects.QRCode.dal.UserRepository;
import projects.QRCode.data.User;
import projects.QRCode.server.Server;
import projects.QRCode.service.UserService;

public class MainActivity extends AppCompatActivity {
    private Server server;

    private static final int REQUEST_SCANNER = 1;

    private final static String QRCODE_MENU = "Qr Code";
    private final static String SCANNER = "Scanner";
    private final static String FRIENDS = "Friends";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        server = new Server(this);

        String[] menu = {QRCODE_MENU, SCANNER, FRIENDS};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);

        ListView listView = (ListView)findViewById(R.id.listViewMenu);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String menuText = ((TextView) view).getText().toString();

                switch (menuText) {
                    case QRCODE_MENU:
                        openQrCode();
                        break;
                    case SCANNER:
                        openScannner();
                        break;
                    case FRIENDS:
                        openFriends();
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SCANNER && resultCode == RESULT_OK) {
            UserRepository userRepository = new UserRepository(this);
            userRepository.addUser(UserService.fromJson(data.getStringExtra("SCAN_RESULT")));
        }
    }

    public void openQrCode() {
        Intent intent = new Intent(this, QrActivity.class);
        startActivity(intent);
    }

    public void openScannner() {
        Intent intent = new Intent(this, ScannerActivity.class);
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, REQUEST_SCANNER);
    }

    public void openFriends() {
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }
}
