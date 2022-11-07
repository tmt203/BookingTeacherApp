package com.phattrienungdungdidong.bookingteacherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.phattrienungdungdidong.bookingteacherapp.Fragments.SignInFragment;
import com.phattrienungdungdidong.bookingteacherapp.Fragments.SignUpFragment;

public class FragmentReplacerActivity extends AppCompatActivity {

    private FrameLayout flActivityFragmentReplace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_replace);
        flActivityFragmentReplace = findViewById(R.id.flActivityFragmentReplace);

        setFragment(new SignInFragment());
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        if (fragment instanceof SignUpFragment) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(flActivityFragmentReplace.getId(), fragment);
        fragmentTransaction.commit();
    }
}