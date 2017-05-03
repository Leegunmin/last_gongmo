package nongsa.agoto.MaintoOther;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.tsengvn.typekit.TypekitContextWrapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import nongsa.agoto.MaintoOther.diary.diary;
import nongsa.agoto.MaintoOther.information.technic;
import nongsa.agoto.MaintoOther.information.term;
import nongsa.agoto.MaintoOther.information.video;
import nongsa.agoto.MaintoOther.vacant.field;
import nongsa.agoto.MaintoOther.vacant.vacants;
import nongsa.agoto.R;

public class Information extends AppCompatActivity {
    Button technique;
    Button videos;
    Button vacantss;
    Button terms;
    Button work_diary;
    Button real_estate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        technique = (Button)findViewById(R.id.technique);//기술정보
        videos = (Button)findViewById(R.id.turn);// 비디오
        real_estate =(Button)findViewById(R.id.real_estate);
        vacantss =  (Button)findViewById(R.id.theothers);//빈집
        terms = (Button)findViewById(R.id.like);//용어
        work_diary= (Button)findViewById(R.id.weather);//일지


        real_estate.setOnClickListener(new View.OnClickListener(){ // 주요정보- 기술정보
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),field.class);
                startActivity(intent);


            }

        });

        technique.setOnClickListener(new View.OnClickListener(){ // 주요정보- 기술정보
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),technic.class);
                startActivity(intent);


            }

        });
        videos.setOnClickListener(new View.OnClickListener(){ // 주요정보- 비디오
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),video.class);
                startActivity(intent);


            }
        });
        vacantss.setOnClickListener(new View.OnClickListener(){ // 주요정보 - 빈집
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),vacants.class);
                startActivity(intent);


            }
        });
        terms.setOnClickListener(new View.OnClickListener(){ // 주요정보 - 빈집
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),term.class);
                startActivity(intent);


            }
        });
        work_diary.setOnClickListener(new View.OnClickListener(){ // 주요정보 - 빈집
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),diary.class);
                startActivity(intent);


            }
        });



    }
    private String SendByHttp(String msg) {
        if(msg == null)
            msg = "";


        // String URL = "서버주소 서버주소 서버주소써줘어어어어";
        String URL = 	"http://data.mafra.go.kr:7080/openapi/sample/xml/Grid_20151001000000000232_1/1/5";
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            /* 체크할 id와 pwd값 서버로 전송 */
            HttpPost post = new HttpPost(URL+"?msg="+msg);


            /* 지연시간 최대 3초 */
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            HttpConnectionParams.setSoTimeout(params, 3000);

            /* 데이터 보낸 뒤 서버에서 데이터를 받아오는 과정 */
            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(),
                            "utf-8"));

            String line = null;
            String result = "";

            while ((line = bufreader.readLine()) != null) {
                result += line;
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            client.getConnectionManager().shutdown();    // 연결 지연 종료
            return "";
        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}




