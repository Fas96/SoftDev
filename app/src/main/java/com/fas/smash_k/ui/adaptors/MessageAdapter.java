package com.fas.smash_k.ui.adaptors;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.fas.smash_k.R;
import com.fas.smash_k.ui.models.chatItems.ItemMessages;

import java.io.PrintStream;
import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int VIEW_TYPE_RECIEVER = 2;
    private static final int VIEW_TYPE_SENER = 1;
    private Context context;
    private ArrayList<ItemMessages> itemMessages;

    public class ViewReceiverHolder extends ViewHolder {
        public TextView dateView;
        public TextView messageView;

        public ViewReceiverHolder(@NonNull View itemView) {
            super(itemView);
            this.messageView = (TextView) itemView.findViewById(R.id.reciever_message);
            this.dateView = (TextView) itemView.findViewById(R.id.date_view);
        }
    }

    public static class ViewSentHolder extends ViewHolder {
        public TextView dateView;
        public TextView messageView;
        public ImageView statusView;

        public ViewSentHolder(@NonNull View itemView) {
            super(itemView);
            this.statusView = (ImageView) itemView.findViewById(R.id.status_image);
            this.messageView = (TextView) itemView.findViewById(R.id.sent_message);
            this.dateView = (TextView) itemView.findViewById(R.id.date_view);
        }
    }

    public MessageAdapter(Context context2, ArrayList<ItemMessages> itemMessages2) {
        this.context = context2;
        this.itemMessages = itemMessages2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ViewSentHolder(LayoutInflater.from(this.context).inflate(R.layout.item_layout_messages_sender, parent, false));
        }
        return new ViewReceiverHolder(LayoutInflater.from(this.context).inflate(R.layout.item_layout_messages_receiver, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        int pos= holder.getAdapterPosition();
        ItemMessages currentMessage = (ItemMessages) this.itemMessages.get(pos);
        switch (getItemViewType(position)) {
            case 1:
                ViewSentHolder senderHolder =  (ViewSentHolder) holder;
                senderHolder.messageView.setText(currentMessage.getMessages());
                senderHolder.dateView.setText(currentMessage.getDate());
                if (currentMessage.getStatus() == ItemMessages.DELIVERED) {
                    senderHolder.statusView.setImageResource(R.drawable.ic_status_delivered);
                    return;
                } else if (currentMessage.getStatus() == ItemMessages.SEEN) {
                    senderHolder.statusView.setImageResource(R.drawable.ic_status_read);
                    return;
                } else if (currentMessage.getStatus() == ItemMessages.SENT) {
                    senderHolder.statusView.setImageResource(R.drawable.ic_status_sent);
                    return;
                } else {
                    return;
                }
            case 2:
                ViewReceiverHolder receiverHolder = (ViewReceiverHolder) holder;
                receiverHolder.messageView.setText(currentMessage.getMessages());
                receiverHolder.dateView.setText(currentMessage.getDate());
                return;
            default:
                return;
        }
    }

    public int getItemCount() {
        return this.itemMessages.size();
    }

    public int getItemViewType(int position) {
        PrintStream printStream = System.out;
        StringBuilder sb = new StringBuilder();
        sb.append("Josiah dup ");
        sb.append(position);
        printStream.println(sb.toString());
//        new SharedPref(this.context);
//        if (SharedPref.getUser(SharedPref.KEY_USER).getId() == ((ItemMessages) this.itemMessages.get(position)).getContact().getId()) {
//            return 1;
//        }
        return 1;
    }

}
