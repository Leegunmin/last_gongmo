package nongsa.agoto.loginandregistration.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import nongsa.agoto.MaintoOther.billing.billingactivity;
import nongsa.agoto.R;
import nongsa.agoto.loginandregistration.activity.setting.ImgUpload;
import nongsa.agoto.loginandregistration.activity.Board;
import nongsa.agoto.loginandregistration.app.AppConfig;
import nongsa.agoto.loginandregistration.app.AppController;
import nongsa.agoto.loginandregistration.helper.SQLiteHandler;
import nongsa.agoto.loginandregistration.helper.SessionManager;

public class SettingActivity extends Activity {
    private static final String TAG = SettingActivity.class.getSimpleName();
    public static Activity setting;

    private Button btnLogout;
    private Button pay;

    private static ImageView img_setting;
    private EditText name_setting;
    private EditText phone_setting;
    private EditText exp_setting;
    private EditText nation_setting;
    private EditText subNation_setting;
    private EditText grow_setting;
    private EditText intro_setting;
    private Button revise_setting;
    private Button upload_setting;

    private SQLiteHandler db;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setting = SettingActivity.this;

        btnLogout = (Button) findViewById(R.id.logout_setting);
        pay = (Button) findViewById(R.id.pay_setting);
        img_setting = (ImageView)findViewById(R.id.img_setting);
        name_setting = (EditText)findViewById(R.id.name_setting);
        phone_setting = (EditText)findViewById(R.id.phone_setting);
        exp_setting = (EditText)findViewById(R.id.exp_setting);
        nation_setting = (EditText)findViewById(R.id.nation_setting);
        subNation_setting = (EditText)findViewById(R.id.subNation_setting);
        grow_setting = (EditText)findViewById(R.id.grow_setting);
        intro_setting = (EditText)findViewById(R.id.intro_setting);
        revise_setting = (Button)findViewById(R.id.revise_setting);
        upload_setting = (Button)findViewById(R.id.upload_setting);



        // SqLite database handler
        db = new SQLiteHandler(Board.board);

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        setImg();



        nation_setting.setText(db.getUserDetails().get("subNationA"));
        subNation_setting.setText(db.getUserDetails().get("subNationB"));

        name_setting.setText(db.getUserDetails().get("name"));
        phone_setting.setText(db.getUserDetails().get("phone"));
        exp_setting.setText(db.getUserDetails().get("exp"));
        grow_setting.setText(db.getUserDetails().get("grow"));
        intro_setting.setText(db.getUserDetails().get("intro"));
        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), billingactivity.class);
                startActivity(intent);

            }
        });
        revise_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTemp = db.getUserDetails().get("email").toString().trim();
                updateInfo(emailTemp, name_setting.getText().toString().trim(), phone_setting.getText().toString().trim(), exp_setting.getText().toString().trim(), grow_setting.getText().toString().trim(), intro_setting.getText().toString().trim(),
                        nation_setting.getText().toString().trim(), subNation_setting.getText().toString().trim());
                logoutUserB();
                reLogin(emailTemp);
                Board.board.finish();//모름모름
                Intent intent = new Intent(getApplicationContext(), Board.class);
                startActivity(intent);
                finish();

            }
        });
        upload_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImgUpload.class);
                startActivity(intent);
            }
        });



    }

    private void reLogin(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_RELOAD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                System.out.println(response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        int exp = user.getInt("exp");
                        String nation = user.getString("nation");
                        String grow = user.getString("grow");
                        String phone = user.getString("phone");

                        int auth = user.getInt("auth");
                        String intro = user.getString("intro");

                        String subNationA = user.getString("subNationA");
                        String subNationB = user.getString("subNationB");

                        System.out.println(name);
                        System.out.println(email);
                        System.out.println(phone);

                        // Inserting row in users table
                        db.addUser(name, email, uid, exp, nation, grow, phone, auth, intro,subNationA ,subNationB);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void updateInfo(final String email, final String name, final String phone, final String exp, final String grow, final String intro
            ,final String subNationA, final String subNationB) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Setting update Response: " + response.toString());
                System.out.println(response);


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("name", name);
                params.put("phone", phone);
                params.put("exp", exp);
                params.put("grow", grow);
                params.put("intro", intro);
                params.put("subNationA", subNationA);
                params.put("subNationB", subNationB);
                params.put("nation", subNationA + " " + subNationB);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Board.board.finish();
        Intent intent = new Intent(getApplicationContext(), Board.class);
        startActivity(intent);
        finish();

    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void logoutUserB() {
        session.setLogin(false);

        db.deleteUsers();
    }

    private void setImg(){
        final Handler handler = new Handler();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                // TODO Auto-generated method stub
                try{
                    // 걍 외우는게 좋다 -_-;
                    URL url = new URL("http://52.79.61.227/file/uploads/" + db.getUserDetails().get("email") + ".jpg");
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            img_setting.setImageBitmap(bm);
                        }
                    });
                } catch(Exception e){

                }

            }
        });
        t.start();
    }




}
