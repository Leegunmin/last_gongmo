package nongsa.agoto.MaintoOther;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import nongsa.agoto.R;

public class Like extends AppCompatActivity {
    final ArrayList<String> EXT_SIDO_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_SIDO_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_GUNGGI_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_GUNGGI_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_GANGWON_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_GANGWON_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_CHUNGBOOK_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_CHUNGBOOK_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_CHUNGNAM_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_CHUNGNAM_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_JUNNAM_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_JUNNAM_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_JUNBOOK_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_JUNBOOK_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_GUNNAM_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_GUNNAM_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_GUNBOOK_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_GUNBOOK_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_JEJU_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_JEJU_CODE = new ArrayList<String>();
    Spinner like_big_spinner;
    Spinner like_small_spinner;
    String big_code;
    String small_code;
    int division;
    Button like_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        like_big_spinner = (Spinner)findViewById(R.id.like_big_spinner);
        like_small_spinner =(Spinner)findViewById(R.id.like_small_spinner) ;
        like_button = (Button)findViewById(R.id.like_button);
        initializing();
        ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(Like.this,R.layout.simple_spinner_item_custom,EXT_SIDO_NAME);
        sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
        like_big_spinner.setAdapter(sAdapter);

        like_big_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                big_code = EXT_SIDO_CODE.get(position);
                if(EXT_SIDO_NAME.get(position).equals("경기도")){
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(Like.this,R.layout.simple_spinner_item_custom,EXT_GUNGGI_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    division = 1;
                    like_small_spinner.setAdapter(sAdapter);
                }
                else if(EXT_SIDO_NAME.get(position).equals("강원도")){
                    division = 2;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(Like.this,R.layout.simple_spinner_item_custom,EXT_GANGWON_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    like_small_spinner.setAdapter(sAdapter);
                }
                else if(EXT_SIDO_NAME.get(position).equals("충청북도")){
                    division = 3;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(Like.this,R.layout.simple_spinner_item_custom,EXT_CHUNGBOOK_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    like_small_spinner.setAdapter(sAdapter);
                }
                else if(EXT_SIDO_NAME.get(position).equals("충청남도")){
                    division = 4;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(Like.this,R.layout.simple_spinner_item_custom,EXT_CHUNGNAM_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    like_small_spinner.setAdapter(sAdapter);
                }
                else if(EXT_SIDO_NAME.get(position).equals("전라북도")){
                    division = 5;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(Like.this,R.layout.simple_spinner_item_custom,EXT_JUNBOOK_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    like_small_spinner.setAdapter(sAdapter);
                }
                else if(EXT_SIDO_NAME.get(position).equals("전라남도")){
                    division = 6;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(Like.this,R.layout.simple_spinner_item_custom,EXT_JUNNAM_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    like_small_spinner.setAdapter(sAdapter);
                }
                else if(EXT_SIDO_NAME.get(position).equals("경상북도")){
                    division = 7;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(Like.this,R.layout.simple_spinner_item_custom,EXT_GUNBOOK_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    like_small_spinner.setAdapter(sAdapter);
                }
                else if(EXT_SIDO_NAME.get(position).equals("경상남도")){
                    division = 8;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(Like.this,R.layout.simple_spinner_item_custom,EXT_GUNNAM_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    like_small_spinner.setAdapter(sAdapter);
                }
                else if(EXT_SIDO_NAME.get(position).equals("제주도")){
                    division = 9;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(Like.this,R.layout.simple_spinner_item_custom,EXT_JEJU_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
                    like_small_spinner.setAdapter(sAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        like_small_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(division == 1){
                    small_code = EXT_GUNGGI_CODE.get(position);
                }
                else if(division == 2){
                    small_code = EXT_GANGWON_CODE.get(position);
                }
                else if(division ==3){
                    small_code = EXT_CHUNGBOOK_CODE.get(position);
                }
                else if(division == 4){
                    small_code = EXT_CHUNGNAM_CODE.get(position);
                }
                else if(division ==5){
                    small_code = EXT_JUNBOOK_CODE.get(position);
                }
                else if(division == 6){
                    small_code = EXT_JUNNAM_CODE.get(position);
                }
                else if(division ==7){
                    small_code = EXT_GUNBOOK_CODE.get(position);
                }
                else if(division == 8){
                    small_code = EXT_GUNNAM_CODE.get(position);
                }
                else if(division ==3){
                    small_code = EXT_CHUNGBOOK_CODE.get(position);
                }
                else if(division ==9){
                    small_code = EXT_JEJU_CODE.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        like_button.setOnClickListener(new View.OnClickListener(){ // 주요정보 - 빈집
            String  big_query = "";
            String small_query = "";
            public void onClick(View v){
                try{
                    big_query = URLEncoder.encode(big_code,"utf-8");
                    small_query = URLEncoder.encode(small_code,"utf-8");
                }catch(UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse("http://52.79.61.227/rec/1.php?sido_cd="+big_query+"&sigun_cd="+small_query);

                i.setData(u);

                startActivity(i);

            }
        });
    }


    public void initializing(){
        EXT_SIDO_NAME.add("경기도"); EXT_SIDO_CODE.add("6410000");EXT_SIDO_NAME.add("강원도");EXT_SIDO_CODE.add("6420000");EXT_SIDO_NAME.add("충청북도");
        EXT_SIDO_CODE.add("6430000");EXT_SIDO_NAME.add("충청남도");EXT_SIDO_CODE.add("6440000");EXT_SIDO_NAME.add("전라북도");EXT_SIDO_CODE.add("6450000");EXT_SIDO_NAME.add("전라남도");
        EXT_SIDO_CODE.add("6460000");EXT_SIDO_NAME.add("경상북도");EXT_SIDO_CODE.add("6470000");EXT_SIDO_NAME.add("경상남도");EXT_SIDO_CODE.add("6480000");
        EXT_SIDO_NAME.add("제주도");EXT_SIDO_CODE.add("6500000");
        //////////////

        EXT_JEJU_NAME.add("제주시");EXT_JEJU_CODE.add("5490000");EXT_JEJU_NAME.add("서귀포시");EXT_JEJU_CODE.add("5500000");

        EXT_GUNNAM_NAME.add("거제시");
        EXT_GUNNAM_CODE.add("5370000");
        EXT_GUNNAM_NAME.add("통영시");
        EXT_GUNNAM_CODE.add("5330000");
        EXT_GUNNAM_NAME.add("고성군");
        EXT_GUNNAM_CODE.add("5420000");
        EXT_GUNNAM_NAME.add("남해시");
        EXT_GUNNAM_CODE.add("5430000");
        EXT_GUNNAM_NAME.add("사천시");
        EXT_GUNNAM_CODE.add("5340000");
        EXT_GUNNAM_NAME.add("하동군");
        EXT_GUNNAM_CODE.add("5440000");
        EXT_GUNNAM_NAME.add("산청군");
        EXT_GUNNAM_CODE.add("5450000");
        EXT_GUNNAM_NAME.add("함양군");
        EXT_GUNNAM_CODE.add("5460000");
        EXT_GUNNAM_NAME.add("거창군");
        EXT_GUNNAM_CODE.add("5470000");
        EXT_GUNNAM_NAME.add("진주시");
        EXT_GUNNAM_CODE.add("5310000");
        EXT_GUNNAM_NAME.add("마산시");
        EXT_GUNNAM_CODE.add("5280000");
        EXT_GUNNAM_NAME.add("진해시");
        EXT_GUNNAM_CODE.add("5320000");
        EXT_GUNNAM_NAME.add("합천군");
        EXT_GUNNAM_CODE.add("5480000");
        EXT_GUNNAM_NAME.add("의령군");
        EXT_GUNNAM_CODE.add("5390000");
        EXT_GUNNAM_NAME.add("함안군");
        EXT_GUNNAM_CODE.add("5400000");
        EXT_GUNNAM_NAME.add("창원시");
        EXT_GUNNAM_CODE.add("5670000");
        EXT_GUNNAM_NAME.add("김해시");
        EXT_GUNNAM_CODE.add("5350000");
        EXT_GUNNAM_NAME.add("창녕군");
        EXT_GUNNAM_CODE.add("5410000");
        EXT_GUNNAM_NAME.add("밀양시");
        EXT_GUNNAM_CODE.add("5360000");
        EXT_GUNNAM_NAME.add("양산시");
        EXT_GUNNAM_CODE.add("5380000");
///////지금까지 경상남도

        EXT_GUNBOOK_NAME.add("청도군");
        EXT_GUNBOOK_CODE.add("5190000");
        EXT_GUNBOOK_NAME.add("고령군");
        EXT_GUNBOOK_CODE.add("5200000");
        EXT_GUNBOOK_NAME.add("성주군");
        EXT_GUNBOOK_CODE.add("5210000");
        EXT_GUNBOOK_NAME.add("칠곡군");
        EXT_GUNBOOK_CODE.add("5220000");
        EXT_GUNBOOK_NAME.add("김천시");
        EXT_GUNBOOK_CODE.add("5060000");
        EXT_GUNBOOK_NAME.add("경산시");
        EXT_GUNBOOK_CODE.add("5130000");
        EXT_GUNBOOK_NAME.add("경주시");
        EXT_GUNBOOK_CODE.add("5050000");
        EXT_GUNBOOK_NAME.add("영천시");
        EXT_GUNBOOK_CODE.add("5100000");
        EXT_GUNBOOK_NAME.add("구미시");
        EXT_GUNBOOK_CODE.add("5080000");
        EXT_GUNBOOK_NAME.add("군위군");
        EXT_GUNBOOK_CODE.add("5140000");
        EXT_GUNBOOK_NAME.add("포항시");
        EXT_GUNBOOK_CODE.add("5020000");
        EXT_GUNBOOK_NAME.add("상주시");
        EXT_GUNBOOK_CODE.add("5110000");
        EXT_GUNBOOK_NAME.add("의성군");
        EXT_GUNBOOK_CODE.add("5150000");
        EXT_GUNBOOK_NAME.add("청송군");
        EXT_GUNBOOK_CODE.add("5160000");
        EXT_GUNBOOK_NAME.add("영덕군");
        EXT_GUNBOOK_CODE.add("5160000");
        EXT_GUNBOOK_NAME.add("문경시");
        EXT_GUNBOOK_CODE.add("5120000");
        EXT_GUNBOOK_NAME.add("예천군");
        EXT_GUNBOOK_CODE.add("5230000");
        EXT_GUNBOOK_NAME.add("안동시");
        EXT_GUNBOOK_CODE.add("5070000");
        EXT_GUNBOOK_NAME.add("영양군");
        EXT_GUNBOOK_CODE.add("5170000");
        EXT_GUNBOOK_NAME.add("울진군");
        EXT_GUNBOOK_CODE.add("5250000");
        EXT_GUNBOOK_NAME.add("봉화군");
        EXT_GUNBOOK_CODE.add("5240000");
        EXT_GUNBOOK_NAME.add("영주시");
        EXT_GUNBOOK_CODE.add("5090000");
        EXT_GUNBOOK_NAME.add("울릉군");
        EXT_GUNBOOK_CODE.add("5260000");

        /////지금까지 경상북도

        EXT_JUNNAM_NAME.add("진도군");
        EXT_JUNNAM_CODE.add("5000000");
        EXT_JUNNAM_NAME.add("해남군");
        EXT_JUNNAM_CODE.add("4930000");
        EXT_JUNNAM_NAME.add("강진군");
        EXT_JUNNAM_CODE.add("4920000");
        EXT_JUNNAM_NAME.add("신안군");
        EXT_JUNNAM_CODE.add("5010000");
        EXT_JUNNAM_NAME.add("목포시");
        EXT_JUNNAM_CODE.add("4800000");
        EXT_JUNNAM_NAME.add("영암군");
        EXT_JUNNAM_CODE.add("4940000");
        EXT_JUNNAM_NAME.add("장흥군");
        EXT_JUNNAM_CODE.add("4910000");
        EXT_JUNNAM_NAME.add("고흥군");
        EXT_JUNNAM_CODE.add("4880000");
        EXT_JUNNAM_NAME.add("보성군");
        EXT_JUNNAM_CODE.add("4890000");
        EXT_JUNNAM_NAME.add("무안군");
        EXT_JUNNAM_CODE.add("4950000");
        EXT_JUNNAM_NAME.add("나주시");
        EXT_JUNNAM_CODE.add("4830000");
        EXT_JUNNAM_NAME.add("화순군");
        EXT_JUNNAM_CODE.add("4900000");
        EXT_JUNNAM_NAME.add("순천시");
        EXT_JUNNAM_CODE.add("4820000");
        EXT_JUNNAM_NAME.add("여수시");
        EXT_JUNNAM_CODE.add("4810000");
        EXT_JUNNAM_NAME.add("광양시");
        EXT_JUNNAM_CODE.add("4840000");
        EXT_JUNNAM_NAME.add("구례군");
        EXT_JUNNAM_CODE.add("4870000");
        EXT_JUNNAM_NAME.add("곡성군");
        EXT_JUNNAM_CODE.add("4860000");
        EXT_JUNNAM_NAME.add("담양군");
        EXT_JUNNAM_CODE.add("4850000");
        EXT_JUNNAM_NAME.add("장성군");
        EXT_JUNNAM_CODE.add("4980000");
        EXT_JUNNAM_NAME.add("영광군");
        EXT_JUNNAM_CODE.add("4970000");
        EXT_JUNNAM_NAME.add("함평군");
        EXT_JUNNAM_CODE.add("4960000");

        ///전라남도


        EXT_JUNBOOK_NAME.add("고창군");
        EXT_JUNBOOK_CODE.add("4780000");
        EXT_JUNBOOK_NAME.add("정읍시");
        EXT_JUNBOOK_CODE.add("4690000");
        EXT_JUNBOOK_NAME.add("순창군");
        EXT_JUNBOOK_CODE.add("4770000");
        EXT_JUNBOOK_NAME.add("남원시");
        EXT_JUNBOOK_CODE.add("4700000");
        EXT_JUNBOOK_NAME.add("임실군");
        EXT_JUNBOOK_CODE.add("4760000");
        EXT_JUNBOOK_NAME.add("부안군");
        EXT_JUNBOOK_CODE.add("4790000");
        EXT_JUNBOOK_NAME.add("김제시");
        EXT_JUNBOOK_CODE.add("4710000");
        EXT_JUNBOOK_NAME.add("전주시");
        EXT_JUNBOOK_CODE.add("4640000");
        EXT_JUNBOOK_NAME.add("장수군");
        EXT_JUNBOOK_CODE.add("4750000");
        EXT_JUNBOOK_NAME.add("진안군");
        EXT_JUNBOOK_CODE.add("4730000");
        EXT_JUNBOOK_NAME.add("무주군");
        EXT_JUNBOOK_CODE.add("4740000");
        EXT_JUNBOOK_NAME.add("완주군");
        EXT_JUNBOOK_CODE.add("4720000");
        EXT_JUNBOOK_NAME.add("익산시");
        EXT_JUNBOOK_CODE.add("4680000");
        EXT_JUNBOOK_NAME.add("군산시");
        EXT_JUNBOOK_CODE.add("4670000");

        /////지금까지 전라북도


        EXT_CHUNGNAM_NAME.add("금산군");
        EXT_CHUNGNAM_CODE.add("4550000");
        EXT_CHUNGNAM_NAME.add("논산시");
        EXT_CHUNGNAM_CODE.add("4540000");
        EXT_CHUNGNAM_NAME.add("부여군");
        EXT_CHUNGNAM_CODE.add("4570000");
        EXT_CHUNGNAM_NAME.add("서천군");
        EXT_CHUNGNAM_CODE.add("4580000");
        EXT_CHUNGNAM_NAME.add("보령시");
        EXT_CHUNGNAM_CODE.add("4510000");
        EXT_CHUNGNAM_NAME.add("청양군");
        EXT_CHUNGNAM_CODE.add("4590000");
        EXT_CHUNGNAM_NAME.add("공주시");
        EXT_CHUNGNAM_CODE.add("4500000");
        EXT_CHUNGNAM_NAME.add("예산군");
        EXT_CHUNGNAM_CODE.add("4610000");
        EXT_CHUNGNAM_NAME.add("홍성군");
        EXT_CHUNGNAM_CODE.add("4600000");
        EXT_CHUNGNAM_NAME.add("서산시");
        EXT_CHUNGNAM_CODE.add("4530000");
        EXT_CHUNGNAM_NAME.add("태안군");
        EXT_CHUNGNAM_CODE.add("4620000");
        EXT_CHUNGNAM_NAME.add("당진시");
        EXT_CHUNGNAM_CODE.add("5680000");
        EXT_CHUNGNAM_NAME.add("아산시");
        EXT_CHUNGNAM_CODE.add("4520000");
        EXT_CHUNGNAM_NAME.add("천안시");
        EXT_CHUNGNAM_CODE.add("4490000");

        ///여기 까지 충천남도


        EXT_CHUNGBOOK_NAME.add("영동군");
        EXT_CHUNGBOOK_CODE.add("4440000");
        EXT_CHUNGBOOK_NAME.add("옥천군");
        EXT_CHUNGBOOK_CODE.add("4430000");
        EXT_CHUNGBOOK_NAME.add("보은군");
        EXT_CHUNGBOOK_CODE.add("4420000");
        EXT_CHUNGBOOK_NAME.add("청원군");
        EXT_CHUNGBOOK_CODE.add("4410000");
        EXT_CHUNGBOOK_NAME.add("청주시");
        EXT_CHUNGBOOK_CODE.add("4360000");
        EXT_CHUNGBOOK_NAME.add("괴산군");
        EXT_CHUNGBOOK_CODE.add("4460000");
        EXT_CHUNGBOOK_NAME.add("증평군");
        EXT_CHUNGBOOK_CODE.add("5570000");
        EXT_CHUNGBOOK_NAME.add("진천군");
        EXT_CHUNGBOOK_CODE.add("4450000");
        EXT_CHUNGBOOK_NAME.add("음성군");
        EXT_CHUNGBOOK_CODE.add("4470000");
        EXT_CHUNGBOOK_NAME.add("충주시");
        EXT_CHUNGBOOK_CODE.add("4390000");
        EXT_CHUNGBOOK_NAME.add("제천시");
        EXT_CHUNGBOOK_CODE.add("4400000");
        EXT_CHUNGBOOK_NAME.add("단양군");
        EXT_CHUNGBOOK_CODE.add("4480000");

        //충청북도

        EXT_GANGWON_NAME.add("철원군");
        EXT_GANGWON_CODE.add("4300000");
        EXT_GANGWON_NAME.add("화천군");
        EXT_GANGWON_CODE.add("4310000");
        EXT_GANGWON_NAME.add("양구군");
        EXT_GANGWON_CODE.add("4320000");
        EXT_GANGWON_NAME.add("고성군");
        EXT_GANGWON_CODE.add("4340000");
        EXT_GANGWON_NAME.add("춘천시");
        EXT_GANGWON_CODE.add("4180000");
        EXT_GANGWON_NAME.add("인제군");
        EXT_GANGWON_CODE.add("4330000");
        EXT_GANGWON_NAME.add("속초시");
        EXT_GANGWON_CODE.add("4230000");
        EXT_GANGWON_NAME.add("홍청군");
        EXT_GANGWON_CODE.add("4250000");
        EXT_GANGWON_NAME.add("양양군");
        EXT_GANGWON_CODE.add("4350000");
        EXT_GANGWON_NAME.add("횡성군");
        EXT_GANGWON_CODE.add("4260000");
        EXT_GANGWON_NAME.add("평창군");
        EXT_GANGWON_CODE.add("4280000");
        EXT_GANGWON_NAME.add("강릉시");
        EXT_GANGWON_CODE.add("4200000");
        EXT_GANGWON_NAME.add("원주시");
        EXT_GANGWON_CODE.add("4190000");
        EXT_GANGWON_NAME.add("영월군");
        EXT_GANGWON_CODE.add("4270000");
        EXT_GANGWON_NAME.add("정선군");
        EXT_GANGWON_CODE.add("4290000");
        EXT_GANGWON_NAME.add("동해시");
        EXT_GANGWON_CODE.add("4210000");
        EXT_GANGWON_NAME.add("삼척시");
        EXT_GANGWON_CODE.add("4240000");
        EXT_GANGWON_NAME.add("태백시");
        EXT_GANGWON_CODE.add("4220000");

        ///강원도

        EXT_GUNGGI_NAME.add("안성시");
        EXT_GUNGGI_CODE.add("4080000");
        EXT_GUNGGI_NAME.add("오산시");
        EXT_GUNGGI_CODE.add("4000000");
        EXT_GUNGGI_NAME.add("이천시");
        EXT_GUNGGI_CODE.add("4070000");
        EXT_GUNGGI_NAME.add("수원시");
        EXT_GUNGGI_CODE.add("3740000");
        EXT_GUNGGI_NAME.add("광주시");
        EXT_GUNGGI_CODE.add("5540000");
        EXT_GUNGGI_NAME.add("의왕시");
        EXT_GUNGGI_CODE.add("4030000");
        EXT_GUNGGI_NAME.add("성남시");
        EXT_GUNGGI_CODE.add("3780000");
        EXT_GUNGGI_NAME.add("군포시");
        EXT_GUNGGI_CODE.add("4020000");
        EXT_GUNGGI_NAME.add("안산시");
        EXT_GUNGGI_CODE.add("3930000");
        EXT_GUNGGI_NAME.add("시흥시");
        EXT_GUNGGI_CODE.add("4010000");
        EXT_GUNGGI_NAME.add("안양시");
        EXT_GUNGGI_CODE.add("3830000");
        EXT_GUNGGI_NAME.add("부천시");
        EXT_GUNGGI_CODE.add("3860000");
        EXT_GUNGGI_NAME.add("광명시");
        EXT_GUNGGI_CODE.add("3900000");
        EXT_GUNGGI_NAME.add("과천시");
        EXT_GUNGGI_CODE.add("3970000");
        EXT_GUNGGI_NAME.add("하남시");
        EXT_GUNGGI_CODE.add("4040000");
        EXT_GUNGGI_NAME.add("구리시");
        EXT_GUNGGI_CODE.add("3980000");
        EXT_GUNGGI_NAME.add("의정부시");
        EXT_GUNGGI_CODE.add("3820000");
        EXT_GUNGGI_NAME.add("고양시");
        EXT_GUNGGI_CODE.add("3940000");
        EXT_GUNGGI_NAME.add("김포시");
        EXT_GUNGGI_CODE.add("4090000");
        EXT_GUNGGI_NAME.add("양주시");
        EXT_GUNGGI_CODE.add("5590000");
        EXT_GUNGGI_NAME.add("동두천시");
        EXT_GUNGGI_CODE.add("3920000");
        EXT_GUNGGI_NAME.add("포천시");
        EXT_GUNGGI_CODE.add("5600000");

    }
}
