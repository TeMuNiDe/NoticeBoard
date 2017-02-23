package in.ac.jntuace.noticeboard;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import in.ac.jntuace.noticeboard.adapters.SetupBottomPagerAdapter;
import in.ac.jntuace.noticeboard.adapters.SetupTopPagerAdapter;
import in.ac.jntuace.noticeboard.data.BoardItem;
import in.ac.jntuace.noticeboard.data.DataBaseBridge;
import in.ac.jntuace.noticeboard.tasks.PreferenceManager;
import in.ac.jntuace.noticeboard.tasks.SetupHelper;
import in.ac.jntuace.noticeboard.tasks.WebHandlerInterface;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SetupActivity extends AppCompatActivity implements FragmentTop.OnTopFragmentClickedListener{
ViewPager topPager,bottomPager;
    PreferenceManager manager;
    SetupTopPagerAdapter topPagerAdapter;
    SetupBottomPagerAdapter bottomPagerAdapter;
    Animation top_down,top_up,bottom_down,bottom_up,fade_in;
    ImageView top_cover,bottom_cover,overlay;
    Button next;
    CircularProgressBar progressBar;
    TextView message_top,message_bottom,text_frame_bottom,final_load;
    SetupHelper setupHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        manager = new PreferenceManager(getApplicationContext());
        progressBar = (CircularProgressBar)findViewById(R.id.cpb);
        setupHelper = new SetupHelper(getApplicationContext());
        next = (Button)findViewById(R.id.next);
        final_load= (TextView)findViewById(R.id.top_loading);
        overlay = (ImageView)findViewById(R.id.overlay);
        top_cover = (ImageView)findViewById(R.id.top_cover);
        bottom_cover = (ImageView)findViewById(R.id.bottom_cover);
        topPager = (ViewPager) findViewById(R.id.setup_frame_top);
        bottomPager = (ViewPager)findViewById(R.id.setup_frame_bottom);
        message_top = (TextView)findViewById(R.id.top_message);
        message_bottom = (TextView)findViewById(R.id.bottom_message);
        text_frame_bottom = (TextView)findViewById(R.id.text_frame_bottom);
        text_frame_bottom.setOnClickListener(staffSelectListener);
        bottomPagerAdapter = new SetupBottomPagerAdapter(getSupportFragmentManager());
        topPagerAdapter = new SetupTopPagerAdapter(getSupportFragmentManager());
        bottomPager.setAdapter(bottomPagerAdapter);
        topPager.setAdapter(topPagerAdapter);
        Interpolator interpolator = new DecelerateInterpolator();
       fade_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
       top_down = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.expand_down);
       bottom_up = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.expand_up);
       top_up = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.contract_up);
        bottom_down = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.contract_down);
        top_down.setDuration(300);
        top_up.setDuration(300);
        bottom_down.setDuration(300);
        bottom_up.setDuration(300);
        fade_in.setDuration(700);
        fade_in.setInterpolator(interpolator);
        bottom_down.setInterpolator(interpolator);
        top_down.setInterpolator(interpolator);
        top_up.setInterpolator(interpolator);
        bottom_up.setInterpolator(interpolator);

    }

    @Override
    public void onTopFragmentClickInteraction(int command) {
       // Log.d("command",Integer.toString(command));
        switch (command){
            case 0:bottom_cover.setVisibility(View.VISIBLE);
                bottom_cover.startAnimation(top_down);
text_frame_bottom.setVisibility(View.GONE);
                              message_bottom.setVisibility(View.VISIBLE);
                message_bottom.startAnimation(bottom_down);
                bottom_down.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
message_bottom.setText("Enter your Admission Number");
                    next.setVisibility(View.VISIBLE);
                    next.startAnimation(fade_in);
                    next.setOnClickListener(nextClickListener);
                    topPager.setCurrentItem(topPager.getCurrentItem()+1,true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

                break;
            case 1:break;
        }
    }


    View.OnClickListener nextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            FragmentTop genFrag = topPagerAdapter.getFragment(1);
            String genID = genFrag.textInputTop.getText().toString();
            if(genID.length()==10){

                finalStep(genID);
            }


        }
    };

    View.OnClickListener staffSelectListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            message_top.setVisibility(View.VISIBLE);
            topPager.setVisibility(View.GONE);
            top_cover.setVisibility(View.VISIBLE);
            bottom_up.setFillAfter(false);
            top_cover.startAnimation(bottom_up);
            message_top.startAnimation(top_up);
            bottom_up.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
message_top.setText("Select Your Department");

                    next.setVisibility(View.VISIBLE);
                    bottomPager.setVisibility(View.VISIBLE);
                    text_frame_bottom.setVisibility(View.GONE);
                    top_cover.setVisibility(View.GONE);
                    bottomPager.setCurrentItem(bottomPager.getCurrentItem()+1,true);
                    next.startAnimation(fade_in);
                    next.setOnClickListener(bottomPassOne);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }
;
    View.OnClickListener bottomPassOne = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
      message_top.setText("Select Position");
            bottomPager.setCurrentItem(bottomPager.getCurrentItem()+1,true);
            setupHelper.bottomPassOne(bottomPagerAdapter);
            next.setOnClickListener(bottomPassTwo);
        }
    };
    View.OnClickListener bottomPassTwo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
String genID = setupHelper.bottomPassTwo(bottomPagerAdapter);
      finalStep(genID);
        }
    };

    public void finalStep(String genID){
new RegisterDevice().execute(genID);
    }
    class RegisterDevice extends AsyncTask<String,Boolean,Boolean>{

        String identity;
        @Override
        protected void onPreExecute() {

            topPager.setVisibility(View.GONE);
            bottomPager.setVisibility(View.GONE);
            bottom_cover.setVisibility(View.GONE);
            message_bottom.setVisibility(View.GONE);
            message_top.setVisibility(View.GONE);
            final_load.setVisibility(View.VISIBLE);
            final_load.startAnimation(fade_in);
            overlay.setVisibility(View.VISIBLE);
            overlay.startAnimation(fade_in);
            next.setVisibility(View.GONE);
           // Toast.makeText(getApplicationContext(),FirebaseInstanceId.getInstance().getToken(),Toast.LENGTH_SHORT).show();
            fade_in.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String ... params) {
identity= params[0];
            DataBaseBridge bridge = new DataBaseBridge(SetupActivity.this);

            WebHandlerInterface handlerInterface = new WebHandlerInterface(bridge);
            boolean setup = handlerInterface.registerDevice(identity);
            ArrayList<BoardItem> freshItems;
            try {
                freshItems = handlerInterface.firstInstance(identity);

            }catch (Exception e){
                FirebaseCrash.report(e);
                return false;
            }
            bridge.insertNew(freshItems);
            return  setup;
                }

        @Override
        protected void onPostExecute(Boolean s) {
            if(!s){
                Toast.makeText(getApplicationContext(),"Setup Cannot Complete without internet connection..!!",Toast.LENGTH_SHORT).show();
                finish();
            }
            else {

              //  Log.d("upload", "complete");
                manager.setStatus("setup", true);
                manager.setIdentity(identity);
                startActivity(new Intent(getApplicationContext(), NoticeBoard.class).putExtra("first_instance",true));
            }
            super.onPostExecute(s);
        }
    }
}
