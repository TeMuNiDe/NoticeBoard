package in.ac.jntuace.noticeboard.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;

import android.util.Log;


import java.util.HashMap;
import java.util.Map;

import in.ac.jntuace.noticeboard.FragmentTop;

/**
 * Created by varma on 17-12-2016.
 */

public class SetupTopPagerAdapter extends FragmentStatePagerAdapter {
    Bundle b;
    public Map<Integer, Fragment> fragmentTopMap;

    public SetupTopPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentTopMap = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment returnAble = new FragmentTop();
        b = new Bundle();
        b.putInt("viewToShow", position);
        returnAble.setArguments(b);
        fragmentTopMap.put(position, returnAble);
        return returnAble;
    }

    @Override
    public int getCount() {
        return 2;
    }


    public FragmentTop getFragment(int position) {
return (FragmentTop)fragmentTopMap.get(position);
    }

}