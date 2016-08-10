package com.adreamer.clock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.adreamer.clock.R;
import com.adreamer.clock.entity.Alarm;
import com.adreamer.clock.util.FormatUtils;

import java.util.List;

/**
 * Created by A Dreamer on 2016/8/4.
 */
public class AlarmAdapter extends BaseAdapter<Alarm, AlarmAdapter.ViewHolder> {


    private OnSwitchCheckedChangedListener mSwitchCheckedChangedListener;
    private CompoundButton.OnCheckedChangeListener mCheckedChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(mSwitchCheckedChangedListener != null) {
                mSwitchCheckedChangedListener.onCheckedChange(getParent(),
                        (Switch)compoundButton, (Alarm)compoundButton.getTag(), b);
            }
        }
    };

    public AlarmAdapter(List<Alarm> mData) {
        super(mData);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_alarm, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alarm alarm = getData().get(position);
        holder.mItemView.setTag(position);
        holder.mTimeTv.setText(FormatUtils.format(alarm.getTime()));
        holder.mRepeatedTv.setText(alarm.isRepeated() ? "每天" : "一次");
        holder.mEnableSwitch.setChecked(alarm.isEnable());
        holder.mEnableSwitch.setTag(alarm);
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {

        public View mItemView;
        public TextView mTimeTv;
        public TextView mRepeatedTv;
        public Switch mEnableSwitch;

        public ViewHolder(View itemView) {
            super(itemView);

            mItemView = itemView;
            mTimeTv = (TextView) itemView.findViewById(R.id.tv_alarm_time);
            mRepeatedTv = (TextView) itemView.findViewById(R.id.tv_alarm_repeated);
            mEnableSwitch = (Switch) itemView.findViewById(R.id.sw_alarm_enable);
            mEnableSwitch.setOnCheckedChangeListener(mCheckedChangedListener);
        }
    }

    public void setOnSwitchCheckedChangedListener(OnSwitchCheckedChangedListener l) {
        mSwitchCheckedChangedListener = l;
    }

    public interface OnSwitchCheckedChangedListener {
        void onCheckedChange(RecyclerView parent, Switch view, Alarm item, boolean isChecked);
    }
}
