package com.fas.smash_k.ui.adaptors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fas.smash_k.MainActivity;
import com.fas.smash_k.R;
import com.fas.smash_k.TalkActivity;
import com.fas.smash_k.ui.models.chatItems.ItemConversation;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.ViewHolder> {
    public static final String CONVENSATIONPOSTON = "conversation_position";
    Context context;
    ArrayList<ItemConversation> conversations;

    public ConversationsAdapter(ArrayList<ItemConversation> conversations2, Context context2) {
        this.conversations = conversations2;
        this.context = context2;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_conversations,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
       // position= holder.getPosition();
        ItemConversation currentConversation = (ItemConversation) this.conversations.get(pos);
        Glide.with(this.context).load(currentConversation.getImageURL()).into((ImageView) holder.circleImageView);
        holder.nameContact.setText(currentConversation.getConttactName());
        holder.lastMessage.setText(currentConversation.getLastMessage());
        holder.date.setText(currentConversation.getDate());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ConversationsAdapter.this.context, TalkActivity.class);
                intent.putExtra(ConversationsAdapter.CONVENSATIONPOSTON, pos);
                ((Activity) ConversationsAdapter.this.context).startActivityForResult(intent, TalkActivity.REQUESTCODE_CONVERSATION_POSITION);
            }
        });
        if (currentConversation.getUnreadCount() == 0) {
            holder.unreadCount.setVisibility(View.GONE);
            return;
        }
        holder.unreadCount.setVisibility(View.VISIBLE);
        if (currentConversation.getUnreadCount() > 9) {
            holder.unreadCount.setText("9+");
        } else {
            holder.unreadCount.setText(String.valueOf(currentConversation.getUnreadCount()));
        }
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView date;
        TextView lastMessage;
        TextView nameContact;
        TextView unreadCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.circleImageView = (CircleImageView) itemView.findViewById(R.id.conversation_image);
            this.nameContact = (TextView) itemView.findViewById(R.id.contact_name);
            this.lastMessage = (TextView) itemView.findViewById(R.id.last_message);
            this.date = (TextView) itemView.findViewById(R.id.conversation_date);
            this.unreadCount = (TextView) itemView.findViewById(R.id.message_count);
        }

    }
}



