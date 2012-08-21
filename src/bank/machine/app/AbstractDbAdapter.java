package bank.machine.app;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public abstract class AbstractDbAdapter {
    protected static final String TAG = "AbstractDbAdapter";
    protected DatabaseHelper mDbHelper;
    protected SQLiteDatabase mDb;
    
    /***COLUMNS***/
	public static final String KEY_ROWID = "_id"; 
	public static final String AMOUNT = "amount";
	public static final String ACCOUNT_NAME = "account_name";
	public static final String BILL_NAME = "bill_name";
	public static final String TIME = "time";
    
    protected static final String ACCOUNT_TABLE = "accounts";
    
    protected static final String CALENDAR_TABLE = "calendar_data";
    
    protected static final String CALENDAR_DATABASE_CREATE =
            "create table calendar_data (_id integer primary key autoincrement, "
            + "account_name text not null, bill_name text not null, amount real, time date );";
    
    protected static final String BANK_DATABASE_CREATE =
            "create table accounts (_id integer primary key autoincrement, "
            + "account_name text not null, amount real);";


    protected static final String DATABASE_NAME = "data";
    protected static final int DATABASE_VERSION = 1;

    protected final Context mCtx;

    protected static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CALENDAR_DATABASE_CREATE);
            db.execSQL(BANK_DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS accounts");
            db.execSQL("DROP TABLE IF EXISTS calendar_data");
            onCreate(db);
        }
    }

    public AbstractDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public AbstractDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }
    
    public SQLiteDatabase getReadableDatabase(){
    	
    	return mDbHelper.getReadableDatabase();
    }
    
    public ArrayList<String> fetchAllAmountsToString(){
    	
    	ArrayList<String> list = new ArrayList<String>();
    	
    	Cursor cursor = mDb.query(ACCOUNT_TABLE, new String[] {AMOUNT}, 
    			null, null, null, null, null);
    	
    	for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
    		   String amount = cursor.getString(cursor.getColumnIndex(BankDbAdapter.AMOUNT));
    		   //String amount = cursor.getString(cursor.getColumnIndex(BankDbAdapter.AMOUNT));
    		   //String[] s = new String[] {name, amount};
    		   list.add(amount);
    		}
    	cursor.close();
    	return list;
    }
    
    public ArrayList<String> fetchAllAccountsToString(){
    	
    	ArrayList<String> list = new ArrayList<String>();
    	
    	Cursor cursor = mDb.query(ACCOUNT_TABLE, new String[] {ACCOUNT_NAME, AMOUNT}, 
    			null, null, null, null, null);
    	
    	for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
    		   String name = cursor.getString(cursor.getColumnIndex(BankDbAdapter.ACCOUNT_NAME));
    		   //String amount = cursor.getString(cursor.getColumnIndex(BankDbAdapter.AMOUNT));
    		   //String[] s = new String[] {name, amount};
    		   list.add(name);
    		}
    	cursor.close();
    	return list;
    }
    public ArrayList<Integer> fetchAllIds(){
    	
    	ArrayList<Integer> list = new ArrayList<Integer>();
    	
    	Cursor cursor = mDb.query(ACCOUNT_TABLE, new String[] {KEY_ROWID}, 
    			null, null, null, null, null);
    	
    	for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
 		   int id = cursor.getInt(cursor.getColumnIndex(BankDbAdapter.KEY_ROWID));
 		   list.add(id);
    	}
    	
    	return list;	   
    }

}

