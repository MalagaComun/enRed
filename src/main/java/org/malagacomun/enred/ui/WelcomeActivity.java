package org.malagacomun.enred.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.malagacomun.enred.Config;
import org.malagacomun.enred.R;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		if (Config.DISALLOW_REGISTRATION_IN_UI == false) {
			final Button createAccount = (Button) findViewById(R.id.create_account);
			createAccount.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(WelcomeActivity.this, MagicCreateActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(intent);
				}
			});
		}
		final Button useOwnProvider = (Button) findViewById(R.id.use_own_provider);
		useOwnProvider.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(WelcomeActivity.this, EditAccountActivity.class));
			}
		});

	}

}
