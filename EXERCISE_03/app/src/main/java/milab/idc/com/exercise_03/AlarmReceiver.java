package milab.idc.com.exercise_03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        QuoteNotificationService.startActionNotify(context);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            QuoteJobService.startActionNotify(context);
//        } else {
//            QuoteNotificationService.startActionNotify(context);
//        }
    }
}
