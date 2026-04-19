package com.example.clear2go;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clear2go.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DatabaseReference databaseRef;
    private static final String TAG="GOOGLE_SIGN_IN_TAG";
    private GoogleSignInClient googleSignInClient;
    private ActivityResultLauncher<Intent> signInLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        checkuser();
        FirebaseApp.initializeApp(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        databaseRef = FirebaseDatabase.getInstance().getReference().child("messages");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        signInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleSignInResult(result));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        binding.loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: begin google sigh in");
                Intent intent = googleSignInClient.getSignInIntent();
                signInLauncher.launch(intent);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "my screen classs");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "my custom screen name");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "pana mea");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    private void checkuser()
    {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            startActivity(new Intent(this,ProfileActivity.class));
            Log.d(TAG, "checkuser: already signed in");
            finish();
        }
    }

    private void handleSignInResult(ActivityResult result) {
        Log.d(TAG, "handleSignInResult: resultCode=" + result.getResultCode() + " data=" + (result.getData() != null));
        Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
        try {
            GoogleSignInAccount account = accountTask.getResult(ApiException.class);
            Log.d(TAG, "handleSignInResult: got account " + account.getEmail());
            firebaseAuthWithGoogleAcc(account);
        } catch (ApiException e) {
            Log.e(TAG, "handleSignInResult: ApiException statusCode=" + e.getStatusCode() + " message=" + e.getMessage(), e);
        } catch (Exception e) {
            Log.e(TAG, "handleSignInResult: exception", e);
        }
    }

    private void firebaseAuthWithGoogleAcc(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogleAcc: begin firebase auth with google acc");
        AuthCredential authCredential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(authCredential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "onSuccess: logged in");
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();
                        Log.d(TAG, "onSuccess: Email"+email);
                        Log.d(TAG, "onSuccess: uid"+uid);
                        if(authResult.getAdditionalUserInfo().isNewUser())
                        {
                            Log.d(TAG, "onSuccess: account created...\n"+email);
                            Toast.makeText(MainActivity.this,"Accout created...\n"+email,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d(TAG, "onSuccess: existing user");
                            Toast.makeText(MainActivity.this,"existing user...\n"+email,Toast.LENGTH_SHORT).show();

                        }

                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: loggin failed");
                    }
                });
    }

    public void writeMessageToDatabase(DatabaseReference databaseRef, String message) {
        // Set a new value for the "messages" node in the database
        databaseRef.setValue(message);
    }
}