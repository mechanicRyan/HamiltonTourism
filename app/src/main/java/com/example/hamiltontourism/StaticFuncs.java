package com.example.hamiltontourism;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class StaticFuncs
{
	private final static String MAIL_FROM = "s3551801@gmail.com";
	private final static String MAIL_PASS = "yky19930119";
	private final static String MAIL_SMTP = "smtp.gmail.com";

	public static void WriteSharedPref(Context context, String key, String value)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("HamiltonTourism", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.apply();
	}

	public static String ReadSharedPref(Context context, String key)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("HamiltonTourism", Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, "");
	}
}