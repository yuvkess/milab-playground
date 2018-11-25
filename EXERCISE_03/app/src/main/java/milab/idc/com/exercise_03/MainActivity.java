package milab.idc.com.exercise_03;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        QuoteJobService.startActionInit(this);

        QuoteNotificationService.startActionInit(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            QuoteJobService.startActionInit(this);
//        } else {
//            QuoteNotificationService.startActionInit(this);
//        }
    }
}