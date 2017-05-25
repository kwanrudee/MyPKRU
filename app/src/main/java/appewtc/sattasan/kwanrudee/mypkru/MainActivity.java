package appewtc.sattasan.kwanrudee.mypkru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Explicit ประกาศตัวแปร
    private EditText userEditText, passwordEditText;
    private TextView textView;
    private Button button;
    private String userString, passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initial View
        initalView();

        // controller
        controller();

    }  // main Method นี้คือคลาสเม็ดดอต

    private void controller() {
        textView.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    private void initalView() {
        userEditText = (EditText) findViewById(R.id.edtUser);
        passwordEditText = (EditText) findViewById(R.id.edtpassword);
        textView = (TextView) findViewById(R.id.txtNewRegister);
        button = (Button) findViewById(R.id.btnLogin);

    }

    @Override
    public void onClick(View v) {

       //For TextView
        if (v == textView) {
             //Intent to NewRegister
            Intent intent = new Intent(MainActivity.this,NewRegister2.class);
            startActivity(intent);
        }

        //for Button
        if (v == button) {
            userString = userEditText.getText().toString().trim();
            passwordString = passwordEditText.getText().toString().trim();

            if (userString.equals("") || passwordString.equals("")) {
                //Have Space
                MyAlert myAlert = new MyAlert(this);
                myAlert.myDialog(getResources().getString(R.string.titleHaveSpace),
                        getResources().getString(R.string.messageHaveSpace));

            } else {
                // No Space
                checkUserAnPass();
            }
        }


    }

    private void checkUserAnPass() {

        String urlPHP = "http://swiftcodingthai.com/pkru/GetUserKwan.php";

    }
} // Main Class นี้คือคลาสหลัก

