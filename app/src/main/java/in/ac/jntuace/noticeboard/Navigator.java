package in.ac.jntuace.noticeboard;


import android.app.Activity;
import android.content.Intent;

import android.os.AsyncTask;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;

import java.net.HttpURLConnection;
import java.net.URL;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import in.ac.jntuace.noticeboard.tasks.PreferenceManager;


public class Navigator extends Activity {

final static int ERR_NO_TOKEN = 0;
 final static int ERR_NO_INTERNET = 1;
    final static int TOKEN_SUCCESS = 2;

CircularProgressBar progressBar;
ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        if(manager.getStatus("setup")) {
            startActivity(new Intent(getApplicationContext(),NoticeBoard.class));
            this.finish();
        }else {
            setContentView(R.layout.activity_navigator);

            progressBar = (CircularProgressBar) findViewById(R.id.nav_progress);
            view = (ImageView) findViewById(R.id.nav_view);
            View mContentView = getWindow().getDecorView();
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

            new ChechConnection().execute();
        }

    }

      class ChechConnection extends AsyncTask<Void,Integer,Integer>{
        @Override
        protected void onPreExecute() {

progressBar.setVisibility(View.VISIBLE);


            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                HttpURLConnection connection = (HttpURLConnection)new URL("http://www.jntuaceasa.com").openConnection();
                connection.connect();
                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    if(!(FirebaseInstanceId.getInstance().getToken()==null)){
                        return TOKEN_SUCCESS;
                    }
                    return ERR_NO_TOKEN;

                }
                return ERR_NO_INTERNET;


                            }catch (Exception e){
                FirebaseCrash.report(e);

                return ERR_NO_INTERNET;

            }


        }

        @Override
        protected void onPostExecute(Integer aBoolean) {
if(aBoolean==TOKEN_SUCCESS){
    //Toast.makeText(getApplicationContext(),FirebaseInstanceId.getInstance().getToken().toString(),Toast.LENGTH_LONG).show();
    progressBar.setVisibility(View.GONE);
    startActivity(new Intent(getApplicationContext(),SetupActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
}else if(aBoolean==ERR_NO_INTERNET){
    Toast.makeText(getApplicationContext(),"You need an active internet connection to complete Setup",Toast.LENGTH_LONG).show();
    finish();
}else if(aBoolean==ERR_NO_TOKEN){
    Toast.makeText(getApplicationContext(),"Bad network connection, Still Loading...",Toast.LENGTH_SHORT).show();
    new ChechConnection().execute();
    }

            super.onPostExecute(aBoolean);
        }

          @Override
          protected void onProgressUpdate(Integer... values) {

              super.onProgressUpdate(values);
          }
      }


}
