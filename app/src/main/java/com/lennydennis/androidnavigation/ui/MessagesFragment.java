package com.lennydennis.androidnavigation.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.adapters.MessageRecyclerViewAdapter;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.util.PreferencesKey;
import com.lennydennis.androidnavigation.util.Users;
import com.lennydennis.androidnavigation.viewmodel.SharedViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MessagesFragment extends Fragment {

    private static final String TAG = "MessagesFragment";
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private MessageRecyclerViewAdapter mMessageRecyclerViewAdapter;

    private ArrayList<User> mUserArrayList = new ArrayList<User>();
    private ChatFragment mChatFragment = new ChatFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        mRecyclerView = view.findViewById(R.id.messages_recycler_view);
        mSearchView = view.findViewById(R.id.action_search);
        getConnections();
        initializeSearchView();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        mMessageRecyclerViewAdapter.setListener((v,position) ->{
            sharedViewModel.setSelectedMessage(mMessageRecyclerViewAdapter.getItemAt(position));
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_content_frame,mChatFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void initializeSearchView(){
        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
        assert searchManager != null;
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMessageRecyclerViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMessageRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void getConnections(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> savedNames = preferences.getStringSet(PreferencesKey.SAVED_CONNECTIONS, new HashSet<String>());
        Users users = new Users();
        if(mUserArrayList != null){
            mUserArrayList.clear();
        }
        for(User user: users.USERS){
            if(savedNames.contains(user.getName())){
                mUserArrayList.add(user);
            }
        }
        if(mMessageRecyclerViewAdapter == null){
            initializeRecyclerView();
        }
    }

    private void initializeRecyclerView(){
        mMessageRecyclerViewAdapter = new MessageRecyclerViewAdapter(mUserArrayList,getActivity());
        mRecyclerView.setAdapter(mMessageRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}