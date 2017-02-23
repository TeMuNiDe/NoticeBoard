package in.ac.jntuace.noticeboard.tasks;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.ac.jntuace.noticeboard.data.BoardItem;
import in.ac.jntuace.noticeboard.data.DataBaseBridge;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by varma on 23-12-2016.
 */

public class WebHandlerInterface {
    DataBaseBridge bridge;
    Context context;
    Request request;
    Response response;
    JSONObject jsonObject;
    FormBody.Builder form;
    List<String> queuedIds;
    public WebHandlerInterface(DataBaseBridge bridge){
        this.bridge = bridge;
    }


    public ArrayList<BoardItem> sync() {

        this.queuedIds = bridge.getQueued();

        List<BoardItem> remoteBoardItems = new ArrayList<>();
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();

        for (String id : queuedIds) {
            Log.d("ID Requedst", id);
            form = new FormBody.Builder().add("id", id);
         //   Log.d("formdata", form.toString());
            request = new Request.Builder().url("http://www.jntuaceasa.com/get_item.php").post(form.build()).build();
            try {
                response = client.newCall(request).execute();
                String Response = response.body().string();
               // Log.d("Response", Response);
                JSONArray array = new JSONArray(Response);
                jsonObject = array.getJSONObject(0);
                remoteBoardItems.add(new BoardItem(jsonObject.getString("id"), jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("content"), jsonObject.getString("image_link"), jsonObject.getString("date"), "read"));
            } catch (Exception e) {
                e.printStackTrace();
              //  Log.d("exception", e.toString());

            }
        }
        return (ArrayList)remoteBoardItems;

    }
    public ArrayList<BoardItem> firstInstance(String identity) throws Exception{
        List<BoardItem> remoteBoardItems = new ArrayList<>();
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();
            form = new FormBody.Builder().add("identity",identity);
            request = new Request.Builder().url("http://www.jntuaceasa.com/first_instance.php").post(form.build()).build();
            try {
                response = client.newCall(request).execute();
                String Response = response.body().string();
                JSONArray array = new JSONArray(Response);
                for(int i = 0;i<array.length();i++) {
                    jsonObject = array.getJSONObject(i);
                   // Log.d("add","yes");
                    remoteBoardItems.add(new BoardItem(jsonObject.getString("id"), jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("content"), jsonObject.getString("image_link"), jsonObject.getString("date"), "unread"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("exception", e.toString());
                if(e instanceof JSONException){
e.printStackTrace();
                }
                else if(e instanceof UnknownHostException){
                    e.printStackTrace();
                }
                else {
                    FirebaseCrash.report(e);
                    throw e;
                }
            }
//Log.d("returning",Integer.toString(remoteBoardItems.size()));
        return (ArrayList)remoteBoardItems;
    }
    public boolean registerDevice(String identity){
        RequestBody postBody = new FormBody.Builder()
                .addEncoded("fcm_token", FirebaseInstanceId.getInstance().getToken())
                .addEncoded("identity",identity).build();
        HttpUrl url = new HttpUrl.Builder().scheme("http").host("www.jntuaceasa.com").addPathSegment("register_device.php").build();
        Request request = new Request.Builder().url(url).post(postBody).build();
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();

        try {

            Response response = client.newCall(request).execute();
            String responses = response.body().string();
           // Log.d("response",responses);
            if(responses.equals("1")){
                return true;

            }


        }catch (Exception e){
            if(e instanceof UnknownHostException){
                e.printStackTrace();
            }else if(e instanceof IOException){
                e.printStackTrace();
                FirebaseCrash.report(e);
            }
            else{
                FirebaseCrash.report(e);
                e.printStackTrace();
            }
            return  false;
        }

        return false;



    }
}
