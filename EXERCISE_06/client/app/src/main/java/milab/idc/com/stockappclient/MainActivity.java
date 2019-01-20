package milab.idc.com.stockappclient;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    static final String USER_ID = "test";
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://10.0.2.2:3000");
        } catch (URISyntaxException e) {}
    }
    private Emitter.Listener onStockPriceReply = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String price;
                    try {
                        price = data.getString("price");
                    } catch (JSONException e) {
                        return;
                    }
                    setStockPrice(price);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSocket.on("stock price reply", onStockPriceReply);
        mSocket.connect();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (getIntent().getExtras() != null) {
            String stockName = (String)getIntent().getExtras().get("stock");
            String stockPrice = (String)getIntent().getExtras().get("price");
            EditText stockInputField = MainActivity.this.findViewById(R.id.stockName);

            stockInputField.setText(stockName);
            setStockPrice(stockPrice);
            setButtonUnfollow();
            mSocket.emit("stock price request", stockName, USER_ID);
        }
    }

    public void getStockPrice(View view) {
        EditText stockInputField = MainActivity.this.findViewById(R.id.stockName);
        String message = stockInputField.getText().toString();

        if (TextUtils.isEmpty(message)) {
            return;
        }
        setButtonUnfollow();
        mSocket.emit("stock price request", message, USER_ID);

    }

    private void setButtonFollow(){
        Button followButton = MainActivity.this.findViewById(R.id.button);
        Button unfollowButton = MainActivity.this.findViewById(R.id.button2);
        TextView stockPriceView = MainActivity.this.findViewById(R.id.stockPrice);
        unfollowButton.setVisibility(View.INVISIBLE);
        followButton.setVisibility(View.VISIBLE);
        stockPriceView.setVisibility(View.INVISIBLE);
    }

    private void setButtonUnfollow(){
        Button followButton = MainActivity.this.findViewById(R.id.button);
        Button unfollowButton = MainActivity.this.findViewById(R.id.button2);
        followButton.setVisibility(View.INVISIBLE);
        unfollowButton.setVisibility(View.VISIBLE);
    }

    public void stopStockUpdates(View view){
        setButtonFollow();
        mSocket.emit("stop stock price updates", USER_ID);
    }

    private void setStockPrice(String price){
        TextView stockPriceView = MainActivity.this.findViewById(R.id.stockPrice);
        stockPriceView.setText(price);
        stockPriceView.setVisibility(View.VISIBLE);
        Log.i("stock","stock update");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.off("stock price reply", onStockPriceReply);
        mSocket.disconnect();
    }
}
