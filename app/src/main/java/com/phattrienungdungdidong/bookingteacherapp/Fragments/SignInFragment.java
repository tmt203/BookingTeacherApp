package com.phattrienungdungdidong.bookingteacherapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.phattrienungdungdidong.bookingteacherapp.HomeScreenActivity;
import com.phattrienungdungdidong.bookingteacherapp.R;

public class SignInFragment extends Fragment {

    private EditText etSignInEmail;
    private TextInputEditText tieSignInPassword;
    private TextView tvSignUp, tvForgetPassword;
    private Button btnSignIn, btnGoogleSignIn;
    private ProgressBar signInProgressBar;
    private FirebaseAuth auth;

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    public static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        auth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext().getApplicationContext());
        if (account != null) {
            navigateToHomeScreenActivity();
        }


        clickListener();
    }

    private void init(View view) {
        etSignInEmail = view.findViewById(R.id.etSignInEmail);
        tieSignInPassword = view.findViewById(R.id.tieSignInPassword);
        tvSignUp = view.findViewById(R.id.tvSignUp);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        signInProgressBar = view.findViewById(R.id.signInProgressBar);
        tvForgetPassword = view.findViewById(R.id.tvForgetPassword);
        btnGoogleSignIn = view.findViewById(R.id.btnGoogleSignIn);

    }

    private void clickListener() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etSignInEmail.getText().toString();
                String password = tieSignInPassword.getText().toString();

                if (email.isEmpty() || email.matches(EMAIL_REGEX)) {
                    etSignInEmail.setError("Vui lòng nhập email!");
                    return;
                }
                if (password.isEmpty() || password.length() < 6) {
                    tieSignInPassword.setError("Vui lòng nhập mật khẩu!");
                    return;
                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                if (!user.isEmailVerified()) {
                                    Toast.makeText(getContext(), "Vui lòng xác thực email!",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                signInProgressBar.setVisibility(View.GONE);
                                String exception = task.getException().getMessage();
                                Toast.makeText(getContext(), "Lỗi " + exception,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });

                navigateToHomeScreenActivity();
            }
        });

        btnGoogleSignIn.setOnClickListener(view -> {
            Intent signInIntent = gsc.getSignInIntent();
            startActivityForResult(signInIntent, 1000);
        });

        tvSignUp.setOnClickListener(view -> {
            Fragment fragment = new SignUpFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flActivityFragmentReplace, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToHomeScreenActivity();
            } catch (ApiException e) {
                Toast.makeText(getContext().getApplicationContext(), "Lỗi bất định", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void navigateToHomeScreenActivity() {
        if (getActivity() == null)
            return;
        startActivity(new Intent(getContext().getApplicationContext(), HomeScreenActivity.class));
        getActivity().finish();
    }
}