package com.fas.smash_k;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.fas.smash_k.ui.adaptors.ConversationsAdapter;
import com.fas.smash_k.ui.adaptors.MessageAdapter;
import com.fas.smash_k.ui.home.ChatFragment;
import com.fas.smash_k.ui.models.chatItems.ItemMessages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class TalkActivity extends Activity {
    //implementing the adapters
    int conversationPosition;
    ArrayList<ItemMessages> itemMessages = new ArrayList<ItemMessages>();
    MessageAdapter messagesAdapter;
    /* access modifiers changed from: private */
    public int sentMessageCount = 1;
    //from item chat
    public static final int REQUESTCODE_CONVERSATION_POSITION = 1500;
//    ListView m_ListView;
//    CustomAdapter m_Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talk_activity);


        //returns the location of the item
        Intent receivedIntent = getIntent();
        if (receivedIntent != null && receivedIntent.hasExtra(ConversationsAdapter.CONVENSATIONPOSTON)) {
            conversationPosition = receivedIntent.getIntExtra(ConversationsAdapter.CONVENSATIONPOSTON, -1);
            System.out.println("Fas pos:"+conversationPosition);
        }

        this.messagesAdapter = new MessageAdapter(getApplicationContext(), itemMessages);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.messages_recyclereview);
        recyclerView.setAdapter(messagesAdapter);
        final EditText messageToBeSentView = (EditText) findViewById(R.id.editText1);
        ((ImageButton) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editText = messageToBeSentView;
                if (editText != null && !editText.getText().toString().isEmpty()) {
                    String message = messageToBeSentView.getText().toString();
                    Date date = new Date();
                    SimpleDateFormat sfm = new SimpleDateFormat("dd/MM/yyyy");
                    String stringDate = sfm.format(date);
//                    User user = SharedPref.getUser(SharedPref.KEY_USER);
                    TalkActivity.this.addMessage(new ItemMessages(message, stringDate, ItemMessages.SENT));
                  //  TalkActivity.this.sentMessageCount = TalkActivity.this.sentMessageCount + 1;
                    messageToBeSentView.setText("");
                    recyclerView.scrollToPosition(TalkActivity.this.messagesAdapter.getItemCount() - 1);
//                    if (TalkActivity.this.sentMessageCount % 5 == 0) {
//                        String message1 = TalkActivity.replyMessage();
//                        Date date1 = new Date();
//                        new SimpleDateFormat("dd/MM/yyyy");
//                        TalkActivity.this.addMessage(new ItemMessages(message1, sfm.format(date1), -1, new Contact(user.getName(), new User(20, "Someone", "LastName").getId())));
//                        TalkActivity.this.sentMessageCount = 1;
//                    }
                }
            }
        });






    /*    //////////////////////////////////////////////////////////////////////
        add
        //////////////////////////////////////////////////////////////////////*/






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
//        m_Adapter = new CustomAdapter();
//
//        // Xml에서 추가한 ListView 연결
//        m_ListView = (ListView) findViewById(R.id.listView1);
//
//        // ListView에 어댑터 연결
//        m_ListView.setAdapter(m_Adapter);

        findViewById(R.id.image_id).setOnClickListener(new Button.OnClickListener() // image 버튼 누를시에
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder image_answer = new AlertDialog.Builder(TalkActivity.this);
                image_answer.setIcon(R.drawable.image_icon); // 금지 아이콘 이미지 출력
                image_answer.setTitle("이미지 전송"); // 텍스트 출력

                image_answer.setNegativeButton("사진", new DialogInterface.OnClickListener() // 사진 버튼 누를시
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 1);
                    }
                });



                image_answer.setNeutralButton("카메라", new DialogInterface.OnClickListener() // 카메라 버튼 누를시
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivity(intent);
                    }
                });

                image_answer.setPositiveButton("취소", new DialogInterface.OnClickListener() // 취소 버튼 누를시
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });

                image_answer.show(); // 팝업창 띄우기

            }
        });

//        findViewById(R.id.button2).setOnClickListener(new Button.OnClickListener()
//         {
//             @Override
//             public void onClick(View v)
//             {
//                 EditText editText = (EditText) findViewById(R.id.editText1);
//                 String inputValue = editText.getText().toString();
//                 editText.setText("");
//                 refresh(inputValue,1);
//             }
//         }
//         );
    }
    public void onBackPressed() {
        System.out.println("back---fas");
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra(ConversationsAdapter.CONVENSATIONPOSTON, conversationPosition);
        setResult(RESULT_OK, i);
        finish();
    }
    private void closeActivity() {
        Intent intent = new Intent();
        intent.putExtra(ChatFragment.EXTRA_KEY_TEST, "Testing passing data back to ActivityOne");
        setResult(ChatFragment.RESULT_CODE, intent);
        finish();
    }
  /*  //////////////////////////////////////////////////////
    added
    /////////////////////////////////////////////////////////*/
  /* access modifiers changed from: private */
  public void addMessage(ItemMessages itemMessage) {
      int itemCount = this.itemMessages.size();
      this.itemMessages.add(itemMessage);
      this.messagesAdapter.notifyItemInserted(itemCount);
  }
    /* access modifiers changed from: private */
    public static String replyMessage() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(50);
        for (int i = 0; i < randomLength; i++) {
            randomStringBuilder.append((char) (generator.nextInt(96) + 32));
        }
        return randomStringBuilder.toString();
    }

}