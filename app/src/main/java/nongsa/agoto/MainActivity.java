package nongsa.agoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;



import com.tsengvn.typekit.TypekitContextWrapper;

import android.annotation.TargetApi;

import nongsa.agoto.MaintoOther.Forecast.TodayWeather;
import nongsa.agoto.MaintoOther.Information;
import nongsa.agoto.MaintoOther.Like;
import nongsa.agoto.MaintoOther.TheOthers;
import nongsa.agoto.MaintoOther.billing.billingactivity;
import nongsa.agoto.loginandregistration.activity.Board;


public class MainActivity extends AppCompatActivity {

    Button like;
    Button information;
    Button weather;
    Button my_unit;
    Button turn;
    Button theothers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        my_unit = (Button)findViewById(R.id.myUnit);
        like = (Button)findViewById(R.id.like);
        information = (Button)findViewById(R.id.information);
        weather = (Button)findViewById(R.id.weather);
        turn = (Button)findViewById(R.id.turn);
        theothers = (Button)findViewById(R.id.theothers);
        if (shouldAskPermissions()) {
            askPermissions();
        }


        like.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Like.class);
                startActivity(intent);
            }
        });
        information.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Information.class);
                startActivity(intent);
            }
        });
        weather.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TodayWeather.class);
                startActivity(intent);
            }
        });
        turn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Board.class);
                startActivity(intent);
            }
        });
        theothers.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TheOthers.class);
                startActivity(intent);
            }
        });
    }
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.INTERNET",
                "android.permission.ACCESS_NETWORK_STATE",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "com.android.vending.BILLING"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
