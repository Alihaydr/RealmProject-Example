package com.example.realmproject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.realmproject.databinding.ActivityMainBinding;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public  class DeletingThread extends Thread {


    Realm realm;
    Context context;

    public DeletingThread(Context context) {

        this.context = context;


    }

    @Override
    public void run() {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();


            }

        });



    }
}
