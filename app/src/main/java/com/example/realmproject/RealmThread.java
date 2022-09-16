package com.example.realmproject;

import android.app.ProgressDialog;
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

public  class RealmThread extends Thread {


    Realm realm;
    Context context;
    ActivityMainBinding binding;
    String result;
    android.app.ProgressDialog ProgressDialog;
    Handler mHandler;

    int value = 0 ;
    public RealmThread(Context context) {

        this.context=context;


    }

    @Override
    public void run() {
        realm = Realm.getDefaultInstance();

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
        realm.close();

        MainActivity.getmInstanceActivity().runOnUiThread(() ->  Toast.makeText(context, "Inserted !", Toast.LENGTH_SHORT).show());
        MainActivity.getmInstanceActivity().pd.dismiss();

        // ShowIt();




    }

    protected void onHandleIntent(Intent intent) {
        Realm realm = Realm.getDefaultInstance();

        //Add sample Customers to database

        realm.beginTransaction();
        RecyclerRealmAdapter adapter =
                new RecyclerRealmAdapter(context,
                        get_data(realm.where(QuotesModuleRealm.class).findAll()));
        MainActivity.getmInstanceActivity().runOnUiThread(() ->    binding.recycler.setAdapter(adapter));
        MainActivity.getmInstanceActivity().runOnUiThread(() ->  Toast.makeText(context,"work !", Toast.LENGTH_SHORT).show());

        realm.commitTransaction();

        realm.close();

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
    public ArrayList<QuotesModuleRealm> get_data(RealmResults<QuotesModuleRealm> result){

        ArrayList<QuotesModuleRealm> listitem = new ArrayList<>();
        for(QuotesModuleRealm p : result)
        {
            listitem.add(p);
        }

        return listitem;

    }


}
