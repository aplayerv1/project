package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class jsonRead extends AsyncTask<String, Integer, String> {
    private static final String TAG_TITLE = "title";
    private static final String TAG_URL = "link";
    private static final String TAG_DESCRIPTION = "description";
    ListView lv;
    Context context;
    ArrayList<Data> data = new ArrayList<>();

    jsonRead(Context context,ListView lv){
        this.context = context;
        this.lv = lv;
    }


    @Override
    protected String doInBackground(String... strings) {
        URL BaseURL = null;
        try {

            BaseURL = new URL("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
            XmlPullParser xpp = XmlPullParserFactory.newInstance().newPullParser();
            HttpURLConnection http = (HttpURLConnection) BaseURL.openConnection();
            http.connect();
            if (http.getResponseCode() != HttpURLConnection.HTTP_OK) {
            //    Log.e(TAG, "run: http request error");
                return null;
            }
         //   Log.e(TAG, "run: RUN"+" "+BaseURL);

            xpp.setInput(new InputStreamReader(BaseURL.openStream()));
         //   Log.e(TAG, "RUN "+xpp.nextTag());
            int eventType = xpp.getEventType();
            String currentTag = null;
            Data data1 = null;
            String s = null;
            Boolean isItem = false;
            while (eventType != XmlPullParser.END_DOCUMENT){
                currentTag = xpp.getName();

                 if(eventType == XmlPullParser.START_TAG) {
                  //  Log.d(TAG, "run: version string " + xpp.getName());
                    if(xpp.getName().equals("item")) {
                        data1 = new Data();
                        isItem = true;
                    }
                }else if(eventType == XmlPullParser.END_TAG){
                  //  Log.d(TAG, "run: version string " + xpp.getName())
                     if(isItem){

                    if (TAG_TITLE.equals(currentTag)) {
                        data1.setName(s);
                    }
                    else if ( TAG_URL.equals(currentTag)) {

                        data1.setUrl(s);

                    }
                   else if ( TAG_DESCRIPTION.equals(currentTag)) {


                        data1.setDescription(s);

                    }
                   else if(currentTag.equals("item")){
                        data.add(data1);
                        isItem=false;
                    }
                }

                 }else if(eventType == XmlPullParser.TEXT){
                   // Log.d(TAG, "run: version string " + xpp.getText().trim()+" "+currentTag);
                    s = xpp.getText();

                }

                eventType = xpp.next();

            }
            Log.d("Respond"," "+data.size());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        publishProgress();
      //  Log.d("Respond","Data Read "+data.size() );
        this.data=data;
        return null;
    }
        @Override
    public void onProgressUpdate(Integer...values){
        Base base = new Base(context,data);
        lv.setAdapter(base);
        base.notifyDataSetChanged();
    }
    public ArrayList getData(){
        return this.data;
    }

}