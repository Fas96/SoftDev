package com.fas.smash_k.ui.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fas.smash_k.R;
import com.fas.smash_k.ui.models.chatItems.ItemConversation;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.ViewHolder> implements Filterable {
    public static final String CONVENSATIONPOSTON = "conversation_position";
    Context context;
    ArrayList<ItemConversation> conversations;
    int pos;


    ArrayList<ItemConversation> convooperationaList;

    private onItemClickedListener mListener;
    private onItemLongPressed onItemLongPressed;


    public interface onItemLongPressed {
        void onLongPressed(int position);
    }

    public void setOnItemLongPressed(onItemLongPressed onItemLongPressed) {
        this.onItemLongPressed = onItemLongPressed;
    }

    public interface onItemClickedListener {
        void onItemClicked(int position);
    }

    public void setOnItemClickedListener(onItemClickedListener mListener) {
        this.mListener = mListener;
    }
    //gestures
//
//    boolean isSwipeDetected = false;
//    GestureDetector gestureDetector;
//
//    // Constructors:
//    public void OnSwipeTouchListener(Context ctx) {
//        gestureDetector = new GestureDetector(ctx, new GestureListener());
//    }

    public ConversationsAdapter(ArrayList<ItemConversation> conversations2, Context context2) {
        this.conversations = conversations2;
        this.context = context2;
        this.convooperationaList = conversations2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_conversations, parent, false);
        return new ViewHolder(view, mListener, onItemLongPressed);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        pos = holder.getAdapterPosition();
        // position= holder.getPosition();
        ItemConversation currentConversation = (ItemConversation) this.conversations.get(pos);
        Glide.with(this.context).load(currentConversation.getImageURL()).into((ImageView) holder.circleImageView);
        holder.nameContact.setText(currentConversation.getConttactName());
        holder.lastMessage.setText(currentConversation.getLastMessage());
        holder.date.setText(currentConversation.getDate());
        //on Touch listener
        //holder.itemView.setOnTouchListener(this);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(ConversationsAdapter.this.context, TalkActivity.class);
//                intent.putExtra(ConversationsAdapter.CONVENSATIONPOSTON, pos);
//                //((Activity) ConversationsAdapter.this.context).startActivityForResult(intent, TalkActivity.REQUESTCODE_CONVERSATION_POSITION);
//                ((Activity) ConversationsAdapter.this.context).startActivityForResult(intent, HomeFragment.REQUEST_CODE);
//            }
//        });

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

        public ViewHolder(@NonNull View itemView, final onItemClickedListener onClickListener, final onItemLongPressed onItemLongPressed) {
            super(itemView);
            this.circleImageView = (CircleImageView) itemView.findViewById(R.id.conversation_image);
            this.nameContact = (TextView) itemView.findViewById(R.id.contact_name);
            this.lastMessage = (TextView) itemView.findViewById(R.id.last_message);
            this.date = (TextView) itemView.findViewById(R.id.conversation_date);
            this.unreadCount = (TextView) itemView.findViewById(R.id.message_count);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        int position = ViewHolder.this.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.onItemClicked(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if (onItemLongPressed != null) {
                        int position = ViewHolder.this.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemLongPressed.onLongPressed(position);
                        }
                    }
                    return false;
                }
            });

        }
    }

    public void filterChat(String s) {
        convooperationaList = new ArrayList<ItemConversation>();
        if (s.isEmpty()) {
            System.out.println("fas---------------------EMP " + s);
            convooperationaList = conversations;
        } else {
            for (ItemConversation item : convooperationaList) {
                if (item.getLastMessage().toLowerCase().contains(s.toLowerCase())) {
                    if (item.getLastMessage().toLowerCase().contains(s.toLowerCase())) {

                        System.out.println("fas--------------------- " + s + "---------------------" + item.getConttactName());
                        convooperationaList.add(item);
                    }
                }
            }
        }

        System.out.println("fas---------------------End " + s);

        notifyDataSetChanged();
    }


    public void clearAllFilters() {
        conversations = convooperationaList;
        notifyDataSetChanged();
    }

    // 데이터 필터 검색 Filterable implement ---------------------------------
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemConversation> filteredList = new ArrayList<ItemConversation>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(convooperationaList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ItemConversation item : convooperationaList) {
                    //TODO filter 대상 setting
                    if (item.getConttactName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            convooperationaList.clear();
            convooperationaList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}



