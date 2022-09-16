package com.example.realmproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class DeletingService extends Service {
    public DeletingService() {
    }

    Realm realm;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        realm=Realm.getDefaultInstance();

        DeletingThread deletingThread = new DeletingThread(MainActivity.getmInstanceActivity());
        deletingThread.start();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

              /*  RecyclerRealmAdapter adapter =
                        new RecyclerRealmAdapter(MainActivity.getmInstanceActivity().getApplicationContext(),
                                get_data(realm.where(QuotesModuleRealm.class).findAll()));*/
                MainActivity.getmInstanceActivity().binding.recycler.setAdapter(null);



            }
        });

       /* realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                MainActivity.getmInstanceActivity().refresh_adapter(realm);
                Toast.makeText(MainActivity.getmInstanceActivity().getApplicationContext(), "Inserted !", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "execute: Service Stopped !");


            }
        });*/
        Toast.makeText(this, "Deleted Successfully !", Toast.LENGTH_SHORT).show();
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
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}