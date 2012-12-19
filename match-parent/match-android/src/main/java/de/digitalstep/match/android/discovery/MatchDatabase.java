package de.digitalstep.match.android.discovery;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import de.digitalstep.match.commons.networking.Peer;

public class MatchDatabase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "match.db";
	private static final int SCHEMA_VERSION = 1;

	public MatchDatabase(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	public void insert(Peer p) {
		ContentValues values = new ContentValues();
		values.put("guid", p.getUid());
		values.put("name", p.getAddress().toString());
		getWritableDatabase().insert("peers", "empty", values);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE peers (" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"guid TEXT," +
				"name TEXT" +
				");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// nothing to upgrade yet
	}

}
