package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

class jsonRead extends AsyncTask<String, Integer, String> {
    private static final String TAG_VERSION = "version";
    String line;
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
                Log.e(TAG, "run: http request error");
                return null;
            }
            Log.e(TAG, "run: RUN"+" "+BaseURL);

            xpp.setInput(new InputStreamReader(BaseURL.openStream()));
            Log.e(TAG, "RUN "+xpp.nextTag());
            int eventType = xpp.getEventType();
            String currentTag = null;
            while (eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_DOCUMENT){

                }else if(eventType == XmlPullParser.START_TAG) {
                    currentTag = xpp.getName();
                    Log.d(TAG, "run: version string " + xpp.getName());

                }else if(eventType == XmlPullParser.END_TAG){
                    Log.d(TAG, "run: version string " + xpp.getName());

                } else if(eventType == XmlPullParser.TEXT){
                    Log.d(TAG, "run: version string " + xpp.getText().trim());
                    if (currentTag != null && TAG_VERSION.equals(currentTag)) {
                        currentTag = null;
                        Log.d(TAG, "run: version string " + xpp.getText().trim());
                        data.add(new Data(xpp.getText().trim()));
                    }
                }
                eventType = xpp.next();

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        publishProgress();
        return null;
    }
        @Override
    public void onProgressUpdate(Integer...values){
        Log.d("Respond","onProgressUpdate jSon  > "+data.toString());
        Base base = new Base(context,data);
        lv.setAdapter(base);
        base.notifyDataSetChanged();
    }
    public ArrayList getData(){
        return this.data;
    }

}