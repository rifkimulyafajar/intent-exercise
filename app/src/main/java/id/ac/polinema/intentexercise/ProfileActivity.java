package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import id.ac.polinema.intentexercise.model.User;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvAbout, tvName, tvEmail, tvHomepage;
    private ImageView imgProfile;
    private Bitmap bitmap;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvAbout = findViewById(R.id.label_about);
        tvName = findViewById(R.id.label_fullname);
        tvEmail = findViewById(R.id.label_email);
        tvHomepage = findViewById(R.id.label_homepage);
        imgProfile = findViewById(R.id.image_profile);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            User user = bundle.getParcelable("user");

            tvName.setText(user.getUsername());
            tvEmail.setText(user.getEmail());
            tvAbout.setText(user.getAbout());
            tvHomepage.setText(user.getHomepage());
            imageUri = user.getImageUri();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            imgProfile.setImageBitmap(bitmap);
        }
    }
}
