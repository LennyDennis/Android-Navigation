package com.lennydennis.androidnavigation.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lennydennis.androidnavigation.IMainActivity;
import com.lennydennis.androidnavigation.MainActivity;
import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.adapters.ChatRecyclerVIewAdapter;
import com.lennydennis.androidnavigation.models.Message;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.util.PreferencesKey;
import com.lennydennis.androidnavigation.util.Resources;
import com.lennydennis.androidnavigation.viewmodel.SharedViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class ChatFragment extends Fragment implements View.OnClickListener {

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
    private User mCurrentUser = new User();
    private ChatRecyclerVIewAdapter mChatRecyclerVIewAdapter;
    private SharedViewModel mViewModel;
    private UserProfileFragment mUserProfileFragment = new UserProfileFragment();
    private IMainActivity mInterface;


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
        mBackArrow.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        mMessage = mViewModel.getSelectedMessage().getValue();
        mMessageArrayList.add(mMessage);
        getSavedPreferences();

        initializeToolbar();
        initializeChatRecyclerView();
        setBackgroundImage(view);
    }

    private void getSavedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String name = preferences.getString(PreferencesKey.NAME, "");
        mCurrentUser.setName(name);

        String gender = preferences.getString(PreferencesKey.GENDER, getString(R.string.gender_none));
        mCurrentUser.setGender(gender);

        String profileImageImage = preferences.getString(PreferencesKey.PROFILE_IMAGE, "");
        mCurrentUser.setProfileImage(profileImageImage);
    }


    private void setBackgroundImage(View view) {
        mBackgroundImage = view.findViewById(R.id.background);
        Glide.with(requireActivity())
                .load(Resources.BACKGROUND_HEARTS)
                .into(mBackgroundImage);
    }

    private void initializeChatRecyclerView() {
        mChatRecyclerVIewAdapter = new ChatRecyclerVIewAdapter(mMessageArrayList, getActivity());
        mRecyclerView.setAdapter(mChatRecyclerVIewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initializeToolbar() {
        mBackArrow.setOnClickListener(this);
        mUserName.setText(mMessage.getUser().getName());
        Glide.with(requireActivity())
                .load(mMessage.getUser().getProfileImage())
                .circleCrop()
                .into(mUserProfile);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chat_back_button) {
            mInterface.onBackPressed();
        } else if (v.getId() == R.id.send_message) {
            mMessageArrayList.add(new Message(mCurrentUser, mInputMessage.getText().toString()));
            mChatRecyclerVIewAdapter.notifyDataSetChanged();
            mInputMessage.setText("");
            mRecyclerView.smoothScrollToPosition(mMessageArrayList.size() - 1);

        } else if (v.getId() == R.id.toolbar_linear_layout) {
            //open user details
            mViewModel.setSelectedUser(mMessage.getUser());
            getParentFragmentManager().beginTransaction()
                    .add(R.id.main_content_frame,mUserProfileFragment)
                    .commit();
        }
    }

}