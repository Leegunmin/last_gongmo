package nongsa.agoto.MaintoOther.information;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import nongsa.agoto.MaintoOther.information.adapter.CustomAdapter;
import nongsa.agoto.MaintoOther.information.adapter.Webview;
import nongsa.agoto.R;

public class technic extends AppCompatActivity {
    int click = 0;
    private CustomAdapter m_Adapter;
    ArrayList<String> LIST_MENU = new ArrayList<String>();
    ArrayList<String> LIST_CODE = new ArrayList<String>();
    ArrayList<String> MID_LIST_MENU = new ArrayList<String>();
    ArrayList<String> MID_LIST_CODE = new ArrayList<String>();
    ArrayList<String> SMA_LIST_MENU = new ArrayList<String>();
    ArrayList<String> SMA_LIST_CODE = new ArrayList<String>();
    ArrayList<String> TECH_LIST_MENU = new ArrayList<String>();
    ArrayList<String> TECH_LIST_CODE = new ArrayList<String>();
    ArrayList<String> SMA_TECH_LIST_MENU = new ArrayList<String>();
    ArrayList<String> SMA_TECH_LIST_CODE = new ArrayList<String>();
    ArrayList<String> LAST_TECH_LIST_MENU = new ArrayList<String>();
    ArrayList<String> LAST_TECH_LIST_CODE = new ArrayList<String>();
    ArrayList<String> LAST_TECH_LIST_URL = new ArrayList<String>();
    ArrayList<String> movie_img = new ArrayList<String>();
    ArrayList<String> movie_link = new ArrayList<String>();
    ArrayList<String> movie_title = new ArrayList<String>();
    String temp_subcategory;
    String temp_movie= "";
    static String a = "";
    static String b = "";
    Spinner big_spinner;
    Spinner mid_spinner;
    Spinner small_spinner;
    Spinner tech_big_spinner;
    static String sort = "big";
    TextView sorting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =  getIntent();
        setContentView(R.layout.activity_technic);

        final ListView listview = (ListView) findViewById(R.id.listviewing) ;
        big_spinner = (Spinner)findViewById(R.id.big_spinner);
        mid_spinner = (Spinner)findViewById(R.id.mid_spinner);
        small_spinner = (Spinner)findViewById(R.id.small_spinner);
        tech_big_spinner = (Spinner)findViewById(R.id.big_tech_spinner);
        m_Adapter = new CustomAdapter();
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;

                String title = null, address = null, mapx = null, mapy = null;
                String query = "농업"; //이부부은 검색어를 UTF-8로 넣어줄거임.
                System.out.println(query);
                LIST_MENU.add("대분류");
                LIST_CODE.add("big");
                try {
                    URL url = new URL("http://api.nongsaro.go.kr/service/cropTechInfo/mainCategoryList?"
                            + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"//여기는 쿼리를 넣으세요(검색어)
                    );


                    XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = parserCreator.newPullParser();

                    parser.setInput(url.openStream(), null);

                    // status.setText("파싱 중이에요..");

                    int parserEvent = parser.getEventType();
                    System.out.println("gunmin : " + parserEvent);
                    while (parserEvent != XmlPullParser.END_DOCUMENT) {
                        //    System.out.println("gunmin : " + parserEvent);
                        switch (parserEvent) {
                            case XmlPullParser.START_TAG:  //parser가 시작 태그를 만나면 실행
                                if (parser.getName().equals("faoCode")) {
                                    //     System.out.println("gunmin : " + parser.getName());
                                    inItem = true;
                                }
                                if (parser.getName().equals("mainCategoryNm")) { //title 만나면 내용을 받을수 있게 하자
                                    inTitle = true;
                                    //   System.out.println("gunmin : " + parser.getName());
                                }
                                if (parser.getName().equals("mainCategoryCode")) { //address 만나면 내용을 받을수 있게 하자
                                    inAddress = true;
                                    //   System.out.println("gunmin : " + parser.getName());
                                }
                                if (parser.getName().equals("numOfRows")) { //mapx 만나면 내용을 받을수 있게 하자
                                    //  System.out.println("gunmin : " + parser.getName());
                                    inMapx = true;
                                }
                                if (parser.getName().equals("wordNm")) { //mapy 만나면 내용을 받을수 있게 하자
                                    inMapy = true;
                                    //  System.out.println("gunmin : " + parser.getName());
                                }
                                if (parser.getName().equals("faoCode")) { //message 태그를 만나면 에러 출력
                                    // status1.setText(status1.getText()+"에러");
                                    //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                                }
                                break;

                            case XmlPullParser.TEXT://parser가 내용에 접근했을때
                                if (inTitle) { //isTitle이 true일 때 태그의 내용을 저장.
                                    title = parser.getText();
                                    LIST_MENU.add(parser.getText());
                                    //  System.out.println("gunmin : " + parser.getText());
                                    inTitle = false;
                                }
                                if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                    address = parser.getText();
                                    LIST_CODE.add(parser.getText());
                                    System.out.println("gunmin : " + parser.getText());
                                    inAddress = false;
                                }
                                if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                    mapx = parser.getText();
                                    System.out.println("gunmin :123 " + parser.getText());
                                    // num = parser.getText();
                                    inMapx = false;
                                }
                                if (inMapy) { //isMapy이 true일 때 태그의 내용을 저장.
                                    mapy = parser.getText();
                                    System.out.println("gunmin : " + parser.getText());
                                    inMapy = false;
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if (parser.getName().equals("item")) {
                                    System.out.println("gunmin : "+parser.getName());
                                    // status1.setText(status1.getText()+"상호 : "+ title +"\n주소 : "+ address +"\n좌표 : " + mapx + ", " + mapy+"\n\n");
                                    inItem = false;
                                }
                                break;
                        }
                        parserEvent = parser.next();
                    }

                    //  status2.setText("파싱 끝!");
                } catch (Exception e) {
                    e.printStackTrace();
                    // status1.setText("에러가..났습니다...");
                }
            }
        };
        thread.start();
        try{
            thread.join();
            ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(technic.this,R.layout.simple_spinner_item_custom,LIST_MENU);
            sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
            big_spinner.setAdapter(sAdapter);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        big_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?>  parent, View view, final int position, long id) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        MID_LIST_CODE = new ArrayList<String>();
                        MID_LIST_MENU = new ArrayList<String>();
                        MID_LIST_CODE.add("MID");
                        MID_LIST_MENU.add("중분류");
                        boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;

                        String title = null, address = null, mapx = null, mapy = null;
                        String query = "농업"; //이부부은 검색어를 UTF-8로 넣어줄거임.
                        System.out.println(query);

                        try {
                            URL url = new URL("http://api.nongsaro.go.kr/service/cropTechInfo/middleCategoryList?"
                                    + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                    + "&mainCategoryCode="+LIST_CODE.get(position)//여기는 쿼리를 넣으세요(검색어)
                            );


                            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                            XmlPullParser parser = parserCreator.newPullParser();

                            parser.setInput(url.openStream(), null);

                            // status.setText("파싱 중이에요..");

                            int parserEvent = parser.getEventType();
                            System.out.println("gunmin : " + parserEvent);
                            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                                //    System.out.println("gunmin : " + parserEvent);
                                switch (parserEvent) {
                                    case XmlPullParser.START_TAG:  //parser가 시작 태그를 만나면 실행
                                        if (parser.getName().equals("faoCode")) {
                                            //     System.out.println("gunmin : " + parser.getName());
                                            inItem = true;
                                        }
                                        if (parser.getName().equals("middleCategoryCode")) { //title 만나면 내용을 받을수 있게 하자
                                            inTitle = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("middleCategoryNm")) { //address 만나면 내용을 받을수 있게 하자
                                            inAddress = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("numOfRows")) { //mapx 만나면 내용을 받을수 있게 하자
                                            //  System.out.println("gunmin : " + parser.getName());
                                            inMapx = true;
                                        }
                                        if (parser.getName().equals("wordNm")) { //mapy 만나면 내용을 받을수 있게 하자
                                            inMapy = true;
                                            //  System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("faoCode")) { //message 태그를 만나면 에러 출력
                                            // status1.setText(status1.getText()+"에러");
                                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                                        }
                                        break;

                                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                                        if (inTitle) { //isTitle이 true일 때 태그의 내용을 저장.
                                            title = parser.getText();
                                            MID_LIST_CODE.add(parser.getText());
                                            //  System.out.println("gunmin : " + parser.getText());
                                            inTitle = false;
                                        }
                                        if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                            address = parser.getText();
                                            MID_LIST_MENU.add(parser.getText());
                                            System.out.println("gunmin : " + parser.getText());
                                            inAddress = false;
                                        }
                                        if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                            mapx = parser.getText();
                                            System.out.println("gunmin :123 " + parser.getText());
                                            // num = parser.getText();
                                            inMapx = false;
                                        }
                                        if (inMapy) { //isMapy이 true일 때 태그의 내용을 저장.
                                            mapy = parser.getText();
                                            System.out.println("gunmin : " + parser.getText());
                                            inMapy = false;
                                        }
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if (parser.getName().equals("item")) {
                                            System.out.println("gunmin : "+parser.getName());
                                            // status1.setText(status1.getText()+"상호 : "+ title +"\n주소 : "+ address +"\n좌표 : " + mapx + ", " + mapy+"\n\n");
                                            inItem = false;
                                        }
                                        break;
                                }
                                parserEvent = parser.next();
                            }

                            //  status2.setText("파싱 끝!");
                        } catch (Exception e) {
                            e.printStackTrace();
                            // status1.setText("에러가..났습니다...");
                        }
                    }
                };
                thread.start();
                try{
                    thread.join();
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(technic.this,R.layout.simple_spinner_item_custom,MID_LIST_MENU);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    mid_spinner.setAdapter(sAdapter);
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);


                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?>  parent) {
                Toast.makeText(getApplicationContext(),
                        "직원을 선택하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        mid_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?>  parent, View view, final int position, long id) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        SMA_LIST_CODE = new ArrayList<String>();
                        SMA_LIST_MENU = new ArrayList<String>();
                        SMA_LIST_CODE.add("SMA");
                        SMA_LIST_MENU.add("소분류");
                        boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;

                        String title = null, address = null, mapx = null, mapy = null;
                        String query = "농업"; //이부부은 검색어를 UTF-8로 넣어줄거임.
                        System.out.println(query);

                        try {
                            URL url = new URL("http://api.nongsaro.go.kr/service/cropTechInfo/subCategoryList?"
                                    + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                    + "&middleCategoryCode="+MID_LIST_CODE.get(position)//여기는 쿼리를 넣으세요(검색어)
                            );


                            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                            XmlPullParser parser = parserCreator.newPullParser();

                            parser.setInput(url.openStream(), null);

                            // status.setText("파싱 중이에요..");

                            int parserEvent = parser.getEventType();
                            System.out.println("gunmin : " + parserEvent);
                            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                                //    System.out.println("gunmin : " + parserEvent);
                                switch (parserEvent) {
                                    case XmlPullParser.START_TAG:  //parser가 시작 태그를 만나면 실행
                                        if (parser.getName().equals("faoCode")) {
                                            //     System.out.println("gunmin : " + parser.getName());
                                            inItem = true;
                                        }
                                        if (parser.getName().equals("subCategoryCode")) { //title 만나면 내용을 받을수 있게 하자
                                            inTitle = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("subCategoryNm")) { //address 만나면 내용을 받을수 있게 하자
                                            inAddress = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("numOfRows")) { //mapx 만나면 내용을 받을수 있게 하자
                                            //  System.out.println("gunmin : " + parser.getName());
                                            inMapx = true;
                                        }
                                        if (parser.getName().equals("wordNm")) { //mapy 만나면 내용을 받을수 있게 하자
                                            inMapy = true;
                                            //  System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("faoCode")) { //message 태그를 만나면 에러 출력
                                            // status1.setText(status1.getText()+"에러");
                                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                                        }
                                        break;

                                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                                        if (inTitle) { //isTitle이 true일 때 태그의 내용을 저장.
                                            title = parser.getText();
                                            SMA_LIST_CODE.add(parser.getText());
                                            //  System.out.println("gunmin : " + parser.getText());
                                            inTitle = false;
                                        }
                                        if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                            address = parser.getText();
                                            SMA_LIST_MENU.add(parser.getText());
                                            System.out.println("gunmin : " + parser.getText());
                                            inAddress = false;
                                        }
                                        if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                            mapx = parser.getText();
                                            System.out.println("gunmin :123 " + parser.getText());
                                            // num = parser.getText();
                                            inMapx = false;
                                        }
                                        if (inMapy) { //isMapy이 true일 때 태그의 내용을 저장.
                                            mapy = parser.getText();
                                            System.out.println("gunmin : " + parser.getText());
                                            inMapy = false;
                                        }
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if (parser.getName().equals("item")) {
                                            System.out.println("gunmin : "+parser.getName());
                                            // status1.setText(status1.getText()+"상호 : "+ title +"\n주소 : "+ address +"\n좌표 : " + mapx + ", " + mapy+"\n\n");
                                            inItem = false;
                                        }
                                        break;
                                }
                                parserEvent = parser.next();
                            }

                            //  status2.setText("파싱 끝!");
                        } catch (Exception e) {
                            e.printStackTrace();
                            // status1.setText("에러가..났습니다...");
                        }
                    }
                };
                thread.start();
                try{
                    thread.join();
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(technic.this,R.layout.simple_spinner_item_custom,SMA_LIST_MENU);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    small_spinner.setAdapter(sAdapter);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?>  parent) {
                Toast.makeText(getApplicationContext(),
                        "직원을 선택하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        small_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;
                        TECH_LIST_CODE = new ArrayList<String>();
                        TECH_LIST_MENU = new ArrayList<String>();
                        TECH_LIST_CODE.add("TECH");
                        TECH_LIST_MENU.add("기술분류");
                        String title = null, address = null, mapx = null, mapy = null;
                        //String query = "농업"; //이부부은 검색어를 UTF-8로 넣어줄거임.
                        //System.out.println(query);

                        try {
                            URL url = new URL("http://api.nongsaro.go.kr/service/cropTechInfo/mainTechList?"
                                    + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                    + "&subCategoryCode="+SMA_LIST_CODE.get(position)//여기는 쿼리를 넣으세요(검색어)
                            );
                            temp_subcategory = SMA_LIST_CODE.get(position);

                            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                            XmlPullParser parser = parserCreator.newPullParser();

                            parser.setInput(url.openStream(), null);

                            // status.setText("파싱 중이에요..");

                            int parserEvent = parser.getEventType();
                            System.out.println("gunmin : " + parserEvent);
                            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                                //    System.out.println("gunmin : " + parserEvent);
                                switch (parserEvent) {
                                    case XmlPullParser.START_TAG:  //parser가 시작 태그를 만나면 실행
                                        if (parser.getName().equals("faoCode")) {
                                            //     System.out.println("gunmin : " + parser.getName());
                                            inItem = true;
                                        }
                                        if (parser.getName().equals("mainTechCode")) { //title 만나면 내용을 받을수 있게 하자
                                            inTitle = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("mainTechNm")) { //address 만나면 내용을 받을수 있게 하자
                                            inAddress = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("numOfRows")) { //mapx 만나면 내용을 받을수 있게 하자
                                            //  System.out.println("gunmin : " + parser.getName());
                                            inMapx = true;
                                        }
                                        if (parser.getName().equals("wordNm")) { //mapy 만나면 내용을 받을수 있게 하자
                                            inMapy = true;
                                            //  System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("faoCode")) { //message 태그를 만나면 에러 출력
                                            // status1.setText(status1.getText()+"에러");
                                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                                        }
                                        break;

                                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                                        if (inTitle) { //isTitle이 true일 때 태그의 내용을 저장.
                                            title = parser.getText();
                                            System.out.println("gunmins1 : "+parser.getText());
                                            TECH_LIST_CODE.add(parser.getText());
                                            //  System.out.println("gunmin : " + parser.getText());
                                            inTitle = false;
                                        }
                                        if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                            address = parser.getText();
                                            System.out.println("gunmins2 : "+parser.getText());
                                            TECH_LIST_MENU.add(parser.getText());
                                            System.out.println("gunmin : " + parser.getText());
                                            inAddress = false;
                                        }
                                        if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                            mapx = parser.getText();
                                            System.out.println("gunmin :123 " + parser.getText());
                                            // num = parser.getText();
                                            inMapx = false;
                                        }
                                        if (inMapy) { //isMapy이 true일 때 태그의 내용을 저장.
                                            mapy = parser.getText();
                                            System.out.println("gunmin : " + parser.getText());
                                            inMapy = false;
                                        }
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if (parser.getName().equals("item")) {
                                            System.out.println("gunmin : " + parser.getName());
                                            // status1.setText(status1.getText()+"상호 : "+ title +"\n주소 : "+ address +"\n좌표 : " + mapx + ", " + mapy+"\n\n");
                                            inItem = false;
                                        }
                                        break;
                                }
                                parserEvent = parser.next();
                            }

                            //  status2.setText("파싱 끝!");
                        } catch (Exception e) {
                            e.printStackTrace();
                            // status1.setText("에러가..났습니다...");
                        }
                    }
                };
                thread.start();
                try {
                    thread.join();
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(technic.this,R.layout.simple_spinner_item_custom,TECH_LIST_MENU);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    tech_big_spinner.setAdapter(sAdapter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),
                        "직원을 선택하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        tech_big_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        click = 1;
                        boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;
                        SMA_TECH_LIST_CODE = new ArrayList<String>();
                        SMA_TECH_LIST_MENU = new ArrayList<String>();
                        String title = null, address = null, mapx = null, mapy = null;
                        //String query = "농업"; //이부부은 검색어를 UTF-8로 넣어줄거임.
                        //System.out.println(query);
                        if(TECH_LIST_CODE.get(position).equals("movie")){
                            temp_movie = "movie";
                            movie_img = new ArrayList<String>();
                            movie_link = new ArrayList<String>();
                            movie_title = new ArrayList<String>();
                            try {
                                URL url = new URL("http://api.nongsaro.go.kr/service/cropTechInfo/videoList?"
                                        + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                        + "&subCategoryCode=" + temp_subcategory//여기는 쿼리를 넣으세요(검색어)
                                );


                                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                                XmlPullParser parser = parserCreator.newPullParser();

                                parser.setInput(url.openStream(), null);

                                // status.setText("파싱 중이에요..");

                                int parserEvent = parser.getEventType();
                                System.out.println("gunmin : " + parserEvent);
                                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                                    //    System.out.println("gunmin : " + parserEvent);
                                    switch (parserEvent) {
                                        case XmlPullParser.START_TAG:  //parser가 시작 태그를 만나면 실행
                                            if (parser.getName().equals("faoCode")) {
                                                //     System.out.println("gunmin : " + parser.getName());
                                                inItem = true;
                                            }
                                            if (parser.getName().equals("videoImg")) { //title 만나면 내용을 받을수 있게 하자
                                                inTitle = true;
                                                //   System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("videoLink")) { //address 만나면 내용을 받을수 있게 하자
                                                inAddress = true;
                                                //   System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("videoTitle")) { //mapx 만나면 내용을 받을수 있게 하자
                                                //  System.out.println("gunmin : " + parser.getName());
                                                inMapx = true;
                                            }
                                            if (parser.getName().equals("wordNm")) { //mapy 만나면 내용을 받을수 있게 하자
                                                inMapy = true;
                                                //  System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("faoCode")) { //message 태그를 만나면 에러 출력
                                                // status1.setText(status1.getText()+"에러");
                                                //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                                            }
                                            break;

                                        case XmlPullParser.TEXT://parser가 내용에 접근했을때
                                            if (inTitle) { //isTitle이 true일 때 태그의 내용을 저장.
                                                title = parser.getText();
                                                System.out.println("gunmins1 : " + parser.getText());
                                                m_Adapter.addIMG(parser.getText());
                                                //  System.out.println("gunmin : " + parser.getText());
                                                inTitle = false;
                                            }
                                            if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                                address = parser.getText();
                                                System.out.println("gunmins2 : " + parser.getText());
                                                m_Adapter.addLINK(parser.getText());
                                                System.out.println("gunmin : " + parser.getText());
                                                inAddress = false;
                                            }
                                            if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                                mapx = parser.getText();
                                                m_Adapter.add(parser.getText());
                                                System.out.println("gunmin :123 " + parser.getText());
                                                // num = parser.getText();
                                                inMapx = false;
                                            }
                                            if (inMapy) { //isMapy이 true일 때 태그의 내용을 저장.
                                                mapy = parser.getText();
                                                System.out.println("gunmin : " + parser.getText());
                                                inMapy = false;
                                            }
                                            break;
                                        case XmlPullParser.END_TAG:
                                            if (parser.getName().equals("item")) {
                                                System.out.println("gunmin : " + parser.getName());
                                                // status1.setText(status1.getText()+"상호 : "+ title +"\n주소 : "+ address +"\n좌표 : " + mapx + ", " + mapy+"\n\n");
                                                inItem = false;
                                            }
                                            break;
                                    }
                                    parserEvent = parser.next();
                                }

                                //  status2.setText("파싱 끝!");
                            } catch (Exception e) {
                                e.printStackTrace();
                                // status1.setText("에러가..났습니다...");
                            }
                        }
                        else if(TECH_LIST_CODE.get(position).equals("variety")){
                            try {
                                URL url = new URL("http://api.nongsaro.go.kr/service/cropTechInfo/varietyList?"
                                        + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                        + "&subCategoryCode=" + temp_subcategory
                                        //여기는 쿼리를 넣으세요(검색어)
                                );
                                temp_movie= "variety";

                                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                                XmlPullParser parser = parserCreator.newPullParser();

                                parser.setInput(url.openStream(), null);

                                // status.setText("파싱 중이에요..");

                                int parserEvent = parser.getEventType();
                                System.out.println("gunmin : " + parserEvent);
                                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                                    //    System.out.println("gunmin : " + parserEvent);
                                    switch (parserEvent) {
                                        case XmlPullParser.START_TAG:  //parser가 시작 태그를 만나면 실행
                                            if (parser.getName().equals("faoCode")) {
                                                //     System.out.println("gunmin : " + parser.getName());
                                                inItem = true;
                                            }
                                            if (parser.getName().equals("subTechCode")) { //title 만나면 내용을 받을수 있게 하자
                                                inTitle = true;
                                                //   System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("subTechNm")) { //address 만나면 내용을 받을수 있게 하자
                                                inAddress = true;
                                                //   System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("numOfRows")) { //mapx 만나면 내용을 받을수 있게 하자
                                                //  System.out.println("gunmin : " + parser.getName());
                                                inMapx = true;
                                            }
                                            if (parser.getName().equals("wordNm")) { //mapy 만나면 내용을 받을수 있게 하자
                                                inMapy = true;
                                                //  System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("faoCode")) { //message 태그를 만나면 에러 출력
                                                // status1.setText(status1.getText()+"에러");
                                                //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                                            }
                                            break;

                                        case XmlPullParser.TEXT://parser가 내용에 접근했을때
                                            if (inTitle) { //isTitle이 true일 때 태그의 내용을 저장.
                                                title = parser.getText();
                                                System.out.println("gunmins1 : " + parser.getText());
                                                SMA_TECH_LIST_CODE.add(parser.getText());
                                                //  System.out.println("gunmin : " + parser.getText());
                                                inTitle = false;
                                            }
                                            if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                                address = parser.getText();
                                                System.out.println("gunmins2 : " + parser.getText());
                                                SMA_TECH_LIST_MENU.add(parser.getText());
                                                System.out.println("gunmin : " + parser.getText());
                                                inAddress = false;
                                            }
                                            if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                                mapx = parser.getText();
                                                System.out.println("gunmin :123 " + parser.getText());
                                                // num = parser.getText();
                                                inMapx = false;
                                            }
                                            if (inMapy) { //isMapy이 true일 때 태그의 내용을 저장.
                                                mapy = parser.getText();
                                                System.out.println("gunmin : " + parser.getText());
                                                inMapy = false;
                                            }
                                            break;
                                        case XmlPullParser.END_TAG:
                                            if (parser.getName().equals("item")) {
                                                System.out.println("gunmin : " + parser.getName());
                                                // status1.setText(status1.getText()+"상호 : "+ title +"\n주소 : "+ address +"\n좌표 : " + mapx + ", " + mapy+"\n\n");
                                                inItem = false;
                                            }
                                            break;
                                    }
                                    parserEvent = parser.next();
                                }

                                //  status2.setText("파싱 끝!");
                            } catch (Exception e) {
                                e.printStackTrace();
                                // status1.setText("에러가..났습니다...");
                            }
                        }
                        else {
                            try {
                                URL url = new URL("http://api.nongsaro.go.kr/service/cropTechInfo/subTechList?"
                                        + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                        + "&subCategoryCode=" + temp_subcategory
                                        + "&mainTechCode=" + TECH_LIST_CODE.get(position)//여기는 쿼리를 넣으세요(검색어)
                                );
                                temp_movie= "another";

                                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                                XmlPullParser parser = parserCreator.newPullParser();

                                parser.setInput(url.openStream(), null);

                                // status.setText("파싱 중이에요..");

                                int parserEvent = parser.getEventType();
                                System.out.println("gunmin : " + parserEvent);
                                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                                    //    System.out.println("gunmin : " + parserEvent);
                                    switch (parserEvent) {
                                        case XmlPullParser.START_TAG:  //parser가 시작 태그를 만나면 실행
                                            if (parser.getName().equals("faoCode")) {
                                                //     System.out.println("gunmin : " + parser.getName());
                                                inItem = true;
                                            }
                                            if (parser.getName().equals("subTechCode")) { //title 만나면 내용을 받을수 있게 하자
                                                inTitle = true;
                                                //   System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("subTechNm")) { //address 만나면 내용을 받을수 있게 하자
                                                inAddress = true;
                                                //   System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("numOfRows")) { //mapx 만나면 내용을 받을수 있게 하자
                                                //  System.out.println("gunmin : " + parser.getName());
                                                inMapx = true;
                                            }
                                            if (parser.getName().equals("wordNm")) { //mapy 만나면 내용을 받을수 있게 하자
                                                inMapy = true;
                                                //  System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("faoCode")) { //message 태그를 만나면 에러 출력
                                                // status1.setText(status1.getText()+"에러");
                                                //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                                            }
                                            break;

                                        case XmlPullParser.TEXT://parser가 내용에 접근했을때
                                            if (inTitle) { //isTitle이 true일 때 태그의 내용을 저장.
                                                title = parser.getText();
                                                System.out.println("gunmins1 : " + parser.getText());
                                                SMA_TECH_LIST_CODE.add(parser.getText());
                                                //  System.out.println("gunmin : " + parser.getText());
                                                inTitle = false;
                                            }
                                            if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                                address = parser.getText();
                                                System.out.println("gunmins2 : " + parser.getText());
                                                SMA_TECH_LIST_MENU.add(parser.getText());
                                                System.out.println("gunmin : " + parser.getText());
                                                inAddress = false;
                                            }
                                            if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                                mapx = parser.getText();
                                                System.out.println("gunmin :123 " + parser.getText());
                                                // num = parser.getText();
                                                inMapx = false;
                                            }
                                            if (inMapy) { //isMapy이 true일 때 태그의 내용을 저장.
                                                mapy = parser.getText();
                                                System.out.println("gunmin : " + parser.getText());
                                                inMapy = false;
                                            }
                                            break;
                                        case XmlPullParser.END_TAG:
                                            if (parser.getName().equals("item")) {
                                                System.out.println("gunmin : " + parser.getName());
                                                // status1.setText(status1.getText()+"상호 : "+ title +"\n주소 : "+ address +"\n좌표 : " + mapx + ", " + mapy+"\n\n");
                                                inItem = false;
                                            }
                                            break;
                                    }
                                    parserEvent = parser.next();
                                }

                                //  status2.setText("파싱 끝!");
                            } catch (Exception e) {
                                e.printStackTrace();
                                // status1.setText("에러가..났습니다...");
                            }
                        }
                    }
                };
                thread.start();
                try {
                    thread.join();
                    if(temp_movie.equals("movie")){
                        ArrayAdapter adapter = new ArrayAdapter(technic.this, R.layout.simple_list_item_1_custom, movie_title);
                        adapter.notifyDataSetChanged();
                        listview.setAdapter(m_Adapter);
                    }
                    else if(temp_movie.equals("variety")){
                        ArrayAdapter adapter = new ArrayAdapter(technic.this, R.layout.simple_list_item_1_custom, SMA_TECH_LIST_MENU);
                        adapter.notifyDataSetChanged();
                        listview.setAdapter(adapter);
                    }
                    else {
                        ArrayAdapter adapter = new ArrayAdapter(technic.this, R.layout.simple_list_item_1_custom, SMA_TECH_LIST_MENU);
                        adapter.notifyDataSetChanged();
                        listview.setAdapter(adapter);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),
                        "직원을 선택하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, final int position, long id) {
                if(click == 1) {
                    click =2;
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;
                            LAST_TECH_LIST_CODE = new ArrayList<String>();
                            LAST_TECH_LIST_MENU = new ArrayList<String>();
                            LAST_TECH_LIST_URL = new ArrayList<String>();
                            String title = null, address = null, mapx = null, mapy = null;
                            //String query = "농업"; //이부부은 검색어를 UTF-8로 넣어줄거임.
                            //System.out.println(query);

                            try {
                                URL url = new URL("http://api.nongsaro.go.kr/service/cropTechInfo/techInfoList?"
                                        + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                        + "&subCategoryCode=" + temp_subcategory
                                        + "&subTechCode=" + SMA_TECH_LIST_CODE.get(position)//여기는 쿼리를 넣으세요(검색어)
                                );

                                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                                XmlPullParser parser = parserCreator.newPullParser();

                                parser.setInput(url.openStream(), null);

                                // status.setText("파싱 중이에요..");

                                int parserEvent = parser.getEventType();
                                System.out.println("gunmin : " + parserEvent);
                                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                                    //    System.out.println("gunmin : " + parserEvent);
                                    switch (parserEvent) {
                                        case XmlPullParser.START_TAG:  //parser가 시작 태그를 만나면 실행
                                            if (parser.getName().equals("faoCode")) {
                                                //     System.out.println("gunmin : " + parser.getName());
                                                inItem = true;
                                            }
                                            if (parser.getName().equals("techNm")) { //title 만나면 내용을 받을수 있게 하자
                                                inTitle = true;
                                                //   System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("regDt")) { //address 만나면 내용을 받을수 있게 하자
                                                inAddress = true;
                                                //   System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("fileDownUrl")) { //mapx 만나면 내용을 받을수 있게 하자
                                                //  System.out.println("gunmin : " + parser.getName());
                                                inMapx = true;
                                            }
                                            if (parser.getName().equals("wordNm")) { //mapy 만나면 내용을 받을수 있게 하자
                                                inMapy = true;
                                                //  System.out.println("gunmin : " + parser.getName());
                                            }
                                            if (parser.getName().equals("faoCode")) { //message 태그를 만나면 에러 출력
                                                // status1.setText(status1.getText()+"에러");
                                                //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                                            }
                                            break;

                                        case XmlPullParser.TEXT://parser가 내용에 접근했을때
                                            if (inTitle) { //isTitle이 true일 때 태그의 내용을 저장.
                                                title = parser.getText();
                                                System.out.println("gunmins1 : " + parser.getText());
                                                LAST_TECH_LIST_CODE.add(parser.getText());
                                                //  System.out.println("gunmin : " + parser.getText());
                                                inTitle = false;
                                            }
                                            if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                                address = parser.getText();
                                                System.out.println("gunmins2 : " + parser.getText());
                                                LAST_TECH_LIST_MENU.add(parser.getText());
                                                System.out.println("gunmin : " + parser.getText());
                                                inAddress = false;
                                            }
                                            if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                                mapx = parser.getText();
                                                LAST_TECH_LIST_URL.add(parser.getText());
                                                System.out.println("gunmin :123 " + parser.getText());
                                                // num = parser.getText();
                                                inMapx = false;
                                            }
                                            if (inMapy) { //isMapy이 true일 때 태그의 내용을 저장.
                                                mapy = parser.getText();
                                                System.out.println("gunmin : " + parser.getText());
                                                inMapy = false;
                                            }
                                            break;
                                        case XmlPullParser.END_TAG:
                                            if (parser.getName().equals("item")) {
                                                System.out.println("gunmin : " + parser.getName());
                                                // status1.setText(status1.getText()+"상호 : "+ title +"\n주소 : "+ address +"\n좌표 : " + mapx + ", " + mapy+"\n\n");
                                                inItem = false;
                                            }
                                            break;
                                    }
                                    parserEvent = parser.next();
                                }

                                //  status2.setText("파싱 끝!");
                            } catch (Exception e) {
                                e.printStackTrace();
                                // status1.setText("에러가..났습니다...");
                            }
                        }
                    };
                    thread.start();
                    try {
                        thread.join();
                        ArrayAdapter adapter = new ArrayAdapter(technic.this, R.layout.simple_list_item_1_custom, LAST_TECH_LIST_CODE);
                        adapter.notifyDataSetChanged();
                        listview.setAdapter(adapter);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(click == 2){
                    try {
                        a = LAST_TECH_LIST_URL.get(position);
                        b = LAST_TECH_LIST_CODE.get(position);
                        //feed();
                        Intent intent = new Intent(technic.this,Webview.class);
                        intent.putExtra("link",a);
                        technic.this.startActivity(intent);
//                        InputStream inputStream = new URL(LAST_TECH_LIST_URL.get(position)).openStream();
//
//                        File file = new File(LAST_TECH_LIST_CODE.get(position));
//                        OutputStream out = new FileOutputStream(file);
//                        writeFile(inputStream, out);
//                        out.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void feed()
    {
        new ProcessFacebookTask().execute("url", "1", "1");
    }

    //AsyncTask<Params,Progress,Result>
    private class ProcessFacebookTask extends AsyncTask<String, String, String> {
        ProgressDialog mDlg;

        protected void onPreExecute() {
            mDlg = new ProgressDialog(technic.this);
            mDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDlg.setMessage("Start");
            mDlg.show();
        }
        @Override
        protected String doInBackground(String... params) {


                // 작업이 진행되면서 호출하며 화면의 업그레이드를 담당하게 된다
                //publishProgress("progress", 1, "Task " + 1 + " number");

            return null;
        }

        protected void onProgressUpdate(String... progress) {
            if (progress[0].equals("progress")) {
                System.out.println("hello4");
                mDlg.setProgress(Integer.parseInt(progress[1]));
                mDlg.setMessage(progress[2]);
            } else if (progress[0].equals("max")) {
                mDlg.setMax(Integer.parseInt(progress[1]));
            }
        }
        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(String unused) {
            mDlg.dismiss();

        }


    }

    public void writeFile(InputStream is, OutputStream os) throws IOException
    {
        int c = 0;
        while((c = is.read()) != -1)
            os.write(c);
        os.flush();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
