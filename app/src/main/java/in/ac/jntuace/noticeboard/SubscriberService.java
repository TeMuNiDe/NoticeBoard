package in.ac.jntuace.noticeboard;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import in.ac.jntuace.noticeboard.tasks.PreferenceManager;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SubscriberService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        super.onTokenRefresh();
        if(new PreferenceManager(this).getStatus("setup")) {
            RequestBody postBody = new FormBody.Builder()
                    .addEncoded("fcm_token", FirebaseInstanceId.getInstance().getToken())
                    .addEncoded("identity", new PreferenceManager(getApplicationContext()).getIdentity()).build();
            HttpUrl url = new HttpUrl.Builder().scheme("http").host("www.jntuaceasa.com").addPathSegment("register_device.php").build();
            Request request = new Request.Builder().url(url).post(postBody).build();
            OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS)
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true).build();
            try {
                Response response = client.newCall(request).execute();
                String responses = response.body().string();
                Log.d("response", responses);
                if (responses.equals("1")) {
                }
            } catch (Exception e) {
                if (e instanceof UnknownHostException) {
                    e.printStackTrace();
                } else if (e instanceof IOException) {
                    e.printStackTrace();
                } else {
                    e.printStackTrace();
                }

            }
        }
    }
}
