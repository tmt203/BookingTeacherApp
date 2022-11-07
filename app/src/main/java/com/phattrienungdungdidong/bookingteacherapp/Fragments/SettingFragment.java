package com.phattrienungdungdidong.bookingteacherapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.phattrienungdungdidong.bookingteacherapp.FragmentReplacerActivity;
import com.phattrienungdungdidong.bookingteacherapp.OnboardingActivity;
import com.phattrienungdungdidong.bookingteacherapp.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingFragment extends Fragment {

    private CircleImageView ciSettingUserAvatar;
    private TextView tvSettingSignOut, tvSettingUserName;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    private String userName, userEmail, userPhone;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext().getApplicationContext());
        if (account != null) {
            String userName = account.getDisplayName();
            String userEmail = account.getEmail();
            tvSettingUserName.setText(userName);
            ciSettingUserAvatar.setImageResource(R.drawable.default_avatar);
        }

        clickListener();
    }

    private void clickListener() {
        tvSettingSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(getContext().getApplicationContext(), FragmentReplacerActivity.class));
                getActivity().finish();

            }
        });
    }

    private void init(View view) {
        tvSettingSignOut = view.findViewById(R.id.tvSettingSignOut);
        ciSettingUserAvatar = view.findViewById(R.id.ciSettingUserAvatar);
        tvSettingUserName = view.findViewById(R.id.tvSettingUserName);
    }


}