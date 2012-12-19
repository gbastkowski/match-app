package de.digitalstep.match.android.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Returns an {@code AbstractAccountAuthenticator}.
 * 
 * @author Gunnar Bastkowski (gunnar@digitalstep.de)
 */
public class AccountAuthenticatorService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
