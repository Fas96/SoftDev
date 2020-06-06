package com.fas.smash_k;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;


public class FriendProfileActivity extends Activity implements View.OnClickListener
{

    public ImageView icon_x,icon_share,icon_setting,icon_call;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_profile_activity);

        icon_x = (ImageView) findViewById(R.id.icon_x_id); // x 아이콘 이미지 ID 매칭
        icon_setting = (ImageView) findViewById(R.id.setting_id); // 톱니바퀴 이미지 ID 매칭
        icon_share = (ImageView) findViewById(R.id.share_id); // 공유 이미지 ID 매칭
        icon_call = (ImageView) findViewById(R.id.call_id); // 전화기 이미지 ID 매칭
        //set click event

        icon_x.setOnClickListener(this);
        icon_setting.setOnClickListener(this);
        icon_share.setOnClickListener(this);
        icon_call.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       int innerID= v.getId();
        if(innerID==R.id.icon_x_id){
            finish();
        }else if(innerID==R.id.setting_id){
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

        }else if(innerID==R.id.share_id){
            String url = "http://fasgh.govt.kr/";
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, url);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share Smash-K on"));
        }else if(innerID ==R.id.call_id){
            Uri uri_a= Uri.parse("tel:01012345678");
            Intent intent_b = new Intent(Intent.ACTION_DIAL, uri_a);
            startActivity(intent_b);
        }
    }
}
