package example.herve.com.magicnote.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import example.herve.com.magicnote.R;
import example.herve.com.magicnote.utils.Ui;

/**
 * Created           :Herve on 2017/2/22.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2017/2/22
 * @ projectName     :MagicNote
 * @ version
 */
public class RoadAdapter extends RecyclerView.Adapter<RoadAdapter.RoadViewHolder> {

    private ArrayList<Integer> data;
    private Context mContext;

    public RoadAdapter(ArrayList<Integer> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public RoadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);

        return new RoadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoadViewHolder holder, int position) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();

        if (position == 0) {
            holder.itemView.setTag("B");
            holder.itemView.setBackgroundColor(Color.BLACK);
            marginLayoutParams.width = Ui.dp2px(mContext, (float) (150));
        } else {
            int random = (int) (Math.random() * 100);
            if (random > 80) {
                holder.itemView.setTag("W");
                holder.itemView.setBackgroundColor(Color.WHITE);
                marginLayoutParams.width = Ui.dp2px(mContext, (float) (30 + Math.random() * 50));
            } else {
                holder.itemView.setTag("B");
                holder.itemView.setBackgroundColor(Color.BLACK);
                marginLayoutParams.width = Ui.dp2px(mContext, (float) (50 + Math.random() * 30));
            }
        }

        holder.itemView.setLayoutParams(marginLayoutParams);

    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    static class RoadViewHolder extends RecyclerView.ViewHolder {

        public RoadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
