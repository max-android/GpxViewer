package com.primerexample.gpxviewer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ProgressBar;
import com.primerexample.gpxviewer.R;
import com.primerexample.gpxviewer.entities.Data;
import com.primerexample.gpxviewer.manager.FileManager;
import com.primerexample.gpxviewer.utilities.Constants;
import com.primerexample.gpxviewer.utilities.Info;
import com.primerexample.gpxviewer.utilities.NetInspector;
import java.util.List;

public class FileActivity extends AppCompatActivity implements FileAdapter.FileClickListener {

    private FileManager fileManager;
    private RecyclerView recyclerFiles;
    private ProgressBar filesProgress;
    private Info message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        initialComponent();

           showListFiles();

        createItemTouch();
    }

    private void showListFiles() {

        Intent filesIntent = getIntent();

      List<String> files = filesIntent.getStringArrayListExtra(Constants.PATHS);

        transferDataToAdapter(files,this);
    }

    private void transferDataToAdapter(List<String> path,FileAdapter.FileClickListener listener){
        recyclerFiles.setAdapter(new FileAdapter(path,listener));
        filesProgress.setVisibility(View.INVISIBLE);
    }

    private void initialComponent(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.fileBar);
        setSupportActionBar(toolbar);
         CoordinatorLayout coordLayout=(CoordinatorLayout) findViewById(R.id.fileCoord);
        recyclerFiles=(RecyclerView)findViewById(R.id.rvFiles);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);
        recyclerFiles.setLayoutManager(mLayoutManager);
        recyclerFiles.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        filesProgress=(ProgressBar)findViewById(R.id.filesProgress);
        filesProgress.setVisibility(View.VISIBLE);
        message=new Info(coordLayout,this);
        fileManager = new FileManager(this);
    }

    private void showRoute(String path){

        filesProgress.setVisibility(View.VISIBLE);

        fileManager.readFile(path, result -> {

            if(result.isEmpty()){

                message.showMessage(getString(R.string.error_parsing));

            }else{

                launchRouteForMap(result);
            }} );
        filesProgress.setVisibility(View.INVISIBLE);
    }

private void launchRouteForMap(List<Data> dates){

    DataHolder.getInstance().setDataList(dates);

    Intent mapIntent=new Intent();

    setResult(RESULT_OK, mapIntent);
    finish();

}

    @Override
    public void onPathClick(String path) {

        if(NetInspector.isOnline(this)){

            showRoute(path);

        }else{

            message.showMessage(getString(R.string.no_internet));
        }
    }

    private void createItemTouch(){
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerFiles);
    }

    private ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            FileAdapter adapter =  (FileAdapter)recyclerFiles.getAdapter();
            adapter.adOnMove(fromPosition,toPosition);
            return true;
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            FileAdapter adapter =  (FileAdapter)recyclerFiles.getAdapter();
            adapter.adOnSwiped(viewHolder);
        }
    };
}
