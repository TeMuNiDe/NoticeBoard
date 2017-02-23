package in.ac.jntuace.noticeboard.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Map;

import in.ac.jntuace.noticeboard.BottomFragment;

/**
 * Created by varma on 17-12-2016.
 */

public class SetupBottomPagerAdapter extends FragmentStatePagerAdapter {

    Bundle b;
    Map<Integer,Fragment> fragmentMap;
    public SetupBottomPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentMap = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment returnable = new BottomFragment();
        b = new Bundle();
        b.putInt("viewToShow",position);
        returnable.setArguments(b);
        fragmentMap.put(position,returnable);
        return returnable;
    }

    @Override
    public int getCount() {
        return 3;
    }
    public BottomFragment getFragment(int position){
        return (BottomFragment)fragmentMap.get(position);
    }
}
