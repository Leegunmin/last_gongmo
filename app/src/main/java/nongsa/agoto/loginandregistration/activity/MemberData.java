package nongsa.agoto.loginandregistration.activity;

import android.net.Uri;

/**
 * Created by Seong Ho-Kyeong on 2017-04-20.
 */

//Member의 정보를 저장하기 위한 클래스.....

public class MemberData {

    String name;    //이름 저장
    String nation;   //지역 저장
    Uri imgId;      //국기 이미지의 리소스 아이디
    int exp;  //경력
    String grow; //재배 작물
    String intro;
    String phone;

    public MemberData(String name, String nation, Uri imgId, int exp, String grow, String intro, String phone)  {
        // TODO Auto-generated constructor stub

        //생성자함수로 전달받은 Member의 정보를 멤버변수에 저장..
        this.name= name;
        this.nation=nation;
        this.imgId=imgId;
        this.exp=exp;
        this.grow=grow;
        this.intro=intro;
        this.phone=phone;
    }

    //이 아래는 getter , setter 메소드듭입니다.
    //OOP(객체 지향 프로그래밍)은 클래스 객체 외부에서 직접 엠머변수에 접근하는 것을 지양합니다.
    //객체의 멤버변수 제어는 객체 스스로 하도록 해서 재사용성을 높인 방법이죠.
    //getter, setter 멤버 메소드들은 그 목적으로 만들어 진 것이죠.

    public void setName(String name) {
        this.name = name;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setImgId(Uri imgId) {
        this.imgId = imgId;
    }

    public void setExp(int exp) {
        this.exp=exp;
    }

    public void setGrow(String grow) {
        this.grow=grow;
    }

    public void setIntro(String intro) {
        this.grow=intro;
    }

    public void setPhone(String phone) {
        this.grow=phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getIntro() {
        return intro;
    }

    public String getName() {
        return name;
    }

    public String getNation() {
        return nation;
    }

    public Uri getImgId() {
        return imgId;
    }

    public int getExp() {
        return exp;
    }

    public String getGrow() {
        return grow;
    }

}
