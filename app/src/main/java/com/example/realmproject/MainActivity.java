package com.example.realmproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.realmproject.databinding.ActivityMainBinding;
import com.example.realmproject.databinding.DeleteLayoutBinding;
import com.example.realmproject.databinding.DialogLayoutBinding;
import com.example.realmproject.databinding.UpdateDialogBinding;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    DialogLayoutBinding dialogLayoutBinding;
    UpdateDialogBinding updateDialogBinding;
    DeleteLayoutBinding deleteLayoutBinding;
    Realm realm;
    ActivityMainBinding binding;
    ProgressDialog pd;
    Handler handler = new Handler();
    ArrayList<QuotesModuleRealm> arrayList;
    public static WeakReference<MainActivity> weakActivity;

    public static MainActivity getmInstanceActivity() {
        return weakActivity.get();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        weakActivity = new WeakReference<>(MainActivity.this);

        realm = Realm.getDefaultInstance();

        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("inserting ..");


        binding.recycler.setLayoutManager(new LinearLayoutManager(this));


        binding.recycler.setHasFixedSize(true);
        binding.recycler.addItemDecoration(new DividerItemDecoration(
                getApplicationContext(),
                DividerItemDecoration.VERTICAL));
// create an adapter with a RealmResults collection
// and attach it to the RecyclerView
        RecyclerRealmAdapter adapter =
                new RecyclerRealmAdapter(this,
                        get_data(realm.where(QuotesModuleRealm.class).findAll()));
        binding.recycler.setAdapter(adapter);
        binding.refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        RecyclerRealmAdapter adapter =
                                new RecyclerRealmAdapter(MainActivity.this,
                                        get_data(realm.where(QuotesModuleRealm.class).findAll()));
                        binding.recycler.setAdapter(adapter);

                        // This line is important as it explicitly
                        // refreshes only once
                        // If "true" it implicitly refreshes forever
                        binding.refreshLayout.setRefreshing(false);
                    }
                }
        );
        binding.insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowInsertDialog();
            }
        });

        binding.readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataReading();
            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowUpdateDialog();
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowDeleteDialog();

            }
        });

        binding.taskStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.show();

                Intent i =new Intent(MainActivity.this,MyService.class);
                startService(i);

            }
        });
        binding.taskStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* try (Realm realm = Realm.getDefaultInstance()){
                     // your queries

                realm.executeTransaction(new Realm.Transaction() {
                                                  @Override
                                                  public void execute(Realm realm) {
                                                      realm.deleteAll();

                                                      RecyclerRealmAdapter adapter =
                                                              new RecyclerRealmAdapter(MainActivity.this,
                                                                      get_data(realm.where(QuotesModuleRealm.class).findAll()));
                                                      binding.recycler.setAdapter(adapter);

                                                  }
                                              });
                 }
*/

                Intent i =new Intent(MainActivity.this,DeletingService.class);
                startService(i);


            }
        });

    }

    public void refresh_adapter(Realm realm){


        RecyclerRealmAdapter adapter =
                new RecyclerRealmAdapter(this,
                        get_data(realm.where(QuotesModuleRealm.class).findAll()));
        binding.recycler.setAdapter(adapter);
    }

    private void ShowInsertDialog() {

       /* AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = al.show();
        dialogLayoutBinding = DataBindingUtil.inflate(alertDialog.getLayoutInflater(), R.layout.dialog_layout, null, false);

        alertDialog.setContentView(dialogLayoutBinding.getRoot());
        dialogLayoutBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
                QuotesModuleRealm quotesModuleRealm = new QuotesModuleRealm();

                Number current_id = realm.where(QuotesModuleRealm.class).max("id");

                long next_id;

                if (current_id == null)
                {
                    next_id = 1;

                }else
                {
                    next_id = current_id.intValue() + 1 ;

                }

                quotesModuleRealm.setId(next_id);
                quotesModuleRealm.setFirstName(dialogLayoutBinding.editFirst.getText().toString());
                quotesModuleRealm.setLastName(dialogLayoutBinding.editLast.getText().toString());


                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        realm.copyToRealm(quotesModuleRealm);

                        Toast.makeText(MainActivity.this, "Inserted !", Toast.LENGTH_SHORT).show();
                        DataReading();

                    }
                });
            }
        });
*/

      /*  RealmThread realmThread = new RealmThread(realm, handler,binding, this);
        realmThread.start();*/
        for (int i = 0; i < 200; i++) {
            Log.d("ShowInsertDialog", "ShowInsertDialog: " + i);

            handler.post(new Runnable() {
                @Override
                public void run() {
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

                            Toast.makeText(MainActivity.this, "Inserted !", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });


        }
        DataReading();



    }



    // function to generate a random string of length n
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


    private void ShowUpdateDialog() {
        final AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);

        final AlertDialog alertDialog = al.show();

        updateDialogBinding = DataBindingUtil.inflate(alertDialog.getLayoutInflater(), R.layout.update_dialog, null, false);

        alertDialog.setContentView(updateDialogBinding.getRoot());
        updateDialogBinding.editId.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                final QuotesModuleRealm dataModel;


                if (!s.toString().equals("")) {
                    dataModel = realm.where(QuotesModuleRealm.class).equalTo("id", Long.parseLong(s.toString())).findFirst();

                    if (dataModel != null) {
                        updateDialogBinding.editFirst.setText(dataModel.getFirstName());
                        updateDialogBinding.editLast.setText(dataModel.getLastName());
                    }

                }

            }


            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        updateDialogBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                long id = Long.parseLong(updateDialogBinding.editId.getText().toString());
                final QuotesModuleRealm dataModel = realm.where(QuotesModuleRealm.class).equalTo("id", id).findFirst();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        assert dataModel != null;
                        dataModel.setFirstName(updateDialogBinding.editFirst.getText().toString());
                        dataModel.setLastName(updateDialogBinding.editLast.getText().toString());
                        realm.copyToRealmOrUpdate(dataModel);
                        Toast.makeText(MainActivity.this, "Updated !", Toast.LENGTH_SHORT).show();
                        DataReading();

                    }
                });
            }
        });
    }


    /*  private void ShowUpdateDialogResult(final QuotesModuleRealm dataModel) {
          AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
          View view=getLayoutInflater().inflate(R.layout.data_input_dialog,null);
          al.setView(view);

          final EditText name=view.findViewById(R.id.name);
          final EditText age=view.findViewById(R.id.age);
          Button save=view.findViewById(R.id.save);
          final AlertDialog alertDialog=al.show();

          name.setText(dataModel.getName());
          age.setText(""+dataModel.getAge());

          save.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  alertDialog.dismiss();


                  realm.executeTransaction(new Realm.Transaction() {
                      @Override
                      public void execute(Realm realm) {
                          dataModel.setAge(Integer.parseInt(age.getText().toString()));
                          dataModel.setName(name.getText().toString());
                          dataModel.setGender(gender.getSelectedItem().toString());
                          realm.copyToRealmOrUpdate(dataModel);
                      }
                  });
              }
          });

      }
      */
    private void ShowDeleteDialog() {

        AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);

        final AlertDialog alertDialog = al.show();

        deleteLayoutBinding = DataBindingUtil.inflate(alertDialog.getLayoutInflater(), R.layout.delete_layout, null, false);

        alertDialog.setContentView(deleteLayoutBinding.getRoot());


        deleteLayoutBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();


                long id = Long.parseLong(deleteLayoutBinding.editId.getText().toString());

                QuotesModuleRealm quotesModuleRealm = realm.where(QuotesModuleRealm.class).equalTo("id", id).findFirst();


                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        QuotesModuleRealm idUpdate = realm.where(QuotesModuleRealm.class).equalTo("id", id).findFirst();
                        if (idUpdate != null) {
                            quotesModuleRealm.deleteFromRealm();
                            Toast.makeText(MainActivity.this, "Deleted !", Toast.LENGTH_SHORT).show();
                            DataReading();
                        } else
                            Toast.makeText(MainActivity.this, id + " does not available !", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });


    }

    private void DataReading() {

        RecyclerRealmAdapter adapter =
                new RecyclerRealmAdapter(this,
                        get_data(realm.where(QuotesModuleRealm.class).findAll()));
        binding.recycler.setAdapter(adapter);
    }
    public ArrayList<QuotesModuleRealm> get_data(RealmResults<QuotesModuleRealm> result){

        ArrayList<QuotesModuleRealm> listitem = new ArrayList<>();
        for(QuotesModuleRealm p : result)
        {
            listitem.add(p);
        }

        return listitem;

    }




    class ReadRealmThread extends Thread {


        Realm realm;
        Context context;
        ActivityMainBinding binding;
        String result;
        ProgressDialog ProgressDialog;

        public ReadRealmThread(Context context,ActivityMainBinding binding,ProgressDialog ProgressDialog) {

            this.context=context;
            this.binding=binding;
            this.ProgressDialog=ProgressDialog;


        }

        @Override
        public void run() {
            realm = Realm.getDefaultInstance();

            RealmResults<QuotesModuleRealm> list = realm.where(QuotesModuleRealm.class).findAll();

            arrayList =new ArrayList<>();

            arrayList=get_data(list);
      /*  binding.textView.setText("");

        for (int i = 0; i < list.size(); i++) {

            binding.textView.append("ID : " + list.get(i).getId() + "  ;  FirstName : " + list.get(i).getFirstName() + "  ;  LastName :  " + list.get(i).getLastName() + "\n");


        }
*/
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    RecyclerRealmAdapter recyclerRealmAdapter=new RecyclerRealmAdapter(MainActivity.this, arrayList);

                    binding.recycler.setAdapter(recyclerRealmAdapter);

                }
            });



        }


    }


    private BroadcastReceiver  bReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle args = intent.getBundleExtra("BUNDLE");
            ArrayList<QuotesModuleRealm> quotesModuleRealms = (ArrayList<QuotesModuleRealm>) args.getSerializable("ARRAYLIST");

        }
    };

    protected void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(bReceiver, new IntentFilter("message"));
    }

    protected void onPause (){
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bReceiver);
    }

    public void ShowIt(){

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<QuotesModuleRealm> quotesModuleRealms = (ArrayList<QuotesModuleRealm>) args.getSerializable("ARRAYLIST");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
    class RealmThread extends Thread {


        Realm realm;
        Context context;
        ActivityMainBinding binding;
        String result;
        ProgressDialog ProgressDialog;
        Handler mHandler;

        public RealmThread(Context context,ActivityMainBinding binding,ProgressDialog ProgressDialog) {

            this.context=context;
            this.binding=binding;
            this.ProgressDialog=ProgressDialog;

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
            runOnUiThread(() ->  Toast.makeText(context, "Inserted !", Toast.LENGTH_SHORT).show());
            pd.dismiss();

            // ShowIt();


            Looper.prepare();

            mHandler = new Handler(Looper.myLooper()) {
                public void handleMessage(Message msg) {

                }
            };

            Looper.loop();
        }

        protected void onHandleIntent(Intent intent) {
            Realm realm = Realm.getDefaultInstance();

            //Add sample Customers to database

            realm.beginTransaction();
            RecyclerRealmAdapter adapter =
                    new RecyclerRealmAdapter(context,
                            get_data(realm.where(QuotesModuleRealm.class).findAll()));
            runOnUiThread(() ->    binding.recycler.setAdapter(adapter));
            runOnUiThread(() ->  Toast.makeText(context,"work !", Toast.LENGTH_SHORT).show());

            realm.commitTransaction();

            realm.close();

        }

        public ArrayList<QuotesModuleRealm> get_data(RealmResults<QuotesModuleRealm> result){

            ArrayList<QuotesModuleRealm> listitem = new ArrayList<>();
            for(QuotesModuleRealm p : result)
            {
                listitem.add(p);
            }

            return listitem;

        }

        private void DataReading() {


            RealmResults<QuotesModuleRealm> list = realm.where(QuotesModuleRealm.class).findAll();

            arrayList =new ArrayList<>();

            arrayList=get_data(list);
      /*  binding.textView.setText("");

        for (int i = 0; i < list.size(); i++) {

            binding.textView.append("ID : " + list.get(i).getId() + "  ;  FirstName : " + list.get(i).getFirstName() + "  ;  LastName :  " + list.get(i).getLastName() + "\n");


        }
*/
         /*   RecyclerRealmAdapter recyclerRealmAdapter=new RecyclerRealmAdapter(MainActivity.this, arrayList);
            binding.recycler.setAdapter(recyclerRealmAdapter);*/
            pd.dismiss();
            /*runOnUiThread(new Runnable() {

                @Override
                public void run() {


                }
            });*/

        }
    }


}