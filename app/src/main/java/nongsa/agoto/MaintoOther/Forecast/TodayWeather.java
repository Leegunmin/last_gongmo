package nongsa.agoto.MaintoOther.Forecast;

import com.tsengvn.typekit.TypekitContextWrapper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import nongsa.agoto.MaintoOther.Forecast.gps_information;
import nongsa.agoto.R;


public class TodayWeather extends AppCompatActivity implements OnClickListener {

    static String weather_citys;
    static TodayWeather exit_today ;

    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

    Typeface weatherFont;
    Button btn_intent;

    private gps_information gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather);
        exit_today = (TodayWeather) TodayWeather.this;

        btn_intent = (Button) findViewById(R.id.btn_intent);
        btn_intent.setOnClickListener(this);

        if (shouldAskPermissions()) {
            askPermissions();
        }


        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf");

        cityField = (TextView) findViewById(R.id.city_field);
        updatedField = (TextView) findViewById(R.id.updated_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) findViewById(R.id.humidity_field);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);


        Function.placeIdTask asyncTask = new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {
                weather_citys = weather_city;
                cityField.setText(weather_city);
                System.out.println("weather_city");
                updatedField.setText(weather_updatedOn);
                System.out.println("weather_updatedOn");
                detailsField.setText(weather_description);
                System.out.println("weather_des");
                currentTemperatureField.setText(weather_temperature);
                System.out.println("weather_temp");
                humidity_field.setText("Humidity: " + weather_humidity);
                System.out.println("weather_hum");
                pressure_field.setText("Pressure: " + weather_pressure);
                System.out.println("weather_press"+weather_iconText);
                weatherIcon.setText(Html.fromHtml(weather_iconText));
                System.out.println("html");

            }
        });
        // GPS 사용유무 가져오기
        gps = new gps_information(TodayWeather.this);
        if (gps.isGetLocation()) {
            System.out.println("lat1: ");
            double latitude = gps.getLatitude();
            System.out.println("lat2: " + latitude);
            double longitude = gps.getLongitude();
            System.out.println("lon" + longitude);

            System.out.println("HELLO!");
            Toast.makeText(
                    getApplicationContext(),
                    "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                    Toast.LENGTH_LONG).show();
            System.out.println("GETHERE");
            asyncTask.execute("" + latitude, "" + longitude);
        }
        else{

            gps.showSettingsAlert();


        }


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
                "android.permission.READ_PHONE_STATE",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), Weather.class);
        startActivity(intent);
        finish();



    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}


