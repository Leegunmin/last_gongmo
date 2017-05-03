package nongsa.agoto.MaintoOther.billing;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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

import nongsa.agoto.R;
import nongsa.agoto.loginandregistration.activity.Board;
import nongsa.agoto.loginandregistration.app.AppController;
import nongsa.agoto.loginandregistration.helper.SQLiteHandler;
import util.IabHelper;
import util.IabResult;
import util.Purchase;

public class billingactivity extends ActionBarActivity {
    private SQLiteHandler db;
    IInAppBillingService mService;
    IabHelper mHelper;
    Button btn1000;
    Button btn3000;
    Button btn5000;
    String coin;
    Bundle querySkus;
    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
            AlreadyPurchaseItems();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billingactivity);
        btn1000 = (Button)findViewById(R.id.buy1000);
        btn3000 = (Button)findViewById(R.id.buy3000);
        btn5000 = (Button)findViewById(R.id.buy5000);
        btn1000.setOnClickListener(btn_1000());
        btn3000.setOnClickListener(btn_3000());
        btn5000.setOnClickListener(btn_5000());
        db = new SQLiteHandler(getApplication());
        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

        // 구글에서 발급받은 바이너리키를 입력해줍니다
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvV8bB7hJD1GGuvmu3gQTlof1re3k+/Go7ubCWJPDfm9yjfhpbzEtNzdT/DCzHbvTv3+5quqQ5wWoy0Hyo+c46l6v+rRabxsH3T+BSMpu/NZtLRmIg1tQHYCgufhgBMEWcIgglo2jICNv/gM6O7Xwmxhkxxkin7gCm32dfqGiaU2AUAoNQSkwXRnr+dATV35H1V9wvzDmWOYIrp+Sn6ySYp9ty+qHRQ0i94OpsZ0077JuhyRp3Vv8k6bcL6LbywaGzpJbd8kWJcB3SPvfBT4rT24OfS8PKytgy0w7nW9rilrtwcmNqcYxDU6iIcQAkSDGDQNX1RvwHcAAF6U3NR0/fwIDAQAB";

        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // 구매오류처리 ( 토스트하나 띄우고 결제팝업 종료시키면 되겠습니다 )
                }
                System.out.println("hererer");
                // 구매목록을 초기화하는 메서드입니다.
                // v3으로 넘어오면서 구매기록이 모두 남게 되는데 재구매 가능한 상품( 게임에서는 코인같은아이템은 ) 구매후 삭제해주어야 합니다.
                // 이 메서드는 상품 구매전 혹은 후에 반드시 호출해야합니다. ( 재구매가 불가능한 1회성 아이템의경우 호출하면 안됩니다 )
            }
        });
        ArrayList<String> skuList = new ArrayList<String> ();
        skuList.add("40000won");
        skuList.add("70000won");
        skuList.add("100000won");
        querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

    }

    public void AlreadyPurchaseItems() {
        Log.d("IAB : ", "--------------------AlreadyPurchaseItems Start--------------------------");
        try {
            Bundle ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
            int response = ownedItems.getInt("RESPONSE_CODE");
            if (response == 0) {

                ArrayList purchaseDataList = ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                String[] tokens = new String[purchaseDataList.size()];
                for (int i = 0; i < purchaseDataList.size(); ++i) {
                    String purchaseData = (String) purchaseDataList.get(i);

                    Log.d("IAB : ", "purchase data: " + purchaseData);

                    JSONObject jo = new JSONObject(purchaseData);

                    Log.d("IAB : ", "jo: " + jo);

                    tokens[i] = jo.getString("purchaseToken");
                    // 여기서 tokens를 모두 컨슘 해주기
                    mService.consumePurchase(3, getPackageName(), tokens[i]);
                }
            }
            // 토큰을 모두 컨슘했으니 구매 메서드 처리
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("IAB : ", "--------------------AlreadyPurchaseItems END--------------------------");
    }

    // 구매
    public void Buy(String id_item) {

        try {
            String sku= "";
            String testprice ="";
            coin = "";
            String itemname = "";
            Bundle skuDetails = mService.getSkuDetails(3, getPackageName(), "inapp", querySkus);
            int response = skuDetails.getInt("RESPONSE_CODE");
            System.out.println("IAB response : "+ response);
            if (response == 0) {
                ArrayList<String> responseList
                        = skuDetails.getStringArrayList("DETAILS_LIST");
                System.out.println("IAB responseList : "+ responseList);
                for (String thisResponse : responseList) {
                    JSONObject object = new JSONObject(thisResponse);
                    sku = object.getString("productId");
                    System.out.println("IAB sku : "+ sku);
                    String price = object.getString("price");
                    System.out.println("IAB price :" + price);
                    System.out.println("IAB coin : " + coin);
                    if (sku.equals(id_item)){
                        testprice = price;
                        itemname = object.getString("productId");
                        coin = object.getString("description");
                    }
                    System.out.println("IAB testprice :"+ testprice);
                }
            }
            Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
                    itemname, "inapp", "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");

            PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

            if(pendingIntent != null){
                startIntentSenderForResult(pendingIntent.getIntentSender(),
                        1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
                        Integer.valueOf(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener  = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            // 여기서 아이템 추가 해주시면 됩니다.
            // 만약 서버로 영수증 체크후에 아이템 추가한다면, 서버로 purchase.getOriginalJson() , purchase.getSignature() 2개 보내시면 됩니다.
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mServiceConn != null){
            unbindService(mServiceConn);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    System.out.println("You have bought the " + sku + ". Excellent choice,"+
                            "adventurer!");
                    //디비에 넣는 코딩
                    int auth = db.getAuth();
                    db.setAuth(auth+1);

                    if(sku.equals("40000won")){

                        getContent(auth+1);
                    }
                    else if(sku.equals("70000won")){
                        getContent(auth+2);
                    }
                    else if(sku.equals("100000won")){
                        getContent(auth+3);
                    }


                }
                catch (JSONException e) {
                    System.out.println("Failed to parse purchase data.");
                    e.printStackTrace();
                }
            }
        }
    }

    private Button.OnClickListener btn_1000(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buy("40000won");

            }
        };
    }

    private Button.OnClickListener btn_3000(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buy("70000won");
            }
        };
    }

    private Button.OnClickListener btn_5000(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buy("100000won");
            }
        };
    }

    private void getContent(final int ia) {
        // Tag used to cancel the request
        String tag_string_req = "req_get_content";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://52.79.61.227/login/updateauth.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("hello", "Get Response: " + response.toString());

                // Check for error node in json
                Toast.makeText(billingactivity.this, "결제를 완료 했습니다.", Toast.LENGTH_SHORT).show();





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
                params.put("email", db.getUserDetails().get("email").toString());
                params.put("ia", String.valueOf(ia));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}