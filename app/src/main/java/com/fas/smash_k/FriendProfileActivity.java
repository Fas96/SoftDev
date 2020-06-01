package com.fas.smash_k;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;


public class FriendProfileActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        icon_share.setOnClickListener(new View.OnClickListener() // 친구에게 내 위치 보여주기 설정
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder location_answer = new AlertDialog.Builder(FriendProfileActivity.this);
                location_answer.setIcon(R.drawable.my_location_icon); // 위치 아이콘 이미지 출력
                location_answer.setTitle("나의 위치 공유 하기"); // 텍스트 출력

                location_answer.setNegativeButton("확인", new DialogInterface.OnClickListener() // 확인 버튼 누를시
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });


                location_answer.setPositiveButton("취소", new DialogInterface.OnClickListener() // 취소 버튼 누를시
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });

                location_answer.show(); // 팝업창 띄우기
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

    }
}
