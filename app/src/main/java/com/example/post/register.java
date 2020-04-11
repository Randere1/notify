package com.example.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {
    private EditText email,pas,wad;
    private Button create;
    private FirebaseAuth mAuth;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.register_email);
        pas = (EditText) findViewById(R.id.register_password);
        wad = (EditText) findViewById(R.id.register_confirm);
        create = (Button) findViewById(R.id.register_account);
        progress = new ProgressDialog(this);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateANewAccount();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser onlineuser= mAuth.getCurrentUser();
        if (onlineuser!=null)
        {
            SendUserToMainActivity();
        }
    }

    private void SendUserToMainActivity() {
        Intent menIntent=new Intent(register.this,MainActivity.class);
        startActivity(menIntent);
    }



    private void CreateANewAccount() {
        String emaail=email.getText().toString();
        String password=pas.getText().toString();
        String confirmpassword=wad.getText().toString();

        if (TextUtils.isEmpty(emaail)){
            Toast.makeText(this,"please write your email",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"please write your password",Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(confirmpassword)){
            Toast.makeText(this,"please confirm your password",Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirmpassword) ){

            Toast.makeText(this,"passwords do not match",Toast.LENGTH_SHORT).show();

        }
        else {
            progress.setTitle("creating account");
            progress.setMessage("please wait! ....Creatin account");
            progress.show();
            progress.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(emaail,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                SendUserToSetupActivity();

                                Toast.makeText(register.this," Authenticated succesfuly:",Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }
                            else{
                                String message = task.getException().getMessage();
                                Toast.makeText(register.this," Error Occured:"+message,Toast.LENGTH_SHORT).show();

                                progress.dismiss();
                            }
                        }
                    });
        }
    }




    private void SendUserToSetupActivity() {
        Intent setIntent =new Intent(register.this,setprofile.class);
        startActivity(setIntent);
        finish();
    }
}
