package projects.QRCode.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import projects.QRCode.R;
import projects.QRCode.dal.UserRepository;
import projects.QRCode.data.User;

public class FriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFriends();
    }

    public void getFriends() {
        UserRepository userRepository = new UserRepository(this);
        User[] users = userRepository.getAllUser();

        ListView listView = (ListView) findViewById(R.id.lstvFriends);
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, users);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
