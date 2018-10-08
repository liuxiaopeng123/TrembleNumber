package com.theworldofluster.example.ziang.tremblenumber;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.activity.CalendarActivity;
import com.theworldofluster.example.ziang.tremblenumber.login.LoginActivity;
import com.theworldofluster.example.ziang.tremblenumber.personal.PersonalActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthAlertActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthConsultActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthIntegralTableActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.PsychologicalTestActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @ViewInject(R.id.main_gv)
    GridView main_gv;
    @ViewInject(R.id.main_title)
    TextView main_title;

    @ViewInject(R.id.activity_health_consult_back)
    RelativeLayout activity_health_consult_back;
    @ViewInject(R.id.main_jiankangtixing)
    LinearLayout main_jiankangtixing;
    @ViewInject(R.id.main_jiankangbaogao)
    LinearLayout main_jiankangbaogao;
    @ViewInject(R.id.main_jiankangzixun)
    LinearLayout main_jiankangzixun;
    @ViewInject(R.id.main_xinqingriji)
    LinearLayout main_xinqingriji;
    @ViewInject(R.id.main_xinliceshi)
    LinearLayout main_xinliceshi;
    @ViewInject(R.id.main_jiankangfenpk)
    LinearLayout main_jiankangfenpk;
    @ViewInject(R.id.main_bianji_ll)
    LinearLayout main_bianji_ll;
    @ViewInject(R.id.main_bianji_rl)
    RelativeLayout main_bianji_rl;

    @ViewInject(R.id.main_edit_check_1)
    ImageView main_edit_check_1;
    @ViewInject(R.id.main_edit_check_2)
    ImageView main_edit_check_2;
    @ViewInject(R.id.main_edit_check_3)
    ImageView main_edit_check_3;
    @ViewInject(R.id.main_edit_check_4)
    ImageView main_edit_check_4;
    @ViewInject(R.id.main_edit_check_5)
    ImageView main_edit_check_5;
    @ViewInject(R.id.main_edit_check_6)
    ImageView main_edit_check_6;

    private boolean flag_is_show_edit=false;
    private String mainIcons="0000";


    private static final boolean[] checked={false,false,false,false,false,false};
    private static final String[] names = {"健康提醒","健康报告","健康资讯","心情日记","心理测试","健康分PK"};
    private static final int[] icons = {R.mipmap.main_icon_tixing,R.mipmap.main_icon_baogao,R.mipmap.main_icon_jiankangzixun,R.mipmap.main_icon_riji,R.mipmap.main_icon_xinli,R.mipmap.main_icon_pk};


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this); //注入view和事件
        Window window = getWindow();
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.parseColor("#ffffff"));

        if(PreferenceUtil.getString("isLogin","").equals("")){
            main_bianji_ll.setVisibility(View.GONE);
            main_gv.setVisibility(View.GONE);
        } else{
            main_bianji_ll.setVisibility(View.VISIBLE);
            main_gv.setVisibility(View.VISIBLE);
        }

        Log.e("Ziang",PreferenceUtil.getString("userId",""));


        initData();
    }


    List<String> names2= new ArrayList<>();
    List<Integer> icons2 = new ArrayList<>();

    public void updateEditView(){
        main_edit_check_1.setBackgroundResource(PreferenceUtil.getBool("mainIcon1", false)?R.mipmap.bianji_off:R.mipmap.bianji_on);
        main_edit_check_2.setBackgroundResource(PreferenceUtil.getBool("mainIcon2", false)?R.mipmap.bianji_off:R.mipmap.bianji_on);
        main_edit_check_3.setBackgroundResource(PreferenceUtil.getBool("mainIcon3", false)?R.mipmap.bianji_off:R.mipmap.bianji_on);
        main_edit_check_4.setBackgroundResource(PreferenceUtil.getBool("mainIcon4", false)?R.mipmap.bianji_off:R.mipmap.bianji_on);
        main_edit_check_5.setBackgroundResource(PreferenceUtil.getBool("mainIcon5", false)?R.mipmap.bianji_off:R.mipmap.bianji_on);
        main_edit_check_6.setBackgroundResource(PreferenceUtil.getBool("mainIcon6", false)?R.mipmap.bianji_off:R.mipmap.bianji_on);
        names2= new ArrayList<>();
        icons2 = new ArrayList<>();
        mainIcons = PreferenceUtil.getString("mainIcons","0000");
        for (int i=0;i<4;i++){
            switch (mainIcons.substring(i,i+1)){
                case "0":
                    if (flag_is_show_edit){
                        names2.add("");
                        icons2.add(R.color.transparent);
                    }
                    break;
                case "1":
                    checked[0]=true;
                    names2.add("健康提醒");
                    icons2.add(R.mipmap.main_icon_tixing);
                    break;
                case "2":
                    checked[1]=true;
                    names2.add("健康报告");
                    icons2.add(R.mipmap.main_icon_baogao);
                    main_edit_check_2.setBackgroundResource(R.mipmap.bianji_off);
                    break;
                case "3":
                    checked[2]=true;
                    names2.add("健康资讯");
                    icons2.add(R.mipmap.main_icon_jiankangzixun);
                    main_edit_check_3.setBackgroundResource(R.mipmap.bianji_off);
                    break;
                case "4":
                    checked[3]=true;
                    names2.add("心情日记");
                    icons2.add(R.mipmap.main_icon_riji);
                    main_edit_check_4.setBackgroundResource(R.mipmap.bianji_off);
                    break;
                case "5":
                    checked[4]=true;
                    names2.add("心理测试");
                    icons2.add(R.mipmap.main_icon_xinli);
                    main_edit_check_5.setBackgroundResource(R.mipmap.bianji_off);
                    break;
                case "6":
                    checked[5]=true;
                    names2.add("健康分PK");
                    icons2.add(R.mipmap.main_icon_pk);
                    main_edit_check_6.setBackgroundResource(R.mipmap.bianji_off);
                    break;
            }
        }
        adapter.notifyDataSetChanged();
    }


    private GridViewAdapter adapter= new GridViewAdapter();
    public void initData() {
        updateEditView();
        main_gv.setAdapter(adapter);
        getList("");

    }

    @OnClick({R.id.main_edit_check_1,R.id.main_edit_check_2,R.id.main_edit_check_3,R.id.main_edit_check_4,R.id.main_edit_check_5,R.id.main_edit_check_6,R.id.main_bianji_start,R.id.main_bianji_cancle,R.id.main_bianji_finish,R.id.activity_health_consult_back,R.id.main_jiankangtixing,R.id.main_jiankangbaogao,R.id.main_jiankangzixun,R.id.main_xinqingriji,R.id.main_xinliceshi,R.id.main_jiankangfenpk})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.main_edit_check_1:

                boolean checked1 = PreferenceUtil.getBool("mainIcon1", false);

                if (!checked1){
                    if (!mainIcons.contains("0")){
                        ToastUtil.showContent(this,"最多编辑4个快捷应用");
                        return;
                    }
                    if (mainIcons.indexOf("0")==0){
                        mainIcons="1000";
                    }else if (mainIcons.indexOf("0")==1){
                        mainIcons=mainIcons.substring(0,1)+"1"+mainIcons.substring(2,4);
                    }else if (mainIcons.indexOf("0")==2){
                        mainIcons=mainIcons.substring(0,2)+"1"+mainIcons.substring(3,4);
                    }else if (mainIcons.indexOf("0")==3){
                        mainIcons=mainIcons.substring(0,3)+"1";
                    }
                    PreferenceUtil.putBool("mainIcon1", !checked1);
                    PreferenceUtil.putString("mainIcons",mainIcons);
                    Log.i("xiaopeng---1",""+mainIcons.indexOf("0"));
                }else {
                    for (int i=0;i<4;i++){
                        if ("1".equals(mainIcons.substring(i,i+1))){
                            mainIcons= (mainIcons.replace("1","")+"0").substring(0,4);
                            PreferenceUtil.putBool("mainIcon1", !checked1);
                            PreferenceUtil.putString("mainIcons",mainIcons);
                            Log.i("xiaopeng---2",""+mainIcons);
                            break;
                        }
                    }
                }
                updateEditView();
                Log.i("xiaopeng---3",""+mainIcons);


                break;
            case R.id.main_edit_check_2:

                boolean checked2 = PreferenceUtil.getBool("mainIcon2", false);

                if (!checked2){
                    if (!mainIcons.contains("0")){
                        ToastUtil.showContent(this,"最多编辑4个快捷应用");
                        return;
                    }
                    if (mainIcons.indexOf("0")==0){
                        mainIcons="2000";
                    }else if (mainIcons.indexOf("0")==1){
                        mainIcons=mainIcons.substring(0,1)+"2"+mainIcons.substring(2,4);
                    }else if (mainIcons.indexOf("0")==2){
                        mainIcons=mainIcons.substring(0,2)+"2"+mainIcons.substring(3,4);
                    }else if (mainIcons.indexOf("0")==3){
                        mainIcons=mainIcons.substring(0,3)+"2";
                    }
                    PreferenceUtil.putBool("mainIcon2", !checked2);
                    PreferenceUtil.putString("mainIcons",mainIcons);
                    Log.i("xiaopeng---1",""+mainIcons.indexOf("0"));
                }else {
                    for (int i=0;i<4;i++){
                        if ("2".equals(mainIcons.substring(i,i+1))){
                            mainIcons= (mainIcons.replace("2","")+"0").substring(0,4);
                            PreferenceUtil.putBool("mainIcon2", !checked2);
                            PreferenceUtil.putString("mainIcons",mainIcons);
                            Log.i("xiaopeng---2",""+mainIcons);
                            break;
                        }
                    }
                }
                updateEditView();
                Log.i("xiaopeng---3",""+mainIcons);
                break;
            case R.id.main_edit_check_3:

                boolean checked3 = PreferenceUtil.getBool("mainIcon3", false);

                if (!checked3){
                    if (!mainIcons.contains("0")){
                        ToastUtil.showContent(this,"最多编辑4个快捷应用");
                        return;
                    }
                    if (mainIcons.indexOf("0")==0){
                        mainIcons="3000";
                    }else if (mainIcons.indexOf("0")==1){
                        mainIcons=mainIcons.substring(0,1)+"3"+mainIcons.substring(2,4);
                    }else if (mainIcons.indexOf("0")==2){
                        mainIcons=mainIcons.substring(0,2)+"3"+mainIcons.substring(3,4);
                    }else if (mainIcons.indexOf("0")==3){
                        mainIcons=mainIcons.substring(0,3)+"3";
                    }
                    PreferenceUtil.putBool("mainIcon3", !checked3);
                    PreferenceUtil.putString("mainIcons",mainIcons);
                    Log.i("xiaopeng---1",""+mainIcons.indexOf("0"));
                }else {
                    for (int i=0;i<4;i++){
                        if ("3".equals(mainIcons.substring(i,i+1))){
                            mainIcons= (mainIcons.replace("3","")+"0").substring(0,4);
                            PreferenceUtil.putBool("mainIcon3", !checked3);
                            PreferenceUtil.putString("mainIcons",mainIcons);
                            Log.i("xiaopeng---2",""+mainIcons);
                            break;
                        }
                    }
                }
                updateEditView();
                Log.i("xiaopeng---3",""+mainIcons);
                break;
            case R.id.main_edit_check_4:

                boolean checked4 = PreferenceUtil.getBool("mainIcon4", false);

                if (!checked4){
                    if (!mainIcons.contains("0")){
                        ToastUtil.showContent(this,"最多编辑4个快捷应用");
                        return;
                    }
                    if (mainIcons.indexOf("0")==0){
                        mainIcons="4000";
                    }else if (mainIcons.indexOf("0")==1){
                        mainIcons=mainIcons.substring(0,1)+"4"+mainIcons.substring(2,4);
                    }else if (mainIcons.indexOf("0")==2){
                        mainIcons=mainIcons.substring(0,2)+"4"+mainIcons.substring(3,4);
                    }else if (mainIcons.indexOf("0")==3){
                        mainIcons=mainIcons.substring(0,3)+"4";
                    }
                    PreferenceUtil.putBool("mainIcon4", !checked4);
                    PreferenceUtil.putString("mainIcons",mainIcons);
                    Log.i("xiaopeng---1",""+mainIcons.indexOf("0"));
                }else {
                    for (int i=0;i<4;i++){
                        if ("4".equals(mainIcons.substring(i,i+1))){
                            mainIcons= (mainIcons.replace("4","")+"0").substring(0,4);
                            PreferenceUtil.putString("mainIcons",mainIcons);
                            PreferenceUtil.putBool("mainIcon4", !checked4);
                            Log.i("xiaopeng---2",""+mainIcons);
                            break;
                        }
                    }
                }
                updateEditView();
                Log.i("xiaopeng---3",""+mainIcons);
                break;
            case R.id.main_edit_check_5:

                boolean checked5 = PreferenceUtil.getBool("mainIcon5", false);

                if (!checked5){
                    if (!mainIcons.contains("0")){
                        ToastUtil.showContent(this,"最多编辑4个快捷应用");
                        return;
                    }
                    if (mainIcons.indexOf("0")==0){
                        mainIcons="5000";
                    }else if (mainIcons.indexOf("0")==1){
                        mainIcons=mainIcons.substring(0,1)+"5"+mainIcons.substring(2,4);
                    }else if (mainIcons.indexOf("0")==2){
                        mainIcons=mainIcons.substring(0,2)+"5"+mainIcons.substring(3,4);
                    }else if (mainIcons.indexOf("0")==3){
                        mainIcons=mainIcons.substring(0,3)+"5";
                    }
                    PreferenceUtil.putBool("mainIcon5", !checked5);
                    PreferenceUtil.putString("mainIcons",mainIcons);
                    Log.i("xiaopeng---1",""+mainIcons.indexOf("0"));
                }else {
                    for (int i=0;i<4;i++){
                        if ("5".equals(mainIcons.substring(i,i+1))){
                            mainIcons= (mainIcons.replace("5","")+"0").substring(0,4);
                            PreferenceUtil.putString("mainIcons",mainIcons);
                            PreferenceUtil.putBool("mainIcon5", !checked5);
                            Log.i("xiaopeng---2",""+mainIcons);
                            break;
                        }
                    }
                }
                updateEditView();
                Log.i("xiaopeng---3",""+mainIcons);
                break;
            case R.id.main_edit_check_6:

                boolean checked6 = PreferenceUtil.getBool("mainIcon6", false);

                if (!checked6){
                    if (!mainIcons.contains("0")){
                        ToastUtil.showContent(this,"最多编辑4个快捷应用");
                        return;
                    }
                    if (mainIcons.indexOf("0")==0){
                        mainIcons="6000";
                    }else if (mainIcons.indexOf("0")==1){
                        mainIcons=mainIcons.substring(0,1)+"6"+mainIcons.substring(2,4);
                    }else if (mainIcons.indexOf("0")==2){
                        mainIcons=mainIcons.substring(0,2)+"6"+mainIcons.substring(3,4);
                    }else if (mainIcons.indexOf("0")==3){
                        mainIcons=mainIcons.substring(0,3)+"6";
                    }
                    PreferenceUtil.putBool("mainIcon6", !checked6);
                    PreferenceUtil.putString("mainIcons",mainIcons);
                    Log.i("xiaopeng---1",""+mainIcons.indexOf("0"));
                }else {
                    for (int i=0;i<4;i++){
                        if ("6".equals(mainIcons.substring(i,i+1))){
                            mainIcons= (mainIcons.replace("6","")+"0").substring(0,4);
                            PreferenceUtil.putString("mainIcons",mainIcons);
                            PreferenceUtil.putBool("mainIcon6", !checked6);
                            Log.i("xiaopeng---2",""+mainIcons);
                            break;
                        }
                    }
                }
                updateEditView();
                Log.i("xiaopeng---3",""+mainIcons);
                break;
            case R.id.main_bianji_start:
                main_bianji_rl.setVisibility(View.VISIBLE);
                main_bianji_ll.setVisibility(View.GONE);
                flag_is_show_edit=true;
                main_title.setText("场景快捷应用编辑");
                updateEditView();
                showAddButton();

                break;
            case R.id.main_bianji_cancle:
                main_bianji_rl.setVisibility(View.GONE);
                main_bianji_ll.setVisibility(View.VISIBLE);
                flag_is_show_edit=false;
                dissShowAddButton();
                main_title.setText("场景应用");
                break;
            case R.id.main_bianji_finish:
                main_bianji_rl.setVisibility(View.GONE);
                main_bianji_ll.setVisibility(View.VISIBLE);
                flag_is_show_edit=false;
                main_title.setText("场景应用");
                updateEditView();
                dissShowAddButton();
                break;
            case R.id.activity_health_consult_back:
                finish();
                break;
            case R.id.main_jiankangtixing:
                if (!flag_is_show_edit){
                    startActivity(new Intent(MainActivity.this, HealthAlertActivity.class));
                }
                break;
            case R.id.main_jiankangbaogao:
                if (!flag_is_show_edit){
                    if (PreferenceUtil.getString("isLogin", "").equals("")) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }else {
                        startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                    }
                }
                break;
            case R.id.main_jiankangzixun:
                if (!flag_is_show_edit){
                    startActivity(new Intent(MainActivity.this, HealthConsultActivity.class));
                }
                break;
            case R.id.main_xinqingriji:
                if (!flag_is_show_edit){
                    if (PreferenceUtil.getString("isLogin", "").equals("")) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }else {
                        startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                    }
                }
                break;
            case R.id.main_xinliceshi:
                if (!flag_is_show_edit){
                    if (PreferenceUtil.getString("isLogin", "").equals("")) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }else {
                        startActivity(new Intent(MainActivity.this, PsychologicalTestActivity.class));
                    }
                }
                break;
            case R.id.main_jiankangfenpk:
                if (!flag_is_show_edit){
                    if (PreferenceUtil.getString("isLogin", "").equals("")) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }else {
                        startActivity(new Intent(MainActivity.this, HealthIntegralTableActivity.class));
                    }
                }
                break;
            default:
                break;
        }
    }

    private void dissShowAddButton() {
        main_edit_check_1.setVisibility(View.GONE);
        main_edit_check_2.setVisibility(View.GONE);
        main_edit_check_3.setVisibility(View.GONE);
        main_edit_check_4.setVisibility(View.GONE);
        main_edit_check_5.setVisibility(View.GONE);
        main_edit_check_6.setVisibility(View.GONE);
    }

    private void showAddButton() {
        main_edit_check_1.setVisibility(View.VISIBLE);
        main_edit_check_2.setVisibility(View.VISIBLE);
        main_edit_check_3.setVisibility(View.VISIBLE);
        main_edit_check_4.setVisibility(View.VISIBLE);
        main_edit_check_5.setVisibility(View.VISIBLE);
        main_edit_check_6.setVisibility(View.VISIBLE);


    }

    //获取列表
    public void getList(String name) {
    }

    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // 返回的条目
            return icons2.size();
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),
                    R.layout.item_main_gv, null);
            TextView tv_name = (TextView) view
                    .findViewById(R.id.tv_homeitem_name);
            ImageView iv_icon = (ImageView) view
                    .findViewById(R.id.iv_homeitem_icon);
            tv_name.setText(names2.get(position));
            iv_icon.setImageResource(icons2.get(position));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!flag_is_show_edit){
                        switch (names2.get(position)){
                            case "健康提醒":
                                startActivity(new Intent(MainActivity.this, HealthAlertActivity.class));
                                break;
                            case "健康报告":
                                startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                                break;
                            case "健康资讯":
                                startActivity(new Intent(MainActivity.this, HealthConsultActivity.class));
                                break;
                            case "心情日记":
                                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                                break;
                            case "心理测试":
                                startActivity(new Intent(MainActivity.this, PsychologicalTestActivity.class));
                                break;
                            case "健康分PK":
                                startActivity(new Intent(MainActivity.this, HealthIntegralTableActivity.class));
                                break;
                        }
                    }
                }
            });
            return view;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

    }
}
