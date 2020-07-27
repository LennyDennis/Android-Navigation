package com.lennydennis.androidnavigation.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lennydennis.androidnavigation.MainActivity;
import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.ui.UserProfileFragment;
import com.lennydennis.androidnavigation.viewmodel.SharedViewModel;

import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>  {

    private List<User> mUserList;
    private Context mContext;
    private SharedViewModel mSharedViewModel;
    private User mUser;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        mUser = mUserList.get(position);
        holder.userName.setText(mUser.getName());
        holder.userInterest.setText(mUser.getInterestedIn());
        holder.userStatus.setText(mUser.getStatus());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .load(mUser.getProfileImage())
                .apply(requestOptions)
                .into(holder.userImage);

        holder.userCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharedViewModel.selectUser(mUser);
                UserProfileFragment userProfileFragment = new UserProfileFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelable(String.valueOf(R.string.intent_user),mUser);
                userProfileFragment.setArguments(bundle);

                ((MainActivity)mContext).getSupportFragmentManager()
                        .beginTransaction().replace(R.id.container, userProfileFragment, String.valueOf(R.string.tag_fragment_home))
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mSharedViewModel = new ViewModelProvider((MainActivity) mContext).get(SharedViewModel.class);
        mSharedViewModel.getSelectedUser().observe((MainActivity) mContext, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mUser = user;
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
