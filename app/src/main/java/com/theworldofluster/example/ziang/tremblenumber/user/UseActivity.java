package com.theworldofluster.example.ziang.tremblenumber.user;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.R;

import org.xutils.view.annotation.ViewInject;

/**
 *
 * 关于
 *
 */

public class UseActivity extends AppCompatActivity {


    TextView use_content;
    TextView use_title;
    @ViewInject(R.id.information_back)
    RelativeLayout information_back;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_use);
        ViewUtils.inject(this); //注入view和事件
        Window window = getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.parseColor("#00000000"));

        ViewGroup mContentView = (ViewGroup)findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }



        initView();
    }

    private void initView() {
        use_title=findViewById(R.id.use_title);
        use_content=findViewById(R.id.use_content);
        String position = getIntent().getStringExtra("position");
        switch (position){
            case "1":
                use_content.setText("一、产品简介\n" +
                        "抖擞是一款针对身心健康管理的全新产品，它颠覆了市面上针对心理或生理单一健康管理的APP产品，真正做到了心理和生理的全方位管理，它无需让用户每天上传繁杂的健康数据，仅按照APP引导提醒进行简单操作即可，同时根据用户的不同状态，提供有针对性的定制化改善策略，帮助用户实现身心健康与平衡。以后你遇到任何困惑，都请与【抖擞】分享，【抖擞】将帮你分担、助你解决。\n" +
                        "\n" +
                        "【抖擞】主要功能包括以下内容：\n" +
                        "①身心健康应用：【健康提醒】、【健康报告】、【健康资讯】 \n" +
                        "②心理健康专项服务：【心情日记】 【心理测试】\n" +
                        "③竞技中心：【健康分PK】\n" +
                        "\n" +
                        "二、基本操作\n" +
                        "首页场景\n" +
                        "1、场景介绍\n" +
                        "① APP首页将【好友】的家作为整体背景，整个背景分为3个区域，分别为：书房、卧室、健身区，每个区域模块分别是小军师与用户交互的场景。\n" +
                        "② 首页通过平面视角来展现小军师家的布局，不同区域如书房、卧室、健身区，可以通过【左右】滑动屏幕来去看到【好友】家的全貌布局\n" +
                        "\n" +
                        "2、好友昵称\n" +
                        "点击APP首页中【好友】的头部，可修改【好友昵称】。\n" +
                        "\n" +
                        "3、快捷应用添加与取消\n" +
                        "① 首页点击【我的健康】进入【场景应用】点击【编辑】按钮，进行场景快捷应用编辑。\n" +
                        "② 点击【场景快捷应用编辑】页面中每个功能图标右上角【＋】可以添加为场景快捷应用。（场景快捷应用最多可添加4个）\n" +
                        "③ 点击【场景快捷应用编辑】页面中每个功能图标右上角【－】可以取消场景快捷应用。\n" +
                        "\n" +
                        "身心健康应用\n" +
                        "1、心情日记\n" +
                        "进入【心情日记】点击右上角图标，进入【有心事】功能，在【有心事】中可以分享或吐槽自己的心事，也可以评论他人的心事\n" +
                        "2、健康报告\n" +
                        "①【健康报告】可以左右滑动查看报告分析详情；\n" +
                        "② 点击【健康分】可以查看近一个月内健康分（含生理分、心理分、平均分）的趋势图；\n" +
                        "③ 点击【排名】可以进入【健康分龙虎榜】查看健康分排名，另外也可以开展【健康分PK】；\n" +
                        "\n" +
                        "3、健康分PK\n" +
                        "① 可以上下滑动查看排行详情，同时可以了解到自己的健康分与其他用户之间的强弱关系；\n" +
                        "② 与其他用户之间的强弱关系，共有3类标签“比我好”、“旗鼓相当”、“比我差”。\n" +
                        "③ 另外，用户在发起【健康分PK】时，只能向【比我好】、【旗鼓相当】用户发起PK，不能向【比我差】的用户发起挑战。");
                use_title.setText("抖擞使用说明");
                break;
            case "2":
                use_content.setText("一、什么是【健康分】\n" +
                        "【抖擞】会关注你每天心理与身体健康数据情况，并在每周一为你上一周的健康数据情况进行打分，形成【健康分】；\n" +
                        "关于健康分的展示：\n" +
                        "1、如果授权开启所有权限并按时完成【抖擞】中【好友】给出的心理与身体有关的全部【日常任务】，那么，【抖擞】能够对你心理与身体的全部健康数据进行评估，并得到完整【健康分】。\n" +
                        "另外，如果在开启所有权限后，并未完成【抖擞】中【好友】给出的心理与身体有关的全部【日常任务】时，仅对心理或身体的健康数据进行评估，并得到心理或身体的【健康分】，不会得到完整【健康分】。\n" +
                        "2、如果未开启相关权限或未完成心理与身体有关的全部【日常任务】时，无法对健康数据进行评估，此时【健康分】将缺失；\n" +
                        "\n" +
                        "二、什么是【健康分PK】\n" +
                        "根据用户每周获得的【健康分】与上周【健康分】的增长幅度，与其他被PK用户的【健康分】增长幅度进行对比，增长幅度最高的获胜，反之则输。\n" +
                        "健康分PK规则：\n" +
                        "当你主动向【健康分龙虎榜】中的其他用户发起pk时，一次可以向1-5人发起PK，系统会将PK消息发送给被挑战的用户，以最先接受挑战的用户为被挑战方。\n" +
                        "1、如果有2人或以上同时接受挑战，系统随机选择1位，剩余的发消息提示已有人接受挑战，PK邀请失效。每周只能挑战一人，或接受1人挑战。\n" +
                        "2、当发起挑战后24小时内，不能再次发起对其他人的挑战，需等上一轮挑战邀请失效后（当发起挑战后24小时内无人应答，则挑战失效），或24小时内邀请的1-5位用户都明确拒绝挑战后，才可再次向其他用户发起挑战。\n" +
                        "\n" +
                        "3、当用户有收到别人的有效期内（24小时内）的挑战邀请时，需应答（即接受或拒绝）后，才能主动发起对他人的挑战。发起挑战和接受挑战不能同时进行。\n" +
                        "4、如果用户在一周内，主动发起5次挑战，且每次不少于3人（即向15-25人发起了挑战），在挑战有效期内均无人应战，则系统给予用户Plan B方案。用户可以进行自我挑战。结合用户情况，通过系统设定的不同档次的健康分提升目标给到用户，用户达到相应档次可给予积分奖励，未达到基础的档次则会相应扣分。");
                use_title.setText("关于健康分PK");
                break;
            case "3":
                use_content.setText("1、如何获得【成长值】？\n" +
                        "【抖擞】的宗旨是帮助你达到心理与身体平衡，那么，【抖擞】中的【好友】会每日关注你的心理和身体状况，并为你提供定制的专属健康提醒和改善策略，而你只需要积极配合【好友】完成健康提醒与改善策略的相关【日常任务】，即可以获得【成长值】。\n" +
                        "除了【日常任务】外，也可以通过完成【特殊任务】获得【成长值】。\n" +
                        "\n" +
                        "2、【日常任务】都有什么？\n" +
                        "目前【抖擞】中的【日常任务】主要是为了帮助你达到心理与身体平衡，所展开的几项任务。\n" +
                        "具体如下：\n" +
                        "①健康提醒：查看【好友】的健康提醒；\n" +
                        "②心情日记：进入【心情日记】完成每日心情上传；\n" +
                        "③心理测试：进入【心理测试】完成心理测试；\n" +
                        "④健康报告：查看每周的健康报告；\n" +
                        "⑤健康分PK：根据每周生成的健康报告，进入【健康分龙虎榜】进行【健康分PK】；\n" +
                        "⑥健康资讯：进入【健康资讯】查看【好友】推荐的相关资讯文章；\n" +
                        "⑦每日进入【抖擞】签到；\n" +
                        "当你达到不同等级时，完成【日常任务】所获得的【成长值】也会随之增加。\n" +
                        "\n" +
                        "3、【特殊任务】都有什么？\n" +
                        "①完成分享； \n" +
                        "大到人类文明和科技的蓬勃发展，是源于交流和共享；健康也是一样，赠人玫瑰手有余香，向家人、好友分享自己的健康成果促进身边所有人，都能成为更好的自己，一起维护这个可爱的【抖擞】世界。\n" +
                        "\n" +
                        "分享【健康资讯】、【心理测试】和自己的【健康报告】，这些充满正能量的行为充实自己的同时也鼓舞着别人，并且得到【成长值】；与【日常任务】一样，随着等级不断提高，每次分享会得到更多的【成长值】。\n" +
                        "\n" +
                        "②开启所有授权项；\n" +
                        "当遇到迷惑和挫折时，总希望知心好友为你解开心结、指点迷津，可同时也需要你完全吐露自己的心声，让好友了解一切问题的症结所在，不然将无法从根源上彻底为你分析解决；\n" +
                        "同样如此，【抖擞】中的【好友】虽然每日会关注你心理和身体状况，为你提供定制的解决方案，帮助达到心理与身体平衡，但是一切的根基都需要你坦诚的将所有信息授权给【好友】，就如同完全吐露自己的心声，让【好友】了解一切问题的症结所在一样，在向【好友】完全了解你的同时也得到了【成长值】。");
                use_title.setText("关于用户成长值");
                break;
            case "4":
                use_content.setText("等级是什么？\n" +
                        "通过在【抖擞】中完成相关【日常任务】和【特殊任务】，都会得到对应的【成长值】，加总就得到了你的总成长值，不同的总成长值会对应到不同等级，并可以解锁对应等级的专享福利。\n" +
                        "等级分为三个级别：VIP1、VIP2、VIP3。\n" +
                        "\n" +
                        "怎么升级？\n" +
                        "等级晋级条件\n" +
                        "序号\t晋级过程\t晋级条件\n" +
                        "1\t游客→VIP1\t完成注册\n" +
                        "2\tVIP1→VIP2\t成长值≥150\n" +
                        "3\tVIP2→VIP3\t成长值≥900\n" +
                        "（注：除VIP1【完成注册】即可获得外，VIP2和VIP3需要通过累计成长值获得。）\n" +
                        "\n" +
                        "做到上面的【日常任务】和【特殊任务】，自然就能快速升级了！");
                use_title.setText("如何升级");
                break;
            case "5":
                use_content.setText("1、如何获得【成长值】？\n" +
                        "【抖擞】的宗旨是帮助你达到心理与身体平衡，那么，【抖擞】中的【好友】会每日关注你的心理和身体状况，并为你提供定制的专属健康提醒和改善策略，而你只需要积极配合【好友】完成健康提醒与改善策略的相关【日常任务】，即可以获得【成长值】。\n" +
                        "除了【日常任务】外，也可以通过完成【特殊任务】获得【成长值】。\n" +
                        "\n" +
                        "2、【日常任务】都有什么？\n" +
                        "目前【抖擞】中的【日常任务】主要是为了帮助你达到心理与身体平衡，所展开的几项任务。\n" +
                        "具体如下：\n" +
                        "①健康提醒：查看【好友】的健康提醒；\n" +
                        "②心情日记：进入【心情日记】完成每日心情上传；\n" +
                        "③心理测试：进入【心理测试】完成心理测试；\n" +
                        "④健康报告：查看每周的健康报告；\n" +
                        "⑤健康分PK：根据每周生成的健康报告，进入【健康分龙虎榜】进行【健康分PK】；\n" +
                        "⑥健康资讯：进入【健康资讯】查看【好友】推荐的相关资讯文章；\n" +
                        "⑦每日进入【抖擞】签到；\n" +
                        "当你达到不同等级时，完成【日常任务】所获得的【成长值】也会随之增加。\n" +
                        "\n" +
                        "3、【特殊任务】都有什么？\n" +
                        "①完成分享； \n" +
                        "大到人类文明和科技的蓬勃发展，是源于交流和共享；健康也是一样，赠人玫瑰手有余香，向家人、好友分享自己的健康成果促进身边所有人，都能成为更好的自己，一起维护这个可爱的【抖擞】世界。\n" +
                        "\n" +
                        "分享【健康资讯】、【心理测试】和自己的【健康报告】，这些充满正能量的行为充实自己的同时也鼓舞着别人，并且得到【成长值】；与【日常任务】一样，随着等级不断提高，每次分享会得到更多的【成长值】。\n" +
                        "\n" +
                        "②开启所有授权项；\n" +
                        "当遇到迷惑和挫折时，总希望知心好友为你解开心结、指点迷津，可同时也需要你完全吐露自己的心声，让好友了解一切问题的症结所在，不然将无法从根源上彻底为你分析解决；\n" +
                        "同样如此，【抖擞】中的【好友】虽然每日会关注你心理和身体状况，为你提供定制的解决方案，帮助达到心理与身体平衡，但是一切的根基都需要你坦诚的将所有信息授权给【好友】，就如同完全吐露自己的心声，让【好友】了解一切问题的症结所在一样，在向【好友】完全了解你的同时也得到了【成长值】。");
                use_title.setText("获取成长值");
                break;

        }
    }

    @OnClick({R.id.information_back})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.information_back:
                finish();
                break;

            default:
                break;
        }
    }
}
