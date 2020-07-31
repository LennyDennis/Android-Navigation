package com.lennydennis.androidnavigation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.models.Message;
import com.lennydennis.androidnavigation.util.Messages;

import java.util.ArrayList;

public class ChatRecyclerVIewAdapter extends RecyclerView.Adapter<ChatRecyclerVIewAdapter.ViewHolder> {
    private ArrayList<Message> mMessageArrayList = new ArrayList<>();
    private Context mContext;

    public ChatRecyclerVIewAdapter(ArrayList<Message> messageArrayList, Context context) {
        mMessageArrayList = messageArrayList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = mMessageArrayList.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .load(message.getUser().getProfileImage())
                .circleCrop()
                .apply(requestOptions)
                .into(holder.userProfile);

        holder.userMessage.setText(message.getMessage());

    }

    @Override
    public int getItemCount() {
        return mMessageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView userProfile;
        TextView userMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.chat_user_profile);
            userMessage = itemView.findViewById(R.id.chat_user_message);
        }
    }
}
