package de.digitalstep.match.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.common.collect.Lists;

import de.digitalstep.match.android.discovery.DiscoveryService;
import de.digitalstep.match.android.discovery.MatchDatabase;
import de.digitalstep.match.commons.discovery.ZeroconfFacade;

/**
 * Displays a list of all available services.
 * 
 * @author Gunnar Bastkowski (gunnar@digitalstep.de)
 */
public class PeersListActivity extends ListActivity {

	private static final Logger log = LoggerFactory.getLogger(PeersListActivity.class);

	private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			log.debug("Received broadcast");
			updateUI();
		}
	};

	private MatchDatabase helper;

	@Override
	public void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	public void onResume() {
		super.onResume();
		registerReceiver(broadcastReceiver, new IntentFilter(DiscoveryService.BROADCAST.getAction()));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.peers_list);

		helper = new MatchDatabase(this);

		updateUI();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		helper.close();
	}

	private void updateUI() {
		setListAdapter(new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,
				Lists.newArrayList(ZeroconfFacade.getInstance().getActiveServices())));
	}

}