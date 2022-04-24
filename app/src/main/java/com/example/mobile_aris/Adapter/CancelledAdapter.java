package com.example.mobile_aris.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mobile_aris.Classes.Appointments.cancelled_tab;
import com.example.mobile_aris.R;
import com.example.mobile_aris.models.cancelledModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class CancelledAdapter extends RecyclerView.Adapter<CancelledAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<cancelledModel> mExampleList;
    private cancelled_tab mListener;
    String id,token;
    private SwipeRefreshLayout refreshLayout;

    public interface OnItemClickListener {
        void onRefresh();

        void onItemClick(int position);
    }

    public void setOnItemClickListener(cancelled_tab listener) {
        mListener = listener;
    }

    public CancelledAdapter(Context context, ArrayList<cancelledModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }


    @Override
    public CancelledAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_cancelled, parent, false);
        SharedPreferences info = mContext.getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");
        id = info.getString("_id", "");
        return new CancelledAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CancelledAdapter.ViewHolder holder, int position) {
        cancelledModel currentItem = mExampleList.get(position);

//        String pid = currentItem.getPId();
        String pdate = currentItem.getPDate();
        String ptime = currentItem.getPTime();
        String ppurpose = currentItem.getPPurpose();
        String pstatus = currentItem.getPStatus();
        String pclinic = currentItem.getPClinic();


        holder.mTextViewTitle.setText(pstatus);
        holder.mTextViewCreator.setText("Date: " + pdate);
        holder.mTextViewLikes.setText("Time: " + ptime);
        holder.mTextViewGender.setText("Purpose: " + ppurpose);
//        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
        holder.mTextViewCreatedAt.setText("Status: " + pstatus);
        holder.mTextViewSpecie.setText("Clinic: " + pclinic);
//        holder.mTextViewBreed.setText("Breed" + breed);
//        holder.mTextViewColor.setText("Color" + color);

//        Picasso.get(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);

    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;
        public TextView mTextViewGender;
        public TextView mTextViewCreatedAt;
        public TextView mTextViewSpecie;
        public TextView mTextViewBreed;
        public TextView mTextViewColor;
        public MaterialButton medit;
        public MaterialButton editbutton;
        public MaterialButton deletebutton;

        public ViewHolder(View itemView) {
            super(itemView);


//            editbutton= itemView.findViewById(R.id.editPet);
            deletebutton= itemView.findViewById(R.id.deleteApp);
//            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewTitle = itemView.findViewById(R.id.txttTitle);
            mTextViewCreator = itemView.findViewById(R.id.app_date);
            mTextViewLikes = itemView.findViewById(R.id.app_time);
            mTextViewGender = itemView.findViewById(R.id.app_purpose);
            mTextViewCreatedAt = itemView.findViewById(R.id.app_status);
            mTextViewSpecie = itemView.findViewById(R.id.app_clinic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
