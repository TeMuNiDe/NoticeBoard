package in.ac.jntuace.noticeboard.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by varma on 15-12-2016.
 */

public class DataBaseBridge extends SQLiteOpenHelper {
    static String DB_NAME = "item_list";
    final String TABLE_NAME = "item_list";
    SQLiteDatabase db;
    public DataBaseBridge(Context context) {
        super(context,DB_NAME, null,1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table "+TABLE_NAME+" (id TEXT,title TEXT,description TEXT,content TEXT,image_link TEXT,date TEXT,status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void enQueue(String id){
        try {
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("status", "queued");
            db.insert(TABLE_NAME, null, values);
        }catch (Exception e){
            FirebaseCrash.report(e);
        }
    }
    public void insertItem(List<BoardItem> boardItems){
        try {
            ContentValues values = new ContentValues();
            for (BoardItem item : boardItems) {
                values.clear();
                values.put("title", item.title);
                values.put("description", item.description);
                values.put("content", item.content);
                values.put("image_link", item.image_link);
                values.put("date", item.date);
                values.put("status", "unread");
                db.update(TABLE_NAME, values, " id LIKE ? ", new String[]{item.id});
            }
        }catch (Exception e){
            FirebaseCrash.report(e);
        }
            }

    public void insertNew(List<BoardItem> boardItems){
        try {
            ContentValues values = new ContentValues();
            for (BoardItem item : boardItems) {
                values.clear();
                values.put("id",item.id);
                values.put("title", item.title);
                values.put("description", item.description);
                values.put("content", item.content);
                values.put("image_link", item.image_link);
                values.put("date", item.date);
                values.put("status", "unread");
                db.insert(TABLE_NAME,null, values);
            }
        }catch (Exception e){
            FirebaseCrash.report(e);
        }
    }

    public void deleteItem(String item) {
try {
    db.delete(TABLE_NAME, " id LIKE ? ", new String[]{item});
}catch (Exception e){
    FirebaseCrash.report(e);
}
    }

    public List<String> getQueued(){
        List<String> queued = new ArrayList<String>();
        try {

            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE status LIKE 'queued'", null);

            while (cursor.moveToNext()) {
                queued.add(cursor.getString(0));

            }
            cursor.close();

        }catch (Exception e){
            FirebaseCrash.report(e);
        }
        return queued;
    }
public List<BoardItem> getAll(){

    List<BoardItem> readItems = new ArrayList<BoardItem>();
    List<BoardItem> unreadItems = new ArrayList<>();
    try {
        Cursor cursor1 = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE status LIKE 'read'  ORDER BY date DESC", null);
        while (cursor1.moveToNext()) {
            readItems.add(new BoardItem(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), cursor1.getString(3), cursor1.getString(4), cursor1.getString(5), cursor1.getString(6)));
        }
        cursor1 = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE status LIKE 'unread'  ORDER BY date DESC", null);

        while (cursor1.moveToNext()) {
            unreadItems.add(new BoardItem(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), cursor1.getString(3), cursor1.getString(4), cursor1.getString(5), cursor1.getString(6)));
        }
        readItems.addAll(unreadItems);
        cursor1.close();
    }catch (Exception e){
        FirebaseCrash.report(e);
    }
    return  readItems;
}

}
