package com.lennydennis.androidnavigation.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lennydennis.androidnavigation.MainActivity;
import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.viewmodel.SharedViewModel;

import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>  {

    private static final String TAG = "UserRecyclerViewAdapter";

    private List<User> mUserList;
    private Context mContext;
    private User mUser;

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
        mUser = mUserList.get(position);
        holder.userName.setText(mUser.getName());
        holder.userInterest.setText(mUser.getInterestedIn());
        holder.userStatus.setText(mUser.getStatus());
        holder.currentPosition = position;

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .load(mUser.getProfileImage())
                .apply(requestOptions)
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
