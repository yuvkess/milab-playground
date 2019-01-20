package milab.idc.com.stockappclient;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private  static final String TAG = "MyFireBaseService";
    private static final String SERVER_ADDRESS = "http://10.0.2.2:3000/";
    private RequestQueue _queue;

    @Override
    public void onCreate() {
        super.onCreate();
        _queue = Volley.newRequestQueue(this);
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "Message received: ");
    }

    private void sendRegistrationToServer(String token) {
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("token", token);
        }
        catch (JSONException e) {}

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, SERVER_ADDRESS + MainActivity.USER_ID + "/token", requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Token saved successfully");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Failed to save token - " + error);
                    }
                });

        _queue.add(req);
    }


}
