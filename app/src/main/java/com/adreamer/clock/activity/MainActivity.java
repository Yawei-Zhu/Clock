package com.adreamer.clock.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TimePicker;

import com.adreamer.clock.R;
import com.adreamer.clock.adapter.BaseAdapter;
import com.adreamer.clock.adapter.AlarmAdapter;
import com.adreamer.clock.entity.Alarm;
import com.adreamer.clock.presenter.AlarmPresenter;
import com.adreamer.clock.presenter.IAlarmPresenter;
import com.adreamer.clock.util.FormatUtils;
import com.adreamer.clock.util.ItemDivider;
import com.adreamer.clock.view.IAlarmView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IAlarmView {

    private RecyclerView mAlarmRv;
    private Button mAddButton;
    private AlarmAdapter mAdapter;
    private IAlarmPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mAlarmRv = (RecyclerView) findViewById(R.id.rv_alarm);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mAlarmRv.setLayoutManager(layoutManager);
        mAlarmRv.addItemDecoration(new ItemDivider(this, LinearLayout.VERTICAL));

        mAddButton = (Button) findViewById(R.id.bt_add);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlarmDialog(null);
            }
        });

        presenter = new AlarmPresenter(this);
        presenter.query();
        new ListView(this).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });
    }

    private void showAlarmDialog(Alarm alarm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alarmDialog = builder.create();
        alarmDialog.show();
        Window window = alarmDialog.getWindow();

        View alarmView = View.inflate(MainActivity.this, R.layout.dialog_alarm, null);
        final TimePicker alarmTp = (TimePicker) alarmView.findViewById(R.id.tp_alarm);
        alarmTp.setIs24HourView(true);
        final RadioGroup rgRepeated = (RadioGroup) alarmView.findViewById(R.id.rg_is_repeated);
        RadioButton rbRepeated = (RadioButton) alarmView.findViewById(R.id.rb_repeated);
        RadioButton rbOnce = (RadioButton) alarmView.findViewById(R.id.rb_once);
        Button btOk = (Button) alarmView.findViewById(R.id.bt_ok);
        Button btConcel = (Button) alarmView.findViewById(R.id.bt_concel);
        btOk.setTag(alarm);

        if(alarm != null) {
            int hour = alarm.getTime() / 1000 / 60 /60;
            int min = alarm.getTime() / 1000 /60 % 60;
            alarmTp.setHour(hour);
            alarmTp.setMinute(min);
            if(alarm.isRepeated()) {
                rbRepeated.setChecked(true);
            }
            else {
                rbOnce.setChecked(true);
            }
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.bt_ok:
                        int time = (alarmTp.getHour() * 60 + alarmTp.getMinute()) * 60 * 1000;
                        Alarm alarm = (Alarm) view.getTag();
                        if(alarm == null) {
                            alarm = new Alarm();
                            alarm.setTime(time);
                            alarm.setRepeated(rgRepeated.getCheckedRadioButtonId() == R.id.rb_repeated);
                            alarm.setEnable(true);
                            mAdapter.getData().add(alarm);
                            presenter.insert(alarm);
                        }
                        else {
                            alarm.setTime(time);
                            alarm.setRepeated(rgRepeated.getCheckedRadioButtonId() == R.id.rb_repeated);
                            alarm.setEnable(true);
                            presenter.update(alarm);
                        }
                        break;
                    case R.id.bt_concel:
                        break;
                }
                alarmDialog.dismiss();
            }
        };

        btOk.setOnClickListener(listener);
        btConcel.setOnClickListener(listener);

        window.setContentView(alarmView);
    }


    @Override
    public void setData(List<Alarm> alarms) {
        mAdapter = new AlarmAdapter(alarms);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(RecyclerView parent, View view, int position) {
                showAlarmDialog(mAdapter.getData().get(position));
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public boolean onLongClick(RecyclerView parent, View view, int position) {
                showDeleteDialog(mAdapter.getData().get(position));
                return true;
            }
        });

        mAdapter.setOnSwitchCheckedChangedListener(new AlarmAdapter.OnSwitchCheckedChangedListener(){
            @Override
            public void onCheckedChange(RecyclerView parent, Switch view, Alarm item, boolean isChecked) {
                if(item != null) {
                    if(isChecked ^ item.isEnable()) {
                        item.setEnable(isChecked);
                        presenter.update(item);
                    }
                }
            }
        });
        mAlarmRv.setAdapter(mAdapter);
    }

    private void showDeleteDialog(final Alarm alarm) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                switch (whichButton) {
                    case DialogInterface.BUTTON_POSITIVE :
                        mAdapter.getData().remove(alarm);
                        presenter.delete(alarm);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE :
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("您确定要删除 " + FormatUtils.format(alarm.getTime()) + " 的闹钟")
                .setNegativeButton("取消", listener).setPositiveButton("确定", listener)
                .setTitle("警告！").create().show();
    }

    @Override
    public void refreshData() {
        mAdapter.notifyDataSetChanged();
    }
}
