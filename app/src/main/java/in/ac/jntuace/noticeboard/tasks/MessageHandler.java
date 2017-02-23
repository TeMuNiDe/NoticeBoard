package in.ac.jntuace.noticeboard.tasks;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import in.ac.jntuace.noticeboard.data.DataBaseBridge;

public class MessageHandler extends FirebaseMessagingService{
    int currentVersion;
    DataBaseBridge bridge;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String,String> data  = remoteMessage.getData();
        bridge = new DataBaseBridge(getApplicationContext());
        Log.d("message reccieved",data.get("operation"));
switch (data.get("operation")){
    case "insert":insertRequest(data.get("id"));break;
    case "delete":deleteRequest(data.get("id"));break;
    case "update":setLatestVersion(data.get("version"));break;
}

    }
    public void insertRequest(String id){
bridge.enQueue(id);
        startService(new Intent(getApplicationContext(),SyncManagerService.class));
    }
    public void deleteRequest(String id){
bridge.deleteItem(id);
    }
    public void setLatestVersion(String version){

        try {
            currentVersion=getPackageManager().getPackageInfo(getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        if(Integer.parseInt(version)>currentVersion){
            new PreferenceManager(getApplicationContext()).setStatus("update",true);
        }



    }
}
