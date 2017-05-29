package in.ac.jntuace.noticeboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;


public class DetailImage extends Fragment {
    CircularProgressBar progressBar;
      public DetailImage() {

    }
    OnImageClick listener;
    Bitmap image;
    View contentView;
    ImageView imageView;
    String url;
    TextView progress;
int index;
    @Override
    public void onAttach(Activity activity) {
        listener = (OnImageClick)activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         contentView = inflater.inflate(R.layout.fragment_detail_image, container, false);
        progressBar = (CircularProgressBar) contentView.findViewById(R.id.image_loader);
        imageView  = (ImageView) contentView.findViewById(R.id.detail_image);
    progress = (TextView)contentView.findViewById(R.id.progress);
     url  = getArguments().getString("url");
        index = getArguments().getInt("index");
        final ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getContext()).build();
        ImageLoader.getInstance().init(configuration);
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).build();
        ImageLoader.getInstance().displayImage(url, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                progressBar.setVisibility(View.VISIBLE);

            }


            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Looks Like some thing has gone wrong", Toast.LENGTH_SHORT).show();
                FirebaseCrash.report(new Throwable("image loading failed::" + url.toString()));


            }

            @Override
            public void onLoadingComplete(String s, View view, final Bitmap bitmap) {
                image = bitmap;
                progressBar.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onImageCLick(image, index);
                    }
                });

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int i, int i1) {
                long percent = 100*i/i1;
                Log.d("update",Integer.toString(i)+"::"+Integer.toString(i1)+"::"+Long.toString(percent));

               progress.setText(Long.toString(percent)+"%");
            }
        });
        return contentView;
    }
    interface OnImageClick{
        void onImageCLick(Bitmap image,int index);
    }

}
