package com.huasport.smartsport.ui.discover.view;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.huasport.smartsport.ui.discover.adapter.ReleaseMsgAdapter;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ReleaseMsgAdapter msgAdapter;
    private int dragFlags;
    private int swipeFlags = 0;

    public SimpleItemTouchHelperCallback(ReleaseMsgAdapter msgAdapter) {
        this.msgAdapter = msgAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getAdapterPosition() == msgAdapter.mList.size() - 1 || target.getAdapterPosition() == msgAdapter.mList.size() - 1) {
            return false;
        } else {
            msgAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    /**
     * 选中Item状态
     *
     * @param viewHolder
     * @param actionState Item状态
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * Item是否支持长按拖动
     *
     * @return true  支持长按操作
     * false 不支持长按操作
     */
    @Override
    public boolean isLongPressDragEnabled() {

        return msgAdapter.dragState();
    }

}
