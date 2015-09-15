# EasyFTP
A Simple Wrapper Class for Apache-commons FTPClient to Easily Upload/Download Any kind of File over FTP. This Library
is targeted for novice developers and providies very minimialistic and easily understandable Interface for FTP usage.

# FEATURES :
-> Simple and Minimialistic with Default Configurations.
-> Totally Customizable (with provisional Access to Apache-commons FTPClient Methods).
-> Ideal for Novice Developers with no FTP background Knowledge and experiance.
-> Demo application Source Code Included.

# USAGE : 
    All methods of library Throw Exceptions to be handeled in method-calling body. FTP operations should be performed
    in ASYNCTASK ( UI independent Thread ). Several use cases are discussed below :

## Dependency :

    build.gradle :
    
    Apache-commons FTPClient, .jar can be downloaded in from here :
    ```
        http://commons.apache.org/proper/commons-net/download_net.cgi
    ```
    
    How to include in Project : 
    ```
        http://stackoverflow.com/questions/8280594/how-to-import-org-apache-commons-net-ftp-ftpclient
    ```
        
    
## Connecting to FTP Server :
    
    Method : 
    ```    
    public void connect(String Address, String userName, String password); //using Default port 21
    ```
    
## Uploading to FTP Server :
    
    Methods :
    ```
    public void uploadFile(String uri); //By giving File Path of File existing on Device , to be uploaded.
    ```
    or
    ```
    public void uploadFile(InputStream srcFileStream, String name);  //By passing InputFileStream and FileName.Extention
    ```
    
    Example of Uploading some Resource Image (existing in res/drawable)
    ```
    
    class uploadTask extends AsyncTask<String, Void, String> {
    @Override
        protected String doInBackground(String... params) {
            try {
                easyFTP ftp = new easyFTP();
                ftp.connect("address","username","password");
                boolean status=false;
                status=ftp.setWorkingDirectory("Files/Uploads/Images"); // if User say provided any Destination then Set it , otherwise
                                                                        // Upload will be stored on Default /root level on server
                ftp.uploadFile(is,"test.png");
                return new String("Upload Successful");
            }catch (Exception e){
                String t="Failure : " + e.getLocalizedMessage();
                return t;
            }
        }
    }
    ```
    
# Downloading to From Server :
    
    Methods :
    ```
    public void downloadFile(String TargetFilePath, String destination);
    ```
    
    Example of Downloading Image (existing on Files/Uploads)
```
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
    
## Other Methods : 
```
    public void setFtpClient(FTPClient mFtpClient);       // Set Your own Customized FTPClient
    public String [] listName ();                         // Returns List of Files in current working directory
    public boolean setWorkingDirectory (String dir);      // Sets Working Directory
    public FTPClient getFtpClient()                       // Returns Private member FTPClient, Apache-commons FTPClient operations
                                                          // can be performed on this allowing complete customization.
    public void setTimeout (int seconds);                 // Sets Connection timout , default is 10 secs.
    public boolean makeDir(String dir);                   // Creates Directory on server at given path 
    public void disconnect();                             // Disconnects FTPClient
    ```
