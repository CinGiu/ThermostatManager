package giulio.cinelli.vimar_termostato;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import giulio.cinelli.vimar_termostato.Utils.Constants;

/**
 * Created by giulio on 04/12/16.
 */

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {
            /* Get Messages */
            Object[] sms = (Object[]) intentExtras.get("pdus");

            for (int i = 0; i < sms.length; ++i) {
                /* Parse Each Message */
                SmsMessage smsMessage;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                     smsMessage = SmsMessage.createFromPdu((byte[]) sms[i],intentExtras.getString("format"));

                }else{
                     smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
                }

                String phone = smsMessage.getOriginatingAddress();

                if(phone.equals(Constants.phoneNo)){
                    String message = smsMessage.getMessageBody();
                    String msg = message.replace("GMS-01913 1/1\r\nC1\r\n","");
                    MainActivity inst = MainActivity.instance();
                    inst.showMessage(msg);
                }
            }
        }
    }
}