package com.fas.smash_k.ui.models.chat.homeChat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.fas.smash_k.R;

public class TalkActivity extends Activity
{
    ListView m_ListView;
    CustomAdapter m_Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talk_activity);

        ImageView icon_x = (ImageView) findViewById(R.id.icon_x_id); // x 아이콘 이미지 ID 매칭
        ImageView icon_friend_profile = (ImageView) findViewById(R.id.icon_friend_id);  // 친구 프로필 아이콘 이미지 ID 매칭

        icon_x.setOnClickListener(new View.OnClickListener() // x 아이콘 이미지를 누르면 이전 화면으로 돌아가기
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


        icon_friend_profile.setOnClickListener(new View.OnClickListener() // 친구 프로필 아이콘 이미지를 누르면 해당하는 액티비티 화면 출력
        {
            @Override
            public void onClick(View view)
            {
                Intent send_intent = new Intent(getApplicationContext(), FriendProfileActivity.class);
                startActivity(send_intent); // TalkActivity 자바 파일에 전달
            }
        });


        // 커스텀 어댑터 생성
        m_Adapter = new CustomAdapter();

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);
/*
        m_Adapter.add("Hi",1);
        m_Adapter.add("Hello",0);
        m_Adapter.add("Good",1);
        m_Adapter.add("ABCDEFGHIJK 가나다라마바사",1);
        m_Adapter.add("JoSungJun",1);
        m_Adapter.add("Fas",0);
        m_Adapter.add("2020/05/30",2);
        m_Adapter.add("Muneer",1);
        m_Adapter.add("mmaskdaasiocoadoawodoasdakw",0);
        m_Adapter.add("Smash-K",1);
*/
        findViewById(R.id.button1).setOnClickListener(new Button.OnClickListener()
          {
              @Override
              public void onClick(View v)
              {
                  EditText
                  editText = (EditText) findViewById(R.id.editText1);
                  String inputValue = editText.getText().toString();
                  editText.setText("");
                  refresh(inputValue,0);
              }
          }
        );

        findViewById(R.id.button2).setOnClickListener(new Button.OnClickListener()
         {
             @Override
             public void onClick(View v)
             {
                 EditText editText = (EditText) findViewById(R.id.editText1);
                 String inputValue = editText.getText().toString();
                 editText.setText("");
                 refresh(inputValue,1);
             }
         }
         );
    }

    private void refresh (String inputValue, int _str)
    {
        m_Adapter.add(inputValue,_str) ;
        m_Adapter.notifyDataSetChanged();
    }
}