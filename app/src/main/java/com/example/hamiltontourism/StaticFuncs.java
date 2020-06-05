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

	static void sendMail(String recipient, String subject, String content)
	{
		try
		{
			Properties properties = System.getProperties();

			properties.put("mail.smtp.host", MAIL_SMTP);

			properties.put("mail.debug", "true");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.EnableSSL.enable", "true");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.socketFactory.port", "465");

			Session mailSession = Session.getInstance(properties, new Authenticator()
			{
				@Override
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(MAIL_FROM, MAIL_PASS);
				}
			});

			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(MAIL_FROM));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(content);

			Transport.send(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}