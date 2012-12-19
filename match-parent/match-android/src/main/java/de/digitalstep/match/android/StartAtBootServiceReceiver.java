package de.digitalstep.match.android;

import static android.content.Intent.ACTION_BOOT_COMPLETED;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Starts services on phone boot.
 * 
 * @author Gunnar Bastkowski (gunnar@digitalstep.de)
 */
public class StartAtBootServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			context.startService(new Intent("de.digitalstep.match.android.discovery.DiscoveryService"));
		}
	}

}