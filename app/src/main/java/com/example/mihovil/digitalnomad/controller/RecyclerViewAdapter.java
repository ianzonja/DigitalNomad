package com.example.mihovil.digitalnomad.controller;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.models.Workspace;

import java.util.List;

/**
 * Created by Ian on 11/18/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.WorkspaceViewHolder> {

    public static class WorkspaceViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView workspaceName;
        public TextView workspaceAge;
        WorkspaceViewHolder(View itemView){
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            workspaceName = (TextView)itemView.findViewById(R.id.workspace_name);
            workspaceAge = (TextView) itemView.findViewById(R.id.workspace_age);
        }
    }

    List<Workspace> workspaces;

    public RecyclerViewAdapter(List<Workspace> workspaces){
        this.workspaces = workspaces;
    }

    @Override
    public WorkspaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        WorkspaceViewHolder wvh = new WorkspaceViewHolder(v);
        return wvh;
    }

    @Override
    public void onBindViewHolder(WorkspaceViewHolder holder, int position) {
        holder.workspaceName.setText(workspaces.get(position).name);
        holder.workspaceAge.setText(workspaces.get(position).age);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return workspaces.size();
    }
}
