package com.lennydennis.androidnavigation.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lennydennis.androidnavigation.MainActivity;
import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.adapters.UserRecyclerViewAdapter;
import com.lennydennis.androidnavigation.models.FragmentTag;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.util.PreferencesKey;
import com.lennydennis.androidnavigation.util.Users;
import com.lennydennis.androidnavigation.viewmodel.SharedViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int COLUMNS = 2;
    private RecyclerView mRecyclerView;
    private ArrayList<User> mUserArrayList = new ArrayList<>();;
    private UserRecyclerViewAdapter mUserRecyclerViewAdapter;

    private UserProfileFragment mUserProfileFragment = new UserProfileFragment();
    private MainActivity mMainActivity = new MainActivity();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String mSelectedInterest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = view.findViewById(R.id.user_profile_recyclerview);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        findMatches();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        mUserRecyclerViewAdapter.setListener((v,position) -> {
            sharedViewModel.setSelectedUser(mUserRecyclerViewAdapter.getItemAt(position));
            mMainActivity.mFragmentTags.add("user profile");
            mMainActivity.mFragments.add(new FragmentTag(mUserProfileFragment,"user profile"));
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_content_frame,mUserProfileFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void findMatches() {
        getSavedPreferences();
        Users users = new Users();
        if(mUserArrayList != null){
            mUserArrayList.clear();
        }
        for(User user: users.USERS){
            if(mSelectedInterest.equals(getString(R.string.interested_in_anyone))){
                mUserArrayList.add(user);
            }
            else if(user.getGender().equals(mSelectedInterest) || user.getGender().equals(getString(R.string.gender_none))){
                mUserArrayList.add(user);
            }
        }
        if(mUserRecyclerViewAdapter == null){
            initializeRecyclerView();
        }
    }

    private void getSavedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        mSelectedInterest = preferences.getString(PreferencesKey.INTERESTED_IN, getString(R.string.interested_in_anyone));
    }

    private void initializeRecyclerView(){
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(COLUMNS, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mUserRecyclerViewAdapter = new UserRecyclerViewAdapter(mUserArrayList,getActivity());
        mRecyclerView.setAdapter(mUserRecyclerViewAdapter);
    }

    public void scrollToTop(){
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onRefresh() {
        findMatches();
        onItemLoadComplete();
    }

    private void onItemLoadComplete() {
        mUserRecyclerViewAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}