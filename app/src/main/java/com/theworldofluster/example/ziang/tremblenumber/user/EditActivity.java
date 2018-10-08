package com.theworldofluster.example.ziang.tremblenumber.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.dialog.HttpDialog;
import com.theworldofluster.example.ziang.tremblenumber.jpushdemo.MainActivity;
import com.theworldofluster.example.ziang.tremblenumber.login.LoginActivity;
import com.theworldofluster.example.ziang.tremblenumber.login.RegisterActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.Bimp;
import com.theworldofluster.example.ziang.tremblenumber.utils.FileUtils;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.view.ActionSheetDialog;
import com.theworldofluster.example.ziang.tremblenumber.view.CircularImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.activity_edit_back)
    private RelativeLayout activity_edit_back;

    @ViewInject(R.id.user_updata_headimg_rl)
    private RelativeLayout user_updata_headimg_rl;

    @ViewInject(R.id.activity_proposal_content)
    EditText activity_proposal_content;

    @ViewInject(R.id.edit_activity_zishu)
    TextView edit_activity_zishu;

    @ViewInject(R.id.user_data_headpic_img)
    private CircularImage user_data_headpic_img;

    private ActionSheetDialog actionSheetDialog;

    private String sdcardPathName = Environment.getExternalStorageDirectory() + "/slwx/";
    private File imageFile;
    private Uri imageUri;
    private BitmapUtils bitmapUtils;
    public static final int PHOTO_REQUEST_CAREMA = 1;
    public static final int CROP_PHOTO = 2;
    public static final int PHOTO_REQUEST_GALLERY = 3;
    public static final int PHOTO_REQUEST_CUT = 4;

    private Bitmap headbitmap = null;

    HttpDialog dia;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit);
        ViewUtils.inject(this); //注入view和事件


        dia = new HttpDialog(this);
        dia.getWindow().setDimAmount(0);
        dia.setCanceledOnTouchOutside(false);

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

        activity_edit_back.setOnClickListener(this);
        user_updata_headimg_rl.setOnClickListener(this);

        activity_proposal_content.setText(getIntent().getStringExtra("qianming"));

        activity_proposal_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit_activity_zishu.setText((60-activity_proposal_content.getText().toString().length())+"");
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        Utils.BJSloadImg(this,PreferenceUtil.getString("userheadUrl",""),user_data_headpic_img);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_edit_back:
                if (getIntent().getStringExtra("qianming").equals(activity_proposal_content.getText().toString().trim())){
                    finish();
                }else {
                    base_edituser();
                }

//

                break;

            case R.id.user_updata_headimg_rl:

                actionSheetDialog = new ActionSheetDialog(this).builder();
                actionSheetDialog.setCancelable(true);
                actionSheetDialog.setCanceledOnTouchOutside(true);

                actionSheetDialog.addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (ActivityCompat.checkSelfPermission(EditActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions(EditActivity.this,
                                            new String[]{Manifest.permission.CAMERA}, 100);
                                }else {
                                    camera();
                                }

                            }
                        });
                actionSheetDialog.addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (ActivityCompat.checkSelfPermission(EditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                                        ActivityCompat.checkSelfPermission(EditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions(EditActivity.this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                                }else {
                                    gallery();
                                }

                            }
                        });
                actionSheetDialog.show();

                break;
        }
    }

    private void base_edituser(){
        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId",PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("sex",PreferenceUtil.getString("userSex","0"));
        params.addQueryStringParameter("age",PreferenceUtil.getString("userSex","2"));
        params.addQueryStringParameter("nickName",getIntent().getStringExtra("nickName"));
        params.addQueryStringParameter("signature",activity_proposal_content.getText().toString().trim());

        HttpUtils http = new HttpUtils();
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_edituser + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_edituser, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ZiangS---更新用户信息",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {
                        PreferenceUtil.putString("qianming",activity_proposal_content.getText().toString().trim());
                        finish();
                    }else{

                        ToastUtil.showContent(EditActivity.this,jsonobject.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.e("ZiangF-更新用户信息",msg);
                dia.dismiss();
            }
        });
    }


    /*
     * 从相机获取
     */
    public void camera() {
        File imgFile = new File(sdcardPathName);
        if (!imgFile.exists()) {
            imgFile.mkdir();
        }
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        imageFile = new File(sdcardPathName, Utils.getrandom()+"head.jpg");

        // 从文件中创建uri
        Uri uri = Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    /*
     * 从相册获取
     */
    public void gallery() {

        // 激活系统图库，选择一张图片android.intent.action.GET_CONTENT
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT) ;
//        intent.addCategory(Intent. CATEGORY_OPENABLE);
//        intent.setType("image/*");
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        // super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA:

                if (resultCode == RESULT_OK) {
                    crop(Uri.fromFile(imageFile));
                }

                break;
            case CROP_PHOTO:

                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PHOTO_REQUEST_GALLERY:
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    // Get the path
                    String path = null;
                    try {
                        path = FileUtils.getPath(getApplicationContext(), uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.d("xiaopeng----", "File Path: " + path);
                    crop(uri);
                }

                break;
            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    // 如果在裁剪图片界面没有裁剪图片并且设点击了返回键，返回的对象为空
                    if (bitmap != null) {
                        // ImageTools.saveImg(bitmap, "myCertificate.jpg");
                        // this.pictureImv.setImageBitmap(bitmap);
                        // 将图片上传给服务器imageUri
                        user_data_headpic_img.setImageBitmap(bitmap);
                        headbitmap = bitmap;
                        base_obtaininformations();
                    }

                }

                break;
            default:
                break;
        }
    }


    /*
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("scale", true);
//		String path = sdcardPathName+"myhead.jpg";
//		Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(sdcardPathName + "head.jpg")));
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        //sdcardPathName = uri.getPath();
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    private void base_obtaininformations(){
        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addBodyParameter("userId",PreferenceUtil.getString("userId",""));
        try {
            params.addBodyParameter("fileHead", Bimp.saveFile(headbitmap, sdcardPathName, Utils.getstringrandom() + Utils.getstringrandom() + Utils.getstringrandom() + Utils.getstringrandom()+".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpUtils http = new HttpUtils();
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_obtaininformations );
        http.send(HttpRequest.HttpMethod.POST, MouthpieceUrl.base_obtaininformations, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ZiangS---更新头像",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {


                    }else{

                        ToastUtil.showContent(EditActivity.this,jsonobject.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.e("ZiangF-用户信息",msg);
                dia.dismiss();
            }
        });
    }

}
