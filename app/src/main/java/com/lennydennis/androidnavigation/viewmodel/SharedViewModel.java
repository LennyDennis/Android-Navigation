package com.lennydennis.androidnavigation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lennydennis.androidnavigation.models.Message;
import com.lennydennis.androidnavigation.models.User;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<User> selectedUser =  new MutableLiveData<>();
    private MutableLiveData<Message> selectedMessage = new MutableLiveData<>();

    public void setSelectedUser(User user){
        selectedUser.setValue(user);
    }

    public MutableLiveData<User> getSelectedUser(){
        return selectedUser;
    }

    public void setSelectedMessage(Message message){
        selectedMessage.setValue(message);
    }

    public  MutableLiveData<Message> getSelectedMessage(){

        if (selectedMessage == null) {
            selectedMessage = new MutableLiveData<>();
        }

        return selectedMessage;
    }
}
