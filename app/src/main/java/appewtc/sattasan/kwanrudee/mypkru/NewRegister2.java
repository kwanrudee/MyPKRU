package appewtc.sattasan.kwanrudee.mypkru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class NewRegister2 extends AppCompatActivity implements View.OnClickListener {

    //Explicit
    private EditText nameEditText, userEditText, passwordEditText;
    private ImageView backImageView, humanImageView, cameraImageView;
    private Button button;

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
        }
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

    }
    // main Class
}
