package com.example.chattick.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chattick.R;
import com.example.chattick.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<Chat> mChat;
    private final Context mContext;
    private String imageuri;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageuri) {

        this.mContext = mContext;
        this.mChat = mChat;
        this.imageuri = imageuri;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Chat chat = mChat.get(position);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())){
            if (imageuri.equals("default")){
                holder.my_profile_image.setImageResource(R.mipmap.ic_launcher);
            }else{
                Glide.with(mContext).load(imageuri).into(holder.my_profile_image);
            }
            holder.myMessage.setText(chat.getMessage());
            holder.myLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);

            if (position == mChat.size()-1){
                if (chat.isIsseen()){
                    holder.my_txt_seen.setText("Seen");
                } else {
                    holder.my_txt_seen.setText("Delivered");
                }
            }else {
                holder.my_txt_seen.setVisibility(View.GONE);
            }


        } else {
            if (imageuri.equals("default")){
                holder.oppo_profile_image.setImageResource(R.mipmap.ic_launcher);
            }else{
                Glide.with(mContext).load(imageuri).into(holder.oppo_profile_image);
            }
            holder.oppoMessage.setText(chat.getMessage());
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);



            if (position == mChat.size()-1){
                if (chat.isIsseen()){
                    holder.oppo_txt_seen.setText("Seen");
                } else {
                    holder.oppo_txt_seen.setText("Delivered");
                }
            }else {
                holder.oppo_txt_seen.setVisibility(View.GONE);
            }


        }



    }


    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout oppoLayout, myLayout;
        private CircleImageView oppo_profile_image, my_profile_image;
        private TextView oppoMessage, myMessage;
        private TextView my_txt_seen, oppo_txt_seen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            oppoLayout = itemView.findViewById(R.id.oppo_layout);
            myLayout = itemView.findViewById(R.id.my_layout);
            oppo_profile_image = itemView.findViewById(R.id.oppo_profile_image);
            my_profile_image = itemView.findViewById(R.id.my_profile_image);
            oppoMessage = itemView. findViewById(R.id.oppo_message);
            myMessage = itemView.findViewById(R.id.my_message);
            my_txt_seen = itemView.findViewById(R.id.my_txt_seen);
            oppo_txt_seen = itemView.findViewById(R.id.oppo_txt_seen);
        }
    }
}
