package in.ac.jntuace.noticeboard.adapters;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.ac.jntuace.noticeboard.DetailImage;

/**
 * Created by varma on 26-12-2016.
 */

public class DetailSlideAdapter extends FragmentStatePagerAdapter {
    String[] images;
    Fragment returnable;
    public void setImages(String[] images){
        this.images = images;
    }
    public DetailSlideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        returnable = new DetailImage();
        Bundle b= new Bundle();
        b.putString("url",images[position]);
        b.putInt("index",position);
        returnable.setArguments(b);
        return returnable;
    }

    @Override
    public int getCount() {
       return images.length;
    }
}
