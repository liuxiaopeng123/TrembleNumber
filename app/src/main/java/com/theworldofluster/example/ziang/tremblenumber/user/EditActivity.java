package com.theworldofluster.example.ziang.tremblenumber.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import com.theworldofluster.example.ziang.tremblenumber.utils.Bimp;
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

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.activity_edit_back)
    private RelativeLayout activity_edit_back;

    @ViewInject(R.id.user_updata_headimg_rl)
    private RelativeLayout user_updata_headimg_rl;

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
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_edit_back:

                finish();

                break;

            case R.id.user_updata_headimg_rl:

                actionSheetDialog = new ActionSheetDialog(this).builder();
                actionSheetDialog.setCancelable(true);
                actionSheetDialog.setCanceledOnTouchOutside(true);

                actionSheetDialog.addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                camera();
                            }
                        });
                actionSheetDialog.addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                gallery();
                            }
                        });
                actionSheetDialog.show();

                break;
        }
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

        imageFile = new File(sdcardPathName, "head.jpg");

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
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
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
            params.addBodyParameter("MultipartFile", Bimp.saveFile(headbitmap, sdcardPathName, Utils.getstringrandom() + Utils.getstringrandom() + Utils.getstringrandom() + Utils.getstringrandom()+".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, MouthpieceUrl.base_obtaininformations, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ZiangS---更新头像",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if ("SUCCESS".equals(jsonobject.getString("code"))) {


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
