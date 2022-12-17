package com.example.myapplication.smartWallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignupActivity extends Activity {

    private static final String TAG = "smartwallet-login";

    // views
    private TextView tStatus;
    private TextView tDetail;
    private EditText eEmail;
    private EditText ePass;
    private ProgressBar pLoading;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);

        // views
        tStatus = (TextView) findViewById(R.id.tStatus);
        tDetail = (TextView) findViewById(R.id.tDetail);
        eEmail = (EditText) findViewById(R.id.eEmail);
        ePass = (EditText) findViewById(R.id.ePass);
        pLoading = (ProgressBar) findViewById(R.id.pLoading);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    setResult(RESULT_OK);
                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        if (!task.isSuccessful())
                            Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();

                        hideProgressDialog();
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                        }

                        if (!task.isSuccessful()) {
                            tStatus.setText("Authentication failed");
                        }

                        Intent intent = new Intent(getApplicationContext(), MainWallet.class);
                        intent.putExtra("user", email);
                        intent.putExtra("pass", password);
                        setResult(RESULT_OK);
                        //finish();


                        hideProgressDialog();
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = eEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            eEmail.setError("Required.");
            valid = false;
        } else {
            eEmail.setError(null);
        }

        String password = ePass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ePass.setError("Required.");
            valid = false;
        } else {
            ePass.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            tStatus.setText("Email user: " + user.getEmail());
            tDetail.setText("Firebase user: " + user.getUid());

            findViewById(R.id.lSignIn).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.bSignOut).setVisibility(View.VISIBLE);
        } else {
            tStatus.setText("Signed out");
            tDetail.setText(null);

            findViewById(R.id.lSignIn).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.bSignOut).setVisibility(View.GONE);
        }
    }

    private void googleSignIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
            if(acc!= null) {
                if (acc.getIdToken() != null) {
                    AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
                    mAuth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithCredential:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                                        updateUI(null);
                                    }
                                }
                            });
                }else{
                    //Toast.makeText(getApplicationContext(), "IDtoken null", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "" + acc.getIdToken() , Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public void clicked(View v) {
        switch (v.getId()) {
            case R.id.bRegister:
                createAccount(eEmail.getText().toString(), ePass.getText().toString());
                break;
            case R.id.bSignIn:
                signIn(eEmail.getText().toString(), ePass.getText().toString());
                break;
            case R.id.bSignOut:
                signOut();
                break;
            case R.id.bGoogleSignIn:
                googleSignIn();
                break;
        }
    }

    private void showProgressDialog() {
        pLoading.setVisibility(View.VISIBLE);
    }

    private void hideProgressDialog() {
        pLoading.setVisibility(View.GONE);
    }
}