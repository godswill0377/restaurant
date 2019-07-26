package com.example.winefi.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.winefi.R;
import com.example.winefi.adapter.CatalogListAdapter;
import com.example.winefi.adapter.RangeListAdapter;
import com.example.winefi.objects.WinefiData;
import com.example.winefi.utils.ConnectionDetector;
import com.example.winefi.utils.DividerItemDecoration;
import com.example.winefi.utils.HttpUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class InformationActivity extends AppCompatActivity {

    String alias;
    String name;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    TextView tvTitle;

    TextView tvyear;
    TextView tvProduction;
    TextView tvheacter;
    TextView tvvariety;
    TextView ivText;
    TextView tvAddress;
    TextView tvsubaddress;
    TextView tvregion;
    TextView tvVisit;
    TextView tvwebsite;
    TextView tvToomuchText;

    LinearLayout lout_detail;
    LinearLayout lout_text;
    ImageView imgview;

    ImageView ivArrow;
    ImageView ivRegion;
    ImageView ivBanner;
    ImageView ivBack;

    LinearLayout lout_facebook;
    LinearLayout lout_twitter;
    LinearLayout lout_google;
    LinearLayout lout_insta;
    LinearLayout lout_youtube;
    LinearLayout lout_call;
    LinearLayout lout_navigate;
    LinearLayout lout_calatalog;

    public String facebook = "";
    public String twitter = "";
    public String google = "";
    public String insta = "";
    public String youtube = "";
    public String website = "";

    String telephone = "";
    double lat = 0.0;
    double longi = 0.0;

    LinearLayout lout_year;
    LinearLayout lout_heactor;
    LinearLayout lout_production;
    LinearLayout lout_variety;


    RecyclerView listcatalog;
    LinearLayoutManager mLayoutManager;
    ArrayList<WinefiData> winefiDataArrayList;
    CatalogListAdapter rangeListAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();
    }

    public void init() {

        name = getIntent().getStringExtra("name");
        alias = getIntent().getStringExtra("alias");
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(name);

        tvyear = (TextView) findViewById(R.id.tvyear);
        imgview = (ImageView) findViewById(R.id.imgview);

        tvProduction = (TextView) findViewById(R.id.tvProduction);
        tvheacter = (TextView) findViewById(R.id.tvheacter);
        tvvariety = (TextView) findViewById(R.id.tvvariety);
        ivText = (TextView) findViewById(R.id.ivText);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvsubaddress = (TextView) findViewById(R.id.tvsubaddress);
        tvregion = (TextView) findViewById(R.id.tvregion);
        tvwebsite = (TextView) findViewById(R.id.tvwebsite);
        tvVisit = (TextView) findViewById(R.id.tvVisit);
        tvToomuchText = (TextView) findViewById(R.id.tvToomuchText);

        lout_detail = (LinearLayout) findViewById(R.id.lout_detail);
        lout_text = (LinearLayout) findViewById(R.id.lout_text);
        lout_facebook = (LinearLayout) findViewById(R.id.lout_facebook);
        lout_twitter = (LinearLayout) findViewById(R.id.lout_twitter);
        lout_google = (LinearLayout) findViewById(R.id.lout_google);
        lout_insta = (LinearLayout) findViewById(R.id.lout_insta);
        lout_youtube = (LinearLayout) findViewById(R.id.lout_youtube);
        lout_call = (LinearLayout) findViewById(R.id.lout_call);
        lout_navigate = (LinearLayout) findViewById(R.id.lout_navigate);
        lout_year = (LinearLayout) findViewById(R.id.lout_year);
        lout_heactor = (LinearLayout) findViewById(R.id.lout_heactor);
        lout_production = (LinearLayout) findViewById(R.id.lout_production);
        lout_variety = (LinearLayout) findViewById(R.id.lout_variety);
        lout_calatalog = (LinearLayout) findViewById(R.id.lout_calatalog);


        ivArrow = (ImageView) findViewById(R.id.ivArrow);
        ivRegion = (ImageView) findViewById(R.id.ivRegion);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBanner = (ImageView) findViewById(R.id.ivBanner);


        listcatalog = (RecyclerView) findViewById(R.id.listcatalog);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listcatalog.setLayoutManager(mLayoutManager);

        if (isInternetPresent) {
            getData();
        } else {
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection!.", Toast.LENGTH_SHORT).show();
        }
        clickEvent();

    }

    public void clickEvent() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lout_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + telephone));
                startActivity(intent);
            }
        });

        lout_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String label = "";
                String uriBegin = "geo:" + lat + "," + longi;
                String query = lat + "," + longi + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        lout_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] listItems = {"Facebook", "Default browser"};
                if (!facebook.equals("")) {

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(InformationActivity.this);
                    mBuilder.setTitle("Open URL using...");
                    mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == 1) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebook));
                                startActivity(browserIntent);
                                dialogInterface.cancel();

                            } else {
                                String facebookurl = getFacebookPageURL(getApplicationContext());
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookurl));
                                startActivity(browserIntent);
                                dialogInterface.cancel();
                            }
                        }
                    });

                    AlertDialog mDialog = mBuilder.create();
                    mDialog.show();


                } else {
                    Toast.makeText(getApplicationContext(), "Coming soon!.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lout_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!twitter.equals("")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Coming soon!.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        lout_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!google.equals("")) {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(google));
                    startActivity(browserIntent);


                } else {
                    Toast.makeText(getApplicationContext(), "Coming soon!.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lout_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!insta.equals("")) {
                    try {
                        Uri uri = Uri.parse(insta);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setPackage("com.instagram.android");
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(insta));
                        startActivity(browserIntent);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Coming soon!.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lout_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!youtube.equals("")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube));
                    startActivity(browserIntent);

                } else {
                    Toast.makeText(getApplicationContext(), "Coming soon!.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lout_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lout_text.getVisibility() == View.VISIBLE) {
                    lout_text.setVisibility(View.GONE);
                    ivArrow.setBackgroundResource(R.drawable.iv_arrow);


                } else {
                    lout_text.setVisibility(View.VISIBLE);
                    ivArrow.setBackgroundResource(R.drawable.iv_down_arrow);
                }

            }
        });

        tvVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!website.equals("")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Coming soon!.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + facebook;
            } else { //older versions of fb app
                return "fb://page/" + facebook.substring(facebook.lastIndexOf("/") + 1);
            }
        } catch (PackageManager.NameNotFoundException e) {
            return facebook; //normal web url
        }
    }


    getAllData getAllDatas;
    ProgressDialog pd;

    public void getData() {
        pd = ProgressDialog.show(InformationActivity.this, "", "Caricamento...", true, false);
        getAllDatas = new getAllData();
        Log.e("***", "" + "http://winefi24.com/api/index.php?route=winery/get/" + alias);
        getAllDatas.execute("http://winefi24.com/api/index.php?route=winery/get/" + alias);
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

            try {
                winefiDataArrayList = new ArrayList<>();
                JSONObject json_data = new JSONObject(res);
                if (json_data.getBoolean("success")) {
                    if (json_data != null && json_data.length() > 0) {

                        facebook = json_data.getString("facebook");
                        twitter = json_data.getString("twitter");
                        google = json_data.getString("google");
                        insta = json_data.getString("instagram");
                        youtube = json_data.getString("youtube");
                        telephone = json_data.getString("telephone");
                        lat = Double.parseDouble(json_data.getString("latitude"));
                        longi = Double.parseDouble(json_data.getString("longitude"));
                        website = json_data.getString("website");

                        if (json_data.has("text")) {
                            ivText.setText(Html.fromHtml(json_data.getString("text")));
                            Glide.with(InformationActivity.this).load(json_data.getString("logo"))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .fitCenter()
                                    .into(imgview);

                        } else {
                            ivText.setVisibility(View.GONE);
                            imgview.setVisibility(View.GONE);
                        }
                        if (json_data.has("year")) {
                            if (!json_data.getString("year").equals("")) {
                                tvyear.setText(json_data.getString("year"));
                            } else {
                                lout_year.setVisibility(View.GONE);
                            }

                        } else {
                            lout_year.setVisibility(View.GONE);
                        }
                        if (json_data.has("hectare")) {
                            if (!json_data.getString("hectare").equals("")) {
                                tvheacter.setText(json_data.getString("hectare"));
                            } else {
                                lout_heactor.setVisibility(View.GONE);
                            }

                        } else {
                            lout_heactor.setVisibility(View.GONE);
                        }

                        if (json_data.has("production")) {
                            if (!json_data.getString("production").equals("")) {
                                tvProduction.setText(json_data.getString("production"));
                            } else {
                                lout_production.setVisibility(View.GONE);
                            }

                        } else {
                            lout_production.setVisibility(View.GONE);
                        }


                        tvAddress.setText(json_data.getString("address"));
                        tvregion.setText(json_data.getString("region"));
//                        tvwebsite.setText(json_data.getString("website"));
                        tvsubaddress.setText(json_data.getString("cap") + " " + json_data.getString("city") + " " + "(" + json_data.getString("initial") + ")");
                        if (json_data.has("varieties")) {
                            JSONArray jsonArray = json_data.getJSONArray("varieties");
                            String varieties = "";
                            if (jsonArray != null && jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    int sizess=jsonArray.length();

                                    if(sizess-1==i)
                                    {
                                        varieties = varieties + "" + jsonArray.get(i).toString() + ".";
                                    }
                                    else
                                    {
                                        varieties = varieties + "" + jsonArray.get(i).toString() + ", ";
                                    }
                                }
                                tvvariety.setText(varieties);
                                if (tvvariety.getLineCount() > 1) {
                                    tvvariety.setVisibility(View.GONE);
                                    tvToomuchText.setVisibility(View.VISIBLE);
                                    tvToomuchText.setText(varieties);
                                }
                            }
                        } else {
                            lout_variety.setVisibility(View.GONE);
                        }


                        Glide.with(getApplicationContext()).load(json_data.getString("region_image"))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(ivRegion);

                        Glide.with(getApplicationContext()).load(json_data.getString("wallpaper"))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(ivBanner);


                        if (json_data.has("catalogs")) {
                            JSONArray jsonArraycatalog = json_data.getJSONArray("catalogs");
                            if (jsonArraycatalog != null && jsonArraycatalog.length() > 0) {
                                for (int i = 0; i < jsonArraycatalog.length(); i++) {
                                    JSONObject jsonObject = jsonArraycatalog.getJSONObject(i);
                                    WinefiData winefiData = new WinefiData();
                                    winefiData.setName(jsonObject.getString("name"));
                                    winefiData.setIcon(jsonObject.getString("image"));
                                    winefiDataArrayList.add(winefiData);
                                }

                                rangeListAdapter = new CatalogListAdapter(InformationActivity.this, winefiDataArrayList);
                                listcatalog.setAdapter(rangeListAdapter);

                            }
                        } else {
                            listcatalog.setVisibility(View.GONE);
                            lout_calatalog.setVisibility(View.GONE);
                        }

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
