package nongsa.agoto.MaintoOther.vacant;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import nongsa.agoto.R;


public class showvacant extends Activity {
    Handler handler = new Handler();

    ImageView image_dial;
    TextView big_city;

    TextView full_city;
    TextView price;
    TextView field_size;
    TextView build_size;
    TextView build_year;
    TextView vacant_year;
    TextView name;
    TextView phone;

    Button phone_dial;
    Button close_dial;
    String finalPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_showvacant);

        image_dial = (ImageView) findViewById(R.id.image_dial);

        big_city = (TextView) findViewById(R.id.cityname);
        full_city = (TextView) findViewById(R.id.full_city_name);
        price = (TextView) findViewById(R.id.price);
        field_size = (TextView) findViewById(R.id.field_size);
        build_size = (TextView) findViewById(R.id.build_size);
        build_year = (TextView) findViewById(R.id.build_year);
        vacant_year = (TextView) findViewById(R.id.vacant_year);
        name = (TextView) findViewById(R.id.ownner);
        phone = (TextView) findViewById(R.id.phone_number);
        close_dial = (Button)findViewById(R.id.close_dial);
        phone_dial = (Button)findViewById(R.id.phone_dial);

        Intent intent = getIntent();
        String image_url = intent.getStringExtra("image_url");
        String bigcity = intent.getStringExtra("bigcity");
        String smallcity = intent.getStringExtra("smallcity");
        String fullcity = intent.getStringExtra("fullcity");
        String pricing = intent.getStringExtra("price");
        String fieldsize = intent.getStringExtra("field_size");
        String buildsize = intent.getStringExtra("build_size");
        String buildyear = intent.getStringExtra("build_year");
        String vacantyear = intent.getStringExtra("vacant_year");
        String naming = intent.getStringExtra("name");
        final String phoneing = intent.getStringExtra("phone");
        finalPhone = phoneing;

        //loadBitmap("http://52.79.61.227/img/asd.bmp");
        //image_dial.setImageURI(ImgId);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다


            }
        });

        t.start();
        if(image_url.equals("null")){
            Glide.with(showvacant.this).load(R.drawable.noimage).into(image_dial);
        }
        else {
            Glide.with(showvacant.this).load(image_url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image_dial);
        }
        big_city.setText("도시명 : "+bigcity+" "+smallcity);
        full_city.setText(fullcity);
        if(pricing.equals("null")){
            price.setText("협의가 : 협의 후 결정");
        }
        else {
            price.setText("협의가 : " + pricing);
        }
        field_size.setText("대지 면적 : "+ fieldsize);
        if(buildsize.equals("null")||buildsize.equals("0")){
            build_size.setText("건축 면적 : 정보 없음");
        }
        else {
            build_size.setText("건축 면적 : " + buildsize);
        }
        if(buildyear.equals("null")){
            build_year.setText("건축년도 : 정보 없음");
        }
        else {
            build_year.setText("건축년도 : " + buildyear);
        }
        if(vacantyear.equals("null")) {
            vacant_year.setText("빈집 발생년도 : 정보 없음");
        }
        else {
            vacant_year.setText("빈집 발생년도 : " + vacantyear);
        }
        if(naming.equals("null")){
            name.setText("소유주 : 비공개");
        }else {
            name.setText("소유주 : " + naming);
        }
        if(phoneing.equals("null")){
            phone.setText("전화번호 : 비공개");
        }
        else {
            phone.setText("전화번호 : " + phoneing);
        }

        phone_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        close_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { // 사용자의 OS 버전이 마시멜로우 이상인지 체크한다.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        /*사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 확인한다.
                         * Android는 C언어 기반으로 만들어졌기 때문에 Boolean 타입보다 Int 타입을 사용한다. */
                        int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);
                        /*패키지는 안드로이드 어플리케이션의 아이디이다.
                         *현재 어플리케이션이 CALL_PHONE에 대해 거부되어있는지 확인한다. */
                        if (permissionResult == PackageManager.PERMISSION_DENIED) {
                            /* 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다.
                            * 거부한적이 있으면 True를 리턴하고 * 거부한적이 없으면 False를 리턴한다. */
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(showvacant.this);
                                dialog.setTitle("권한이 필요합니다.")
                                        .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
                                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                /*새로운 인스턴스(onClickListener)를 생성했기 때문에
                                                * 버전체크를 다시 해준다. */
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    // CALL_PHONE 권한을 Android OS에 요청한다.
                                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                                }
                                            }
                                        })
                                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(showvacant.this, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                            // 최초로 권한을 요청할 때
                            else {
                                // CALL_PHONE 권한을 Android OS에 요청한다.
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                            }
                        }
                        // CALL_PHONE의 권한이 있을 때
                        else {
                            // 즉시 실행
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+finalPhone));
                            startActivity(intent);
                        }
                    }
                    // 마시멜로우 미만의 버전일 때
                    else {
                        // 즉시 실행
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+finalPhone));
                        startActivity(intent);
                    }

                }
            }

        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            // 요청한 권한을 사용자가 "허용" 했다면...
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ finalPhone));
                // Add Check Permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            } else {
                Toast.makeText(showvacant.this, "권한요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadBitmap(String strUrl) {

        Bitmap bitmap = null;

        try {

            URL url = new URL(strUrl.toString());

            HttpURLConnection conn =  (HttpURLConnection)url.openConnection();

            conn.setDoInput(true);

            conn.connect();



            InputStream is = conn.getInputStream();

            bitmap = BitmapFactory.decodeStream(is);

            image_dial.setImageBitmap(bitmap);

        } catch (IOException e)

        {

            e.printStackTrace();

        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}

