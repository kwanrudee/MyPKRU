package appewtc.sattasan.kwanrudee.mypkru;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class NewRegister2 extends AppCompatActivity implements View.OnClickListener {

    //Explicit
    private EditText nameEditText, userEditText, passwordEditText;
    private ImageView backImageView, humanImageView, cameraImageView;
    private Button button;
    private Uri humanUri, camaraUri;
    private String pathImageString, nameImageString,
            nameUserString, userString,passwordString;
    private boolean aBoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register2);

        //Initial View
        initialView();

        //Controller
        controller();

    }  // main Method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            //For Human
        if ((requestCode == 0)&& (resultCode == RESULT_OK)) {
            Log.d("24MayV1", "Human OK");
            aBoolean = false;

            //Show Image
            humanUri = data.getData();
            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(humanUri));
                humanImageView.setImageBitmap(bitmap);

                findPathAnNAme(humanUri);

            } catch (Exception e) {
                Log.d("24MayV1","e humanUei ==> " + e.toString());
            }

        }   // if human
            // for Camera
        if ((requestCode == 1)&&(resultCode == RESULT_OK)) {

            Log.d("24MayV1", "Camera Result OK");
            aBoolean = false;

            //Show Image
            camaraUri = data.getData();
            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(camaraUri));
                humanImageView.setImageBitmap(bitmap);

                findPathAnNAme(camaraUri);

            } catch (Exception e) {
                Log.d("24MayV1", " e camera ==> " + e.toString());
            }

        }  // if Camera

    }   // onActivity

    private void findPathAnNAme(Uri uri) {

        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            pathImageString = cursor.getString(index);

        } else {
            pathImageString = uri.getPath();
        }
        Log.d("24MayV1", "Path ==> " + pathImageString);
        nameImageString = pathImageString.substring(pathImageString.indexOf("/"));
        Log.d("24MayV1", "Name ==> " + nameImageString);

    }

    private void controller() {
        backImageView.setOnClickListener(this);
        humanImageView.setOnClickListener(this);
        cameraImageView.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    private void initialView() {
        nameEditText = (EditText) findViewById(R.id.edtName);
        userEditText = (EditText) findViewById(R.id.edtUser);
        passwordEditText = (EditText) findViewById(R.id.edtpassword);
        backImageView = (ImageView) findViewById(R.id.btnBack);
        humanImageView= (ImageView) findViewById(R.id.imvHumen);
        cameraImageView = (ImageView) findViewById(R.id.imvCamera);
        button = (Button) findViewById(R.id.btnRegister);

    }

    @Override
    public void onClick(View v) {

        //For Back
        if (v == backImageView) {
            finish();
        }

          //For Human
        if (v == humanImageView) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Please Choose App for Choose Image"), 0);
        }

        //For camera
        if (v == cameraImageView) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }

        // For Register
        if (v == button) {

            //Get Value From Edit Text
            nameUserString = nameEditText.getText().toString().trim();
            userString = userEditText.getText().toString().trim();
            passwordString = passwordEditText.getText().toString().trim();

            //Check space
            if (nameUserString.equals("") || userString.equals("") || passwordString.equals("")) {
                //Have Space
                MyAlert myAlert = new MyAlert(this);
                myAlert.myDialog(getResources().getString(R.string.titleHaveSpace),
                        getResources().getString(R.string.messageHaveSpace));
            } else if (aBoolean) {
                //No Image
                MyAlert myAlert = new MyAlert(this);
                myAlert.myDialog(getResources().getString(R.string.titleNoImage),
                        getResources().getString(R.string.messageNoImage));

            } else {
                //Upload Value to Server
                uploadValueToServer();

            }
        }
    }

    private void uploadValueToServer() {
        try {

            //Change policy
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        //Up Image to Server
            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21,
                    "pkru@swiftcodingthai.com", "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("ImageKwan");
            simpleFTP.stor(new File(pathImageString));
            simpleFTP.disconnect();

            Toast.makeText(NewRegister2.this, "Upload Image Success",
                    Toast.LENGTH_SHORT).show();

            // Up Date mySQL
            String urlPHP = "http://swiftcodingthai.com/pkru/addUserMaster.php";
            PostNewUser postNewUser = new PostNewUser(this);
            postNewUser.execute(nameUserString, userString, passwordString, nameImageString
                    , urlPHP);

            if (Boolean.parseBoolean(postNewUser.get())) {
                finish();
            } else {
                Toast.makeText(NewRegister2.this, "Error Update", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.d("24MayV1", "e upload ==>" + e.toString());
        }
    }
    // main Class
}
