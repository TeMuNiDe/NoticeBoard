package in.ac.jntuace.noticeboard.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.health.PackageHealthStats;

/**
 * Created by varma on 15-12-2016.
 */

public class BoardItem  implements Parcelable{
    public String id;
    public String title;
    public String description;
    public String content;
    public String image_link;
    public String date;
    public String status;
    public BoardItem(String id,String title,String description,String content,String image_link,String date,String status){
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.image_link = image_link;
        this.date = date;
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(content);
        dest.writeString(image_link);
        dest.writeString(date);
        dest.writeString(status);



    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
                @Override
        public BoardItem createFromParcel(Parcel source) {
                return new BoardItem(source);
        }

        @Override
        public BoardItem[] newArray(int size) {
            return new BoardItem[size];
        }
    };
    public BoardItem(Parcel in){
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.content = in.readString();
        this.image_link = in.readString();
        this.date = in.readString();
        this.status = in.readString();
    }


    }
