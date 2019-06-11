package com.alhusseiny.till_man;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class SentActivity extends AppCompatActivity {
    private static SentActivity inst;
    ArrayList<Message> smsMessageslist = new ArrayList<>();
    ListView smsListview;

    MyAdapter arrayAdapter;


    public static SentActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);

        smsListview = findViewById(R.id.smsList);

        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            refreshSmsInbox();
        } else {
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(SentActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);

        }

        arrayAdapter = new MyAdapter(this, smsMessageslist);
        smsListview.setAdapter(arrayAdapter);


    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        smsMessageslist.clear();
        do {
            String origin = smsInboxCursor.getString(indexAddress);
            if (origin.equalsIgnoreCase("MPESA")) {
                String str = "SMS FROM:" + smsInboxCursor.getString(indexAddress) +
                        "\n" + smsInboxCursor.getString(indexBody)

                        + "\n";

                String body = smsInboxCursor.getString(indexBody);

                if (body.contains(" sent ")) {
                    try {
                        String TransCode = body.substring(0, 11).replace(" ", "").trim();
                        String name = body.substring(body.indexOf(" to "), body.indexOf(" 07")).replace(" to ", "").trim();
                        String date = body.substring(body.indexOf(" on "), body.indexOf(" at ")).replace("on", "").trim();
                        String amount = body.substring(body.indexOf("ed. Ksh"), body.indexOf(" sent ")).replace("ed.", "").trim();
                        ;

                        Log.d("MPESA", "refreshSmsInbox: " + TransCode);
                        Log.d("MPESA", "refreshSmsInbox: " + name);
                        Log.d("MPESA", "refreshSmsInbox: " + date);
                        Log.d("MPESA", "refreshSmsInbox: " + amount);
                        //String all = "CODE " + TransCode + "\nFROM:" + name + "\n" + "AMOUNT:" + amount + "\n" + "DATE:" + date;
                        com.alhusseiny.till_man.Message all = new com.alhusseiny.till_man.Message(TransCode,name, amount, date);
                        smsMessageslist.add(all);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }


            }

        } while (smsInboxCursor.moveToNext());


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        refreshSmsInbox();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /*public void updateLisst(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();


    }
*/
   /* public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessageslist.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }
            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
*/

}

