package com.lennydennis.androidnavigation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
import com.lennydennis.androidnavigation.models.User;

import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>  {

    private static final String TAG = "UserRecyclerViewAdapter";

    private List<User> mUserList;
    private Context mContext;

    private OnItemClickListener mListener;

    public UserRecyclerViewAdapter(List<User> userList, Context context) {
        mUserList = userList;
        mContext = context;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public User getItemAt(int position) {
        return mUserList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        User user = mUserList.get(position);
        holder.userName.setText(user.getName());
        holder.userInterest.setText(user.getInterestedIn());
        holder.userStatus.setText(user.getStatus());
        holder.currentPosition = position;

        Log.d(TAG, "onBindViewHolder: "+user.getProfileImage());

        Glide.with(mContext)
                .load(user.getProfileImage())
                .into(holder.userImage);
    }


    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView userImage;
        TextView userName;
        TextView userInterest;
        TextView userStatus;
        int currentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            userImage = itemView.findViewById(R.id.tv_user_profile);
            userName = itemView.findViewById(R.id.tv_user_name);
            userInterest = itemView.findViewById(R.id.tv_user_interest);
            userStatus = itemView.findViewById(R.id.tv_user_status);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v,getAdapterPosition());
        }
    }
}
