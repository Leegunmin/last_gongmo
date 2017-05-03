package nongsa.agoto.MaintoOther.vacant;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import nongsa.agoto.MaintoOther.information.adapter.fieldAdapter;
import nongsa.agoto.R;

public class field extends AppCompatActivity {
    public static field fieldactivity;
    private fieldAdapter m_Adapter;
    ListView field_listing;
    Button field_button;
    EditText field_edit;
    protected void onCreate(Bundle savedInstanceState) {
        m_Adapter = new fieldAdapter();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        final ArrayList<String> LIST_TITLE = new ArrayList<String>();
        final ArrayList<String> LIST_DESCRIPTION = new ArrayList<String>();
        final ArrayList<String> LIST_TELEPHONE = new ArrayList<String>();
        final ArrayList<String> LIST_ADDRESS = new ArrayList<String>();
        final ArrayList<String> LIST_ROADADRESS = new ArrayList<String>();
        field_button = (Button)findViewById(R.id.termbutton);
        field_edit = (EditText)findViewById(R.id.termedit);
        field_listing = (ListView)findViewById(R.id.field_list) ;
        fieldactivity = (field)field.this;
        //java코드로 특정 url에 요청보내고 응답받기
        //기본 자바 API를 활용한 방법



        field_button.setOnClickListener(new View.OnClickListener(){ // 주요정보- 기술정보
            public void onClick(View v) {
                m_Adapter = new fieldAdapter();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            String clientID = "iPGdgKzi83KtPts92cZ3"; //네이버 개발자 센터에서 발급받은 clientID입력
                            String clientSecret = "5Y9N77599E";        //네이버 개발자 센터에서 발급받은 clientSecret입력
                            String query = field_edit.getText().toString()+" 부동산";
                            query = URLEncoder.encode(query.toString(),"utf-8");
                            URL url = new URL("https://openapi.naver.com/v1/search/local?query="+query); //API 기본정보의 요청 url을 복사해오고 필수인 query를 적어줍니당!
                            String thing = "";
                            URLConnection urlConn = url.openConnection(); //openConnection 해당 요청에 대해서 쓸 수 있는 connection 객체

                            urlConn.setRequestProperty("X-Naver-Client-ID", clientID);
                            urlConn.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                            int number = 0;
                            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                            String msg = null;
                            for(int i = 0 ;i <6;i++){
                                msg = br.readLine();
                                if(msg.startsWith("\""+"display"+"\":")){
                                    String split[] =  msg.split("\""+"display"+"\": ");
                                    number = Integer.parseInt(split[1].replace(",",""));
                                    System.out.println(Integer.parseInt(split[1].replace(",","")));
                                }

                            }
                            if(number == 0){
                                Toast.makeText(field.this, "검색 결과가 없습니다.", Toast.LENGTH_LONG).show();
                            }
                            else {
                                while ((msg = br.readLine()) != null) {
                                    System.out.println(msg);
                                    if (msg.startsWith("\"" + "title" + "\":")) {
                                        String split[] = msg.split("\"" + "title" + "\": ");
                                        thing = split[1].replace(",", "");
                                        thing = thing.replace("\"", "");
                                        thing = thing.replace("<b>", " ");
                                        thing = thing.replace("</b>", " ");
                                        System.out.println(thing);
                                        LIST_TITLE.add(thing);
                                        m_Adapter.add(thing);
                                    }
                                    else if (msg.startsWith("\"" + "description" + "\":")) {
                                        String split[] = msg.split("\"" + "description" + "\": ");
                                        thing = split[1].replace(",", "");
                                        thing = thing.replace("\"", "");
                                        thing = thing.replace("<b>", " ");
                                        thing = thing.replace("</b>", " ");
                                        System.out.println(thing);
                                        LIST_DESCRIPTION.add(thing);
                                        m_Adapter.addDESCRIPTION(thing);
                                    }
                                    else if (msg.startsWith("\"" + "telephone" + "\":")) {
                                        String split[] = msg.split("\"" + "telephone" + "\": ");
                                        thing = split[1].replace(",", "");
                                        thing = thing.replace("\"", "");
                                        System.out.println(thing);
                                        LIST_TELEPHONE.add(thing);
                                        m_Adapter.addTELEPHONE(thing);
                                    }
                                    else if (msg.startsWith("\"" + "address" + "\":")) {
                                        String split[] = msg.split("\"" + "address" + "\": ");
                                        thing = split[1].replace(",", "");
                                        thing = thing.replace("\"", "");
                                        System.out.println(thing);
                                        LIST_ADDRESS.add(thing);
                                        m_Adapter.addADDRESS(thing);

                                    }
                                    else if (msg.startsWith("\"" + "roadAddress" + "\":")) {
                                        String split[] = msg.split("\"" + "roadAddress" + "\": ");
                                        thing = split[1].replace(",", "");
                                        thing = thing.replace("\"", "");
                                        System.out.println(thing);
                                        LIST_ROADADRESS.add(thing);
                                        m_Adapter.addROADADDRESS(thing);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
                try{
                    thread.join();
                    m_Adapter.notifyDataSetChanged();
                    field_listing.setAdapter(m_Adapter) ;
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

        });


            }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    public void showSettingsAlert(final String finalPhone ){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(field.this);

        alertDialog.setTitle("전화 연결");
        alertDialog.setMessage("전화 연결을 하시겠습니까?");

        // OK 를 누르게 되면 설정창으로 이동합니다.
        alertDialog.setPositiveButton("전화",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

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
                                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(field.this);
                                    dialog1.setTitle("권한이 필요합니다.")
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
                                                    Toast.makeText(field.this, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();
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
                });
        // Cancle 하면 종료 합니다.
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
}


