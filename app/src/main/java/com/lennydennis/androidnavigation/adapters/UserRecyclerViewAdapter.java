package com.lennydennis.androidnavigation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.models.User;

import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>  {

    private List<User> mUserList;
    private Context mContext;

    public UserRecyclerViewAdapter(List<User> userList, Context context) {
        mUserList = userList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = mUserList.get(position);
        holder.userName.setText(user.getName());
        holder.userInterest.setText(user.getInterestedIn());
        holder.userStatus.setText(user.getStatus());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .load(user.getProfileImage())
                .apply(requestOptions)
                .into(holder.userImage);

        holder.userCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userImage;
        TextView userName;
        TextView userInterest;
        TextView userStatus;
         CardView userCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userCardView = itemView.findViewById(R.id.user_card_view);
            userImage = itemView.findViewById(R.id.tv_user_profile);
            userName = itemView.findViewById(R.id.tv_user_name);
            userInterest = itemView.findViewById(R.id.tv_user_interest);
            userStatus = itemView.findViewById(R.id.tv_user_status);
        }
    }
}
