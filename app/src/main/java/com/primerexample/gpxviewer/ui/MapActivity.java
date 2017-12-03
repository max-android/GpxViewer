package com.primerexample.gpxviewer.ui;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.primerexample.gpxviewer.R;
import com.primerexample.gpxviewer.entities.Data;
import com.primerexample.gpxviewer.manager.FileManager;
import com.primerexample.gpxviewer.utilities.Constants;
import com.primerexample.gpxviewer.utilities.Info;
import com.primerexample.gpxviewer.utilities.ReadPermission;
import java.util.ArrayList;
import java.util.List;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FloatingActionButton fabSearch;
    private MapView mapView;
    private GoogleMap gMap;
    private ProgressBar mapProgress;
    private FileManager fileManager;
    private Info message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initialMap(savedInstanceState);

        initialComponent();

        ReadPermission.requestPermission(this,fabSearch);

        launchSearchFiles();

    }

    private void initialMap(Bundle savedInstanceState){

        mapView=(MapView)findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        MapsInitializer.initialize(this);
    }

    private void  initialComponent(){

        CoordinatorLayout coordLayout=(CoordinatorLayout) findViewById(R.id.mapCoord);

        mapProgress=(ProgressBar)findViewById(R.id.mapProgress);
        mapProgress.setVisibility(View.INVISIBLE);

        fileManager=new FileManager(this);

        message=new Info(coordLayout,this);
        fabSearch=(FloatingActionButton) findViewById(R.id.fab);
        fabSearch.hide();

    }

    private void launchSearchFiles(){
        mapProgress.setVisibility(View.VISIBLE);
        fabSearch.setOnClickListener(view -> searchFiles());
        mapProgress.setVisibility(View.INVISIBLE);
    }

    private void searchFiles() {

        fileManager.searchGpxFile(result -> {

                 if(result.isEmpty()){

                     message.showMessage(getString(R.string.not_gpx));

                 }else {

                     launchListFiles(result);
                 }
        });

    }


    private  void launchListFiles(List<String> paths){

        Intent fileIntent = new Intent(MapActivity.this,FileActivity.class);
        fileIntent.putExtra(Constants.PATHS,new ArrayList<>(paths));
        startActivityForResult(fileIntent,Constants.REQUEST_CODE_GPX);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case Constants.READ_FILE_PERMISSION_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    message.showMessage(getString(R.string.permission_granted));

                    fabSearch.show();

                }else{

                    message.showMessage(getString(R.string.permission_denied));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            switch (requestCode){
                case Constants.REQUEST_CODE_GPX:

                    List<Data> dataList = DataHolder.getInstance().getDataList();

                    showRouteOnMap(dataList);

                    break;
            }
        } else {

            message.showMessage(getString(R.string.error_result));

        }
    }

    private void showRouteOnMap(List<Data> dataList) {

        mapProgress.setVisibility(View.VISIBLE);

        for(Data date:dataList) {

            LatLng point = new LatLng(date.getLatitude(), date.getLongitude());

            gMap.addMarker(new MarkerOptions().position(point).title(Constants.ROUTER));

            gMap.moveCamera(CameraUpdateFactory.newLatLng(point));

        }
        mapProgress.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap=googleMap;

    }

    @Override
    protected void onStart() {
        mapView.onStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();

    }
}
