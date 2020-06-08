package com.fas.smash_k.ui.adaptors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fas.smash_k.R;
import com.fas.smash_k.TalkActivity;
import com.fas.smash_k.ui.home.HomeFragment;
import com.fas.smash_k.ui.models.chatItems.ItemConversation;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.ViewHolder>  {
    public static final String CONVENSATIONPOSTON = "conversation_position";
    Context context;
    ArrayList<ItemConversation> conversations;
    int pos;

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
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_conversations, parent, false);
        return new ViewHolder(view);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ConversationsAdapter.this.context, TalkActivity.class);
                intent.putExtra(ConversationsAdapter.CONVENSATIONPOSTON, pos);
                //((Activity) ConversationsAdapter.this.context).startActivityForResult(intent, TalkActivity.REQUESTCODE_CONVERSATION_POSITION);
                ((Activity) ConversationsAdapter.this.context).startActivityForResult(intent, HomeFragment.REQUEST_CODE);
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
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        // Fields:
//        /** Whether a swipe motion has been detected */
//         float x1=0,x2=0;
//         final int MIN_DISTANCE = 150;
//        switch(event.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//                x1 = event.getX();
//                break;
//            case MotionEvent.ACTION_UP:
//                x2 = event.getX();
//                float deltaX = x2 - x1;
//
//                if (Math.abs(deltaX) > MIN_DISTANCE)
//                {
//                    // Left to Right swipe action
//                    if (x2 > x1)
//                    {
//
//                    }
//
//                    // Right to left swipe action
//                    else
//                    {
//                        Toast.makeText(context, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
//                    }
//
//                }
//                else
//                {
//                    // consider as something else - a screen tap for example
//
//                    Intent intent = new Intent(ConversationsAdapter.this.context, TalkActivity.class);
//                    intent.putExtra(ConversationsAdapter.CONVENSATIONPOSTON, pos);
//                    ((Activity) ConversationsAdapter.this.context).startActivityForResult(intent, TalkActivity.REQUESTCODE_CONVERSATION_POSITION);
//                }
//                break;
//            default:
//
//        }
//
//        return true;
//    }
//
//
//
//    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
//        private static final int HORIZONTAL_SWIPE_THRESHOLD = 0;
//        private static final int HORIZONTAL_SWIPE_VELOCITY_THRESHOLD = 0;
//        private static final int VERTICAL_SWIPE_THRESHOLD = 100;
//        private static final int VERTICAL_SWIPE_VELOCITY_THRESHOLD = 100;
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            boolean result = false;
//            try {
//                float diffY = e2.getY() - e1.getY();
//                float diffX = e2.getX() - e1.getX();
//                if (Math.abs(diffX) > Math.abs(diffY)) {    // More of a horizontal movement
//                    if (Math.abs(diffX) > HORIZONTAL_SWIPE_THRESHOLD && Math.abs(velocityX) > HORIZONTAL_SWIPE_VELOCITY_THRESHOLD) {
//                        if (diffX > 0) {
//                            isSwipeDetected = true;
//                            onSwipeRight();
//                        } else {
//                            isSwipeDetected = true;
//                            onSwipeLeft();
//                        }
//                    }
//                    result = true;
//                } else {    // Vertical movement
//                    if (Math.abs(diffY) > VERTICAL_SWIPE_THRESHOLD && Math.abs(velocityY) > VERTICAL_SWIPE_VELOCITY_THRESHOLD) {
//                        if (diffY > 0) {
//                            isSwipeDetected = true;
//                            onSwipeBottom();
//                        } else {
//                            isSwipeDetected = true;
//                            onSwipeTop();
//                        }
//                    }
//                    result = true;
//                }
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//
//            return result;
//        }
//        public void onSwipeRight() {
//            System.out.println("fas onSwipeRight");
//        }
//        public void onSwipeLeft() {
//            System.out.println("fas onSwipeLeft");
//        }
//        public void onSwipeTop() {
//            System.out.println("fas onSwipeTop");
//        }
//        public void onSwipeBottom() {
//            System.out.println("fas onSwipeBottom");
//        }
//
//    }
}



