package com.adeel.easyftp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.adeel.library.easyFTP;

import java.io.InputStream;


public class demo extends ActionBarActivity {
    private EditText ip,username,password,spath,dest,dir;
    private ProgressDialog prg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        //Removing Auto-focus of keyboard
        View view = this.getCurrentFocus();
        if (view != null) { //Removing on screen keyboard if still active
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        ip=(EditText)findViewById(R.id.address);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        spath=(EditText)findViewById(R.id.server_pth);
        dest=(EditText)findViewById(R.id.dest);
        dir=(EditText)findViewById(R.id.dir);
       // ip.setText("ftp.swfwmd.state.fl.us");
       // username.setText("Anonymous");
       // password.setText("abc@xyz.com");
       // dest.setText("/storage/emulated/0/Pictures/Screenshots/abc.png");
    }

    private boolean uploadCheck(){
        String str=ip.getText().toString();
        if (str.isEmpty()){
            Toast.makeText(this,"Enter server Address",Toast.LENGTH_LONG).show();
            return false;
        }
        str=username.getText().toString();
        if (str.isEmpty()){
            Toast.makeText(this,"Enter username",Toast.LENGTH_LONG).show();
            return false;
        }
        str=password.getText().toString();
        if (str.isEmpty()){
            Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean downloadCheck(){
        String str=spath.getText().toString();
        if (str.isEmpty()){
            Toast.makeText(this,"Enter path of File on Server",Toast.LENGTH_LONG).show();
            return false;
        }
        str=dest.getText().toString();
        if (str.isEmpty()){
            Toast.makeText(this,"Enter Destination Path",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public  void up(View v){
        if (!uploadCheck()){
            return;
        }
        String address=ip.getText().toString(),u=username.getText().toString(),p=password.getText().toString(),directory=dir.getText().toString();
        uploadTask async=new uploadTask();
        async.execute(address,u,p,directory);//Passing arguments to AsyncThread
    }

    public  void down(View v){
        if (!uploadCheck() || !downloadCheck()){
            return;
        }
        String address=ip.getText().toString(),u=username.getText().toString(),p=password.getText().toString(),serverPath=spath.getText().toString(),destination=dest.getText().toString();
        downloadTask async=new downloadTask();
        async.execute(address,u,p,serverPath,destination);//Passing arguments to AsyncThread
    }

    //* PERFORM Connectivity and upload/download independently from UI Thread (e.g async Task)
    //* Library Methods throw Exceptions so try/Catch is necessary
    //* Follwoing Code is tested on 2 servers with serveral file types upload/download
    class uploadTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            prg = new ProgressDialog(demo.this);
            prg.setMessage("Uploading...");
            prg.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                easyFTP ftp = new easyFTP();
                InputStream is=getResources().openRawResource(+R.drawable.easyftptest);
                ftp.connect(params[0],params[1],params[2]);
                boolean status=false;
                if (!params[3].isEmpty()){
                    status=ftp.setWorkingDirectory(params[3]); // if User say provided any Destination then Set it , otherwise
                }                                              // Upload will be stored on Default /root level on server
                ftp.uploadFile(is,"test.png");
                return new String("Upload Successful");
            }catch (Exception e){
                String t="Failure : " + e.getLocalizedMessage();
                return t;
            }
        }

        @Override
        protected void onPostExecute(String str) {
            prg.dismiss();
            Toast.makeText(demo.this,str,Toast.LENGTH_LONG).show();
        }
    }

    class downloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            prg = new ProgressDialog(demo.this);
            prg.setMessage("Uploading...");
            prg.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                easyFTP ftp = new easyFTP();
                InputStream is=getResources().openRawResource(+R.drawable.easyftptest);
                ftp.connect(params[0],params[1],params[2]);
                ftp.downloadFile(params[3],params[4]);
                return new String("Download Successful");
            }catch (Exception e){
                String t="Failure : " + e.getLocalizedMessage();
                return t;
            }
        }

        @Override
        protected void onPostExecute(String str) {
            prg.dismiss();
            Toast.makeText(demo.this,str,Toast.LENGTH_LONG).show();
        }
    }
}
