package nongsa.agoto.MaintoOther.information;

import android.content.Context;
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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

import nongsa.agoto.MaintoOther.information.adapter.CustomAdapter;
import nongsa.agoto.R;

public class video extends AppCompatActivity {
    private CustomAdapter m_Adapter;
    int channel = 1;
    EditText video_edit;
    Button video_button;
    Button video_back;
    Button video_go;
    Button button_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        m_Adapter = new CustomAdapter();
        video_edit = (EditText)findViewById(R.id.videoedit);
        video_button = (Button)findViewById(R.id.videobutton);
        video_back = (Button)findViewById(R.id.videoback);
        video_go = (Button)findViewById(R.id.videogo);
        final ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(m_Adapter);

        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;

                String title = null, address = null, mapx = null, mapy = null;
                String query = "농업"; //이부부은 검색어를 UTF-8로 넣어줄거임.
                System.out.println(query);

                try {
                    URL url = new URL("http://api.nongsaro.go.kr/service/agriTechVideo/videoList?"
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
                                if (parser.getName().equals("videoTitle")) { //title 만나면 내용을 받을수 있게 하자
                                    inTitle = true;
                                    //   System.out.println("gunmin : " + parser.getName());
                                }
                                if (parser.getName().equals("videoImg")) { //address 만나면 내용을 받을수 있게 하자
                                    inAddress = true;
                                    //   System.out.println("gunmin : " + parser.getName());
                                }
                                if (parser.getName().equals("videoLink")) { //mapx 만나면 내용을 받을수 있게 하자
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
                                    m_Adapter.add(parser.getText());
                                    //  System.out.println("gunmin : " + parser.getText());
                                    inTitle = false;
                                }
                                if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                    address = parser.getText();
                                    m_Adapter.addIMG(parser.getText());
                                    System.out.println("gunmin : " + parser.getText());
                                    inAddress = false;
                                }
                                if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                    mapx = parser.getText();
                                    m_Adapter.addLINK(parser.getText());
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
            //ArrayAdapter adapter = new ArrayAdapter(video.this, android.R.layout.simple_list_item_1, LIST_TITLE) ;
            //adapter.notifyDataSetChanged();
            listview.setAdapter(m_Adapter) ;
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        video_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_Adapter = new CustomAdapter();

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;

                        String title = null, address = null, mapx = null, mapy = null;
                        String query =video_edit.getText().toString(); //이부부은 검색어를 UTF-8로 넣어줄거임.
                        System.out.println(query);

                        try {
                            URL url = new URL("http://api.nongsaro.go.kr/service/agriTechVideo/videoList?"
                                    + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                    +"&videoTitle="
                                    + query//여기는 쿼리를 넣으세요(검색어)
                                    //video_edit.getText()
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
                                        if (parser.getName().equals("videoTitle")) { //title 만나면 내용을 받을수 있게 하자
                                            inTitle = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("videoImg")) { //address 만나면 내용을 받을수 있게 하자
                                            inAddress = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("videoLink")) { //mapx 만나면 내용을 받을수 있게 하자
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
                                            m_Adapter.add(parser.getText());
                                            //  System.out.println("gunmin : " + parser.getText());
                                            inTitle = false;
                                        }
                                        if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                            address = parser.getText();
                                            m_Adapter.addIMG(parser.getText());
                                            System.out.println("gunmin : " + parser.getText());
                                            inAddress = false;
                                        }
                                        if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                            mapx = parser.getText();
                                            m_Adapter.addLINK(parser.getText());
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
                    //ArrayAdapter adapter = new ArrayAdapter(video.this, android.R.layout.simple_list_item_1, LIST_TITLE) ;
                    //adapter.notifyDataSetChanged();
                    listview.setAdapter(m_Adapter) ;
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        video_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(channel == 1){
                    Toast.makeText(video.this, "처음 페이지 입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    channel--;
                    m_Adapter = new CustomAdapter();
                }

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;

                        String title = null, address = null, mapx = null, mapy = null;
                        String query = "농업"; //이부부은 검색어를 UTF-8로 넣어줄거임.
                        System.out.println(query);

                        try {
                            URL url = new URL("http://api.nongsaro.go.kr/service/agriTechVideo/videoList?"
                                    + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                    +"&pageNo="+channel//여기는 쿼리를 넣으세요(검색어)
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
                                        if (parser.getName().equals("videoTitle")) { //title 만나면 내용을 받을수 있게 하자
                                            inTitle = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("videoImg")) { //address 만나면 내용을 받을수 있게 하자
                                            inAddress = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("videoLink")) { //mapx 만나면 내용을 받을수 있게 하자
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
                                            m_Adapter.add(parser.getText());
                                            //  System.out.println("gunmin : " + parser.getText());
                                            inTitle = false;
                                        }
                                        if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                            address = parser.getText();
                                            m_Adapter.addIMG(parser.getText());
                                            System.out.println("gunmin : " + parser.getText());
                                            inAddress = false;
                                        }
                                        if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                            mapx = parser.getText();
                                            m_Adapter.addLINK(parser.getText());
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
                    //ArrayAdapter adapter = new ArrayAdapter(video.this, android.R.layout.simple_list_item_1, LIST_TITLE) ;
                    //adapter.notifyDataSetChanged();
                    listview.setAdapter(m_Adapter) ;
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        video_go.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                channel++;
                m_Adapter = new CustomAdapter();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;

                        String title = null, address = null, mapx = null, mapy = null;
                        String query = "농업"; //이부부은 검색어를 UTF-8로 넣어줄거임.
                        System.out.println(query);

                        try {
                            URL url = new URL("http://api.nongsaro.go.kr/service/agriTechVideo/videoList?"
                                    + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                    +"&pageNo="+channel//여기는 쿼리를 넣으세요(검색어)
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
                                        if (parser.getName().equals("videoTitle")) { //title 만나면 내용을 받을수 있게 하자
                                            inTitle = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("videoImg")) { //address 만나면 내용을 받을수 있게 하자
                                            inAddress = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("videoLink")) { //mapx 만나면 내용을 받을수 있게 하자
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
                                            m_Adapter.add(parser.getText());
                                            //  System.out.println("gunmin : " + parser.getText());
                                            inTitle = false;
                                        }
                                        if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                            address = parser.getText();
                                            m_Adapter.addIMG(parser.getText());
                                            System.out.println("gunmin : " + parser.getText());
                                            inAddress = false;
                                        }
                                        if (inMapx) { //isMapx이 true일 때 태그의 내용을 저장.
                                            mapx = parser.getText();
                                            m_Adapter.addLINK(parser.getText());
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
                    //ArrayAdapter adapter = new ArrayAdapter(video.this, android.R.layout.simple_list_item_1, LIST_TITLE) ;
                    //adapter.notifyDataSetChanged();
                    listview.setAdapter(m_Adapter) ;
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
}
