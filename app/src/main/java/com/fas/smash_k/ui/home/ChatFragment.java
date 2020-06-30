package com.fas.smash_k.ui.home;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fas.smash_k.R;
import com.fas.smash_k.TalkActivity;
import com.fas.smash_k.ui.adaptors.ConversationsAdapter;
import com.fas.smash_k.ui.models.chatItems.ItemConversation;
import com.fas.smash_k.ui.models.chatItems.ItemFriendsProfile;

import java.util.ArrayList;

public class ChatFragment extends Fragment  {
    public static final int REQUEST_CODE = 11;
    public static final int RESULT_CODE = 12;
    public static final String EXTRA_KEY_TEST = "testKey";
    //Home Conversation Adapter
      ArrayList<ItemConversation> conversationList;
      ConversationsAdapter conversationsAdapter;
      RecyclerView recyclerView;
    int conversationPosition;
    ChatFragment conversationsFragment;
    FragmentManager fragmentManager;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        fragmentManager = getFragmentManager();
        //ScrollView scroll_view = (ScrollView) findViewById(R.id.scroll_view_id);
        //recycler view
         recyclerView = (RecyclerView) root.findViewById(R.id.conversation);
        System.out.println("Fas");
        String[] name = {"Volvo", "BMW", "Ford", "Mazda","Bhim","Anyass","Lorry"};
        conversationList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int days = i + 10;
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append("/");
            sb.append(days);
            sb.append("/2020");
            ItemConversation conversation = new ItemConversation("https://images6.alphacoders.com/688/688916.jpg", name[i], "whats boiling down", sb.toString(), i);
            conversationList.add(conversation);
        }
        conversationsAdapter = new ConversationsAdapter(conversationList, getActivity());
        recyclerView.setAdapter(conversationsAdapter);
        conversationsAdapter.setOnItemClickedListener(new ConversationsAdapter.onItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getContext(), TalkActivity.class);
                intent.putExtra(ConversationsAdapter.CONVENSATIONPOSTON, position);
                //((Activity) ConversationsAdapter.this.context).startActivityForResult(intent, TalkActivity.REQUESTCODE_CONVERSATION_POSITION);
                startActivityForResult(intent, ChatFragment.REQUEST_CODE);
            }
        });
        conversationsAdapter.setOnItemLongPressed(new ConversationsAdapter.onItemLongPressed() {
            @Override
            public void onLongPressed(int position) {

                AlertDialog.Builder longClicked = new AlertDialog.Builder(getContext());
                longClicked.setIcon(R.drawable.ic_baseline_delete_24);
                longClicked.setTitle("Do you wanna delete Chat?");

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
                    }
                });

                longClicked.show();
            }
        });
        return root;
    }

    // Initializing and starting the second Activity
    private void startSecondActivity() {
        Intent intent = new Intent(getActivity(), TalkActivity.class);
        startActivityForResult(intent, ChatFragment.REQUEST_CODE);
    }


    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String testResult = data.getStringExtra(EXTRA_KEY_TEST);
            int conversationPosition = data.getIntExtra(ConversationsAdapter.CONVENSATIONPOSTON, -1);

                ((ItemConversation) conversationList.get(conversationPosition)).setUnreadCount(0);
                this.conversationsAdapter.notifyItemChanged(conversationPosition);

        }
    }


    public  void insertItem(int pos){
        int i = 9;
        int days = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(9);
        sb.append("/");
        sb.append(days);
        sb.append("/2020");
        conversationList.add(pos,new ItemConversation("https://images6.alphacoders.com/688/688916.jpg", "Bhim", "whats boiling down", sb.toString(), i));
        conversationsAdapter.notifyItemInserted(pos);
    }
    public  void removeItem(int pos){
        conversationList.remove(pos);
        conversationsAdapter.notifyItemRemoved(pos);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_search){
            Toast.makeText(getContext(),"ger bar",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = new MenuInflater(getContext());

        menuInflater.inflate(R.menu.chatmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        for (int i= 0; i<menu.size();i++){
            menu.getItem(i).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }


        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                conversationsAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    }




