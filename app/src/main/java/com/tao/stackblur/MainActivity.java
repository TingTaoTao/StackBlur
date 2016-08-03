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

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;

    private StackBlurManager _stackBlurManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.id_button);
        imageView = (ImageView) findViewById(R.id.id_imageview);

        _stackBlurManager = new StackBlurManager(getBitmapFromAsset(this, "android_platform_256.png"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap( _stackBlurManager.process(10) );
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
        return bitmap;    }
}
