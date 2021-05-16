package com.example.studenttrackapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenttrackapp.R;
import com.example.studenttrackapp.model.StudentAppInfo;

import java.util.ArrayList;

public class StudentAppAdapter extends RecyclerView.Adapter<StudentAppAdapter.StudentAppViewHolder> {
    private final LayoutInflater mInflater;
    private final ArrayList<StudentAppInfo> mStudentAppInfo;

    public StudentAppAdapter(Context applicationContext, ArrayList<StudentAppInfo> mStudentAppInfo) {
        this.mInflater = LayoutInflater.from(applicationContext);
        this.mStudentAppInfo = mStudentAppInfo;
    }

    @NonNull
    @Override
    public StudentAppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.student_app_item, parent, false);
        return new StudentAppViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StudentAppAdapter.StudentAppViewHolder holder, int position) {
        holder.tvAppId.setText(mStudentAppInfo.get(position).getId());
        holder.tvAppName.setText(mStudentAppInfo.get(position).getAppName());
        holder.tvCreatedTime.setText(mStudentAppInfo.get(position).getCreateTime());
        holder.tvCourseName.setText(mStudentAppInfo.get(position).getCourseName());
        holder.tvStuId.setText(mStudentAppInfo.get(position).getStuId());
        holder.tvTechId.setText(mStudentAppInfo.get(position).getTechId());

    }

    @Override
    public int getItemCount() {
        return mStudentAppInfo.size();
    }

    public static class StudentAppViewHolder extends RecyclerView.ViewHolder {
        TextView tvAppId, tvAppName, tvCreatedTime, tvCourseName, tvStuId, tvTechId;

        public StudentAppViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAppId = itemView.findViewById(R.id.tvStudentAppId);
            tvAppName = itemView.findViewById(R.id.tvAppName);
            tvCreatedTime = itemView.findViewById(R.id.tvCreateTime);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvStuId = itemView.findViewById(R.id.tvStuId);
            tvTechId = itemView.findViewById(R.id.tvTeachId);
        }
    }
}
