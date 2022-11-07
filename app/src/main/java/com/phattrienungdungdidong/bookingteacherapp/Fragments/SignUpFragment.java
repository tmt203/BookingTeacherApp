package com.phattrienungdungdidong.bookingteacherapp.Fragments;

import static com.phattrienungdungdidong.bookingteacherapp.FragmentReplacerActivity.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.phattrienungdungdidong.bookingteacherapp.FragmentReplacerActivity;
import com.phattrienungdungdidong.bookingteacherapp.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    private EditText etName, etEmail, etPhone;
    private TextInputEditText tiePassword;
    private TextView tvLogin;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    public static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        clickListener();
    }

    private void init(View view) {
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        tiePassword = view.findViewById(R.id.tiePassword);
        tvLogin = view.findViewById(R.id.tvLogin);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        etPhone = view.findViewById(R.id.etPhone);
        progressBar = view.findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
    }

    private void clickListener() {

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentReplacerActivity) getActivity()).setFragment(new SignInFragment());
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etName.getText().toString();
                String password = tiePassword.getText().toString();
                String phone = etName.getText().toString();

                if (name.isEmpty() || name.equals("")) {
                    etName.setError("Vui lòng nhập tên!");
                    return;
                }
                if (email.isEmpty() || email.matches(EMAIL_REGEX)) {
                    etEmail.setError("Vui lòng nhập email!");
                    return;
                }
                if (password.isEmpty() || password.length() < 6) {
                    tiePassword.setError("Vui lòng nhập mật khẩu!");
                    return;
                }
                if (phone.isEmpty() || phone.equals("")) {
                    etPhone.setError("Vui lòng nhập số điện thoại!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                createAccount(name, email, password);
            }
        });

    }

    private void createAccount(String name, String email, String password) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Email xác thực đã được gửi", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                            uploadUser(user, name, email);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            String exception = task.getException().getMessage();
                            Toast.makeText(getContext(), "Lỗi " + exception, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    private void uploadUser(FirebaseUser user, String name, String email) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("profileImage", " ");
        map.put("uid", user.getUid());

        FirebaseFirestore.getInstance().collection("Users").document(user.getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            assert getActivity() != null;
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getActivity().getApplicationContext(), SignInFragment.class));
                            getActivity().finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            String exception = task.getException().getMessage();
                            Toast.makeText(getContext(), "Lỗi " + exception, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

}