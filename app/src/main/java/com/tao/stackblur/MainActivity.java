package com.tao.stackblur;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tao.stackblur.blur.BlurAlgorithm;
import com.tao.stackblur.blur.OldRenderScriptBlur;
import com.tao.stackblur.blur.StackBlur;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button, button2, button3, button4;

    private StackBlurManager stackBlurManager;

    private BlurAlgorithm blurAlgorithm;
    private Bitmap mBlurBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.id_button);
        button2 = (Button) findViewById(R.id.id_button_02);
        button3 = (Button) findViewById(R.id.id_button_03);
        button4 = (Button) findViewById(R.id.id_button_04);
        imageView = (ImageView) findViewById(R.id.id_imageview);
        //第一种方式和第三种方式
//        stackBlurManager = new StackBlurManager(getBitmapFromAsset(this, "android_platform_256.png"));
        stackBlurManager = new StackBlurManager(this,BitmapFactory.decodeResource(getResources(), R.mipmap.dayu));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第一种方式（设置自己想要的模糊度）
                imageView.setImageBitmap( stackBlurManager.process(20) );
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第二种方式 (该方法在部分机型上有点问题，可以参考一下。如果想用可看看另外两种方式)
                blurAlgorithm = new StackBlur(true);
                imageView.setImageBitmap(blurAlgorithm.blur(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.dayu),10));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第三种方式（设置自己想要的模糊度）
                imageView.setImageBitmap(stackBlurManager.processRenderScript(10));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第四种方式，改方法只适配 API_17以上的版本
                blurAlgorithm = new OldRenderScriptBlur(getApplication(), false);
                imageView.setImageBitmap(blurAlgorithm.blur(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.dayu),10));
            }
        });

    }

    private Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();
        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(strName);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        if (stackBlurManager != null)
            stackBlurManager.onDestory();
        super.onDestroy();
    }
}
