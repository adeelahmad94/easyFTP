[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-EasyFTP-green.svg?style=flat)](https://android-arsenal.com/details/1/2499)
# EasyFTP
A Simple Wrapper Class for Apache-commons FTPClient to Easily Upload/Download Any kind of File over FTP. This Library
is targeted for novice developers and providies very minimialistic and easily understandable Interface for FTP usage.

# FEATURES :
* Simple and Minimialistic with Default Configurations.
* Totally Customizable (with provisional Access to Apache-commons FTPClient Methods).
* Ideal for Novice Developers with no FTP background Knowledge and experiance.
* Demo application Source Code Included.

# Demo & Testing :
You can [Download APK](http://www.mediafire.com/download/aqv09p5e9z7uvk3/app-debug.apk) for testing. This Demo app. will upload a test.png image file on specified server and downloads specified file to your device for server.


![alt tag](http://s8.postimg.org/wc2z7xqnp/image.png)         ![alt tag](http://s16.postimg.org/e1202pghx/image.png)

You can use your public FTP Servers for testing like [This one](https://www.swfwmd.state.fl.us/data/ftp/)

# USAGE : 
All methods of library Throw Exceptions to be handeled in method-calling body. FTP operations should be performed in ASYNCTASK ( UI independent Thread ). Several use cases are discussed below :

### Add this to your build.gradle dependenies of your module :
```java

dependencies {

    compile 'com.adeel:easyFTP:1.0'
    //...other dependencies 
}
```
Make sure that your top level build.gradle has JCenter() repo :

```java
allprojects {
    repositories {
        jcenter()
        //...other repos.
    }
}
```

## Connecting to FTP Server :
    
    Method : 
```java
    public void connect(String Address, String userName, String password); //using Default port 21
```
    
    
## Uploading to FTP Server :
    
    Methods :
```java
    public void uploadFile(String uri); //By giving File Path of File existing on Device , to be uploaded.
```
    OR
```java
    public void uploadFile(InputStream srcFileStream, String name);  //By passing InputFileStream and          //FileName.Extention
```
    
    
    Example of Uploading some Resource Image (existing in res/drawable)
```java
    class uploadTask extends AsyncTask<String, Void, String> {
    @Override
        protected String doInBackground(String... params) {
            try {
                easyFTP ftp = new easyFTP();
                ftp.connect("address","username","password");
                boolean status=false;
                status=ftp.setWorkingDirectory("Files/Uploads/Images"); // if User say provided any Destination then Set it , otherwise
                                                                        // Upload will be stored on Default /root level on server
                InputStream is=getResources().openRawResource(+R.drawable.easyftptest);
                ftp.uploadFile(is,"test.png");
                return new String("Upload Successful");
            }catch (Exception e){
                String t="Failure : " + e.getLocalizedMessage();
                return t;
            }
        }
    }
```
    
## Downloading to From Server :
    
    Methods :
```java
    public void downloadFile(String TargetFilePath, String destination);
```
    
    Example of Downloading Image (existing on Files/Uploads)
```java
    class downloadTask extends AsyncTask<String, Void, String> {
    @Override
        protected String doInBackground(String... params) {
            try {
                easyFTP ftp = new easyFTP();
                InputStream is=getResources().openRawResource(+R.drawable.easyftptest);
                ftp.connect("address","username","password");
                ftp.downloadFile( "Files/Uploads/test.png" , "downloaded_image.png" );
                return new String("Download Successful");
            }catch (Exception e){
                String t="Failure : " + e.getLocalizedMessage();
                return t;
            }
        
    }
```
## Compression (Mode-Z) : 

You can send a File in compress mode which can reduce size of certain files to speed up transfers. [Read more about it]( http://stackoverflow.com/questions/26420836/does-apache-ftp-client-compress-files-while-transfer)

## Other Methods :

```java
    public void setFtpClient(FTPClient mFtpClient);       // Set Your own Customized FTPClient
    public String [] listName ();                         // Returns List of Files in current working directory
    public boolean setWorkingDirectory (String dir);      // Sets Working Directory
    public FTPClient getFtpClient()                       // Returns Private member FTPClient, Apache-commons FTPClient operations
                                                          // can be performed on this allowing complete customization.
    public void setTimeout (int seconds);                 // Sets Connection timout , default is 10 secs.
    public boolean makeDir(String dir);                   // Creates Directory on server at given path 
    public void disconnect();                             // Disconnects FTPClient
```
  
## LICENSE 

    Copyright 2015-2016 ADEEL AHMAD
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
        
        http://www.apache.org/licenses/LICENSE-2.0
        
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
