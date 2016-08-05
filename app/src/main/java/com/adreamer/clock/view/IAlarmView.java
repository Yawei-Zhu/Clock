package com.adreamer.clock.view;

import com.adreamer.clock.entity.Alarm;

import java.util.List;

/**
 * Created by A Dreamer on 2016/8/2.
 */
public interface IAlarmView extends IView {
    void setData(List<Alarm> alarms);
    void refreshData();
}
