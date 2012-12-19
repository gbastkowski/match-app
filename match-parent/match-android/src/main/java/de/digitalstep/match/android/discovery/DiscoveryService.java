package de.digitalstep.match.android.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.google.common.base.Function;

import de.digitalstep.match.android.PeersListActivity;
import de.digitalstep.match.android.R;
import de.digitalstep.match.commons.discovery.ServiceUpdate;
import de.digitalstep.match.commons.discovery.ZeroconfFacade;

/**
 * Discovers other peers on the net.
 * 
 * @author Gunnar Bastkowski (gunnar@digitalstep.de)
 */
public class DiscoveryService extends Service {

	public static final Intent BROADCAST = new Intent("de.digitalstep.match.discoveryevent");

	private static final Logger log = LoggerFactory.getLogger(DiscoveryService.class);

	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			notifyUser(msg.arg1, msg.obj.toString(), msg.obj.toString());
		}
	};

	private final ZeroconfFacade jmdns = ZeroconfFacade.getInstance();
	private WifiManager.MulticastLock lock;

	@Override
	public IBinder onBind(Intent intent) {
		log.debug("onBind");
		return null;
	}

	@Override
	public void onDestroy() {
		jmdns.stop();
		lock.release();
		notifyUser(1337, "Service destroyed!!!", "Service destroyed!!!");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		log.debug("onStartCommand");

		lock = aquireMulticastLock();

		jmdns.setResolvedCallback(new Function<ServiceUpdate, Void>() {
			@Override
			public Void apply(ServiceUpdate event) {
				sendMessage(1339, "Service resolved: " + event.getQualifiedName() + " port:" + event.getPort());
				return null;
			}
		}).setRemovedCallback(new Function<ServiceUpdate, Void>() {
			@Override
			public Void apply(ServiceUpdate event) {
				sendMessage(1338, "Service removed: " + event.getQualifiedName());
				return null;
			}
		}).start();

		notifyUser(1337, "Service started!!!", "Service started!!!");

		return START_STICKY;
	}

	private MulticastLock aquireMulticastLock() {
		WifiManager wifiManager = (WifiManager) getSystemService(android.content.Context.WIFI_SERVICE);
		MulticastLock lock = wifiManager.createMulticastLock("mylockthereturn");
		lock.setReferenceCounted(true);
		lock.acquire();
		return lock;
	}

	private void notifyUser(int id, String ticker, String message) {
		Notification notification = new Notification(R.drawable.ic_menu_notifications, ticker, System.currentTimeMillis());
		notification.setLatestEventInfo(this, ticker, message,
				PendingIntent.getActivity(this, 0, new Intent(this, PeersListActivity.class), 0));

		((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(id, notification);
	}

	private void sendMessage(int id, String text) {
		sendBroadcast(BROADCAST);
		Message message = handler.obtainMessage();
		message.arg1 = id;
		message.obj = text;
		message.sendToTarget();
	}
}
