package com.lennydennis.androidnavigation.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.adapters.ChatRecyclerVIewAdapter;
import com.lennydennis.androidnavigation.models.Message;
import com.lennydennis.androidnavigation.util.Resources;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class ChatFragment extends Fragment implements View.OnClickListener{

    private ImageView mBackArrow;
    private ImageView mUserProfile;
    private TextView mUserName;
    private LinearLayout mToolbarUserDetails;
    private TextView mSendMessage;
    private EditText mInputMessage;
    private ImageView mBackgroundImage;

    private Message mMessage;
    private RecyclerView mRecyclerView;
    private ArrayList<Message> mMessageArrayList = new ArrayList<>();
    private ChatRecyclerVIewAdapter mChatRecyclerVIewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        mBackArrow = view.findViewById(R.id.chat_back_button);
        mUserProfile = view.findViewById(R.id.toolbar_user_profile);
        mUserName = view.findViewById(R.id.toolbar_user_name);
        mToolbarUserDetails = view.findViewById(R.id.toolbar_linear_layout);
        mSendMessage = view.findViewById(R.id.send_message);
        mInputMessage = view.findViewById(R.id.input_message);
        mRecyclerView = view.findViewById(R.id.chat_recyclerview);

        mSendMessage.setOnClickListener(this);
        mToolbarUserDetails.setOnClickListener(this);

        initializeToolbar();
        initializeChatRecyclerView();
        setBackgroundImage(view);

        return view;
    }

    private void setBackgroundImage(View view) {
        mBackgroundImage = view.findViewById(R.id.background);
        Glide.with(requireActivity())
                .load(Resources.BACKGROUND_HEARTS)
                .into(mBackgroundImage);
    }

    private void initializeChatRecyclerView() {
        mChatRecyclerVIewAdapter = new ChatRecyclerVIewAdapter(mMessageArrayList,getActivity());
        mRecyclerView.setAdapter(mChatRecyclerVIewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initializeToolbar() {
        mBackArrow.setOnClickListener(this);
        mUserName.setText(mMessage.getUser().getName());
        Glide.with(requireActivity())
                .load(mMessage.getUser().getProfileImage())
                .into(mUserProfile);
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.chat_back_button){
            //go back to the previous screen

        }else if(v.getId() == R.id.send_message){
            //add message

        }else if(v.getId() == R.id.input_message){
            //open user details

        }
    }

}