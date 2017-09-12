package com.example.yiyuangou.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private PopupWindow popupWindow;

    private ListView lv_group;

    private View view;

    private View top_title;
    private Button RightButton;

    private TextView tvtitle;

    private List<String> groups;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        top_title = this.findViewById(R.id.popwindow_title);
        RightButton= (Button) findViewById(R.id.header_right_button);
        tvtitle = (TextView) top_title.findViewById(R.id.header_title);

        tvtitle.setText("设置");
        RightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWindow(view);
            }
        });
        tvtitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showWindow(v);
            }
        });

    }

    private void showWindow(View parent) {

        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.group_list, null);

            lv_group = (ListView) view.findViewById(R.id.list_group);
            // 加载数据
            groups = new ArrayList<String>();
            groups.add("全部");
            groups.add("预测");
            groups.add("走势");
            groups.add("咨讯");
            groups.add("开奖");
            groups.add("我的");
            groups.add("遗漏");

            PopWindowListAdapter groupAdapter = new PopWindowListAdapter(this, groups);
            lv_group.setAdapter(groupAdapter);
            // 创建一个PopuWidow对象
            popupWindow = new PopupWindow(view, 300, 350);
        }

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        Log.i("coder", "xPos:" + xPos);

        popupWindow.showAsDropDown(parent, xPos, 0);

        lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {

                Toast.makeText(MainActivity.this,
                        groups.get(position), Toast.LENGTH_LONG)
                        .show();

                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }
}
