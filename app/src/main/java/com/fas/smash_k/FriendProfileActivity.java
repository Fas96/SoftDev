package com.fas.smash_k;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.fas.smash_k.ui.models.chat.homeChat.CustomAdapter;

import java.util.ArrayList;


public class FriendProfileActivity extends Activity
{

    ListView listView;

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_profile_activity);

        ImageView icon_x = (ImageView) findViewById(R.id.icon_x_id); // x 아이콘 이미지 ID 매칭
        ImageView icon_setting = (ImageView) findViewById(R.id.setting_id); // 톱니바퀴 이미지 ID 매칭
        ImageView icon_share = (ImageView) findViewById(R.id.share_id); // 공유 이미지 ID 매칭
        ImageView icon_call = (ImageView) findViewById(R.id.call_id); // 전화기 이미지 ID 매칭

        icon_x.setOnClickListener(new View.OnClickListener() // x 아이콘 이미지를 누르면 이전 화면으로 돌아가기
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        icon_setting.setOnClickListener(new View.OnClickListener() // 톱니바퀴 이미지를 누르면 친구 삭제 기능
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder delete_answer = new AlertDialog.Builder(FriendProfileActivity.this);
                delete_answer.setIcon(R.drawable.friend_delete_icon); // 금지 아이콘 이미지 출력
                delete_answer.setTitle("친구 삭제 하기"); // 텍스트 출력

                delete_answer.setNegativeButton("확인", new DialogInterface.OnClickListener() // 확인 버튼 누를시
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });


                delete_answer.setPositiveButton("취소", new DialogInterface.OnClickListener() // 취소 버튼 누를시
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });

                delete_answer.show(); // 팝업창 띄우기

            }
        });

        icon_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://fasgh.govt.kr/";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, url);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share Smash-K on"));
            }
        });

        icon_call.setOnClickListener(new View.OnClickListener() // 친구에게 전화하기 (저장된 전화번호부로 해야됨)
        {
            @Override
            public void onClick(View view)
            {
                Uri uri_a= Uri.parse("tel:01012345678");
                Intent intent_b = new Intent(Intent.ACTION_DIAL, uri_a);
                startActivity(intent_b);
            }
        });


        listView = (ListView)this.findViewById(R.id.friend_list_id);

        ArrayList<String> items = new ArrayList<>();
        items.add("Fas");
        items.add("Muneer");
        items.add("JoSungJun");
        items.add("Friend_1");
        items.add("Friend_2");
        items.add("Friend_3");
        items.add("Friend_4");
        items.add("Friend_5");
        items.add("Friend_6");
        items.add("Friend_7");
        items.add("Friend_8");
        items.add("Friend_9");
        items.add("Friend_10");

        CustomAdapter adapter = new CustomAdapter(this, 0, items);
        listView.setAdapter(adapter);


    }

    private class CustomAdapter extends ArrayAdapter<String>
    {
        private ArrayList<String> items;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects)
        {
            super(context, textViewResourceId, objects);
            this.items = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View v = convertView;
            if (v == null)
            {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.friend_list_view_image, null);
            }

            // ImageView 인스턴스
            ImageView imageView = (ImageView)v.findViewById(R.id.imageView); // 친구의 친구 프로필 사진

            /*
            // friend_profile_image
            if("Fas".equals(items.get(position)))
                imageView.setImageResource(R.drawable.fas_id);
            else if("Muneer".equals(items.get(position)))
                imageView.setImageResource(R.drawable.muneer_id);
            */

            TextView textView = (TextView)v.findViewById(R.id.textView);
            textView.setText(items.get(position));

            final String text = items.get(position);
            Button friend_add_btn = (Button)v.findViewById(R.id.friend_add_id); // ADD 버튼 ID 매칭
            TextView friend_add_tv = (TextView)v.findViewById(R.id.friend_relationship_id); // 친구와의 관계 텍스트 뷰 ID 매칭

            if("Fas".equals(items.get(position)))
            {

                friend_add_btn.setVisibility(View.INVISIBLE); // 버튼을 사라지게하고
                friend_add_tv.setVisibility(View.VISIBLE); // 텍스트뷰 보이게하기
                friend_add_tv.setText("MUTUAL"); // 텍스트뷰 글자 변경
            }
            else if ("Muneer".equals(items.get(position)))
            {
                friend_add_btn.setVisibility(View.INVISIBLE); // 버튼을 사라지게하고
                friend_add_tv.setVisibility(View.VISIBLE); // 텍스트뷰 보이게하기
                friend_add_tv.setText("MUTUAL"); // 텍스트뷰 글자 변경
            }
            else if ("JoSungJun".equals(items.get(position)))
            {
                friend_add_btn.setVisibility(View.INVISIBLE); // 버튼을 사라지게하고
                friend_add_tv.setVisibility(View.VISIBLE); // 텍스트뷰 보이게하기
                friend_add_tv.setText("YOU"); // 텍스트뷰 글자 변경
            }
            else
            {
                friend_add_btn.setVisibility(View.VISIBLE); // ADD 버튼 보이게 하기
                friend_add_tv.setVisibility(View.VISIBLE); // 텍스트뷰 사라지게 하기
            }

            friend_add_btn.setOnClickListener(new View.OnClickListener() // ADD 버튼을 누를시에
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(FriendProfileActivity.this, "INVITED MESSAGE SEND", Toast.LENGTH_SHORT).show(); // Toast 메시지 출력
                    friend_add_btn.setVisibility(View.INVISIBLE); // 버튼을 사라지게하고
                    friend_add_tv.setVisibility(View.VISIBLE); // 텍스트뷰 보이게하기
                    friend_add_tv.setText("INVITED MESSAGE"); // 텍스트뷰 글자 변경
                }
            });

            return v;
        }
    }
}
