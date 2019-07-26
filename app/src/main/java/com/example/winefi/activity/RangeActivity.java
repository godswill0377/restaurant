package com.example.winefi.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.winefi.R;
import com.example.winefi.adapter.RangeListAdapter;
import com.example.winefi.adapter.WinefiListAdapter;
import com.example.winefi.objects.WinefiData;
import com.example.winefi.utils.ConnectionDetector;
import com.example.winefi.utils.Constant;
import com.example.winefi.utils.DividerItemDecoration;
import com.example.winefi.utils.GPSTracker;
import com.example.winefi.utils.HttpUtility;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RangeActivity extends AppCompatActivity {

    Boolean isInternetPresent = false;
    ConnectionDetector cd;

    Toolbar toolbar;

    RecyclerView listwinfi;
    LinearLayoutManager mLayoutManager;
    ArrayList<WinefiData> winefiDataArrayList;
    RangeListAdapter rangeListAdapter;
    int pos = 0;

    LinearLayout lout_slider_range;
    int[] some_array;


    RelativeLayout lout_select_range;
    LinearLayout lout_set_seekbar;
    LinearLayout lout_logo;
    ImageView ivIcon;
    CheckBox ivChecked;
    ImageView ivArrow;
    IndicatorSeekBar indicatorSeekBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_range);
        init();
    }

    public void init() {


        TypedArray ta = getResources().obtainTypedArray(R.array.add_text_color);
        some_array = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            some_array[i] = ta.getColor(i, 0);
        }
        ta.recycle();

        pos = getIntent().getIntExtra("pos", 0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        lout_slider_range = (LinearLayout) findViewById(R.id.lout_slider_range);
        lout_slider_range.setVisibility(View.VISIBLE);

        lout_select_range = (RelativeLayout) findViewById(R.id.lout_select_range);
        lout_set_seekbar = (LinearLayout) findViewById(R.id.lout_set_seekbar);
        lout_logo = (LinearLayout) findViewById(R.id.lout_logo);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        ivArrow = (ImageView) findViewById(R.id.ivArrow);
        ivChecked = (CheckBox) findViewById(R.id.ivChecked);
        indicatorSeekBar = (IndicatorSeekBar) findViewById(R.id.indicatorSeekBar);


        listwinfi = (RecyclerView) findViewById(R.id.listwinfi);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listwinfi.addItemDecoration(new DividerItemDecoration(getApplicationContext(), getResources().getDrawable(R.drawable.line_divider_gray)));
        listwinfi.setLayoutManager(mLayoutManager);

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();

        setrangeData();

        if (isInternetPresent) {
            getData();
        } else {
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection!.", Toast.LENGTH_SHORT).show();
        }


    }

    public void setrangeData() {
        ivIcon.setBackgroundResource(Constant.home_logo[pos]);
        lout_logo.setBackgroundColor(some_array[pos]);

        lout_select_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (lout_set_seekbar.getVisibility() == View.VISIBLE) {
                    lout_set_seekbar.setVisibility(View.GONE);
                    ivArrow.setBackgroundResource(R.drawable.iv_down_arrow);

                } else {
                    lout_set_seekbar.setVisibility(View.VISIBLE);

                    ivArrow.setBackgroundResource(R.drawable.iv_up_arrow);
                }


            }
        });

        ivChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckLocationPermission();

            }
        });


        indicatorSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

                CheckLocationPermission();

//                if (Build.VERSION.SDK_INT >= 23) {
//                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                            != PackageManager.PERMISSION_GRANTED) {
//
//                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                11);
//
//                    } else {
//                        GetlatlongLogin();
//                    }
//                } else {
//
//                    GetlatlongLogin();
//
//                }
//                isInternetPresent = cd.isConnectingToInternet();
//
//                if (isInternetPresent) {
//                    gps = new GPSTracker(getApplicationContext(), RangeActivity.this);
//                    if (gps.canGetLocation()) {
//                        latitude = gps.getLatitude();
//                        longitude = gps.getLongitude();
//                        getRangeData(seekBar.getProgress());
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Please make your gps location Enable.", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection!.", Toast.LENGTH_SHORT).show();
//                }


            }
        });
    }

    getAllData getAllDatas;
    ProgressDialog pd;

    public void getData() {
        pd = ProgressDialog.show(RangeActivity.this, "", "Caricamento...", true, false);
        getAllDatas = new getAllData();
        getAllDatas.execute("http://winefi24.com/api/index.php?route=winery/all");
    }

    private class getAllData extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuilder response = new StringBuilder();
            try {
                try {
                    String url = params[0].toString();

                    HttpUtility.sendGetRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String[] data = HttpUtility.readMultipleLinesRespone();
                for (String line : data) {
                    response.append(line);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            HttpUtility.disconnect();
            return response.toString();
        }


        protected void onPostExecute(String res) {
            pd.dismiss();

            listwinfi.setVisibility(View.VISIBLE);
            winefiDataArrayList = new ArrayList<>();
            try {
                JSONObject json_data = new JSONObject(res);
                if (json_data.getBoolean("success")) {
                    if (json_data != null && json_data.length() > 0) {
                        if (json_data.has("results")) {
                            JSONArray jsonArray = json_data.getJSONArray("results");
                            if (jsonArray.length() > 0 && jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    WinefiData winefiData = new WinefiData();
                                    winefiData.setName(jsonObject.getString("name"));
                                    winefiData.setAlis(jsonObject.getString("alias"));
                                    winefiData.setIcon(jsonObject.getString("logo"));
                                    winefiDataArrayList.add(winefiData);

                                }

                                rangeListAdapter = new RangeListAdapter(RangeActivity.this, winefiDataArrayList, pos);
                                listwinfi.setAdapter(rangeListAdapter);

                            } else {
                                listwinfi.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Nessun risultato nelle vicinanze...", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            listwinfi.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Nessun risultato nelle vicinanze...", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        listwinfi.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Nessun risultato nelle vicinanze...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nessun risultato nelle vicinanze...", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    getAllRangeData getAllRangeDatas;

    public void getRangeData(int range) {
        pd = ProgressDialog.show(RangeActivity.this, "", "Caricamento...", true, false);
        String url = " http://winefi24.com/api/index.php?route=winery/all/" + latitude + "/" + longitude + "/" + range + "";
        Log.e("url", "" + url);
        getAllRangeDatas = new getAllRangeData();
        getAllRangeDatas.execute(url);
    }

    private class getAllRangeData extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuilder response = new StringBuilder();
            try {
                try {
                    String url = params[0].toString();

                    HttpUtility.sendGetRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String[] data = HttpUtility.readMultipleLinesRespone();
                for (String line : data) {
                    response.append(line);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            HttpUtility.disconnect();
            return response.toString();
        }


        protected void onPostExecute(String res) {
            pd.dismiss();
            listwinfi.setVisibility(View.VISIBLE);
            winefiDataArrayList = new ArrayList<>();
            try {
                JSONObject json_data = new JSONObject(res);
                if (json_data.getBoolean("success")) {
                    if (json_data != null && json_data.length() > 0) {
                        if (json_data.has("results")) {
                            JSONArray jsonArray = json_data.getJSONArray("results");
                            if (jsonArray.length() > 0 && jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    WinefiData winefiData = new WinefiData();
                                    winefiData.setName(jsonObject.getString("name"));
                                    winefiData.setAlis(jsonObject.getString("alias"));
                                    winefiData.setIcon(jsonObject.getString("logo"));
                                    winefiDataArrayList.add(winefiData);

                                }

                                rangeListAdapter = new RangeListAdapter(RangeActivity.this, winefiDataArrayList, pos);
                                listwinfi.setAdapter(rangeListAdapter);

                            } else {
                                listwinfi.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Nessun risultato nelle vicinanze", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            listwinfi.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Nessun risultato nelle vicinanze", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        listwinfi.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Nessun risultato nelle vicinanze", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nessun risultato nelle vicinanze", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public void CheckLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        11);

            } else {
                GetlatlongLogin();
            }
        } else {

            GetlatlongLogin();

        }
    }

    GPSTracker gps;
    double latitude;
    double longitude;

    public void GetlatlongLogin() {

        gps = new GPSTracker(getApplicationContext(), RangeActivity.this);
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if (latitude == 0.0 && longitude == 0.0) {
                GetlatlongLogin();
            } else {
                if (ivChecked.isChecked()) {
                    getRangeData(indicatorSeekBar.getProgress());
                } else {
                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent) {
                        getData();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection!.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } else {
            displayPromptForEnablingGPS(this);
        }

    }

    public void displayPromptForEnablingGPS(final Activity activity) {

        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RangeActivity.this);




        // Setting Dialog Title
        alertDialog.setTitle(getResources().getString(R.string.app_name));

        // Setting Dialog Message
        alertDialog.setMessage(getResources().getString(R.string.gps_text1));

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setPositiveButton("IMPOSTAZIONI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                activity.startActivityForResult(new Intent(action), 589);
                dialog.dismiss();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("CANCELLA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        alertDialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 11: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GetlatlongLogin();
                } else {

                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 589) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (provider != null) {
                        GetlatlongLogin();
                    } else {

                        Toast.makeText(getApplicationContext(), "Please enable gps location service.", Toast.LENGTH_SHORT).show();

                    }

                }
            }, 1000);


        }

    }


    public void clickEvent() {


    }
}
