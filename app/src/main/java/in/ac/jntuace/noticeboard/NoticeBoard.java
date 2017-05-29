package in.ac.jntuace.noticeboard;

import android.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.ac.jntuace.noticeboard.adapters.MainBoardAdapter;
import in.ac.jntuace.noticeboard.data.BoardItem;
import in.ac.jntuace.noticeboard.data.DataBaseBridge;
import in.ac.jntuace.noticeboard.tasks.PreferenceManager;
import in.ac.jntuace.noticeboard.tasks.SyncManagerService;
import in.ac.jntuace.noticeboard.tasks.WebHandlerInterface;

import static in.ac.jntuace.noticeboard.R.styleable.ForegroundLinearLayout;
import static in.ac.jntuace.noticeboard.R.styleable.RecyclerView;

public class NoticeBoard extends AppCompatActivity implements MainBoardAdapter.onItemClickListener{
    DataBaseBridge bridge;
    public   static String ACTION_MESSAGE = "MESSAGE_RECEIVED";
    List<BoardItem> boardItemsall;
    SwipeRefreshLayout layout;
    UpdateInterface updateInterface;
    RecyclerView mainBoardList;
    MainBoardAdapter adapter;
    TextView done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

done = (TextView)findViewById(R.id.done_for_today) ;


        bridge = new DataBaseBridge(getApplicationContext());
        boardItemsall = bridge.getAll();

      //  Log.d("items",Integer.toString(boardItemsall.size()));
     mainBoardList = (RecyclerView) findViewById(R.id.main_board_list);
        if(boardItemsall.size()==0){
            done.setVisibility(View.VISIBLE);
            mainBoardList.setVisibility(View.GONE);
        }
        adapter = new MainBoardAdapter(boardItemsall,getApplicationContext(),this);
        mainBoardList.setAdapter(adapter);
        mainBoardList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        layout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        layout.setColorSchemeResources(R.color.gplus_color_1, R.color.gplus_color_2, R.color.gplus_color_3, R.color.gplus_color_4);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.d("execute","start");
                new Refresh().execute();
            }
        }) ;




        AlertDialog.Builder builder = new AlertDialog.Builder(NoticeBoard.this);
        builder.setMessage("New Update Available").setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("market://details?id="+getPackageName()));
                startActivity(i);
            }
        }).setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
dialog.cancel();
            }
        });


        AlertDialog dialog = builder.create();
        if(new PreferenceManager(getApplicationContext()).getStatus("update")){
            dialog.show();
        }

    }

    @Override
    public void onClick(BoardItem item) {


        startActivity(new Intent(getApplicationContext(),DetailedView.class).putExtra("board_item",item));
    }

    class Refresh extends AsyncTask<Void,List<BoardItem>,List<BoardItem>>{

        @Override
        protected List<BoardItem> doInBackground(Void... params) {

return new WebHandlerInterface(bridge).sync();
        }

        @Override
        protected void onPreExecute() {
            layout.setRefreshing(true);
                  }

        @Override
        protected void onPostExecute(List<BoardItem> boardItems) {
            boardItemsall.addAll(boardItems);
            adapter.boardItems = boardItemsall;
            adapter.notifyDataSetChanged();
            bridge.insertItem(boardItems);

            layout.setRefreshing(false);
            if(boardItemsall.size()!=0){
                done.setVisibility(View.GONE);
                mainBoardList.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_board_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
switch (item.getItemId()) {

    case R.id.rate_us: Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("market://details?id="+getPackageName()));
        startActivity(i);break;
    case R.id.mail_us:Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto","jntuaceasa@gmail.com",null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Notice Board App - reg");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "write your content here");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
        break;

}

        return super.onOptionsItemSelected(item);
    }
    class UpdateInterface extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            adapter.boardItems.add(0,(BoardItem)intent.getParcelableExtra("boarditem"));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        if(updateInterface!=null){
            unregisterReceiver(updateInterface);
        }
        super.onPause();

    }

    @Override
    protected void onResume() {

        if(updateInterface==null){
            updateInterface = new UpdateInterface();

        }
        IntentFilter filter = new IntentFilter(ACTION_MESSAGE);
        registerReceiver(updateInterface,filter);
        super.onResume();
    }
}
