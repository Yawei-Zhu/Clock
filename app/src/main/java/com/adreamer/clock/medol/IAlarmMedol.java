package com.adreamer.clock.medol;

import com.adreamer.clock.entity.Alarm;

import java.util.List;

/**
 * Created by A Dreamer on 2016/8/2.
 */
public interface IAlarmMedol extends IMedol {
    long insert(Alarm alarm);
    int delete(Alarm alarm);
    int update(Alarm alarm);
    List<Alarm> query(String selection, String[] selectionArgs);
}
