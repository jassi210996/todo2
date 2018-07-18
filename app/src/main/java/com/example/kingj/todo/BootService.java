package com.example.kingj.todo;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class BootService extends IntentService {

    public BootService() {
        super("BootService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
