package com.phattrienungdungdidong.bookingteacherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private Context mContext;
    private List<Onboarding> onboardings;
    private ViewPager2 viewPager2;

    public OnboardingAdapter(Context mContext, List<Onboarding> onboardings, ViewPager2 viewPager2) {
        this.mContext = mContext;
        this.onboardings = onboardings;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.onboarding_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboarding(onboardings.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardings.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView onboardingImage;
        private TextView onboardingTitle, onboardingSubTitle;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            onboardingImage = itemView.findViewById(R.id.onboardingImage);
            onboardingTitle = itemView.findViewById(R.id.onboardingTitle);
            onboardingSubTitle = itemView.findViewById(R.id.onboardingSubTitle);
        }

        public void setOnboarding(Onboarding onboarding) {
            Glide.with(mContext).load(onboarding.getImg()).into(onboardingImage);
            onboardingImage.setImageResource(onboarding.getImg());
            onboardingTitle.setText(onboarding.getTitle());
            onboardingSubTitle.setText(onboarding.getSubTitle());
        }
    }
}
