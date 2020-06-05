package com.example.hamiltontourism;

public class MailT extends Thread
{
	private String mRecipient;
	private String mSubject;
	private String mContent;

	//  Sending an e-mail is obviously a network activity, which is normally considered as time-consuming.
	//  Encapsulate all the work to send an e-mail into a child thread to prevent blocking the UI thread

	MailT(String recipient, String subject, String content)
	{
		mRecipient = recipient;
		mSubject = subject;
		mContent = content;
	}

	public void run()
	{
		StaticFuncs.sendMail(mRecipient, mSubject, mContent);
	}
}