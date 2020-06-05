package com.example.hamiltontourism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//  These imports aimed to play YouTube video
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	//  Users will be asked to login if he tries to go to accommodation and shopping cart.
	//  After the user has successfully logged in, we need to know where he wanted to go
	private final static int REQUEST_CART = 0x01;
	private final static int REQUEST_ACCOMMODATION = 0x02;

	public boolean bSignedIn;
	public String email;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//  Initialize the ViewPager in the MainActivity
		ViewPager viewPager = findViewById(R.id.viewpager);
		viewPager.setPageMargin(80);

		List<Integer> list = new ArrayList<>();
		list.add(R.mipmap.island0);
		list.add(R.mipmap.island1);
		list.add(R.mipmap.island2);
		list.add(R.mipmap.island3);

		viewPager.setAdapter(new IslandPagerAdapter(this, list));
	}

	//  The MainActivity may be SHOWN by other Acitivities, for example, after successfully logged in
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK)
		{
			bSignedIn = true;

			email = data.getStringExtra("email");

			((AppCompatButton)findViewById(R.id.login)).setVisibility(View.GONE);

			switch (requestCode)
			{
				case REQUEST_CART:
				{
					Intent i = new Intent(MainActivity.this, ShoppingCartActivity.class);
					i.putExtra("email", email);
					startActivity(i);
					break;
				}

				case REQUEST_ACCOMMODATION:
				{
					Intent i = new Intent(MainActivity.this, AccommodationActivity.class);
					i.putExtra("email", email);
					startActivity(i);
					break;
				}
			}
		}
	}

	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.facebook:
			{
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/hamiltonisland")));
				break;
			}

			case R.id.instagram:
			{
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/accounts/login/?next=/hamiltonisland/")));
				break;
			}

			case R.id.twitter:
			{
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/hamiltonisland")));
				break;
			}

			case R.id.shoppingcart:
			{
				if (bSignedIn)
				{
					startActivity(new Intent(MainActivity.this, ShoppingCartActivity.class));
				}
				else
				{
					startActivityForResult(new Intent(MainActivity.this, SignInActivity.class), REQUEST_CART);
				}

				break;
			}

			case R.id.login:
			{
				if (bSignedIn)
				{
					Toast.makeText(getBaseContext(), "Already signed in ...", Toast.LENGTH_LONG).show();
				}
				else
				{
					startActivityForResult(new Intent(MainActivity.this, SignInActivity.class), 0);
				}

				break;
			}

			case R.id.accommodation:
			{
				if (bSignedIn)
				{
					startActivity(new Intent(MainActivity.this, AccommodationActivity.class));
				}
				else
				{
					startActivityForResult(new Intent(MainActivity.this, SignInActivity.class), REQUEST_ACCOMMODATION);
				}

				break;
			}

			case R.id.findBtn:
			{
				final YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
				youTubePlayerView.setVisibility(View.VISIBLE);
				youTubePlayerView.initialize(new YouTubePlayerInitListener() {
					@Override
					public void onInitSuccess(final YouTubePlayer initializedYouTubePlayer) {
						initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
							@Override
							public void onReady()
							{
								String videoId ="xVhnUS7Low0";
								initializedYouTubePlayer.loadVideo(videoId, 0);
							}
							//  Hide the video view after it is finished
							@Override
							public void onStateChange(int state)
							{
								if (state == 0)
								{
									youTubePlayerView.setVisibility(View.GONE);
								}
								super.onStateChange(state);
							}
						});
					}
				}, true);
				break;
			}
		}
	}
}