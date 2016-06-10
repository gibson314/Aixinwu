package com.aixinwu.axw.fragment;

import android.app.Activity;
import android.os.Handler;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import com.aixinwu.axw.R;
import com.aixinwu.axw.activity.LoginActivity;
import com.aixinwu.axw.activity.MainActivity;
import com.aixinwu.axw.activity.SendToAXw;
import com.aixinwu.axw.activity.SendToPeople;
import com.aixinwu.axw.tools.GlobalParameterApplication;
import com.aixinwu.axw.tools.Tool;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
//import org.json.JSONObject;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


/**
 * Created by liangyuding on 2016/4/6.
 */
public class SubmitThings extends Fragment {
    private int iii;
    private View axw;
    private View people;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.submit_things,null);
        axw = view.findViewById(R.id.button3);
        people = view.findViewById(R.id.button);
        axw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalParameterApplication.getLogin_status()==1){
                Intent intent = new Intent();
                intent.setClass(getActivity(), SendToAXw.class);
                startActivityForResult(intent,12);
                }
                else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);

                }
            }
        });
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalParameterApplication.getLogin_status()==1) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SendToPeople.class);
                    startActivityForResult(intent,11);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);

                }
            }
        });




        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11){
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getActivity(), "人人商品发布成功", Toast.LENGTH_LONG);
                new  AlertDialog.Builder(getActivity())
                        .setTitle("消息" )
                        .setMessage("人人商品发布成功" )
                        .setPositiveButton("确定",null)
                        .show();
            }

        }    else if (requestCode==12){
            if (resultCode == Activity.RESULT_OK){
                new  AlertDialog.Builder(getActivity())
                        .setTitle("消息" )
                        .setMessage("爱心屋捐赠成功" )
                        .setPositiveButton("确定",null)
                        .show();
            Toast.makeText(getActivity(),"爱心屋捐赠成功",Toast.LENGTH_LONG);}
        }

    }



}
