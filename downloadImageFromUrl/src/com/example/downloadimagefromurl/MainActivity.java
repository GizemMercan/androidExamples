package com.example.downloadimagefromurl;

import java.io.InputStream;
import java.net.URL;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
public class MainActivity extends Activity {
  Button load_img;
  ImageView img;
  Bitmap bitmap;
  ProgressDialog pDialog;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    load_img = (Button)findViewById(R.id.load);
    img = (ImageView)findViewById(R.id.img);
    load_img.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        // TODO Auto-generated method stub
         new LoadImage().execute("http://www.learn2crack.com/wp-content/uploads/2014/04/node-cover-720x340.png");
      }
    });
  }
  private class LoadImage extends AsyncTask<String, String, Bitmap> {
    @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();
    }
       protected Bitmap doInBackground(String... args) {
         try {
               bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
        } catch (Exception e) {
              e.printStackTrace();
        }
      return bitmap;
       }
       protected void onPostExecute(Bitmap image) {
         if(image != null){
           img.setImageBitmap(image);
           pDialog.dismiss();
         }else{
           pDialog.dismiss();
           Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
         }
       }
   }
}