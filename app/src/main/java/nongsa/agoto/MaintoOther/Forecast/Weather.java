package nongsa.agoto.MaintoOther.Forecast;
import com.tsengvn.typekit.TypekitContextWrapper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nongsa.agoto.R;


public class Weather extends AppCompatActivity {
    public static final int THREAD_HANDLER_SUCCESS_INFO = 1;
    TextView tv_WeatherInfo;
    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    ForeCastManager mForeCast;
    gps_information gps_forecast;
    static String lat;
    static String lon;
    Typeface weatherFont;
    Button btn_next;
    int num = 0;
    Button btn_back;

    Weather mThis;
    ArrayList<ContentValues> mWeatherData;
    ArrayList<WeatherInfo> mWeatherInfomation;

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherforecast);
        Intent intent =  getIntent();
        btn_next = (Button)findViewById(R.id.btn_intent);
        btn_back = (Button)findViewById(R.id.btn_back);
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf");
        cityField = (TextView) findViewById(R.id.city_field);
        updatedField = (TextView) findViewById(R.id.updated_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) findViewById(R.id.humidity_field);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        btn_back.setOnClickListener(new View.OnClickListener(){ // 주요정보 - 빈집
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),TodayWeather.class);
                startActivity(intent);
                finish();

            }
        });
        btn_next.setOnClickListener(new View.OnClickListener(){ // 주요정보 - 빈집
            public void onClick(View v){
                num++;
                PrintValue(num);

            }
        });

        gps_forecast = new gps_information(Weather.this);
        if (gps_forecast.isGetLocation()) {
            System.out.println("lat1: ");
            double latitude = gps_forecast.getLatitude();
            System.out.println("lat2: " + latitude);
            double longitude = gps_forecast.getLongitude();
            System.out.println("lon" + longitude);

            System.out.println("HELLO22!");
            Toast.makeText(
                    getApplicationContext(),
                    "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                    Toast.LENGTH_LONG).show();
            lon = "" + latitude;
            lat = "" + longitude;


        }
        else{
            gps_forecast.showSettingsAlert();

        }
        Initialize();
    }

    public void Initialize()
    {
        //tv_WeatherInfo = (TextView)findViewById(R.id.tv_WeatherInfo);

        mWeatherInfomation = new ArrayList<>();
        mThis = this;
        mForeCast = new ForeCastManager(lon,lat,mThis);
        mForeCast.run();
    }

    public void PrintValue(int i)
    {
        String mData = "";
        //for(int i = 0; i < mWeatherInfomation.size(); i ++)
        // {
        cityField .setText(TodayWeather.weather_citys);
        updatedField .setText(mWeatherInfomation.get(i).getWeather_Day() );
        detailsField .setText(mWeatherInfomation.get(i).getWeather_Name());
        currentTemperatureField .setText(mWeatherInfomation.get(i).getTemp_Max()+"도");
        humidity_field  .setText(mWeatherInfomation.get(i).getTemp_Min()+"도");
        pressure_field .setText(mWeatherInfomation.get(i).getHumidity()+"%");
        if(mWeatherInfomation.get(i).getWeather_Name().equals("sky is clear")){
            weatherIcon.setText(Html.fromHtml("&#xf00d"));
        }
        else if(mWeatherInfomation.get(i).getWeather_Name().equals("moderate rain")) {

            weatherIcon.setText(Html.fromHtml("&#xf013"));
        }
        else if(mWeatherInfomation.get(i).getWeather_Name().equals("light rain")) {

            weatherIcon.setText(Html.fromHtml("&#xf00d"));
        }
        else if(mWeatherInfomation.get(i).getWeather_Name().equals("heavy intensity rain")) {

            weatherIcon.setText(Html.fromHtml("&#xf00d"));
        }

        // }
        // return mData;
    }

    public void DataChangedToHangeul()
    {
        for(int i = 0 ; i <mWeatherInfomation.size(); i ++)
        {
            WeatherToHangeul mHangeul = new WeatherToHangeul(mWeatherInfomation.get(i));
            mWeatherInfomation.set(i,mHangeul.getHangeulWeather());
        }
    }


    public void DataToInformation()
    {
        for(int i = 0; i < mWeatherData.size(); i++)
        {
            mWeatherInfomation.add(new WeatherInfo(
                    String.valueOf(mWeatherData.get(i).get("weather_Name")),  String.valueOf(mWeatherData.get(i).get("weather_Number")), String.valueOf(mWeatherData.get(i).get("weather_Much")),
                    String.valueOf(mWeatherData.get(i).get("weather_Type")),  String.valueOf(mWeatherData.get(i).get("wind_Direction")),  String.valueOf(mWeatherData.get(i).get("wind_SortNumber")),
                    String.valueOf(mWeatherData.get(i).get("wind_SortCode")),  String.valueOf(mWeatherData.get(i).get("wind_Speed")),  String.valueOf(mWeatherData.get(i).get("wind_Name")),
                    String.valueOf(mWeatherData.get(i).get("temp_Min")),  String.valueOf(mWeatherData.get(i).get("temp_Max")),  String.valueOf(mWeatherData.get(i).get("humidity")),
                    String.valueOf(mWeatherData.get(i).get("Clouds_Value")),  String.valueOf(mWeatherData.get(i).get("Clouds_Sort")), String.valueOf(mWeatherData.get(i).get("Clouds_Per")),String.valueOf(mWeatherData.get(i).get("day"))
            ));

        }

    }
    public Handler handler = new Handler(){
        @Override      public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case THREAD_HANDLER_SUCCESS_INFO :
                    mForeCast.getmWeather();
                    mWeatherData = mForeCast.getmWeather();
                    if(mWeatherData.size() ==0)
                        tv_WeatherInfo.setText("데이터가 없습니다");

                    DataToInformation(); // 자료 클래스로 저장,

                    String data = "";
                    PrintValue(num);
                    //data = PrintValue();
                    DataChangedToHangeul();
                    //data = data + PrintValue();

                    // tv_WeatherInfo.setText(data);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}



