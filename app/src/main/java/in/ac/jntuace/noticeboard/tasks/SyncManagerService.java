package in.ac.jntuace.noticeboard.tasks;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.ac.jntuace.noticeboard.DetailedView;
import in.ac.jntuace.noticeboard.NoticeBoard;
import in.ac.jntuace.noticeboard.R;
import in.ac.jntuace.noticeboard.data.BoardItem;
import in.ac.jntuace.noticeboard.data.DataBaseBridge;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SyncManagerService extends IntentService {
    DataBaseBridge bridge;

    public SyncManagerService() {
        super("SyncManagerService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            bridge = new DataBaseBridge(getApplicationContext());
            WebHandlerInterface handlerInterface = new WebHandlerInterface(bridge);
            List<BoardItem> newItems = handlerInterface.sync();
            bridge.insertItem(newItems);
            int count = 0;



            for(BoardItem item:newItems) {
                sendBroadcast(new Intent(NoticeBoard.ACTION_MESSAGE).putExtra("boarditem",item));
                Intent launcher = new Intent(getApplicationContext(), DetailedView.class);
                launcher.putExtra("board_item",item);
                TaskStackBuilder builder = TaskStackBuilder.create(this);
                builder.addParentStack(DetailedView.class);
                builder.addNextIntent(launcher);
                PendingIntent pendingIntent = builder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle(item.title)
                        .setContentText(item.description)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .build();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).notify(count++, notification);
            }
        }catch (Exception e){
            FirebaseCrash.report(e);
        }
        }


    }



