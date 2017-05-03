package nongsa.agoto.MaintoOther.information;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import nongsa.agoto.R;

public class term extends AppCompatActivity {
    private ArrayAdapter<String> _arrAdapter ;

    EditText TermEdit;
    // String num =0;
    String TAG = "gunmin : ";
    Button TermButton;
    final ArrayList<String> LIST_MENU = new ArrayList<String>();
    final ArrayList<String> LIST_NUM = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =  getIntent();
        setContentView(R.layout.activity_term);
        TermButton = (Button) findViewById(R.id.termbutton);
        final ListView listview = (ListView) findViewById(R.id.listview1) ;
        TermEdit = (EditText) findViewById(R.id.termedit);
        _arrAdapter = new ArrayAdapter<String>( getApplicationContext(), R.layout.simple_list_item_1_custom ) ;
        ListView listView = (ListView) findViewById( R.id.listview1 ) ;
        listView.setAdapter( _arrAdapter ) ;


        TermButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LIST_MENU.clear();
                LIST_NUM.clear();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;

                        String title = null, address = null, mapx = null, mapy = null;
                        String query = TermEdit.getText().toString(); //이부부은 검색어를 UTF-8로 넣어줄거임.
                        System.out.println(query);

                        try {
                            URL url = new URL("http://api.nongsaro.go.kr/service/farmDic/searchFrontMatch?"
                                    + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                    + "&word=" + query
                                    + "&pageNo=" + 1//여기는 쿼리를 넣으세요(검색어)
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
                                        if (parser.getName().equals("wordNo")) { //title 만나면 내용을 받을수 있게 하자
                                            inTitle = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("totalCount")) { //address 만나면 내용을 받을수 있게 하자
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
                                            LIST_NUM.add(parser.getText());
                                            //  System.out.println("gunmin : " + parser.getText());
                                            inTitle = false;
                                        }
                                        if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                            address = parser.getText();
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
                                            LIST_MENU.add(parser.getText());
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
                        try {
                            URL url = new URL("http://api.nongsaro.go.kr/service/farmDic/searchFrontMatch?"
                                    + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                    + "&word=" + query
                                    + "&pageNo=" + 2//여기는 쿼리를 넣으세요(검색어)
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
                                        if (parser.getName().equals("wordNo")) { //title 만나면 내용을 받을수 있게 하자
                                            inTitle = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("totalCount")) { //address 만나면 내용을 받을수 있게 하자
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
                                            LIST_NUM.add(parser.getText());
                                            //  System.out.println("gunmin : " + parser.getText());
                                            inTitle = false;
                                        }
                                        if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                            address = parser.getText();
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
                                            LIST_MENU.add(parser.getText());
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
                    ArrayAdapter adapter = new ArrayAdapter(term.this, R.layout.simple_list_item_1_custom, LIST_MENU) ;
                    adapter.notifyDataSetChanged();
                    listview.setAdapter(adapter) ;
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, final int position, long id) {

                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position) ;
                // System.out.println("hi : "+strText);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean inItem = false, inTitle = false, inAddress = false, inMapx = false, inMapy = false;

                        String title = null, address = null, mapx = null, mapy = null;
                        String query = "농업"; //이부부은 검색어를 UTF-8로 넣어줄거임.
                        // System.out.println(query);

                        try {
                            URL url = new URL("http://api.nongsaro.go.kr/service/farmDic/detailWord?"
                                    + "apiKey=20170410IFDID996CLWJ8KOOTVQBA"
                                    + "&wordNo=" + LIST_NUM.get(position) //여기는 쿼리를 넣으세요(검색어)
                            );


                            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                            XmlPullParser parser = parserCreator.newPullParser();

                            parser.setInput(url.openStream(), null);

                            // status.setText("파싱 중이에요..");

                            int parserEvent = parser.getEventType();
                            //  System.out.println("gunmin : " + url);
                            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                                //    System.out.println("gunmin : " + parserEvent);
                                switch (parserEvent) {
                                    case XmlPullParser.START_TAG:  //parser가 시작 태그를 만나면 실행
                                        if (parser.getName().equals("item")) {
                                            //     System.out.println("gunmin : " + parser.getName());
                                            inItem = true;
                                        }
                                        if (parser.getName().equals("faoCode")) { //title 만나면 내용을 받을수 있게 하자
                                            inTitle = true;
                                            //   System.out.println("gunmin : " + parser.getName());
                                        }
                                        if (parser.getName().equals("wordDc")) { //address 만나면 내용을 받을수 있게 하자
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
                                        if (parser.getName().equals("wordNo")) { //message 태그를 만나면 에러 출력
                                            // status1.setText(status1.getText()+"에러");
                                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                                        }
                                        break;

                                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                                        if (inTitle) { //isTitle이 true일 때 태그의 내용을 저장.
                                            title = parser.getText();
                                            //  System.out.println("gunmin : " + parser.getText());
                                            inTitle = false;
                                        }
                                        if (inAddress) { //isAddress이 true일 때 태그의 내용을 저장.
                                            address = parser.getText();
                                            System.out.println("gunmini : " + parser.getText());
                                            Intent intent = new Intent(getApplicationContext(), showterm.class);
                                            intent.putExtra("name", LIST_MENU.get(position));
                                            intent.putExtra("context", parser.getText());
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            startActivity(intent);
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
                                            LIST_MENU.add(parser.getText());
                                            inMapy = false;
                                        }
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if (parser.getName().equals("item")) {
                                            //  System.out.println("gunmin : "+parser.getName());
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

                // TODO : use strText
            }
        }) ;

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
