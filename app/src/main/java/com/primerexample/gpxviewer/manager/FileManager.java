package com.primerexample.gpxviewer.manager;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.primerexample.gpxviewer.entities.Data;
import com.primerexample.gpxviewer.ui.Func;
import com.primerexample.gpxviewer.utilities.Constants;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.ticofab.androidgpxparser.parser.GPXParser;
import io.ticofab.androidgpxparser.parser.domain.Gpx;
import io.ticofab.androidgpxparser.parser.domain.Track;
import io.ticofab.androidgpxparser.parser.domain.TrackPoint;
import io.ticofab.androidgpxparser.parser.domain.TrackSegment;


public class FileManager {

    private Handler handler;
    private Context context;

    public FileManager(Context context) {
           this.context=context;
        handler=new Handler(Looper.getMainLooper());
    }


    public void searchGpxFile(Func<List<String>> transfer){

        List<String> paths=new ArrayList<>();
        List<String> res=new ArrayList<>();

        new Thread(()-> {

            FileFinder fileFinder=new FileFinder();

        String rootPath =  Environment.getExternalStorageDirectory().getAbsolutePath();

        File topDirectory = new File(rootPath);

            res.addAll(fileFinder.search(topDirectory ,paths,Constants.SEARCH));

            handler.post(()->{transfer.transferResult(res);});


        }).start();
    }


    public void readFile(String path,Func<List<Data>> transfer){

        ArrayList<Data> dates= new ArrayList<>();


        new Thread(()->{

            File gpxFile = new File(path);

            GPXParser mParser = new GPXParser();

            Gpx parsedGpx = null;

            FileInputStream inputStream=null;
            try {

                 inputStream=new FileInputStream(gpxFile);

                parsedGpx = mParser.parse(inputStream);

                inputStream.close();
            } catch (XmlPullParserException e) {
                Log.d("XmlPullParserException", e.getMessage());

            } catch (IOException e) {
                Log.d("IOException", e.getMessage());
            }finally{
                      try{
                            if(inputStream!=null) inputStream.close();

                            } catch(IOException ex) {

                          Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();

                      } }

            if (parsedGpx != null) {


               dates.addAll(convertXmlToList(parsedGpx));

            } else {
                Log.e("Error", "Error parsing gpx track!");
            }


            handler.post(()->{transfer.transferResult(dates);});

        }).start();

    }


    private  ArrayList<Data> convertXmlToList(Gpx parsedGpx){

        ArrayList<Data> dates=new ArrayList<>();

        List<Track> tracks = parsedGpx.getTracks();
        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);

            List<TrackSegment> segments = track.getTrackSegments();
            for (int j = 0; j < segments.size(); j++) {
                TrackSegment segment = segments.get(i);

                for (TrackPoint trackPoint : segment.getTrackPoints()) {

                    dates.add(new Data(trackPoint.getLatitude(), trackPoint.getLongitude()));
                }
            }
        }
    return dates;
    }


}
