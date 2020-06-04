package com.example.hamiltontourism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity
{
	private ProgressBar mBar;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		mBar = findViewById(R.id.loading);

		//  Initialize Firebase
		mAuth = FirebaseAuth.getInstance();
		mBar = findViewById(R.id.loading);
	}

	public void onSignIn(View view)
	{
		final String username = ((EditText)findViewById(R.id.username)).getText().toString();
		final String password = ((EditText)findViewById(R.id.password)).getText().toString();

		mBar.setVisibility(View.VISIBLE);

		//  Firstly try to create new user.
		//  If the e-mail exists, the create function will fail
		//  So I put the sign in function in the else part
		//  So, the if the e-mail is new, it will be used to register.
		//  If the e-mail is an existing one, it will be used to login
		mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
		{
			@Override
			public void onComplete(@NonNull Task<AuthResult> task)
			{
				try
				{
					if (task.isSuccessful())
					{
						mBar.setVisibility(View.GONE);

						Intent intent = getIntent();
						intent.putExtra("email", username);
						setResult(RESULT_OK,intent);
						finish();
					}
					else
					{
						mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
						{
							@Override
							public void onComplete(@NonNull Task<AuthResult> task)
							{
								mBar.setVisibility(View.GONE);

								try
								{
									if (task.isSuccessful())
									{
										Intent intent = getIntent();
										intent.putExtra("email", username);
										setResult(RESULT_OK,intent);
										finish();
									}
								}
								catch (Exception ignored){}
							}
						});
					}
				}
				catch (Exception ignored){}
			}
		});
	}
}