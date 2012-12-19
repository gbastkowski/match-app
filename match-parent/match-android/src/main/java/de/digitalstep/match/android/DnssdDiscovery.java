package de.digitalstep.match.android;

import java.io.IOException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DnssdDiscovery extends Activity {

	private android.os.Handler handler = new android.os.Handler();
	private JmDNS jmdns = null;
	private ServiceListener listener = null;
	private android.net.wifi.WifiManager.MulticastLock lock;
	private String type = "_match._tcp.local.";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dnssd);

		listener = createListener();

		handler.postDelayed(new Runnable() {
			public void run() {
				android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) getSystemService(android.content.Context.WIFI_SERVICE);
				lock = wifi.createMulticastLock("mylockthereturn");
				lock.setReferenceCounted(true);
				lock.acquire();
				try {
					jmdns = JmDNS.create();
					jmdns.addServiceListener(type, listener);
					jmdns.registerService(ServiceInfo.create("_match._tcp.local.", "AndroidTest", 0, "plain test service from android"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 1000);

	}

	private ServiceListener createListener() {
		return new ServiceListener() {

			@Override
			public void serviceAdded(ServiceEvent event) {
				// Required to force serviceResolved to be called
				// again
				// (after the first search)
				jmdns.requestServiceInfo(event.getType(), event.getName(), 1);
			}

			@Override
			public void serviceRemoved(ServiceEvent ev) {
				notifyUser("Service removed: " + ev.getName());
			}

			@Override
			public void serviceResolved(ServiceEvent ev) {
				notifyUser("Service resolved: " + ev.getInfo().getQualifiedName() + " port:" + ev.getInfo().getPort());
			}
		};
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (jmdns != null) {
			if (listener != null) {
				jmdns.removeServiceListener(type, listener);
				listener = null;
			}
			jmdns.unregisterAllServices();
			try {
				jmdns.close();
			} catch (IOException e) {
				// TODO Create an error message dialog
				e.printStackTrace();
			}
			jmdns = null;
		}
		lock.release();
		super.onStop();
	}

	private void notifyUser(final String msg) {
		handler.postDelayed(new Runnable() {
			public void run() {

				TextView t = (TextView) findViewById(R.id.text);
				t.setText(msg + "\n=== " + t.getText());
			}
		}, 1);

	}
}