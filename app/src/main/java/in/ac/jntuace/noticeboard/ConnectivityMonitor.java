package in.ac.jntuace.noticeboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import in.ac.jntuace.noticeboard.tasks.SyncManagerService;

public class ConnectivityMonitor extends BroadcastReceiver {
    public ConnectivityMonitor() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, SyncManagerService.class));
    }
}
