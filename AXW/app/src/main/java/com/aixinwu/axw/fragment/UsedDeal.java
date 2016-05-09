package com.aixinwu.axw.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aixinwu.axw.Adapter.HomepageGuiedAdapter;
import com.aixinwu.axw.R;
import com.aixinwu.axw.activity.Buy;
import com.aixinwu.axw.activity.HelloWorld;
import com.aixinwu.axw.activity.ProductDetailActivity;
import com.aixinwu.axw.model.HomepageGuide;
import com.aixinwu.axw.model.Product;
import com.aixinwu.axw.tools.ADInfo;
import com.aixinwu.axw.tools.Bean;
import com.aixinwu.axw.tools.GlobalParameterApplication;
import com.aixinwu.axw.tools.SearchAdapter;
import com.aixinwu.axw.tools.Tool;
import com.aixinwu.axw.tools.ViewFactory;
import com.aixinwu.axw.view.BaseViewPager;
import com.aixinwu.axw.view.CycleViewPager;
import com.aixinwu.axw.view.MyScrollView;
import com.aixinwu.axw.view.SearchView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangyuding on 2016/4/6.
 */
public class UsedDeal extends CycleViewPager implements MyScrollView.ScrollViewListener{

    public boolean needtochange = false;
    private int start = 0;
   // public SearchView searchView;
    public SearchAdapter resultAdapter;
    public String MyToken;
    public String needtochange_text="";
    public Tool am = new Tool();
    public static List<Bean> dbData;
    public static List<Bean> resultData;
    public String surl = GlobalParameterApplication.getSurl();
    private TextView info1,info2,info3,moreInfo;
    private LinearLayout newCommodity;
    private RelativeLayout popCommodity;
    private RelativeLayout showCommodity;
    private DisplayMetrics metrics;
    private int screenHalfWidth;
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    private MyScrollView scrollView;
    View searchHomePage;
    View search;
    private ListView lvResults;
    private int searchTouchTime = 0;

    private ArrayList<HomepageGuide> homepageGuides = new ArrayList<HomepageGuide>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.used_deal,null);

        //获取屏幕宽度
        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHalfWidth = metrics.widthPixels / 2;
        lvResults = (ListView) view.findViewById(R.id.main_lv_search_results);
        lvResults.setVisibility(View.VISIBLE);
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), i + " is what you want!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("itemId", resultData.get(i).getItemId());

                intent.setClass(getActivity(), Buy.class);
                startActivity(intent);


            }
        });
        dbData=new ArrayList<Bean>();
        mThread.start();
        resultAdapter = new SearchAdapter(getActivity(), dbData, R.layout.item_bean_list);
        lvResults.setAdapter(resultAdapter);
        //继承自父类
        viewPager = (BaseViewPager) view.findViewById(R.id.viewPager2);
        indicatorLayout = (LinearLayout) view
                .findViewById(R.id.layout_viewpager_indicator2);

        viewPagerFragmentLayout = (FrameLayout) view
                .findViewById(R.id.layout_viewager_content);

        setViewPagerScrollSpeed(1000);//设置滑动速度
        init();

        search =  view.findViewById(R.id.homepage_search2);
        searchTouchTime = 0;
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (searchTouchTime == 0) {
                    searchTouchTime++;
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                    intent.putExtra("param1", "搜索");
                    getActivity().startActivityForResult(intent, 2);
                }

                return false;
            }
        });


        //自己的

        scrollView = (MyScrollView) view.findViewById(R.id.homepageScroll2);
        scrollView.setScrollViewListener(this);

        searchHomePage = view.findViewById(R.id.searchHomePage2);
        searchHomePage.setVisibility(View.INVISIBLE);
        searchHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("param1","搜索");
                getActivity().startActivity(intent);
            }
        });

        initializeGuide(view);
        configImageLoader();
        initialize();

        return view;
    }
    @SuppressLint("NewApi")
    private void initialize() {

        //cycleViewPager = (CycleViewPager) getSupportFragmentManager()
        //        .findFragmentById(R.id.fragment_cycle_viewpager_content);

        for(int i = 0; i < imageUrls.length; i ++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("ͼƬ-->" + i );
            infos.add(info);
        }

        // �����һ��ImageView��ӽ���
        views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrl()));
        }
        // ����һ��ImageView��ӽ���
        views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl()));

        // ����ѭ�����ڵ���setData����ǰ����
        setCycle(true);

        // �ڼ�������ǰ�����Ƿ�ѭ��
        setData(views, infos, mAdCycleViewListener);
        //�����ֲ�
        setWheel(true);

        // �����ֲ�ʱ�䣬Ĭ��5000ms
        setTime(2000);
        //����Բ��ָʾͼ���������ʾ��Ĭ�Ͽ���
        setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (isCycle()) {
                position = position - 1;
                Toast.makeText(getActivity(),
                        "number" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();
            }

        }

    };

    /**
     * ����ImageLoder
     */
    private void configImageLoader() {
        // ��ʼ��ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // ����ͼƬ�����ڼ���ʾ��ͼƬ
                .showImageForEmptyUri(R.drawable.icon_empty) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
                .showImageOnFail(R.drawable.icon_error) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
                .cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
                .cacheOnDisc(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
                // .displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
                .build(); // �������ù���DisplayImageOption����

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy){

        if (y > search.getHeight()){
            searchHomePage.setVisibility(View.VISIBLE);
        }
        else {
            searchHomePage.setVisibility(View.INVISIBLE);
        }
        //Toast.makeText(getActivity(),x+" "+search.getHeight()+" "+y,Toast.LENGTH_SHORT).show();
    }

    private void initializeGuide(View view){
        homepageGuides.add(new HomepageGuide(R.mipmap.product1,"二手物品"));
        homepageGuides.add(new HomepageGuide(R.mipmap.img1,"爱心"));
        homepageGuides.add(new HomepageGuide(R.mipmap.product2,"租赁"));
        homepageGuides.add(new HomepageGuide(R.mipmap.product3,"志愿者"));
        homepageGuides.add(new HomepageGuide(R.mipmap.product4,"众筹"));
        homepageGuides.add(new HomepageGuide(R.mipmap.img2,"规则清单"));
        homepageGuides.add(new HomepageGuide(R.mipmap.img3,"待定"));
        homepageGuides.add(new HomepageGuide(R.mipmap.img1,"待定"));

        HomepageGuiedAdapter homepageGuiedAdapter = new HomepageGuiedAdapter(
                getActivity(),
                R.layout.homepage_guide_item,
                homepageGuides);

        GridView guides = (GridView) view.findViewById(R.id.homepage_guide2);
        guides.setAdapter (homepageGuiedAdapter);
        guides.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomepageGuide guide = homepageGuides.get(i);
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("param1",guide.getName());
                getActivity().startActivity(intent);

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){

        if (requestCode == 2){
            searchTouchTime = 0;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            getDbData();
            start += 10;
            Message msg= new Message();
            msg.what = 23212;
            handler.sendMessage(msg);

        }
    });
    public Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 23212:
                    resultAdapter.notifyDataSetChanged();
                    break;
            }
        }

    };
    private void getDbData(){
        MyToken=GlobalParameterApplication.getToken();
        JSONObject data = new JSONObject();
        data.put("startPos",start);
        data.put("length",10);
        {

            Log.i("UsedDeal", "get");
            try {
                URL url = new URL(surl + "/item_get_all");
                try {
                    Log.i("UsedDeal","getconnection");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    conn.getOutputStream().write(data.toJSONString().getBytes());
                    java.lang.String ostr = IOUtils.toString(conn.getInputStream());
                    org.json.JSONObject outjson = null;
                    try {
                        JSONArray result = null;
                        outjson = new org.json.JSONObject(ostr);
                        result = outjson.getJSONArray("items");
                        start+=result.length();
                        for (int i = 0; i < result.length(); i++) {
                            String[] rr = result.getJSONObject(i).getString("images").split(",");
                            if (rr[0]=="") {
                                BitmapFactory.Options cc = new BitmapFactory.Options();
                                cc.inSampleSize = 20;
                                dbData.add(new Bean(result.getJSONObject(i).getInt("ID"), BitmapFactory.decodeResource(getResources(), R.drawable.icon, cc), result.getJSONObject(i).getString("ownerID"), result.getJSONObject(i).getString("description")));
                            } else
                                dbData.add(new Bean(result.getJSONObject(i).getInt("ID"), am.DownloadFile(surl, rr[0], 20), result.getJSONObject(i).getString("ownerID"), result.getJSONObject(i).getString("description")));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }
}
