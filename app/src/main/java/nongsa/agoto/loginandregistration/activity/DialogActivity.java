package nongsa.agoto.loginandregistration.activity;
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


import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import nongsa.agoto.R;


public class DialogActivity extends Activity {
    Handler handler = new Handler();

    ImageView image_dial;
    TextView name_dial;
    TextView exp_dial;
    TextView grow_dial;
    TextView nation_dial;
    TextView intro_dial;
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
        setContentView(R.layout.activity_dialog);

        image_dial = (ImageView) findViewById(R.id.image_dial);

        name_dial = (TextView) findViewById(R.id.name_dial);
        exp_dial = (TextView) findViewById(R.id.exp_dial);
        grow_dial = (TextView) findViewById(R.id.grow_dial);
        nation_dial = (TextView) findViewById(R.id.nation_dial);
        intro_dial = (TextView) findViewById(R.id.intro_dial);

        phone_dial = (Button) findViewById(R.id.phone_dial);
        close_dial = (Button) findViewById(R.id.close_dial);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int exp = intent.getIntExtra("exp", 0);
        String grow = intent.getStringExtra("grow");
        String nation = intent.getStringExtra("nation");
        String intro = intent.getStringExtra("intro");

        final String phone = intent.getStringExtra("phone");
        finalPhone = phone;
        System.out.println(name);

        final Uri imgId = Uri.parse(intent.getStringExtra("imgld"));
        System.out.println(intent.getStringExtra("imgld"));
        //loadBitmap("http://52.79.61.227/img/asd.bmp");
        //image_dial.setImageURI(ImgId);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                // TODO Auto-generated method stub
                try{
                    // 걍 외우는게 좋다 -_-;
                    URL url = new URL(imgId.toString());
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            image_dial.setImageBitmap(bm);
                        }
                    });
                } catch(Exception e){

                }

            }
        });

        t.start();
        System.out.println("BEFORE:"+name);
        name_dial.setText("이름 : " + name);
        System.out.println("AFTER:"+name);
        exp_dial.setText("경력 : " + exp);
        grow_dial.setText("재배 작물 : " + grow);
        nation_dial.setText("지역 : " + nation);
        intro_dial.setText("소개 : " + intro);

        close_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        phone_dial.setOnClickListener(new View.OnClickListener() {
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
                                AlertDialog.Builder dialog = new AlertDialog.Builder(DialogActivity.this);
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
                                                Toast.makeText(DialogActivity.this, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DialogActivity.this, "권한요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
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



