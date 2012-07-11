package bank.machine.app;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class BankDbProvider extends ContentProvider {

	private BankDbAdpater mDB;
	
	private static final String AUTHORITY = "bank.machine.app.BankDbProvider";
	public static final int TUTORIALS = 100;
	public static final int TUTORIAL_ID = 110;
	 
	private static final String TUTORIALS_BASE_PATH = "tutorials";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
	        + "/" + TUTORIALS_BASE_PATH);
	 
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/mt-tutorial";
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
	        + "/mt-tutorial";

	private static final UriMatcher sURIMatcher = new UriMatcher(
	        UriMatcher.NO_MATCH);
	static {
	    sURIMatcher.addURI(AUTHORITY, TUTORIALS_BASE_PATH, TUTORIALS);
	    sURIMatcher.addURI(AUTHORITY, TUTORIALS_BASE_PATH + "/#", TUTORIAL_ID);
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mDB = new BankDbAdpater(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	    queryBuilder.setTables(BankDbAdpater.DATABASE_TABLE);
	 
	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case TUTORIAL_ID:
	        queryBuilder.appendWhere(BankDbAdpater.KEY_ROWID + "="
	                + uri.getLastPathSegment());
	        break;
	    case TUTORIALS:
	        // no filter
	        break;
	    default:
	        throw new IllegalArgumentException("Unknown URI");
	    }
	 
	    Cursor cursor = queryBuilder.query(mDB.getReadableDatabase(),
	            projection, selection, selectionArgs, null, null, sortOrder);
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);
	    return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
