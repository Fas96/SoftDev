package com.fas.smash_k.ui.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fas.smash_k.R;
import com.fas.smash_k.ui.models.chatItems.ItemConversation;
import com.fas.smash_k.ui.models.chatItems.ItemFriendsProfile;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuggestFriendAdapter extends RecyclerView.Adapter<SuggestFriendAdapter.SugFriendViewHolder> {

    public static final String CONVERSATIONPOSTION = "conversation";
    Context context;
    ArrayList<ItemFriendsProfile> itemFriendsProfilesList;
    int pos;
    private onItemClickedListener mListener;
    private onItemLongPressed onItemLongPressed;

    public  interface  onItemClickedListener{
        void onItemClicked(int position);
        void addButtonClicked(int pos);
    }
    public  interface onItemLongPressed{
        void onLongPressed(int position);
    }

    public  void setOnItemLongPressed(onItemLongPressed onItemLongPressed){
        this.onItemLongPressed=onItemLongPressed;
    }
    public void setOnItemClickedListener(onItemClickedListener mListener) {
        this.mListener = mListener;
    }

    public SuggestFriendAdapter(Context context, ArrayList<ItemFriendsProfile> itemFriendsProfilesList) {
        this.context = context;
        this.itemFriendsProfilesList = itemFriendsProfilesList;
    }

    public static class SugFriendViewHolder extends  RecyclerView.ViewHolder{

        CircleImageView imageOfSuggested;
        TextView nameOfSuggested;
        TextView mutualOrFriend;
        TextView profState;
        Button addFriends;
        public SugFriendViewHolder(@NonNull View itemView,onItemClickedListener onItemClickedListener,onItemLongPressed onItemLongPressed) {
            super(itemView);
            imageOfSuggested = itemView.findViewById(R.id.sug_friend_image);
            nameOfSuggested = itemView.findViewById(R.id.sug_or_friends_contact_name);
            mutualOrFriend = itemView.findViewById(R.id.start_message_loc_mutual);
            profState = itemView.findViewById(R.id.friend_prof_state);
            addFriends = itemView.findViewById(R.id.add_friend_lnt);

            itemView.setOnClickListener(v -> {
                if(onItemClickedListener!= null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        onItemClickedListener.onItemClicked(position);
                    }
                }
            });
            addFriends.setOnClickListener(v -> {
                if(onItemClickedListener!= null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        onItemClickedListener.addButtonClicked(position);
                        System.out.println("clicked: "+position);
                    }
                }
            });

            itemView.setOnLongClickListener(v -> {
                if(onItemLongPressed!= null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        onItemLongPressed.onLongPressed(position);
                    }
                }
                return false;
            });

        }
    }


    @NonNull
    @Override
    public SugFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SugFriendViewHolder(LayoutInflater.from(this.context).inflate(R.layout.friends_to_add_or_delete,parent,false), (onItemClickedListener) mListener,onItemLongPressed);
    }

    @Override
    public void onBindViewHolder(@NonNull SugFriendViewHolder holder, int position) {
        pos = holder.getAdapterPosition();
        // position= holder.getPosition();
        ItemFriendsProfile currentConversation = (ItemFriendsProfile) itemFriendsProfilesList.get(pos);

        Glide.with(this.context).load(currentConversation.getProfImageUrl()).into((CircleImageView)holder.imageOfSuggested);
        holder.nameOfSuggested.setText(currentConversation.getProfileName());
        holder.mutualOrFriend.setText(currentConversation.getStateOnApp());
        //WOULD CHANGE IF USER IS FRIENDS WITHS THE USER
        holder.profState.setVisibility(View.GONE);
        holder.addFriends.setText(currentConversation.getAddFriends());

        holder.addFriends.setOnClickListener(v -> {
            holder.profState.setVisibility(View.VISIBLE);
            holder.addFriends.setVisibility(View.GONE);
        });




    }

    @Override
    public int getItemCount() {
        return itemFriendsProfilesList.size();
    }
}
