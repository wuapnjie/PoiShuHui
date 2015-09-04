package com.flying.xiaopo.poishuhui.Adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.flying.xiaopo.poishuhui.Beans.ItemBean;
import com.flying.xiaopo.poishuhui.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 瀑布流的adapter
 * Created by lenovo on 2015/8/19.
 */
public class StaggeredAdapter extends GridAdapter {
    List<Integer> heights;

    public StaggeredAdapter(Context context) {
        super(context);
        heights = new ArrayList<>();
    }

    @Override
    public void obtainData(List<ItemBean> data) {
        super.obtainData(data);
        if (data==null) return;
        getRandomHeights(heights);
    }

    private void getRandomHeights(List<Integer> heights) {
        for (int i = 0; i < super.mData.size(); i++) {
            heights.add((int) (Utils.dp2px(200) + Math.random() * Utils.dp2px(100)));
        }
    }


    @Override
    public void onBindViewHolder(CellViewHolder holder, int position) {
        if (mData.size() != 0) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = heights.get(position);
        }
        super.onBindViewHolder(holder, position);
    }
}
