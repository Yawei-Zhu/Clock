package com.adreamer.clock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Yawei-Zhu
 * Created by A Dreamer on 2016/8/3.
 */
public abstract class BaseAdapter<E, H extends BaseAdapter.BaseViewHolder> extends  RecyclerView.Adapter<H>  {

    private List<E> mData;
    private RecyclerView mParent;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public BaseAdapter(List<E> data) {
        mData = data;
    }

    /**
     * 通过复写该方法获取父控件
     * @param recyclerView
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mParent = recyclerView;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    /**
     *
     * @return adapter中data的数量（int）
     */
    @Override
    public final int getItemCount() {
        return mData.size();
    }

    public final RecyclerView getParent() {
        return mParent;
    }

    public final List<E> getData() {
        return mData;
    }

    public final void setData(List<E> data) {
        mData = data;
    }

    public final void setOnItemClickListener(OnItemClickListener l) {
        mItemClickListener = l;
    }

    public final void setOnItemLongClickListener(OnItemLongClickListener l) {
        mItemLongClickListener = l;
    }

    public interface OnItemClickListener {
        void onClick(RecyclerView parent, View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onLongClick(RecyclerView parent, View view, int position);
    }


    private View.OnClickListener onItemViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mItemClickListener != null) {
                mItemClickListener.onClick(mParent, view, ((BaseViewHolder)view.getTag()).getAdapterPosition());
            }
        }
    };

    private View.OnLongClickListener onItemViewLongClickListener =  new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if(mItemLongClickListener != null) {
                return mItemLongClickListener
                        .onLongClick(mParent, view, ((BaseViewHolder)view.getTag()).getAdapterPosition());
            }
            return false;
        }
    };

    public class BaseViewHolder extends  RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);

            itemView.setTag(this);
            itemView.setOnClickListener(onItemViewClickListener);
            itemView.setOnLongClickListener(onItemViewLongClickListener);
        }

    }
}
