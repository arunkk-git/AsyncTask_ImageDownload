package tech.sree.com.asynctask_imagedownload;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    String imageUrl ="https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQZHH9Fp_xgdhayc2n47D62pr3WKNdtp9FesZGMYfl8cjoq_-nSim2oyZU";//"http://3.bp.blogspot.com/-QZFjkU-dDwE/VJwpXIAi99I/AAAAAAAABlU/kPSB2MkOOY8/s1600/Output.jpg";
    Button download ;
    ImageView imageView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        download = (Button)findViewById(R.id.btn);
        imageView = (ImageView)findViewById(R.id.image);
        progressBar = (ProgressBar)findViewById(R.id.progess);

    }
    public void showImage(View V){

        MyDownloadTask myDownloadTask = new MyDownloadTask();
        myDownloadTask.execute(imageUrl);
    }

    class MyDownloadTask extends AsyncTask<String,Integer,String>{

        String File_PATH = "/sdcard/DEMO_IMAGE/Async_Image_Download.jpg";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            String urlPath = params[0];
            int contentSize ;
            try {
                URL url = new URL(urlPath);
                URLConnection urlConnection =  url.openConnection();
                contentSize = urlConnection.getContentLength();
                File newFolder = new File("/sdcard/DEMO_IMAGE");
                if(!newFolder.exists()){
                    newFolder.mkdir();
                }
                File fileName  = new File(newFolder,"Async_Image_Download.jpg");


                InputStream inputStream =  new BufferedInputStream(url.openStream(),8*1024);
                byte[] data =  new byte[1024];
                int readCount =0;
                int Total = 0;
                OutputStream outputStream = new FileOutputStream(fileName);

                while((readCount = inputStream.read(data)) != -1 ){
                    Total +=readCount;
                    outputStream.write(data,0,readCount);
                    int progress  =  Total * 100 /contentSize ;
                    publishProgress(progress);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Image Download Completed !!!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress =  values[0];

            progressBar.setProgress(progress);

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            imageView.setImageDrawable(Drawable.createFromPath(File_PATH));

        }
    }
}


