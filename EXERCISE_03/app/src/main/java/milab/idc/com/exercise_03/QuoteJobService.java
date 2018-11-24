package milab.idc.com.exercise_03;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

public class QuoteJobService extends JobIntentService {
    private static final String ACTION_INIT = "milab.idc.com.exercise_03.action.ACTION_INIT";
    private static final String ACTION_NOTIFY = "milab.idc.com.exercise_03.action.ACTION_NOTIFY";
    private static final String CHANNEL_ID = "NotifyChannel";
    protected int mNotificationId = 1;
    private static String[][] quotes = new String[2][];
    private int[] counters = {0,0};
    private final int[] images = {R.drawable.alyssa, R.drawable.james};
    private static int index = 0;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, JobIntentService.class, 1, work);
    }

    public static void startActionInit(Context context) {
        Intent intent = new Intent(context, QuoteJobService.class);
        intent.setAction(ACTION_INIT);
//        context.startService(intent);
        enqueueWork(context, intent);
    }

    public static void startActionNotify(Context context) {
        Intent intent = new Intent(context, QuoteJobService.class);
        intent.setAction(ACTION_NOTIFY);
//        context.startService(myIntent);
        enqueueWork(context, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                handleActionInit();
            } else if (ACTION_NOTIFY.equals(action)) {
                handleActionNotify();
            }
        }
    }

    private void handleActionInit() {
        quotes[0] = getResources().getStringArray(R.array.alyssa_quotes);
        quotes[1] = getResources().getStringArray(R.array.james_quotes);

        AlarmReceiver br = new AlarmReceiver();
        IntentFilter filter = new IntentFilter(ACTION_NOTIFY);
        getApplicationContext().registerReceiver(br, filter);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction(ACTION_NOTIFY);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),
                (long)60000, alarmIntent);
        createNotificationChannel();
    }

    private void handleActionNotify() {
        String quote;
        int image;

        if(counters[index] == quotes[index].length - 1){
            counters[index] = 0;
        }
        quote = quotes[index][counters[index]];
        image =  images[index];
        counters[index] += counters[index];
        index = 1-index;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("New Quote")
                .setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), image))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(quote));

        notificationManager.notify(mNotificationId++, mBuilder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
