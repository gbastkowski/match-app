package de.digitalstep.match.android;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class ContactsAdapterBuilder {

	public static final ContactsAdapterBuilder INSTANCE = new ContactsAdapterBuilder();

	ListAdapter buildNameAdapter(Activity activity) {
		return new SimpleCursorAdapter(activity, android.R.layout.simple_list_item_1,
				createCursor(activity,
						Contacts.CONTENT_URI,
						new String[] { Contacts._ID, Contacts.DISPLAY_NAME }),
				new String[] { Contacts.DISPLAY_NAME },
				new int[] { android.R.id.text1 });
	}

	ListAdapter buildPhonesAdapter(Activity activity) {
		return new SimpleCursorAdapter(activity,
				android.R.layout.simple_list_item_2,
				createCursor(activity,
						Phone.CONTENT_URI,
						new String[] { Contacts._ID, Contacts.DISPLAY_NAME, Phone.NUMBER }),
				new String[] { Contacts.DISPLAY_NAME, Phone.NUMBER },
				new int[] { android.R.id.text1, android.R.id.text2 });
	}

	ListAdapter buildEmailAdapter(Activity activity) {
		return new SimpleCursorAdapter(activity,
				android.R.layout.simple_list_item_2,
				createCursor(activity,
						Email.CONTENT_URI,
						new String[] { Contacts._ID, Contacts.DISPLAY_NAME, Email.DATA }),
				new String[] { Contacts.DISPLAY_NAME, Email.DATA },
				new int[] { android.R.id.text1, android.R.id.text2 });
	}

	private Cursor createCursor(Activity activity, Uri contentUri, String[] projection) {
		return activity.managedQuery(contentUri, projection, null, null, null);
	}
}
