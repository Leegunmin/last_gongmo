package nongsa.agoto.MaintoOther.diary;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import nongsa.agoto.R;

public class diary extends AppCompatActivity {
    ArrayList<String> LIST_MENU = new ArrayList<String>();
    ArrayList<String> SECOND_LIST_MENU = new ArrayList<String>();
    ArrayList<String> SECOND_LIST_NUM = new ArrayList<String>();
    String first_temp;
    int second_temp;
    Button back_btn;
    int click = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        final ListView listview = (ListView) findViewById(R.id.listviewa) ;
        back_btn = (Button)findViewById(R.id.back_button);
        LIST_MENU.add("과수");
        LIST_MENU.add("밭농사");
        LIST_MENU.add("벼");
        LIST_MENU.add("채소");
        ArrayAdapter adapter = new ArrayAdapter(diary.this, R.layout.simple_list_item_1_custom, LIST_MENU);
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, final int position, long id) {
                if(click==0) {
                    SECOND_LIST_MENU = new ArrayList<String>();
                    SECOND_LIST_NUM = new ArrayList<String>();
                    if (LIST_MENU.get(position).equals("과수")) {
                        first_temp = "f";
                        SECOND_LIST_MENU.add("살구");
                        SECOND_LIST_MENU.add("매실");
                        SECOND_LIST_MENU.add("무화과-노지재배");
                        SECOND_LIST_MENU.add("무화과-시설재배");
                        SECOND_LIST_MENU.add("앵두");
                        SECOND_LIST_MENU.add("감귤-보통");
                        SECOND_LIST_MENU.add("감귤-시설재배");
                        SECOND_LIST_MENU.add("포도-표준");
                        SECOND_LIST_MENU.add("참다래");
                        SECOND_LIST_MENU.add("단감");
                        SECOND_LIST_MENU.add("단감-시설재배");
                        SECOND_LIST_MENU.add("복숭아");
                        SECOND_LIST_MENU.add("한라봉");
                        SECOND_LIST_MENU.add("자두");
                        SECOND_LIST_MENU.add("배");
                        SECOND_LIST_MENU.add("사과");
                        ArrayAdapter adapter = new ArrayAdapter(diary.this, R.layout.simple_list_item_1_custom, SECOND_LIST_MENU);
                        adapter.notifyDataSetChanged();
                        listview.setAdapter(adapter);
                    } else if (LIST_MENU.get(position).equals("밭농사")) {
                        first_temp = "d";
                        SECOND_LIST_MENU.add("조");
                        SECOND_LIST_MENU.add("팥");
                        SECOND_LIST_MENU.add("풋콩");
                        SECOND_LIST_MENU.add("참깨");
                        SECOND_LIST_MENU.add("일반보리");
                        SECOND_LIST_MENU.add("감자");
                        SECOND_LIST_MENU.add("유채");
                        SECOND_LIST_MENU.add("수수");
                        SECOND_LIST_MENU.add("강낭콩");
                        SECOND_LIST_MENU.add("완두");
                        SECOND_LIST_MENU.add("들깨");
                        SECOND_LIST_MENU.add("녹두");
                        SECOND_LIST_MENU.add("고구마");
                        SECOND_LIST_MENU.add("메밀");
                        SECOND_LIST_MENU.add("옥수수");
                        SECOND_LIST_MENU.add("땅콩");
                        SECOND_LIST_MENU.add("맥주보리");
                        SECOND_LIST_MENU.add("밀");
                        SECOND_LIST_MENU.add("콩");
                        ArrayAdapter adapter = new ArrayAdapter(diary.this, android.R.layout.simple_list_item_1, SECOND_LIST_MENU);
                        adapter.notifyDataSetChanged();
                        listview.setAdapter(adapter);
                    } else if (LIST_MENU.get(position).equals("벼")) {
                        first_temp = "r";
                        SECOND_LIST_MENU.add("직파재배");
                        SECOND_LIST_MENU.add("기계이앙재배");
                        ArrayAdapter adapter = new ArrayAdapter(diary.this, R.layout.simple_list_item_1_custom, SECOND_LIST_MENU);
                        adapter.notifyDataSetChanged();
                        listview.setAdapter(adapter);
                    } else if (LIST_MENU.get(position).equals("채소")) {
                        first_temp = "v";
                        SECOND_LIST_MENU.add("신선초");
                        SECOND_LIST_MENU.add("참나물");
                        SECOND_LIST_MENU.add("비트");
                        SECOND_LIST_MENU.add("쪽파");
                        SECOND_LIST_MENU.add("연근");
                        SECOND_LIST_MENU.add("우엉");
                        SECOND_LIST_MENU.add("치커리");
                        SECOND_LIST_MENU.add("냉이");
                        SECOND_LIST_MENU.add("호박-늙은호박");
                        SECOND_LIST_MENU.add("청경채");
                        SECOND_LIST_MENU.add("참취");
                        SECOND_LIST_MENU.add("곰취");
                        SECOND_LIST_MENU.add("배추-고랭지재배");
                        SECOND_LIST_MENU.add("아욱");
                        SECOND_LIST_MENU.add("두릅");
                        SECOND_LIST_MENU.add("고추-꽈리고추");
                        SECOND_LIST_MENU.add("딸기-반촉성재배");
                        SECOND_LIST_MENU.add("오이");
                        SECOND_LIST_MENU.add("마늘");
                        SECOND_LIST_MENU.add("근대");
                        SECOND_LIST_MENU.add("파슬리");
                        SECOND_LIST_MENU.add("딸기-사계성여름재배");
                        SECOND_LIST_MENU.add("쑥갓");
                        SECOND_LIST_MENU.add("적채");
                        SECOND_LIST_MENU.add("결구상추");
                        SECOND_LIST_MENU.add("샐러리");
                        SECOND_LIST_MENU.add("들깨잎");
                        SECOND_LIST_MENU.add("참외");
                        SECOND_LIST_MENU.add("잎마늘");
                        SECOND_LIST_MENU.add("미나리");
                        SECOND_LIST_MENU.add("토란");
                        SECOND_LIST_MENU.add("단호박");
                        SECOND_LIST_MENU.add("생강");
                        SECOND_LIST_MENU.add("브로콜리-고랭지");
                        SECOND_LIST_MENU.add("브로콜리-평야");
                        SECOND_LIST_MENU.add("양파");
                        SECOND_LIST_MENU.add("배추");
                        SECOND_LIST_MENU.add("양배추");
                        SECOND_LIST_MENU.add("파");
                        SECOND_LIST_MENU.add("당근");
                        SECOND_LIST_MENU.add("호박");
                        SECOND_LIST_MENU.add("단호박");
                        SECOND_LIST_MENU.add("상추");
                        SECOND_LIST_MENU.add("부추");
                        SECOND_LIST_MENU.add("고추-촉성재배");
                        SECOND_LIST_MENU.add("무-고랭지재배");
                        SECOND_LIST_MENU.add("수박");
                        SECOND_LIST_MENU.add("무");
                        SECOND_LIST_MENU.add("방울토마토");
                        SECOND_LIST_MENU.add("가지");
                        SECOND_LIST_MENU.add("고들빼기");
                        SECOND_LIST_MENU.add("멜론");
                        SECOND_LIST_MENU.add("아스파라거스");
                        SECOND_LIST_MENU.add("고사리");
                        SECOND_LIST_MENU.add("피망");
                        SECOND_LIST_MENU.add("고추-보통재배");
                        SECOND_LIST_MENU.add("갓");
                        SECOND_LIST_MENU.add("시금치");
                        SECOND_LIST_MENU.add("딸기촉성재배");
                        SECOND_LIST_MENU.add("토마토");


                        ArrayAdapter adapter = new ArrayAdapter(diary.this, R.layout.simple_list_item_1_custom, SECOND_LIST_MENU);
                        adapter.notifyDataSetChanged();
                        listview.setAdapter(adapter);
                    }
                    click = 1;
                }
                else if(click == 1){
                    System.out.println("position : "+position);
                    second_temp = position;
//                    Intent intent = new Intent(diary.this,Webview.class);
//                    intent.putExtra("link","http://13.124.6.157/schedule/fruit/aa.pdf");
////                    intent.putExtra("link","http://13.124.6.157/schedule/fruit/aa.pdf");
//                    startActivity(intent);
                    feed();
                    //웹뷰를 띄운다.
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(click == 1) {
                    click = 0;
                    ArrayAdapter adapter = new ArrayAdapter(diary.this, R.layout.simple_list_item_1_custom, LIST_MENU);
                    adapter.notifyDataSetChanged();
                    listview.setAdapter(adapter);
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
            mDlg = new ProgressDialog(diary.this);
            mDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDlg.setMessage("Start");
            mDlg.show();
        }
        @Override
        protected String doInBackground(String... params) {


            int count = 0;

            try {
                Thread.sleep(100);
               // URLEncoder.encode(ttt, "UTF-8");
                second_temp++;
                URL url = new URL("http://52.79.61.227/schedule/fruit/"+first_temp+second_temp+".pdf");


                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/"+first_temp+second_temp+".pdf");
                System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/"+first_temp+second_temp+".pdf");
                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    System.out.println("total : "+total);
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
                File f1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/"+first_temp+second_temp+".pdf");
                diary.this.sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f1)) );
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (f1.getName().endsWith(".pdf")){
                    intent.setDataAndType(Uri.fromFile(f1), "application/pdf");
                }else if (f1.getName().endsWith(".hwp")){
                    intent.setDataAndType(Uri.fromFile(f1), "application/hwp");
                }
                try{
                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    System.out.println("해당파일을 실항할 수 있는 어플리케이션이 없습니다.\n파일을 열 수 없습니다.");
                    e.printStackTrace();
                }
                // 작업이 진행되면서 호출하며 화면의 업그레이드를 담당하게 된다
                //publishProgress("progress", 1, "Task " + 1 + " number");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

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

