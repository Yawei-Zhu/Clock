package com.adreamer.clock.presenter;

import com.adreamer.clock.entity.Alarm;
import com.adreamer.clock.medol.IAlarmMedol;

/**
 * Created by A Dreamer on 2016/8/2.
 */
public interface IAlarmPresenter extends IPresenter {
    void insert(Alarm alarm);
    void delete(Alarm alarm);
    void update(Alarm alarm);
    void query();
}
