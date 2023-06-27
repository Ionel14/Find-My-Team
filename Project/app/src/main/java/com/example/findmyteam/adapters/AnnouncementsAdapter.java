package com.example.findmyteam.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyteam.R;
import com.example.findmyteam.helpers.OnItemClickListener;
import com.example.findmyteam.models.Announcement;
import com.example.findmyteam.models.Categories;
import com.example.findmyteam.models.Cities;

import java.util.List;

public class AnnouncementsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Announcement> announcements;

    private OnItemClickListener clickListener;
    public AnnouncementsAdapter(List<Announcement> announcements){this.announcements = announcements;}

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AnnouncementViewHolder viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement, parent, false);

        viewHolder = new AnnouncementViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AnnouncementViewHolder) {
            Announcement announcement = (Announcement) announcements.get(position);
            ((AnnouncementViewHolder)holder).bind(announcement);
        }
        Log.e("Adapter", "onBindViewHolder; position=" + position);
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    class AnnouncementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userNameTextView;
        TextView categoryTextView;
        TextView cityTextView;
        androidx.appcompat.widget.AppCompatButton favoriteButton;
        androidx.appcompat.widget.AppCompatButton moreInfo;

        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameTextView = itemView.findViewById(R.id.user_name);
            categoryTextView = itemView.findViewById(R.id.category);
            cityTextView = itemView.findViewById(R.id.city);

            favoriteButton = itemView.findViewById(R.id.add_to_favorite);
            moreInfo = itemView.findViewById(R.id.announcement_details);

            favoriteButton.setOnClickListener(this);
            moreInfo.setOnClickListener(this);
        }

        public void bind(Announcement announcement) {
            userNameTextView.setText(announcement.getTitle());
            categoryTextView.setText(Categories.Companion.fromValue(Integer.parseInt(announcement.getCategoryId())).toString());
            cityTextView.setText(Cities.Companion.fromNumber(Integer.parseInt(announcement.getLocationId())).toString());
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAbsoluteAdapterPosition());
        }
    }

}

