package project.hotelsync.com.hotelsync;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView signUpNow = (TextView)findViewById(R.id.signUpLinkText);
        final Intent signUpPage = new Intent(this, UserSignUp.class);
        signUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(signUpPage);
            }
        });
    }


}
