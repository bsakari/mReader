package com.alhusseiny.till_man;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    public  static final String SMS_Bundle = "pdus";

    public void  onReceive(Context context, Intent intent){
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null){
            Object[] sms = (Object[]) intentExtras.get(SMS_Bundle);
            String smsMessageStr = "";
            for (int i = 0; i< sms.length; ++i ){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS FROM:" + address + "\n";
                smsMessageStr += smsBody + "\n";
            }
            Toast.makeText(context,smsMessageStr,Toast.LENGTH_SHORT).show();
            MainActivity isnt = MainActivity.instance();
            //isnt.updateList(smsMessageStr);
        }
    }
}
