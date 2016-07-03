package projects.QRCode.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import projects.QRCode.R;
import projects.QRCode.service.UserService;

public class QrActivity extends AppCompatActivity {

    private final String URL =  "http://zxing.org/w/chart?cht=qr&chs=350x350&chld=L&choe=UTF-8&chl=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        generateQRCode();
    }

    private void generateQRCode() {
        ImageView qrImage = (ImageView) findViewById(R.id.qrImageView);
        Picasso.with(this).load(URL + UserService.toJson(UserService.getCurrentUser(this))).into(qrImage);
    }
}
