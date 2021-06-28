package com.andrew.library.net.exception;

import androidx.lifecycle.LiveData;


public class NetLiveData<T> extends LiveData<T> {
    @Override
    protected void postValue(T value) {
        super.postValue(value);
    }
}
