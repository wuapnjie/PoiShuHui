package com.flying.xiaopo.shader.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.flying.xiaopo.shader.Activity.AddTipActivity;
import com.flying.xiaopo.shader.Bean.TipBean;
import com.flying.xiaopo.shader.ClockDBHelper;
import com.flying.xiaopo.shader.R;
import com.flying.xiaopo.shader.Reciver.TipsReciver;
import com.flying.xiaopo.shader.View.CellTip;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2015/6/6.
 */
public class TipsRecyclerAdapter extends RecyclerView.Adapter<TipsRecyclerAdapter.RemindsVieHolder> implements View.OnTouchListener, CellTip.OnTipClickListener {
    private Context context;

    private int size;
    private List<TipBean> mDatas;
    public OnItemClickListener onItemClickListener;
    public OnItemTouchListener onItemTouchListener;
    public OnDelClickListener onDelClickListener;

    private int[] hopes_id = new int[]{R.string.hope1,R.string.hope2,R.string.hope3,R.string.hope4,R.string.hope5,R.string.hope6,R.string.hope7,R.string.hope8,R.string.hope9,R.string.hope10,R.string.hope11,R.string.hope12};
//    public OnHideViewClickListener onHideViewClickListener;

    public TipsRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void obtainData(List<TipBean> datas) {
        this.mDatas = datas;
        size = datas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }

    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        this.onDelClickListener = onDelClickListener;
    }

    @Override
    public RemindsVieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_tip, parent, false);
        return new RemindsVieHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RemindsVieHolder holder, int position) {
        bindAnimate(holder, position);
        bindContent(holder, position);
        bindListener(holder, position);
    }

    private void bindListener(final RemindsVieHolder holder, final int pos) {
        final int position = holder.getPosition();
        holder.cell_container.setTag(holder.getID());
        holder.tv_delete.setTag(holder.getID());
        holder.cell_container.setOnTipClickListener(this);
        holder.cell_container.setOnTouchListener(this);
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDelClickListener!=null) onDelClickListener.onDelClick(v,position);
            }
        });
    }

    @Override
    public void onTipClick(View v, MotionEvent event) {
        onItemClickListener.onItemClick(v, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        onItemTouchListener.onItemTouch(v, event);
        return false;
    }

//    @Override
//    public void onHideViewClick(View v) {
//        onHideViewClickListener.onHideViewClick(v);
//    }

    private void bindContent(RemindsVieHolder holder, int pos) {
        int position = holder.getPosition();
        TipBean bean = mDatas.get(position);
        holder.setID(bean.getId());
        holder.tv_previous.setText(bean.getTitle().substring(0, 1));
        holder.tv_event.setText(bean.getTitle());
//        Log.d(AddTipActivity.TAG, bean.getTitle());
        holder.tv_time.setText(bean.getDate() + "\t" + bean.getTime());
        holder.tv_repeat.setText(hopes_id[position%12]);
//        Log.d(AddTipActivity.TAG, bean.getDate() + "\t" + bean.getTime());
//        Log.d(AddTipActivity.TAG, "ID=" + bean.getId());
    }

    private void bindAnimate(RemindsVieHolder holder, int pos) {
        int position = holder.getPosition();
        holder.itemView.setTranslationX(-500);
        holder.itemView.animate().translationX(0f).alpha(1f).setDuration(600).setInterpolator(new AccelerateInterpolator()).setStartDelay(position * 30).start();
    }

    public void removeItem(int ID, int posotion) {
        new TipsReciver().cancelAlarm(context, ID);
        new ClockDBHelper(context).deleteReminder(ID);
        notifyItemRemoved(posotion);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public interface OnDelClickListener {
        void onDelClick(View view,int pos);
    }

    public interface OnItemClickListener {
        void onItemClick(View v, MotionEvent event);
    }

    public interface OnItemTouchListener {
        void onItemTouch(View v, MotionEvent event);
    }


    public static class RemindsVieHolder extends RecyclerView.ViewHolder {
        int ID;
        @InjectView(R.id.cell_container)
        CellTip cell_container;
        @InjectView(R.id.tv_previous)
        TextView tv_previous;
        @InjectView(R.id.tv_event)
        TextView tv_event;
        @InjectView(R.id.tv_time)
        TextView tv_time;
        @InjectView(R.id.tv_repeat)
        TextView tv_repeat;
        @InjectView(R.id.tv_delete)
        ImageButton tv_delete;

        public RemindsVieHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getID() {
            return ID;
        }
    }
}
