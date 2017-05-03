package nongsa.agoto.loginandregistration.activity;

/**
 * Created by oeunju on 2017-04-26.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tsengvn.typekit.TypekitContextWrapper;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nongsa.agoto.R;
import nongsa.agoto.loginandregistration.app.AppConfig;
import nongsa.agoto.loginandregistration.app.AppController;
import nongsa.agoto.loginandregistration.helper.SQLiteHandler;
import nongsa.agoto.loginandregistration.helper.SessionManager;


public class Board extends Activity {
    public static Activity board;
    //이름,국적,이미지리소스아이디를 가지고 있는 MemberData 클래스의 객체를
    //배열로 보관하기 위한 ArrayList 객체 생성
    //MemberData[] 이렇게 선언하는 일반배열은 배열 개수가 정해져 있어서 나중에 추가,삭제가 불편하죠.
    //배열 요소의 개수를 유동적으로 조절할 수 있는 ArrayList 객체로 data 보관
    ArrayList<MemberData> datas= new ArrayList<MemberData>();

    //ListView 참조변수

    ListView listview;

    private static final String TAG = Board.class.getSimpleName();
    public static ProgressDialog pDialog;

    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;

    private Button setting;
    private Button reg_board;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        board = Board.this;

        btnLogout = (Button) findViewById(R.id.btnLogout);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

/*		// Fetching user details from SQLite
		HashMap<String, String> user = db.getUserDetails();

		String name = user.get("name");
		String email = user.get("email");

		// Displaying the user details on the screen
		txtName.setText(name);
		txtEmail.setText(email);*/

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        setting=(Button)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Board.this , SettingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //intent.putExtra("imgld",db.getUserDetails().get("email"));
                startActivity(intent);
            }
        });
        reg_board=(Button)findViewById(R.id.reg_board);
        reg_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerContents(db.getUserDetails().get("email"));
            }
        });



        /*
        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Board.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }*/

        //이번 예제에서는 우선 직접 코딩으로 멤버정보를 입력하고
        //다른 예제에서 추가/삭제 관련 작업을 해보겠습니다.
        //비정상 회담(요즘 유명하기에 허락없이 도용합니다.-_-)멤버 정보 생성
        getContent();
		/*datas.add( new MemberData("유세윤", "대한민국", R.drawable.korea, 10, "apple"));
		datas.add( new MemberData("블레어", "호주", R.drawable.australia, 10, "apple"));
		datas.add( new MemberData("기욤 패트리", "캐나다", R.drawable.canada, 10, "apple"));
		datas.add( new MemberData("장위안", "중국", R.drawable.china, 10, "apple"));
		datas.add( new MemberData("로빈", "프랑스", R.drawable.france, 10, "apple"));
		datas.add( new MemberData("다니엘", "대한민국", R.drawable.germany, 10, "apple"));
		datas.add( new MemberData("알베르토", "대한민국", R.drawable.italy, 10, "apple"));
		datas.add( new MemberData("샘오취리", "대한민국", R.drawable.ghana, 10, "apple"));*/
        //귀찮아서 다 못넣겠네요...-_-

        //ListView 객체 찾아와서 참조
        listview= (ListView)findViewById(R.id.listview);


    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);


        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(Board.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private AdapterView.OnItemClickListener itemClickListenerOfLanguageList = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> adapterView, View clickedView, int pos, long id)
        {
            String toastMessage = datas.get(pos).getName() + " is selected.";
            Toast.makeText(
                    getApplicationContext(),
                    toastMessage,
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(Board.this , DialogActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("name",datas.get(pos).getName().toString());
            intent.putExtra("exp",datas.get(pos).getExp());
            intent.putExtra("grow",datas.get(pos).getGrow().toString());
            intent.putExtra("nation",datas.get(pos).getNation().toString());
            intent.putExtra("imgld",datas.get(pos).getImgId().toString());
            intent.putExtra("intro", datas.get(pos).getIntro().toString());
            intent.putExtra("phone", datas.get(pos).getPhone().toString());
            startActivity(intent);

        }
    };

    private void getContent() {
        // Tag used to cancel the request
        String tag_string_req = "req_get_content";

        pDialog.setMessage("Getting Content ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_READ, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jArr = jObj.getJSONArray("result");


                    System.out.println(jArr);
                    System.out.println(jObj);

                    for (int i=0; i<jArr .length(); i++) {
                        boolean error = jArr.getJSONObject(i).getBoolean("error");

                        // Check for error node in json
                        if (!error) {

                            // Now store the user in SQLite
                            int id = jArr.getJSONObject(i).getInt("id");

                            String email = jArr.getJSONObject(i).getString("email");
                            String nation = jArr.getJSONObject(i).getString("nation");
                            String grow = jArr.getJSONObject(i).getString("grow");
                            int exp = jArr.getJSONObject(i).getInt("exp");
                            Uri myUri = Uri.parse("http://52.79.61.227/file/uploads/" + email + ".jpg");
                            String intro = jArr.getJSONObject(i).getString("intro");
                            String phone = jArr.getJSONObject(i).getString("phone");

                            datas.add( new MemberData(email, nation, myUri, exp, grow, intro, phone));


                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }

                    //AdapterView의 일종인 ListView에 적용할 Adapter 객체 생성
                    //MemberData 객체의 정보들(이름, 국적, 이미지)를 적절하게 보여줄 뷰로 만들어주는 Adapter클래스 객체생성
                    //이 예제에서는 MemberDataAdapter.java 파일로 클래스를 설계하였음.
                    //첫번재 파라미터로 xml 레이아웃 파일을 객체로 만들어 주는 LayoutInflater 객체 얻어와서 전달..
                    //두번째 파라미터는 우리가 나열한 Data 배열..
                    MemberDataAdapter adapter= new MemberDataAdapter( getLayoutInflater() , datas);
                    //위에 만든 Adapter 객체를 AdapterView의 일종인 ListView에 설정.
                    adapter.notifyDataSetChanged();
                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(itemClickListenerOfLanguageList);

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Board Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            /*@Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }*/

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void registerContents(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Writing ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_WRITE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Write Response: " + response.toString());
                System.out.println("떠라!!!"+response);
                hideDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Write Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

   public static  void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}

