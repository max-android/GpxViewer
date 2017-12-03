package com.primerexample.gpxviewer.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.primerexample.gpxviewer.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Максим on 01.12.2017.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private List<String> pathList;

    private final FileClickListener listener;


    public FileAdapter(List<String> pathList,  FileClickListener listener) {
        this.pathList = pathList;

        this.listener = listener;
    }


    public void remove(int position) {
        if (position < 0 || position >= pathList.size()) {
            return;
        }
        pathList.remove(position);
        notifyItemRemoved(position);
    }


    public void adOnSwiped(RecyclerView.ViewHolder viewHolder) {
        int swipedPosition = viewHolder.getAdapterPosition();
        pathList.remove(swipedPosition);
        notifyItemRemoved(swipedPosition);
    }


    public void adOnMove(int fromPos,int toPos) {

        Collections.swap(pathList,fromPos,toPos);
        notifyItemMoved(fromPos, toPos);
    }




    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());

        View view=inflater.inflate(R.layout.item_path,parent,false);

        return  new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(FileAdapter.ViewHolder holder, int position) {

        String path = pathList.get(position);
        holder.bindTo(path);

    }

    @Override
    public int getItemCount() {
        return pathList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        private final TextView tvPath;

        private String path;

        public ViewHolder(View itemView, final FileClickListener listener) {
            super(itemView);


            tvPath=(TextView) itemView.findViewById(R.id.tvPath);

            itemView.setOnClickListener(this::launchRoute);
        }


        private void launchRoute(View view){

            listener.onPathClick(path);

        }

        public  void bindTo(String path){

            this.path=path;
            tvPath.setText(path);

        }
    }


    public interface FileClickListener {


        void onPathClick(String path);
    }

}

