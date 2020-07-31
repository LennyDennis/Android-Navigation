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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.adapters.UserRecyclerViewAdapter;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.util.PreferencesKey;
import com.lennydennis.androidnavigation.util.Users;
import com.lennydennis.androidnavigation.viewmodel.SharedViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SavedConnectionFragment extends Fragment {
    private static final String TAG = "SavedConnectionFragment";
    private static final int NUM_GRID_COLUMNS = 2;

    private UserRecyclerViewAdapter mUserRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private UserProfileFragment mUserProfileFragment = new UserProfileFragment();

    ArrayList<User> mUserArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_connection, container, false);
        mRecyclerView = view.findViewById(R.id.connection_recycler_view);

        getConnections();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        mUserRecyclerViewAdapter.setListener((v,position) -> {
            sharedViewModel.setSelectedUser(mUserRecyclerViewAdapter.getItemAt(position));
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_content_frame,mUserProfileFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void getConnections() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        Set<String> savedNames = sharedPreferences.getStringSet(PreferencesKey.SAVED_CONNECTIONS, new HashSet<String>());

        Users users = new Users();
        if(mUserArrayList != null){
            mUserArrayList.clear();
        }
        for(User user:users.USERS){
            if(savedNames.contains(user.getName())){
                mUserArrayList.add(user);
            }
        }
        if(mUserRecyclerViewAdapter == null){
            initRecyclerView();
        }

    }

    private void initRecyclerView(){
        mUserRecyclerViewAdapter = new UserRecyclerViewAdapter(mUserArrayList,getActivity());
        mRecyclerView.setAdapter(mUserRecyclerViewAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_GRID_COLUMNS, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }
}