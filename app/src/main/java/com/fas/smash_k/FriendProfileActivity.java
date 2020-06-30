package com.fas.smash_k;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fas.smash_k.ui.adaptors.ConversationsAdapter;
import com.fas.smash_k.ui.adaptors.SuggestFriendAdapter;
import com.fas.smash_k.ui.home.ChatFragment;
import com.fas.smash_k.ui.models.chat.homeChat.CustomAdapter;
import com.fas.smash_k.ui.models.chatItems.ItemConversation;
import com.fas.smash_k.ui.models.chatItems.ItemFriendsProfile;

import java.util.ArrayList;


public class FriendProfileActivity extends AppCompatActivity
{
    public static final int REQUEST_CODE = 100;
    public static final int RESULT_CODE = 200;
    public static final String EXTRA_KEY_TEST = "testKey";
    //Home Conversation Adapter
    ArrayList<ItemFriendsProfile> itemFriendsProfilesList;
    SuggestFriendAdapter suggestFriendAdapter;
    RecyclerView recyclerView;

    ItemFriendsProfile itemFriendsProfileItem;
    TextView textViewNumberOfFriends,mutualCount;

    String friendSec ="";
    String mutualFriends = "";
    private  static int clickCount = 0;
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_profile_activity);

        getSupportActionBar().show();

        //recycler view
        recyclerView = (RecyclerView) findViewById(R.id.friends_list_fprof);
        textViewNumberOfFriends=findViewById(R.id.id_friends_number_tv);
        mutualCount=findViewById(R.id.id_mutual_number_tv);

        System.out.println("Fas");
        itemFriendsProfilesList = new ArrayList<ItemFriendsProfile>();
        for (int i = 0; i < 5; i++) {
             itemFriendsProfileItem = new ItemFriendsProfile("Abu","At Home","Mutual friend"," + ADD","https://images6.alphacoders.com/688/688916.jpg");
            itemFriendsProfilesList.add(itemFriendsProfileItem);
        }
        suggestFriendAdapter = new SuggestFriendAdapter(getApplicationContext(),itemFriendsProfilesList );
        recyclerView.setAdapter(suggestFriendAdapter);
        suggestFriendAdapter.setOnItemClickedListener(new SuggestFriendAdapter.onItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Toast.makeText(getApplicationContext(),"here "+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void addButtonClicked(int position) {
                mutualFriends = clickCount+" MUTUAL FRIENDS".toString();
                mutualCount.setText(mutualFriends);
                clickCount+=1;
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                intent.putExtra(SuggestFriendAdapter.CONVERSATIONPOSTION, position);
//                startActivityForResult(intent, FriendProfileActivity.REQUEST_CODE);

            }
        });
        suggestFriendAdapter.setOnItemLongPressed(new SuggestFriendAdapter.onItemLongPressed() {
            @Override
            public void onLongPressed(final int position) {

                AlertDialog.Builder longClicked = new AlertDialog.Builder(FriendProfileActivity.this);
                longClicked.setIcon(R.drawable.ic_baseline_delete_24);
                longClicked.setTitle("Do you wanna delete User?");

                longClicked.setNegativeButton("Cancel", new DialogInterface.OnClickListener() // 확인 버튼 누를시
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });


                longClicked.setPositiveButton("Ok", new DialogInterface.OnClickListener() // 취소 버튼 누를시
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        removeItem(position);
                        friendSec = itemFriendsProfilesList.size()+" FRIENDS".toString();
                        textViewNumberOfFriends.setText(friendSec);
                    }
                });

                longClicked.show();
            }
        });

         friendSec = itemFriendsProfilesList.size()+" FRIENDS".toString();
        textViewNumberOfFriends.setText(friendSec);
        mutualFriends = clickCount+" MUTUAL FRIENDS".toString();
        mutualCount.setText(mutualFriends);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.friend_profileactivity_nav_menu,menu);
        System.out.println("MENU     fas--");

        for (int i= 0; i<menu.size();i++){
        menu.getItem(i).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel_return_menu_item:
                finish();
                return true;

            case R.id.setting_menu_item:
                //code
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
                return true;
            case R.id.share_menu_item:
                //code
                String url = "http://fasgh.govt.kr/";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, url);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share Smash-K on"));
                return true;
            case R.id.call_contact_menu_item:
                //code
                Uri uri_a= Uri.parse("tel:01012345678");
                Intent intent_b = new Intent(Intent.ACTION_DIAL, uri_a);
                startActivity(intent_b);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void insertItem(int pos){
        itemFriendsProfilesList.add( 8,new ItemFriendsProfile("Fas","At Home","Mutual friend"," + ADD","https://images6.alphacoders.com/688/688916.jpg"));
        suggestFriendAdapter.notifyItemInserted(pos);
    }
    public void removeItem(int pos){
        itemFriendsProfilesList.remove(pos);
        suggestFriendAdapter.notifyItemRemoved(pos);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(getApplicationContext(),"here ",Toast.LENGTH_SHORT).show();
        if (requestCode == REQUEST_CODE && resultCode == FriendProfileActivity.RESULT_OK) {
          /*  String testResult = data.getStringExtra(EXTRA_KEY_TEST);
            int conversationPosition = data.getIntExtra(SuggestFriendAdapter.CONVERSATIONPOSTION, -1);

            Toast.makeText(getApplicationContext(),"here "+conversationPosition,Toast.LENGTH_SHORT).show();
            ((ItemFriendsProfile) itemFriendsProfilesList.get(conversationPosition)).setAddFriends("");
            this.suggestFriendAdapter.notifyItemChanged(conversationPosition);*/

        }
    }
}
