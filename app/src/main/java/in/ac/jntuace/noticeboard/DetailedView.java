package in.ac.jntuace.noticeboard;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.media.audiofx.EnvironmentalReverb;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import in.ac.jntuace.noticeboard.adapters.DetailSlideAdapter;
import in.ac.jntuace.noticeboard.adapters.MainBoardAdapter;
import in.ac.jntuace.noticeboard.data.BoardItem;
import me.relex.circleindicator.CircleIndicator;

import static android.content.Intent.ACTION_VIEW;

public class DetailedView extends AppCompatActivity implements DetailImage.OnImageClick {
ViewPager detail_slides;
    CircleIndicator indicator;
    BoardItem item;
    File imag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},0);

            }

        }
        try {
            indicator = (CircleIndicator) findViewById(R.id.indicator);
            WebView Webview = (WebView) findViewById(R.id.webView);
            detail_slides = (ViewPager) findViewById(R.id.activity_detail_slides);
            BoardItem item = getIntent().getExtras().getParcelable("board_item");
            this.item = item;
            setTitle(item.title);
            String[] slides = item.image_link.split("\n");
            if (!slides[0].equals("http://www.jntuaceasa.com/")) {
                indicator.setVisibility(View.VISIBLE);
                detail_slides.setVisibility(View.VISIBLE);
                DetailSlideAdapter adapter = new DetailSlideAdapter(getSupportFragmentManager());
                adapter.setImages(slides);
                detail_slides.setAdapter(adapter);
                indicator.setViewPager(detail_slides);
                adapter.registerDataSetObserver(indicator.getDataSetObserver());
            }
            //  Toast.makeText(getApplicationContext(),Integer.toString(slides.length),Toast.LENGTH_LONG).show();
            String content = getResources().getString(R.string.header_html) + item.content.replace("\\", "") + "</body></html>";
           // Log.d("html", content);
            Webview.getSettings().setJavaScriptEnabled(true);
            Webview.getSettings().setUseWideViewPort(true);
            Webview.getSettings().setBuiltInZoomControls(true);
            Webview.getSettings().setDisplayZoomControls(true);
            Webview.setInitialScale(100);
            Webview.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "UTF-8", null);

        }catch (Exception e){
            FirebaseCrash.report(e);
        }
    }


    @Override
    public void onImageCLick(final Bitmap image, int index) {


        String directory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/JNTUACEA";
        File folder = new File(directory);
        try {
            if (!folder.exists()) {
                folder.mkdirs();
            }
            imag = new File(directory+"/" + item.title + item.date + Integer.toString(index)+".jpeg");
            if(!imag.exists()) {
                OutputStream stream = new FileOutputStream(imag);
                image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.flush();
                stream.close();
            }

            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{imag.getAbsolutePath()}, new String[]{"image/*"} , new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    Intent view = new Intent(ACTION_VIEW);
//Log.d("filepath",imag.toString()+imag.getAbsolutePath());
                    view.setDataAndType(Uri.fromFile(imag),"image/*");
                    startActivity(view);
                }
            });



        }catch (Exception e){
            if(e instanceof FileNotFoundException){

                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},0);

                    }

                }
            }
            else {
                e.printStackTrace();
                FirebaseCrash.report(e);
            }

        }
    }
}
