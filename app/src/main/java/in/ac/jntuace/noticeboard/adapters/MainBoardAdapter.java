package in.ac.jntuace.noticeboard.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.util.List;

import in.ac.jntuace.noticeboard.DetailedView;
import in.ac.jntuace.noticeboard.NoticeBoard;
import in.ac.jntuace.noticeboard.R;
import in.ac.jntuace.noticeboard.data.BoardItem;
import in.ac.jntuace.noticeboard.data.DataBaseBridge;

/**
 * Created by varma on 16-12-2016.
 */

public class MainBoardAdapter extends RecyclerView.Adapter<MainBoardAdapter.BoardItemHolder> {
    Context context;
  public  List<BoardItem> boardItems;
    BoardItem currentItem;
    Animation fade_in;
    Activity parent;
    onItemClickListener listner;

    public MainBoardAdapter(List<BoardItem> boardItems, Context context, Activity parent) {
        this.context = context;
        this.boardItems = boardItems;
        this.parent = parent;
        listner = (onItemClickListener) parent;
        fade_in = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        fade_in.setInterpolator(new DecelerateInterpolator());
        fade_in.setDuration(500);

    }

    @Override
    public BoardItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new BoardItemHolder(inflater.inflate(R.layout.board_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BoardItemHolder holder, int position) {
        currentItem = boardItems.get(position);
        holder.board_item_title.setText(currentItem.title);
        holder.board_item_date.setText(currentItem.date);
        holder.board_item_description.setText(currentItem.description);
        holder.item = currentItem;
    }

    @Override
    public int getItemCount() {
        //Log.d("Item Count",Integer.toString(boardItems.size()));
        return boardItems.size();
    }

    public class BoardItemHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        TextView board_item_title;
        TextView board_item_date;
        TextView board_item_description;
        BoardItem item;

        public BoardItemHolder(View itemView) {
            super(itemView);
            board_item_title = (TextView) itemView.findViewById(R.id.board_item_title);
            board_item_date = (TextView) itemView.findViewById(R.id.board_item_date);
            board_item_description = (TextView) itemView.findViewById(R.id.board_item_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listner.onClick(item);
        }

    }

    public interface onItemClickListener {
        void onClick(BoardItem item);
    }

}
