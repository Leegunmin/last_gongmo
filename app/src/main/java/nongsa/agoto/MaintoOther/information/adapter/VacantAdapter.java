package nongsa.agoto.MaintoOther.information.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.util.ArrayList;

import nongsa.agoto.MaintoOther.vacant.field;
import nongsa.agoto.MaintoOther.vacant.showvacant;
import nongsa.agoto.MaintoOther.vacant.vacants;
import nongsa.agoto.R;

/**
 * Created by gunmin on 2017-04-18.
 */
public class VacantAdapter extends BaseAdapter {

    // 문자열을 보관 할 ArrayList
    private ArrayList<String>   m_List;
    private ArrayList<String>   m_IMG;
    private ArrayList<String>   m_LINK;
    private ArrayList<String>   m_PRICE;
    // 생성자
    public VacantAdapter() {
        m_List = new ArrayList<String>();
        m_IMG = new ArrayList<String>();
        m_LINK = new ArrayList<String>();
        m_PRICE = new ArrayList<String>();
    }

    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
        return m_List.size();
    }

    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
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
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vacant_custom, parent, false);

            // TextView에 현재 position의 문자열 추가
            ImageView video_img = (ImageView)convertView.findViewById(R.id.video_img);
            if(m_IMG.get(position).equals("null")){
                Glide.with(parent.getContext()).load(R.drawable.noimage).into(video_img);
            }
            else {
                Glide.with(parent.getContext()).load(m_IMG.get(position)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(video_img);
            }
            TextView text = (TextView) convertView.findViewById(R.id.text);
            TextView price_text = (TextView)convertView.findViewById(R.id.vacant_text2);
            text.setText(m_List.get(position));
            if(m_PRICE.get(position).equals("null")){
                price_text.setText("희망 매매가 : 협의");
            }
            else if(m_PRICE.get(position).equals("here")){
                price_text.setText("해당 지역 부동산 리스트");
            }
            else{
                price_text.setText("희망 매매가 : "+m_PRICE.get(position));
            }

            // 버튼을 터치 했을 때 이벤트 발생

            // 리스트 아이템을 터치 했을 때 이벤트 발생
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    if(vacants.fullcityname.get(position).equals("부동산 정보가 필요하시면 클릭하세요.")){
                        Intent intent = new Intent(parent.getContext(), field.class);
                        intent.putExtra("bigcity", vacants.cityname.get(position));
                        intent.putExtra("smallcity", vacants.smallcityname.get(position));
                        parent.getContext().startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(parent.getContext(), showvacant.class);
                        intent.putExtra("image_url", vacants.image_url.get(position));
                        intent.putExtra("bigcity", vacants.cityname.get(position));
                        intent.putExtra("smallcity", vacants.smallcityname.get(position));
                        intent.putExtra("fullcity", vacants.fullcityname.get(position));
                        intent.putExtra("price", vacants.wish_price.get(position));
                        intent.putExtra("field_size", vacants.filed_size.get(position));
                        intent.putExtra("build_size", vacants.build_size.get(position));
                        intent.putExtra("build_year", vacants.build_year.get(position));
                        intent.putExtra("vacant_year", vacants.vacant_year.get(position));
                        intent.putExtra("name", vacants.have_name.get(position));
                        intent.putExtra("phone", vacants.phone.get(position));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        parent.getContext().startActivity(intent);
                    }


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
        m_List.add(_msg);
    }
    public void addIMG(String _msg) {
        m_IMG.add(_msg);
    }
    public void addLINK(String _msg) {
        m_LINK.add(_msg);
    }
    public void addPrice(String _msg) {
        m_PRICE.add(_msg);
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        m_List.remove(_position);
    }
}