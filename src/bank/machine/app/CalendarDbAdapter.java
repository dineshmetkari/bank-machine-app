package bank.machine.app;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CalendarDbAdapter {

	public static final String KEY_ROWID = "_id";
	
		
	private static final String TAG = "CalendarDbAdapter";
	 
	 
	public static final String DATABASE_NAME = "data";	
	public static final String CALENDAR_TABLE = "caldenar_data";
	public static final String ACCOUNT_TABLE= "accounts";
	
	public static final String AMOUNT = "amount";
	public static final String ACCOUNT_NAME = "account_name";
	public static final String BILL_NAME = "bill_name";
	public static final String TIME = "time";
	
	private static final int DATABASE_VERSION = 4;
	
	private final Context mCtx;
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	
    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
        "create table calendar_data (_id integer primary key autoincrement, "
        + "account_name text not null, bill_name text not null, amount real, time date );";

    
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS calendar_data");
            onCreate(db);
        }
    }
    
    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public CalendarDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    
    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public CalendarDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }
    
    public long createEntry(String accName, String billName, String time, double amount){
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(ACCOUNT_NAME, accName);
    	initialValues.put(BILL_NAME, billName);
    	initialValues.put(AMOUNT, amount);
    	initialValues.put(TIME, time);

    	
    		
    	return mDb.insert(CALENDAR_TABLE, null, initialValues);
    }
    
    public ArrayList<String> fetchAllAmountsToString(){
    	
    	ArrayList<String> list = new ArrayList<String>();
    	
    	Cursor cursor = mDb.query(ACCOUNT_TABLE, new String[] {AMOUNT}, 
    			null, null, null, null, null);
    	
    	for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
    		   String amount = cursor.getString(cursor.getColumnIndex(BankDbAdpater.AMOUNT));
    		   //String amount = cursor.getString(cursor.getColumnIndex(BankDbAdpater.AMOUNT));
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
    		   String name = cursor.getString(cursor.getColumnIndex(BankDbAdpater.ACCOUNT_NAME));
    		   //String amount = cursor.getString(cursor.getColumnIndex(BankDbAdpater.AMOUNT));
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
 		   int id = cursor.getInt(cursor.getColumnIndex(BankDbAdpater.KEY_ROWID));
 		   list.add(id);
    	}
    	
    	return list;	   
    }
}
