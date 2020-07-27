package com.lennydennis.androidnavigation.viewmodel;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.ui.HomeFragment;
import com.lennydennis.androidnavigation.ui.UserProfileFragment;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<User> selectedUser =  new MutableLiveData<User>();

    public void setSelectedUser(User user){
        selectedUser.setValue(user);
    }

    public LiveData<User> getSelectedUser(){
        return selectedUser;
    }
}
