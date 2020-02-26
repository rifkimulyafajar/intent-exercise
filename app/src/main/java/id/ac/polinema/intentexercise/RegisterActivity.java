package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import id.ac.polinema.intentexercise.model.User;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    private ImageView avatarImage;


    private TextInputEditText input_fullname, input_email, input_password,
            input_conPassword, input_homepage, input_about;

    private Uri UriImage;
    private Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_fullname = findViewById(R.id.text_fullname);
        input_email = findViewById(R.id.text_email);
        input_password = findViewById(R.id.text_password);
        input_conPassword = findViewById(R.id.text_confirm_password);
        input_homepage = findViewById(R.id.text_homepage);
        input_about = findViewById(R.id.text_about);
        avatarImage = findViewById(R.id.image_profile);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                try {
                    UriImage = data.getData();
                    bit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImage);
                    avatarImage.setImageBitmap(bit);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void changeProfile(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void handleProfile(View view) {
        String fullname = input_fullname.getText().toString();
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();
        String conPassword = input_conPassword.getText().toString();
        String homepage = input_homepage.getText().toString();
        String about = input_about.getText().toString();

        if (fullname.isEmpty()) {
            input_fullname.setError("this column cannot be empty");
        }
        else if (email.isEmpty()) {
            input_email.setError("this column cannot be empty");
        }
        else if (password.isEmpty()) {
            input_password.setError("this column cannot be empty");
        }
        else if (! conPassword.equals(password)) {
            input_conPassword.setError("The password must be the same");
        }
        else if (homepage.isEmpty()) {
            input_homepage.setError("this column cannot be empty");
        }
        else if (homepage.isEmpty()) {
            input_homepage.setError("this column cannot be empty");
        }
        else if (about.isEmpty()) {
            input_about.setError("this column cannot be empty");
        }
        else {

            User user = new User(fullname, email, password, conPassword, homepage, about, UriImage);
            Intent intent = new Intent(this, ProfileActivity.class);

            intent.putExtra("user", user);
            startActivity(intent);
        }
    }
}
