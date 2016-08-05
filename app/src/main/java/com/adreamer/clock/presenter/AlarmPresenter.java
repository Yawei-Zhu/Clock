package com.adreamer.clock.presenter;

import com.adreamer.clock.app.AlarmApplication;
import com.adreamer.clock.entity.Alarm;
import com.adreamer.clock.medol.AlarmMedol;
import com.adreamer.clock.medol.IAlarmMedol;
import com.adreamer.clock.view.IAlarmView;

import java.util.List;

/**
 * Created by A Dreamer on 2016/8/2.
 */
public class AlarmPresenter implements IAlarmPresenter {

    private IAlarmView mView;
    private IAlarmMedol mMedol;

    public AlarmPresenter(IAlarmView view) {
        mView = view;
        mMedol = new AlarmMedol(AlarmApplication.getApp());
    }

    @Override
    public void insert(Alarm alarm) {
        long id = mMedol.insert(alarm);
        if(id >= 0) {
            alarm.setId(id);
            mView.refreshData();
        }
    }

    @Override
    public void delete(Alarm alarm) {
        int col = mMedol.delete(alarm);
        if(col > 0) {
            mView.refreshData();
        }
    }

    @Override
    public void update(Alarm alarm) {
        int col = mMedol.update(alarm);
        if(col > 0) {
            mView.refreshData();
        }
    }

    @Override
    public void query() {
        List<Alarm> alarms = mMedol.query(null, null);
        mView.setData(alarms);
    }
}
