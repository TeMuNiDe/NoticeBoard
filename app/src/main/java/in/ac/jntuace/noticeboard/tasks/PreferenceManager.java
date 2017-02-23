package in.ac.jntuace.noticeboard.tasks;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by varma on 25-12-2016.
 */

public class PreferenceManager {
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public PreferenceManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("userpreferences",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean getStatus(String key){


        return preferences.getBoolean(key,false);
    }
    public String getIdentity() {
        return preferences.getString("identity",null);
    }
    public void setStatus(String key,boolean value){

        editor.putBoolean(key,value);
        editor.commit();
    }
    public void setIdentity(String value){
        editor.putString("identity",value);
        editor.commit();
    }
    public void clear(){
        editor.clear();
        editor.commit();
    }

}
