package com.fas.smash_k;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.fas.smash_k.ui.adaptors.ConversationsAdapter;
import com.fas.smash_k.ui.home.HomeFragment;
import com.fas.smash_k.ui.models.chat.homeChat.CustomAdapter;

public class TalkActivity extends Activity {
    public static final String CONVENSATIONPOSTON ="here";
    int conversationPosition;
    //from item chat
    public static final int REQUESTCODE_CONVERSATION_POSITION = 1500;
    public static final String SHARED_PREF_USER_LOGGED_IN = "user_logged_in";
    Notification accountFragments;
    HomeFragment conversationsFragment;
    FragmentManager fragmentManager = getFragmentManager();
    ListView m_ListView;
    CustomAdapter m_Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talk_activity);

        //returns the location of the item
        Intent receivedIntent = getIntent();
        if (receivedIntent != null && receivedIntent.hasExtra(ConversationsAdapter.CONVENSATIONPOSTON)) {
            this.conversationPosition = receivedIntent.getIntExtra(ConversationsAdapter.CONVENSATIONPOSTON, -1);
            System.out.println("Fas pos:"+conversationPosition);
        }

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

    public void onBackPressed() {
        System.out.println("---fas");
        Intent i = new Intent(getApplicationContext(),HomeFragment.class);
        i.putExtra(ConversationsAdapter.CONVENSATIONPOSTON, this.conversationPosition);
        setResult(RESULT_OK, i);
        finish();
    }

}