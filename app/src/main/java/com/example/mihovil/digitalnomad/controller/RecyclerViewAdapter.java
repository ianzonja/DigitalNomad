package com.example.mihovil.digitalnomad.controller;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.Interface.ClickListener;
import com.example.mihovil.digitalnomad.Interface.LongPressListener;
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
        public TextView workspaceCountry;
        public TextView workspaceTown;
        public ImageView workspaceImage;
        public int positionClicked;
        WorkspaceViewHolder(final View itemView){
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            workspaceName = (TextView)itemView.findViewById(R.id.recycler_workspace_name);
            workspaceCountry = (TextView) itemView.findViewById(R.id.recycler_workspace_country);
            workspaceTown = (TextView) itemView.findViewById(R.id.recycler_workspace_town);
            workspaceImage = (ImageView) itemView.findViewById(R.id.recycler_workspace_photo);
        }
    }

    List<Workspace> workspaces;
    LongPressListener longListener;
    ClickListener clickListener;

    public RecyclerViewAdapter(List<Workspace> workspaces, LongPressListener listener, ClickListener clistener){
        this.workspaces = workspaces;
        longListener = listener;
        clickListener = clistener;
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
        holder.workspaceCountry.setText(workspaces.get(position).country);
        holder.workspaceTown.setText(workspaces.get(position).town);
        holder.workspaceImage.setImageBitmap(workspaces.get(position).workspaceImage);
        holder.itemView.setLongClickable(true);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "Position is " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                holder.positionClicked = holder.getAdapterPosition();
                longListener.onLongPressAction(holder.getAdapterPosition());
                return false;
            }
        });
        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "Position is " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                holder.positionClicked = holder.getAdapterPosition();
                clickListener.onPressAction(holder.getAdapterPosition());
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
