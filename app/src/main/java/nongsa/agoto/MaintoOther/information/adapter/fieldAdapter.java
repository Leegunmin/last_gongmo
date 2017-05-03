package nongsa.agoto.MaintoOther.information.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import nongsa.agoto.MaintoOther.vacant.field;
import nongsa.agoto.R;

/**
 * Created by gunmin on 2017-04-18.
 */
public class fieldAdapter extends BaseAdapter {

    // 문자열을 보관 할 ArrayList
    private ArrayList<String> m_TITLE;
    private ArrayList<String> m_DESCRIPTION;
    private ArrayList<String> m_TELEPHONE;
    private ArrayList<String> m_ADDRESS;
    private ArrayList<String> m_ROADADRESS;
    // 생성자
    public fieldAdapter() {
        m_TITLE = new ArrayList<String>();
        m_DESCRIPTION = new ArrayList<String>();
        m_TELEPHONE = new ArrayList<String>();
        m_ADDRESS = new ArrayList<String>();
        m_ROADADRESS = new ArrayList<String>();
    }

    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
        return m_TITLE.size();
    }

    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return m_TITLE.get(position);
    }

    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 출력 될 아이템 관리
    @Override
    public View getView(final int position, View convertView,final  ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.field_item, parent, false);

            TextView title = (TextView)convertView.findViewById(R.id.field_title);
            TextView description = (TextView)convertView.findViewById(R.id.field_description);
            TextView address = (TextView)convertView.findViewById(R.id.field_address);

            title.setText(m_TITLE.get(position));
            if(m_DESCRIPTION.get(position).equals("")){
                description.setText("부동산 설명 : 토지 매매 임대 등 ");
            }else {
                description.setText("부동산 설명 : "+m_DESCRIPTION.get(position));
            }
            address.setText("주소: "+ m_ADDRESS.get(position));

            // 버튼을 터치 했을 때 이벤트 발생

            // 리스트 아이템을 터치 했을 때 이벤트 발생
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(parent.getContext());

                    alertDialog.setTitle("전화 연결");
                    alertDialog.setMessage("전화 연결을 하시겠습니까?");

                    // OK 를 누르게 되면 설정창으로 이동합니다.
                    alertDialog.setPositiveButton("전화",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        /*사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 확인한다.
                         * Android는 C언어 기반으로 만들어졌기 때문에 Boolean 타입보다 Int 타입을 사용한다. */
                                        int permissionResult = parent.getContext().checkSelfPermission(Manifest.permission.CALL_PHONE);
                        /*패키지는 안드로이드 어플리케이션의 아이디이다.
                         *현재 어플리케이션이 CALL_PHONE에 대해 거부되어있는지 확인한다. */
                                        if (permissionResult == PackageManager.PERMISSION_DENIED) {
                            /* 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다.
                            * 거부한적이 있으면 True를 리턴하고 * 거부한적이 없으면 False를 리턴한다. */
                                            if (field.fieldactivity.shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(parent.getContext());
                                                dialog1.setTitle("권한이 필요합니다.")
                                                        .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
                                                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                /*새로운 인스턴스(onClickListener)를 생성했기 때문에
                                                * 버전체크를 다시 해준다. */
                                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                                    // CALL_PHONE 권한을 Android OS에 요청한다.
                                                                    field.fieldactivity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                                                }
                                                            }
                                                        })
                                                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Toast.makeText(parent.getContext(), "기능을 취소했습니다", Toast.LENGTH_SHORT).show();
                                                            }
                                                        })
                                                        .create()
                                                        .show();
                                            }
                                            // 최초로 권한을 요청할 때
                                            else {
                                                // CALL_PHONE 권한을 Android OS에 요청한다.
                                                field.fieldactivity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                            }
                                        }
                                        // CALL_PHONE의 권한이 있을 때
                                        else {
                                            // 즉시 실행
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+m_TELEPHONE.get(position)));
                                            parent.getContext().startActivity(intent);
                                        }
                                    }
                                    // 마시멜로우 미만의 버전일 때
                                    else {
                                        // 즉시 실행
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+m_TELEPHONE.get(position)));
                                        parent.getContext().startActivity(intent);
                                    }

                                }
                            });
                    // Cancle 하면 종료 합니다.
                    alertDialog.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();


                }
            });

            // 리스트 아이템을 길게 터치 했을 떄 이벤트 발생
            convertView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    return true;
                }
            });
        }

        return convertView;

    }


    // 외부에서 아이템 추가 요청 시 사용
    public void add(String _msg) {
        m_TITLE.add(_msg);
    }
    public void addDESCRIPTION(String _msg) {
        m_DESCRIPTION.add(_msg);
    }
    public void addTELEPHONE(String _msg) {
        m_TELEPHONE.add(_msg);
    }
    public void addADDRESS(String _msg) {
        m_ADDRESS.add(_msg);
    }
    public void addROADADDRESS(String _msg) {
        m_ROADADRESS.add(_msg);
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        m_TITLE.remove(_position);
    }



}