package com.phattrienungdungdidong.bookingteacherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPagerOnboarding;
    private CircleIndicator3 circleIndicator;
    private OnboardingAdapter onboardingAdapter;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();

        viewPagerOnboarding = findViewById(R.id.viewPagerOnboarding);
        circleIndicator = findViewById(R.id.circleIndicator);
        btnStart = findViewById(R.id.btnStart);


        onboardingAdapter = new OnboardingAdapter(getApplicationContext(), fetchAllOnboarding(), viewPagerOnboarding);
        viewPagerOnboarding.setAdapter(onboardingAdapter);

        circleIndicator.setViewPager(viewPagerOnboarding);
        onboardingAdapter.registerAdapterDataObserver(circleIndicator.getAdapterDataObserver());

        btnStart.setOnClickListener(view -> {
            if (user == null) {
                startActivity(new Intent(OnboardingActivity.this, FragmentReplacerActivity.class));
            } else {
                startActivity(new Intent(OnboardingActivity.this, HomeScreenActivity.class));
            }
            finish();
        });
    }

    public List<Onboarding> fetchAllOnboarding() {
        List<Onboarding> onboardings = new ArrayList<>();
        onboardings.add(new Onboarding(R.drawable.img_onboarding_1, "Tìm gia sư tin cậy", "Phương pháp tối ưu nhất giúp học sinh yếu kém nhanh lấy lại căn bản, nâng cao kiến thức cho học sinh khá, giỏi."));
        onboardings.add(new Onboarding(R.drawable.img_onboarding_3, "Chọn gia sư giỏi", "Hỗ trợ phát triển hơn nữa trong quá trình học tập về các môn học phổ thông cũng như rèn luyện những kỹ năng trong cuộc sống!"));
        onboardings.add(new Onboarding(R.drawable.img_onboarding_2, "Học tại gia tiện lợi", "Đội ngũ giảng viên là các giáo viên uy tín và sinh viên ưu tú đáp ứng đủ nhu cầu học tập tại nhà cho học sinh."));
        return onboardings;
    }
}