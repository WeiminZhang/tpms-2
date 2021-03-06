package com.apical.tpms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

public class TpmsTestActivity extends Activity {
    private static final String  TAG = "TpmsTestActivity";
    private static final boolean HIDE_REFRESH_BUTTONS = true;
    private static final boolean SHOW_HUMAN_READABLE_DATA = true;

    //++ msg
    private static final int     MSG_MATCH_TIRE   = 1;
    private static final int     MSG_TPMS_ALERT   = tpms.TPMS_TYPE_ALERT;
    private static final int     MSG_TPMS_TIRES   = tpms.TPMS_TYPE_TIRES;
    private static final int     MSG_TPMS_LEARN   = tpms.TPMS_TYPE_LEARN;
    private static final int     MSG_TPMS_UNWATCH = tpms.TPMS_TYPE_UNWATCH;
    //-- msg

    private int      mTpmsFuncRet;
    private int[]    mTpmsAlerts = new int[tpms.MAX_ALERT_NUM * 2];
    private int[]    mTpmsTires  = new int[tpms.MAX_TIRES_NUM * 4];
    private boolean  mResumeFlag = false;
    private int[]    mMatchBlink = new int[tpms.MAX_TIRES_NUM];
    private Button   mBtnHandShake;
    private Button   mBtnRefreshAll;
    private Button   mBtnRefreshTire1;
    private Button   mBtnRefreshTire2;
    private Button   mBtnRefreshTire3;
    private Button   mBtnRefreshTire4;
    private Button   mBtnRefreshTire5;
    private Button   mBtnMatchTire1;
    private Button   mBtnMatchTire2;
    private Button   mBtnMatchTire3;
    private Button   mBtnMatchTire4;
    private Button   mBtnMatchTire5;
    private Button   mBtnUnwatchTire1;
    private Button   mBtnUnwatchTire2;
    private Button   mBtnUnwatchTire3;
    private Button   mBtnUnwatchTire4;
    private Button   mBtnUnwatchTire5;
    private Button   mBtnRefreshAlert1;
    private Button   mBtnRefreshAlert2;
    private Button   mBtnRefreshAlert3;
    private Button   mBtnRefreshAlert4;
    private Button   mBtnRefreshAlert5;
    private Button   mBtnRefreshAlert6;
    private Button   mBtnConfigAlert1;
    private Button   mBtnConfigAlert2;
    private Button   mBtnConfigAlert3;
    private Button   mBtnConfigAlert4;
    private Button   mBtnConfigAlert5;
    private Button   mBtnConfigAlert6;
    private Button   mBtnRefreshTireAll;
    private Button   mBtnMatchTireAll;
    private Button   mBtnMatchCancel;
    private Button   mBtnUnwatchTireAll;
    private Button   mBtnRefreshAlertAll;
    private Button   mBtnConfigAlertAll;
    private TextView mTxtTpmsStatus;
    private TextView mTxtTpmsTire1;
    private TextView mTxtTpmsTire2;
    private TextView mTxtTpmsTire3;
    private TextView mTxtTpmsTire4;
    private TextView mTxtTpmsTire5;
    private TextView mTxtTpmsAlert1;
    private TextView mTxtTpmsAlert2;
    private TextView mTxtTpmsAlert3;
    private TextView mTxtTpmsAlert4;
    private TextView mTxtTpmsAlert5;
    private TextView mTxtTpmsAlert6;
    private TextView mTxtTpmsEvent;

    private TpmsTestService mTpmsServ = null;
    private ServiceConnection mTpmsServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder serv) {
            mTpmsServ = ((TpmsTestService.TpmsTestBinder)serv).getService(TpmsTestActivity.this);
            mTpmsFuncRet = mTpmsServ.tpmsRequestAlert(0);
            mTpmsFuncRet = mTpmsServ.tpmsRequestTire (0);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mTpmsServ = null;
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mBtnHandShake      = (Button)findViewById(R.id.btn_refresh_status   );
        mBtnRefreshAll     = (Button)findViewById(R.id.btn_refresh_all      );
        mBtnRefreshTire1   = (Button)findViewById(R.id.btn_refresh_tire1    );
        mBtnRefreshTire2   = (Button)findViewById(R.id.btn_refresh_tire2    );
        mBtnRefreshTire3   = (Button)findViewById(R.id.btn_refresh_tire3    );
        mBtnRefreshTire4   = (Button)findViewById(R.id.btn_refresh_tire4    );
        mBtnRefreshTire5   = (Button)findViewById(R.id.btn_refresh_tire5    );
        mBtnMatchTire1     = (Button)findViewById(R.id.btn_match_tire1      );
        mBtnMatchTire2     = (Button)findViewById(R.id.btn_match_tire2      );
        mBtnMatchTire3     = (Button)findViewById(R.id.btn_match_tire3      );
        mBtnMatchTire4     = (Button)findViewById(R.id.btn_match_tire4      );
        mBtnMatchTire5     = (Button)findViewById(R.id.btn_match_tire5      );
        mBtnUnwatchTire1   = (Button)findViewById(R.id.btn_unwatch_tire1    );
        mBtnUnwatchTire2   = (Button)findViewById(R.id.btn_unwatch_tire2    );
        mBtnUnwatchTire3   = (Button)findViewById(R.id.btn_unwatch_tire3    );
        mBtnUnwatchTire4   = (Button)findViewById(R.id.btn_unwatch_tire4    );
        mBtnUnwatchTire5   = (Button)findViewById(R.id.btn_unwatch_tire5    );
        mBtnRefreshAlert1  = (Button)findViewById(R.id.btn_refresh_alert1   );
        mBtnRefreshAlert2  = (Button)findViewById(R.id.btn_refresh_alert2   );
        mBtnRefreshAlert3  = (Button)findViewById(R.id.btn_refresh_alert3   );
        mBtnRefreshAlert4  = (Button)findViewById(R.id.btn_refresh_alert4   );
        mBtnRefreshAlert5  = (Button)findViewById(R.id.btn_refresh_alert5   );
        mBtnRefreshAlert6  = (Button)findViewById(R.id.btn_refresh_alert6   );
        mBtnConfigAlert1   = (Button)findViewById(R.id.btn_config_alert1    );
        mBtnConfigAlert2   = (Button)findViewById(R.id.btn_config_alert2    );
        mBtnConfigAlert3   = (Button)findViewById(R.id.btn_config_alert3    );
        mBtnConfigAlert4   = (Button)findViewById(R.id.btn_config_alert4    );
        mBtnConfigAlert5   = (Button)findViewById(R.id.btn_config_alert5    );
        mBtnConfigAlert6   = (Button)findViewById(R.id.btn_config_alert6    );
        mBtnRefreshTireAll = (Button)findViewById(R.id.btn_refresh_tire_all );
        mBtnMatchTireAll   = (Button)findViewById(R.id.btn_match_tire_all   );
        mBtnMatchCancel    = (Button)findViewById(R.id.btn_match_tire_cancel);
        mBtnUnwatchTireAll = (Button)findViewById(R.id.btn_unwatch_tire_all );
        mBtnRefreshAlertAll= (Button)findViewById(R.id.btn_refresh_alert_all);
        mBtnConfigAlertAll = (Button)findViewById(R.id.btn_config_alert_all );
        mBtnHandShake      .setOnClickListener(mOnClickListener);
        mBtnRefreshAll     .setOnClickListener(mOnClickListener);
        mBtnRefreshTire1   .setOnClickListener(mOnClickListener);
        mBtnRefreshTire2   .setOnClickListener(mOnClickListener);
        mBtnRefreshTire3   .setOnClickListener(mOnClickListener);
        mBtnRefreshTire4   .setOnClickListener(mOnClickListener);
        mBtnRefreshTire5   .setOnClickListener(mOnClickListener);
        mBtnMatchTire1     .setOnClickListener(mOnClickListener);
        mBtnMatchTire2     .setOnClickListener(mOnClickListener);
        mBtnMatchTire3     .setOnClickListener(mOnClickListener);
        mBtnMatchTire4     .setOnClickListener(mOnClickListener);
        mBtnMatchTire5     .setOnClickListener(mOnClickListener);
        mBtnUnwatchTire1   .setOnClickListener(mOnClickListener);
        mBtnUnwatchTire2   .setOnClickListener(mOnClickListener);
        mBtnUnwatchTire3   .setOnClickListener(mOnClickListener);
        mBtnUnwatchTire4   .setOnClickListener(mOnClickListener);
        mBtnUnwatchTire5   .setOnClickListener(mOnClickListener);
        mBtnRefreshAlert1  .setOnClickListener(mOnClickListener);
        mBtnRefreshAlert2  .setOnClickListener(mOnClickListener);
        mBtnRefreshAlert3  .setOnClickListener(mOnClickListener);
        mBtnRefreshAlert4  .setOnClickListener(mOnClickListener);
        mBtnRefreshAlert5  .setOnClickListener(mOnClickListener);
        mBtnRefreshAlert6  .setOnClickListener(mOnClickListener);
        mBtnConfigAlert1   .setOnClickListener(mOnClickListener);
        mBtnConfigAlert2   .setOnClickListener(mOnClickListener);
        mBtnConfigAlert3   .setOnClickListener(mOnClickListener);
        mBtnConfigAlert4   .setOnClickListener(mOnClickListener);
        mBtnConfigAlert5   .setOnClickListener(mOnClickListener);
        mBtnConfigAlert6   .setOnClickListener(mOnClickListener);
        mBtnRefreshTireAll .setOnClickListener(mOnClickListener);
        mBtnMatchTireAll   .setOnClickListener(mOnClickListener);
        mBtnMatchCancel    .setOnClickListener(mOnClickListener);
        mBtnUnwatchTireAll .setOnClickListener(mOnClickListener);
        mBtnRefreshAlertAll.setOnClickListener(mOnClickListener);
        mBtnConfigAlertAll .setOnClickListener(mOnClickListener);

        mTxtTpmsStatus = (TextView)findViewById(R.id.txt_tpms_status);
        mTxtTpmsTire1  = (TextView)findViewById(R.id.txt_tire1 );
        mTxtTpmsTire2  = (TextView)findViewById(R.id.txt_tire2 );
        mTxtTpmsTire3  = (TextView)findViewById(R.id.txt_tire3 );
        mTxtTpmsTire4  = (TextView)findViewById(R.id.txt_tire4 );
        mTxtTpmsTire5  = (TextView)findViewById(R.id.txt_tire5 );
        mTxtTpmsAlert1 = (TextView)findViewById(R.id.txt_alert1);
        mTxtTpmsAlert2 = (TextView)findViewById(R.id.txt_alert2);
        mTxtTpmsAlert3 = (TextView)findViewById(R.id.txt_alert3);
        mTxtTpmsAlert4 = (TextView)findViewById(R.id.txt_alert4);
        mTxtTpmsAlert5 = (TextView)findViewById(R.id.txt_alert5);
        mTxtTpmsAlert6 = (TextView)findViewById(R.id.txt_alert6);
        mTxtTpmsEvent  = (TextView)findViewById(R.id.txt_tpms_event);
        mTxtTpmsTire1 .setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        mTxtTpmsTire2 .setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        mTxtTpmsTire3 .setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        mTxtTpmsTire4 .setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        mTxtTpmsTire5 .setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        mTxtTpmsAlert1.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        mTxtTpmsAlert2.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        mTxtTpmsAlert3.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        mTxtTpmsAlert4.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        mTxtTpmsAlert5.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        mTxtTpmsAlert6.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);

        if (HIDE_REFRESH_BUTTONS) {
            mBtnHandShake      .setVisibility(View.GONE);
            mBtnRefreshTire1   .setVisibility(View.GONE);
            mBtnRefreshTire2   .setVisibility(View.GONE);
            mBtnRefreshTire3   .setVisibility(View.GONE);
            mBtnRefreshTire4   .setVisibility(View.GONE);
            mBtnRefreshTire5   .setVisibility(View.GONE);
            mBtnRefreshAlert1  .setVisibility(View.GONE);
            mBtnRefreshAlert2  .setVisibility(View.GONE);
            mBtnRefreshAlert3  .setVisibility(View.GONE);
            mBtnRefreshAlert4  .setVisibility(View.GONE);
            mBtnRefreshAlert5  .setVisibility(View.GONE);
            mBtnRefreshAlert6  .setVisibility(View.GONE);
            mBtnRefreshTireAll .setVisibility(View.GONE);
            mBtnRefreshAlertAll.setVisibility(View.GONE);
        }

        for (int i=0; i<mMatchBlink.length; i++) {
            mMatchBlink[i] = Color.WHITE;
        }

        // start record service
        Intent i = new Intent(TpmsTestActivity.this, TpmsTestService.class);
        startService(i);

        // bind record service
        bindService(i, mTpmsServiceConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        // unbind record service
        unbindService(mTpmsServiceConn);

        // stop record service
        Intent i = new Intent(TpmsTestActivity.this, TpmsTestService.class);
        stopService(i);

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mResumeFlag = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mResumeFlag = false;
    }

    private void showConfigAlertDialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText      editor  = new EditText(this);

        String str = "";
        switch (i) {
        case 0: str = ""
                    + mTxtTpmsAlert1.getText() + "\n"
                    + mTxtTpmsAlert2.getText() + "\n"
                    + mTxtTpmsAlert3.getText() + "\n"
                    + mTxtTpmsAlert4.getText() + "\n"
                    + mTxtTpmsAlert5.getText() + "\n"
                    + mTxtTpmsAlert6.getText();
                break;
        case 1: str = "" + mTxtTpmsAlert1.getText(); break;
        case 2: str = "" + mTxtTpmsAlert2.getText(); break;
        case 3: str = "" + mTxtTpmsAlert3.getText(); break;
        case 4: str = "" + mTxtTpmsAlert4.getText(); break;
        case 5: str = "" + mTxtTpmsAlert5.getText(); break;
        case 6: str = "" + mTxtTpmsAlert6.getText(); break;
        }
        editor.setText(str);
        editor.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);

        builder.setTitle("modify alert(s)");
        builder.setView(editor);
        builder.setCancelable(true);

        builder.setPositiveButton("comfirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (i != 0) {
                    int hot = 0;
                    int low = 0;
                    String[] splits = editor.getText().toString().split("\\s+");
                    if (SHOW_HUMAN_READABLE_DATA) {
                        if (i != tpms.MAX_ALERT_NUM) {
                            if (splits.length > 1) hot = (int)(Float.parseFloat(splits[1]) * 10);
                            if (splits.length > 2) low = (int)(Float.parseFloat(splits[2]) * 10);
                        }
                        else {
                            if (splits.length > 1) hot = Integer.parseInt(splits[1]) + 50;
                        }
                    }
                    else {
                        if (splits.length > 1) hot = Integer.parseInt(splits[1]);
                        if (splits.length > 2) low = Integer.parseInt(splits[2]);
                    }
                    mTpmsFuncRet = mTpmsServ.tpmsConfigAlert(i, hot, low);
                }
                else {
                    int[]    alerts = new int[tpms.MAX_ALERT_NUM * 2];
                    String[] splits = editor.getText().toString().split("\\s+");
                    for (int i=0; i<tpms.MAX_ALERT_NUM; i++) {
                        try {
                            if (SHOW_HUMAN_READABLE_DATA) {
                                if (i != tpms.MAX_ALERT_NUM - 1) {
                                    alerts[i*2+0] = (int)(Float.parseFloat(splits[i*3+1]) * 10);
                                    alerts[i*2+1] = (int)(Float.parseFloat(splits[i*3+2]) * 10);
                                }
                                else {
                                    alerts[i*2+0] = Integer.parseInt(splits[i*3+1]) + 50;
                                }
                            }
                            else {
                                alerts[i*2+0] = Integer.parseInt(splits[i*3+1]);
                                alerts[i*2+1] = Integer.parseInt(splits[i*3+2]);
                            }
                        } catch (Exception e) {}
                    }
                    mTpmsFuncRet = mTpmsServ.tpmsConfigAlert(alerts);
                }

                // request alert to refresh ui
                mTpmsFuncRet = mTpmsServ.tpmsRequestAlert(0);
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
            case R.id.btn_refresh_status   : mTpmsFuncRet = mTpmsServ.tpmsHandShake   () ; break;
            case R.id.btn_refresh_tire1    : mTpmsFuncRet = mTpmsServ.tpmsRequestTire (1); break;
            case R.id.btn_refresh_tire2    : mTpmsFuncRet = mTpmsServ.tpmsRequestTire (2); break;
            case R.id.btn_refresh_tire3    : mTpmsFuncRet = mTpmsServ.tpmsRequestTire (3); break;
            case R.id.btn_refresh_tire4    : mTpmsFuncRet = mTpmsServ.tpmsRequestTire (4); break;
            case R.id.btn_refresh_tire5    : mTpmsFuncRet = mTpmsServ.tpmsRequestTire (5); break;
            case R.id.btn_refresh_tire_all : mTpmsFuncRet = mTpmsServ.tpmsRequestTire (0); break;
            case R.id.btn_match_tire1      : mTpmsFuncRet = mTpmsServ.tpmsMatchTire   (1); break;
            case R.id.btn_match_tire2      : mTpmsFuncRet = mTpmsServ.tpmsMatchTire   (2); break;
            case R.id.btn_match_tire3      : mTpmsFuncRet = mTpmsServ.tpmsMatchTire   (3); break;
            case R.id.btn_match_tire4      : mTpmsFuncRet = mTpmsServ.tpmsMatchTire   (4); break;
            case R.id.btn_match_tire5      : mTpmsFuncRet = mTpmsServ.tpmsMatchTire   (5); break;
            case R.id.btn_match_tire_all   : mTpmsFuncRet = mTpmsServ.tpmsMatchTire   (0); break;
            case R.id.btn_match_tire_cancel: mTpmsFuncRet = mTpmsServ.tpmsMatchTire(0xff); break;
            case R.id.btn_unwatch_tire1    : mTpmsFuncRet = mTpmsServ.tpmsUnwatchTire (1); break;
            case R.id.btn_unwatch_tire2    : mTpmsFuncRet = mTpmsServ.tpmsUnwatchTire (2); break;
            case R.id.btn_unwatch_tire3    : mTpmsFuncRet = mTpmsServ.tpmsUnwatchTire (3); break;
            case R.id.btn_unwatch_tire4    : mTpmsFuncRet = mTpmsServ.tpmsUnwatchTire (4); break;
            case R.id.btn_unwatch_tire5    : mTpmsFuncRet = mTpmsServ.tpmsUnwatchTire (5); break;
            case R.id.btn_unwatch_tire_all : mTpmsFuncRet = mTpmsServ.tpmsUnwatchTire (0); break;
            case R.id.btn_refresh_alert1   : mTpmsFuncRet = mTpmsServ.tpmsRequestAlert(1); break;
            case R.id.btn_refresh_alert2   : mTpmsFuncRet = mTpmsServ.tpmsRequestAlert(2); break;
            case R.id.btn_refresh_alert3   : mTpmsFuncRet = mTpmsServ.tpmsRequestAlert(3); break;
            case R.id.btn_refresh_alert4   : mTpmsFuncRet = mTpmsServ.tpmsRequestAlert(4); break;
            case R.id.btn_refresh_alert5   : mTpmsFuncRet = mTpmsServ.tpmsRequestAlert(5); break;
            case R.id.btn_refresh_alert6   : mTpmsFuncRet = mTpmsServ.tpmsRequestAlert(6); break;
            case R.id.btn_refresh_alert_all: mTpmsFuncRet = mTpmsServ.tpmsRequestAlert(0); break;
            case R.id.btn_config_alert1    : showConfigAlertDialog(1); break;
            case R.id.btn_config_alert2    : showConfigAlertDialog(2); break;
            case R.id.btn_config_alert3    : showConfigAlertDialog(3); break;
            case R.id.btn_config_alert4    : showConfigAlertDialog(4); break;
            case R.id.btn_config_alert5    : showConfigAlertDialog(5); break;
            case R.id.btn_config_alert6    : showConfigAlertDialog(6); break;
            case R.id.btn_config_alert_all : showConfigAlertDialog(0); break;
            case R.id.btn_refresh_all:
                mTpmsFuncRet = mTpmsServ.tpmsRequestTire(0);
                mTpmsFuncRet = mTpmsServ.tpmsRequestAlert(0);
                break;
            }
            if (  id == R.id.btn_match_tire1 || id == R.id.btn_match_tire2 || id == R.id.btn_match_tire3
               || id == R.id.btn_match_tire4 || id == R.id.btn_match_tire5 || id == R.id.btn_match_tire_all) {
                for (int i=0; i<mMatchBlink.length; i++) {
                    mMatchBlink[i] = (id == R.id.btn_match_tire_all) ? Color.YELLOW : Color.WHITE;
                }
                switch (id) {
                case R.id.btn_match_tire1: mMatchBlink[0] = Color.YELLOW; break;
                case R.id.btn_match_tire2: mMatchBlink[1] = Color.YELLOW; break;
                case R.id.btn_match_tire3: mMatchBlink[2] = Color.YELLOW; break;
                case R.id.btn_match_tire4: mMatchBlink[3] = Color.YELLOW; break;
                case R.id.btn_match_tire5: mMatchBlink[4] = Color.YELLOW; break;
                }
                mHandler.sendEmptyMessageDelayed(MSG_MATCH_TIRE, 1000);
            }
            updateUI();
        }
    };

    private void updateUI() {
        if (!mResumeFlag) return;
        mTxtTpmsStatus.setText("status: " + (mTpmsFuncRet == 0 ? "connected" : "disconnect"));

        String tirelist[] = new String[tpms.MAX_TIRES_NUM];
        for (int i=0; i<tirelist.length; i++) {
            if (mTpmsTires[i*4+0] == 0 && mTpmsTires[i*4+1] == 0 && mTpmsTires[i*4+2] == 0 && mTpmsTires[i*4+3] == 0) {
                tirelist[i] = String.format("tire%d: ------ ------ --- --", i + 1);
            }
            else {
                if (SHOW_HUMAN_READABLE_DATA) {
                    tirelist[i] = String.format("tire%d: %06X %6.3f %3d %02X", i + 1,
                        mTpmsTires[i*4+0], mTpmsTires[i*4+1]*0.025, mTpmsTires[i*4+2]-50, mTpmsTires[i*4+3]);
                }
                else {
                    tirelist[i] = String.format("tire1: %06X %-4d %-3d %02X", i + 1,
                        mTpmsTires[i*4+0], mTpmsTires[i*4+1]*0.025, mTpmsTires[i*4+2]-50, mTpmsTires[i*4+3]);
                }
            }
        }
        mTxtTpmsTire1.setText(tirelist[0]);
        mTxtTpmsTire2.setText(tirelist[1]);
        mTxtTpmsTire3.setText(tirelist[2]);
        mTxtTpmsTire4.setText(tirelist[3]);
        mTxtTpmsTire5.setText(tirelist[4]);

        String alertlist[] = new String[tpms.MAX_ALERT_NUM];
        for (int i=0; i<alertlist.length; i++) {
            if (SHOW_HUMAN_READABLE_DATA) {
                if (i != tpms.MAX_ALERT_NUM - 1) {
                    alertlist[i] = String.format("alert%d: %4.1f %4.1f", i + 1,
                        mTpmsAlerts[i*2+0]*0.1, mTpmsAlerts[i*2+1]*0.1);
                }
                else {
                    alertlist[i] = String.format("alert%d: %3d", i + 1,
                        mTpmsAlerts[i*2+0] - 50);
                }
            }
            else {
                alertlist[i] = String.format("alert%d: %-3d  %-3d", i + 1,
                    mTpmsAlerts[i*2+0], mTpmsAlerts[i*2+1]);
            }
        }
        mTxtTpmsAlert1.setText(alertlist[0]);
        mTxtTpmsAlert2.setText(alertlist[1]);
        mTxtTpmsAlert3.setText(alertlist[2]);
        mTxtTpmsAlert4.setText(alertlist[3]);
        mTxtTpmsAlert5.setText(alertlist[4]);
        mTxtTpmsAlert6.setText(alertlist[5]);

        mTxtTpmsTire1.setTextColor(mMatchBlink[0]);
        mTxtTpmsTire2.setTextColor(mMatchBlink[1]);
        mTxtTpmsTire3.setTextColor(mMatchBlink[2]);
        mTxtTpmsTire4.setTextColor(mMatchBlink[3]);
        mTxtTpmsTire5.setTextColor(mMatchBlink[4]);
    }

    public void sendMessage(int type, int i) {
        Message msg = new Message();
        msg.what = type;
        msg.arg1 = i;
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case tpms.TPMS_TYPE_ALERT:
                mTpmsServ.tpmsGetParams(tpms.TPMS_TYPE_ALERT, mTpmsAlerts);
                mTxtTpmsEvent.setText("TPMS_EVENT_ALERT - " + msg.arg1);
                break;
            case tpms.TPMS_TYPE_TIRES:
                mTpmsServ.tpmsGetParams(tpms.TPMS_TYPE_TIRES, mTpmsTires );
                mTxtTpmsEvent.setText("TPMS_EVENT_TIRE - " + msg.arg1);
                break;
            case tpms.TPMS_TYPE_LEARN:
                mTpmsServ.tpmsGetParams(tpms.TPMS_TYPE_TIRES, mTpmsTires );
                mTxtTpmsEvent.setText("TPMS_EVENT_LEARN - " + msg.arg1);
                if (msg.arg1 == 0xff || msg.arg1 == 0x00) {
                    for (int i=0; i<mMatchBlink.length; i++) {
                        mMatchBlink[i] = Color.WHITE;
                    }
                }
                else if (msg.arg1 >= 1 && msg.arg1 <= 5) {
                    mMatchBlink[msg.arg1-1] = Color.WHITE;
                }
                boolean flag = false;
                for (int i=0; i<mMatchBlink.length; i++) {
                    if (mMatchBlink[i] != Color.WHITE) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    mHandler.removeMessages(MSG_MATCH_TIRE);
                }
                break;
            case tpms.TPMS_TYPE_UNWATCH:
                mTpmsServ.tpmsRequestTire(0);
                mTxtTpmsEvent.setText("TPMS_EVENT_UNWATCH - " + msg.arg1);
                break;
            case MSG_MATCH_TIRE:
                mHandler.sendEmptyMessageDelayed(MSG_MATCH_TIRE, 500);
                for (int i=0; i<mMatchBlink.length; i++) {
                    switch (mMatchBlink[i]) {
                    case Color.YELLOW: mMatchBlink[i] = 0           ; break;
                    case 0           : mMatchBlink[i] = Color.YELLOW; break;
                    }
                }
                break;
            }
            updateUI();
        }
    };
}
