package nongsa.agoto.MaintoOther.vacant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nongsa.agoto.MaintoOther.information.adapter.VacantAdapter;
import nongsa.agoto.R;
import nongsa.agoto.loginandregistration.app.AppController;

public class vacants extends AppCompatActivity {
    private VacantAdapter m_Adapter;
    Button small_city_button;
    Spinner big_city_spinner;
    Spinner small_city_spinner;
    static public ListView vacant_List;
    static public ArrayList<String> LIST_MENU = new ArrayList<String>();
    static public ArrayList<String> SMA_LIST_MENU = new ArrayList<String>();
    static public ArrayList<String> cityname = new ArrayList<String>();
    static public ArrayList<String> smallcityname =new ArrayList<String>();
    static public ArrayList<String> fullcityname = new ArrayList<String>();
    static public ArrayList<String> wish_price = new ArrayList<String>();
    static public ArrayList<String> provide_name =new ArrayList<String>();
    static public ArrayList<String> filed_size = new ArrayList<String>();
    static public ArrayList<String> build_size = new ArrayList<String>();
    static public ArrayList<String> another_size = new ArrayList<String>();;
    static public ArrayList<String> build_year = new ArrayList<String>();
    static public ArrayList<String> vacant_year = new ArrayList<String>();
    static public ArrayList<String> have_name = new ArrayList<String>();
    static public ArrayList<String> phone = new ArrayList<String>();
    static public ArrayList<String> image_url = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacants);
        vacant_List = (ListView)findViewById(R.id.vacant_list);
        big_city_spinner = (Spinner)findViewById(R.id.big_city_spinner);
        small_city_spinner = (Spinner)findViewById(R.id.small_city_spinner);
        small_city_button = (Button)findViewById(R.id.small_city_button);
        m_Adapter = new VacantAdapter();
        small_city_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                m_Adapter = new VacantAdapter();
                getContent();

            }
        });
        initialize();
        big_city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                setSmall_city(LIST_MENU.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getContent() {
        // Tag used to cancel the request
        String tag_string_req = "req_get_content";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://52.79.61.227/board/json1.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("hello", "Get Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jArr = jObj.getJSONArray("result");


                    System.out.println(jArr);
                    System.out.println(jObj);

                    for (int i=0; i<jArr .length(); i++) {
                        boolean error = jArr.getJSONObject(i).getBoolean("error");

                        // Check for error node in json
                        if (!error) {
                            // user successfully logged in
                            // Create login session

                            // Now store the user in SQLite

                            cityname.add(jArr.getJSONObject(i).getString("cityname").replaceAll("\"",""));
                            smallcityname.add(jArr.getJSONObject(i).getString("smallcityname").replaceAll("\"",""));
                            String fullname = jArr.getJSONObject(i).getString("fullcityname").replaceAll("\"","");
                            fullcityname.add(fullname);
                            m_Adapter.add(fullname);
                            String pricing = jArr.getJSONObject(i).getString("wish_price").replaceAll("\"","");
                            wish_price.add(pricing);
                            m_Adapter.addPrice(pricing);
                            provide_name.add(jArr.getJSONObject(i).getString("provide_name").replaceAll("\"",""));
                            filed_size.add(jArr.getJSONObject(i).getString("filed_size").replaceAll("\"",""));
                            build_size.add(jArr.getJSONObject(i).getString("build_size").replaceAll("\"",""));
                            build_year.add(jArr.getJSONObject(i).getString("build_year").replaceAll("\"",""));
                            vacant_year.add(jArr.getJSONObject(i).getString("vacant_year").replaceAll("\"",""));
                            String nameing = jArr.getJSONObject(i).getString("have_name").replaceAll("\"","");
                            have_name.add(nameing);
                            m_Adapter.addLINK(nameing);
                            phone.add(jArr.getJSONObject(i).getString("phone").replaceAll("\"",""));
                            String img_url = jArr.getJSONObject(i).getString("image_url").replaceAll("\"","");
                            System.out.println("image_url : "+img_url);
                            image_url.add(img_url);
                            m_Adapter.addIMG(img_url);



                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }
                    ArrayAdapter adapter = new ArrayAdapter(vacants.this,R.layout.simple_list_item_1_custom, fullcityname);
                    adapter.notifyDataSetChanged();
                    vacant_List.setAdapter(m_Adapter) ;


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("hello", "Board Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {


            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("post_cityname", big_city_spinner.getSelectedItem().toString());
                params.put("post_small", small_city_spinner.getSelectedItem().toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    private void setSmall_city(String city){
        SMA_LIST_MENU = new ArrayList<String>();
        if(city.equals("강원도")){
            SMA_LIST_MENU.add("시군명");
            SMA_LIST_MENU.add("동해시");
            SMA_LIST_MENU.add("평창군");
            SMA_LIST_MENU.add("홍천군");
            ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(vacants.this,R.layout.simple_spinner_item_custom,SMA_LIST_MENU);
            sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
            small_city_spinner.setAdapter(sAdapter);

        }
        else if(city.equals("경기도")){
            SMA_LIST_MENU.add("시군명");
            SMA_LIST_MENU.add("양평군");
            SMA_LIST_MENU.add("파주시");
            ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(vacants.this,R.layout.simple_spinner_item_custom,SMA_LIST_MENU);
            sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
            small_city_spinner.setAdapter(sAdapter);
        }
        else if(city.equals("경상남도")){
            SMA_LIST_MENU.add("시군명");
            SMA_LIST_MENU.add("거창군");
            SMA_LIST_MENU.add("산청군");
            SMA_LIST_MENU.add("의령군");
            ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(vacants.this,R.layout.simple_spinner_item_custom,SMA_LIST_MENU);
            sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            small_city_spinner.setAdapter(sAdapter);
        }
        else if(city.equals("경상북도")){
            SMA_LIST_MENU.add("시군명");
            SMA_LIST_MENU.add("상주시");
            SMA_LIST_MENU.add("안동시");
            SMA_LIST_MENU.add("영덕군");
            SMA_LIST_MENU.add("영주시");
            SMA_LIST_MENU.add("예천군");
            SMA_LIST_MENU.add("의성군");
            ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(vacants.this,R.layout.simple_spinner_item_custom,SMA_LIST_MENU);
            sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
            small_city_spinner.setAdapter(sAdapter);
        }
        else if(city.equals("전라남도")){
            SMA_LIST_MENU.add("시군명");
            SMA_LIST_MENU.add("강진군");
            SMA_LIST_MENU.add("고흥군");
            SMA_LIST_MENU.add("광양시");
            SMA_LIST_MENU.add("구례군");
            SMA_LIST_MENU.add("나주시");
            SMA_LIST_MENU.add("영광군");
            SMA_LIST_MENU.add("영암군");
            SMA_LIST_MENU.add("완도군");
            SMA_LIST_MENU.add("장성군");
            SMA_LIST_MENU.add("장흥군");
            SMA_LIST_MENU.add("화순군");
            ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(vacants.this,R.layout.simple_spinner_item_custom,SMA_LIST_MENU);
            sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
            small_city_spinner.setAdapter(sAdapter);
        }
        else if(city.equals("전라북도")){
            SMA_LIST_MENU.add("시군명");
            SMA_LIST_MENU.add("군산시");
            SMA_LIST_MENU.add("김제시");
            SMA_LIST_MENU.add("남원시");
            SMA_LIST_MENU.add("순창군");
            SMA_LIST_MENU.add("장수군");
            SMA_LIST_MENU.add("정읍시");
            ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(vacants.this,R.layout.simple_spinner_item_custom,SMA_LIST_MENU);
            sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
            small_city_spinner.setAdapter(sAdapter);
        }
        else if(city.equals("충청남도")){
            SMA_LIST_MENU.add("시군명");
            SMA_LIST_MENU.add("보령시");
            SMA_LIST_MENU.add("부여군");
            SMA_LIST_MENU.add("서산시");
            SMA_LIST_MENU.add("예산군");
            SMA_LIST_MENU.add("청양군");
            SMA_LIST_MENU.add("홍성군");
            ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(vacants.this,R.layout.simple_spinner_item_custom,SMA_LIST_MENU);
            sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
            small_city_spinner.setAdapter(sAdapter);
        }
        else if(city.equals("충청북도")){
            SMA_LIST_MENU.add("시군명");
            SMA_LIST_MENU.add("괴상군");
            SMA_LIST_MENU.add("단양군");
            SMA_LIST_MENU.add("보은군");
            SMA_LIST_MENU.add("영동군");
            SMA_LIST_MENU.add("청주시");
            SMA_LIST_MENU.add("충주시");
            ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(vacants.this,R.layout.simple_spinner_item_custom,SMA_LIST_MENU);
            sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
            small_city_spinner.setAdapter(sAdapter);
        }
        else if(city.equals("시도명")){
            SMA_LIST_MENU.add("시군명");
            ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(vacants.this,R.layout.simple_spinner_item_custom,SMA_LIST_MENU);
            sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
            small_city_spinner.setAdapter(sAdapter);
        }
    }
    private void initialize(){
        LIST_MENU = new ArrayList<String>();
        LIST_MENU.add("시도명");
        LIST_MENU.add("강원도");
        LIST_MENU.add("경기도");
        LIST_MENU.add("경상남도");
        LIST_MENU.add("경상북도");
        LIST_MENU.add("전라남도");
        LIST_MENU.add("전라북도");
        LIST_MENU.add("충청남도");
        LIST_MENU.add("충청북도");
        ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(vacants.this,R.layout.simple_spinner_item_custom,LIST_MENU);
        sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
        big_city_spinner.setAdapter(sAdapter);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
