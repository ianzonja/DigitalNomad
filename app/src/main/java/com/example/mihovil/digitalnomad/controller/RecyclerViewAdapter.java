package com.example.mihovil.digitalnomad.controller;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.Interface.LongPressListener;
import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.fragments.PopUpWorkspacesMessage;
import com.example.mihovil.digitalnomad.models.Workspace;

import java.security.AccessController;
import java.util.List;

/**
 * Created by Ian on 11/18/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.WorkspaceViewHolder> {

    public static class WorkspaceViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView workspaceName;
        public TextView workspaceAge;
        public int positionClicked;
        WorkspaceViewHolder(final View itemView){
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            workspaceName = (TextView)itemView.findViewById(R.id.workspace_name);
            workspaceAge = (TextView) itemView.findViewById(R.id.workspace_age);
        }
    }

    List<Workspace> workspaces;
    LongPressListener listener;

    public RecyclerViewAdapter(List<Workspace> workspaces, LongPressListener listener){
        this.workspaces = workspaces;
        this.listener = listener;
    }

    @Override
    public WorkspaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        WorkspaceViewHolder wvh = new WorkspaceViewHolder(v);
        return wvh;
    }

    @Override
    public void onBindViewHolder(final WorkspaceViewHolder holder, int position) {
        holder.workspaceName.setText(workspaces.get(position).name);
        holder.workspaceAge.setText(workspaces.get(position).age);
        holder.itemView.setLongClickable(true);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "Position is " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                holder.positionClicked = holder.getAdapterPosition();
                listener.onLongPressAction(holder.getAdapterPosition());
                return false;
            }
        });
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
