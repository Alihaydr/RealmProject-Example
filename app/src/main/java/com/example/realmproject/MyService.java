package com.example.realmproject;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyService extends Service {
    public MyService() {
    }

    int count = 0;
    Realm realm;
    Handler mHandler;
    int value;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "start service !", Toast.LENGTH_SHORT).show();

        realm=Realm.getDefaultInstance();

/*

        for (int i = 0; i < 100; i++) {
            Log.d("ShowInsertDialog", "ShowInsertDialog: " + i);


            QuotesModuleRealm quotesModuleRealm = new QuotesModuleRealm();

            Number current_id = realm.where(QuotesModuleRealm.class).max("id");

            long next_id;

            if (current_id == null) {
                next_id = 1;

            } else {
                next_id = current_id.intValue() + 1;

            }

            quotesModuleRealm.setId(next_id);
            quotesModuleRealm.setFirstName(getAlphaNumericString(5));
            quotesModuleRealm.setLastName(getAlphaNumericString(5));
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {

                    realm.copyToRealm(quotesModuleRealm);


                }
            });
        }
        MainActivity.getmInstanceActivity().runOnUiThread(() ->  Toast.makeText(MainActivity.getmInstanceActivity().getApplicationContext(), "Inserted !", Toast.LENGTH_SHORT).show());
        MainActivity.getmInstanceActivity().pd.dismiss();
*/

        RealmThread realmThread = new RealmThread(MainActivity.getmInstanceActivity());
        realmThread.start();

        int value = realmThread.getValue();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RecyclerRealmAdapter adapter =
                        new RecyclerRealmAdapter(MainActivity.getmInstanceActivity().getApplicationContext(),
                                get_data(realm.where(QuotesModuleRealm.class).findAll()));
                MainActivity.getmInstanceActivity().binding.recycler.setAdapter(adapter);

                Toast.makeText(MainActivity.getmInstanceActivity().getApplicationContext(), "Inserted !", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "execute: Service Stopped !");


            }
        });



        // ShowIt();


      /*  Looper.prepare();

        mHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg) {

            }
        };

        Looper.loop();*/



       /* realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();

                 MainActivity.getmInstanceActivity().refresh_adapter(realm);
                Toast.makeText(MainActivity.getmInstanceActivity().getApplicationContext(), "Deleted Successfully  !", Toast.LENGTH_SHORT).show();
                stopSelf();
                Log.d("TAG", "execute: Service Stopped !");


            }
        });*/


        stopSelf();
        return START_STICKY;
    }
    public ArrayList<QuotesModuleRealm> get_data(RealmResults<QuotesModuleRealm> result){

        ArrayList<QuotesModuleRealm> listitem = new ArrayList<>();
        for(QuotesModuleRealm p : result)
        {
            listitem.add(p);
        }

        return listitem;

    }



    static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public ArrayList<QuotesModuleRealm> receiveArray (ArrayList<QuotesModuleRealm> quotesModuleRealms){

        return quotesModuleRealms;

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("TAG", "onDestroy: service destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}