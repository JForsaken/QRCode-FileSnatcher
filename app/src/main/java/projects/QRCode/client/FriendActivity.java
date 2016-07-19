package projects.QRCode.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import projects.QRCode.R;
import projects.QRCode.dal.UserRepository;
import projects.QRCode.data.User;

public class FriendActivity extends AppCompatActivity {

    private UserRepository userRepository;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        userRepository = new UserRepository(this);

        Bundle extras = getIntent().getExtras();
        user = userRepository.getUser(extras.getString("username"));

        TextView username = (TextView) findViewById(R.id.textViewUsername);
        username.setText(user.getUsername());
    }

    public void getFiles(View view) {
    }

    public void getFriends(View view) {
    }

}
