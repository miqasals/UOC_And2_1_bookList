package com.uoc.miquel.uocpac1app.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by mucl on 30/11/2016.
 */

public class NotificationsService extends IntentService {

    public NotificationsService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int position = intent.getIntExtra("position",-1);
        String action = intent.getAction();

        if (action.equals(MyFirebaseMessagingService.ACTION_DETAIL)){

        } else if (action.equals(MyFirebaseMessagingService.ACTION_ERASE)) {

        }
    }
}
