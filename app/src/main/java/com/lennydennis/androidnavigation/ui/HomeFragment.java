package com.lennydennis.androidnavigation.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.adapters.UserRecyclerViewAdapter;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.util.Users;
import com.lennydennis.androidnavigation.viewmodel.SharedViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final int COLUMNS = 2;
    private RecyclerView mRecyclerView;
    private ArrayList<User> mUserArrayList = new ArrayList<>();;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private UserRecyclerViewAdapter mUserRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = view.findViewById(R.id.user_profile_recyclerview);
        displayUsers();
        return view;
    }

    private void displayUsers(){
        Users users = new Users();
        if (mUserArrayList != null) {
            mUserArrayList.clear();
        }
        for (User user : users.USERS) {
            mUserArrayList.add(user);
        }
        if (mUserRecyclerViewAdapter== null) {
            initializeRecyclerView();
        }
    }

    private void initializeRecyclerView(){
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(COLUMNS, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mUserRecyclerViewAdapter = new UserRecyclerViewAdapter(mUserArrayList,getActivity());
        mRecyclerView.setAdapter(mUserRecyclerViewAdapter);
    }
}