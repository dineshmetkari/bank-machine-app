package bank.machine.app;

//import com.android.demo.notepad3.NotesDbAdapter;
//import com.android.demo.notepad3.NotesDbAdapter.DatabaseHelper;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BankDbAdpater {


    public static final String KEY_ROWID = "_id";
    public static final String ACCOUNT_NAME = "account_name";
    public static final String ACCOUNT_HOLDER = "account_holder";
    public static final String AMOUNT = "amount";
    
    private static final String TAG = "BankDbAdapter";
    
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    public static final String DATABASE_NAME = "data";
    public static final String DATABASE_TABLE = "accounts";
    private static final int DATABASE_VERSION = 2;
    
    private static final int ADD = 1;
    private static final int SUB = 2;
    private static final int NONE = 0;
    
    private final Context mCtx;
    
    
    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
        "create table accounts (_id integer primary key autoincrement, "
        + "account_name text not null, account_holder text not null," 
        + "amount integer);";


    
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
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }
    
    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public BankDbAdpater(Context ctx) {
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
    public BankDbAdpater open() throws SQLException {
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


    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the note
     * @param body the body of the note
     * @return rowId or -1 if failed
     */
    /*
    public long createNote(String title, String body) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_BODY, body);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }*/
    
    public long createEntry(String accHolder, String accName, int amount){
    	ContentValues initialValues = new ContentValues();
    	//initialValues.put(KEY_ROWID, id);
    	initialValues.put(ACCOUNT_NAME, accName);
    	initialValues.put(ACCOUNT_HOLDER, accHolder);
    	initialValues.put(AMOUNT, amount);
    	
    		
    	return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the note with the given rowId
     * 
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */
    /*
    public boolean deleteNote(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }*/
    
    public boolean deleteEntry(long rowId){
    	
    	return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all notes in the database
     * 
     * @return Cursor over all notes
     */
    /*
    public Cursor fetchAllNotes() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                KEY_BODY}, null, null, null, null, null);
    }*/
    
    public Cursor fetchAllAccounts(){
    	
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, ACCOUNT_NAME,
        		ACCOUNT_HOLDER, AMOUNT}, null, null, null, null, "ACCOUNT_NAME");
    }
    
    public ArrayList<String> fetchAllAccountsToString(){
    	
    	ArrayList<String> list = new ArrayList<String>();
    	
    	Cursor cursor = mDb.query(DATABASE_TABLE, new String[] {ACCOUNT_NAME, AMOUNT}, 
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
    
    public ArrayList<String> fetchAllAmountsToString(){
    	
    	ArrayList<String> list = new ArrayList<String>();
    	
    	Cursor cursor = mDb.query(DATABASE_TABLE, new String[] {AMOUNT}, 
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
    
    public ArrayList<Integer> fetchAllIds(){
    	
    	ArrayList<Integer> list = new ArrayList<Integer>();
    	
    	Cursor cursor = mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID}, 
    			null, null, null, null, null);
    	
    	for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
 		   int id = cursor.getInt(cursor.getColumnIndex(BankDbAdpater.KEY_ROWID));
 		   list.add(id);
    	}
    	
    	return list;
 		   
 		   
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    /*
    public Cursor fetchNote(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                    KEY_TITLE, KEY_BODY}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }*/
    
    public Cursor getEntry(long rowId) throws SQLException {
    	
    	Cursor mCursor = 
    		
    		mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
    				ACCOUNT_NAME, ACCOUNT_HOLDER, AMOUNT }, KEY_ROWID + "=" + rowId, null, null, null, null, null);
    	
    	if(mCursor != null){
    		mCursor.moveToFirst();
    	}
    	
    	return mCursor;
    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     * 
     * @param rowId id of note to update
     * @param title value to set note title to
     * @param body value to set note body to
     * @return true if the note was successfully updated, false otherwise
     */
    /*
    public boolean updateNote(long rowId, String title, String body) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_BODY, body);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }*/
    
    public boolean updateEntry(long rowId, String accHolder, String accName){
    	ContentValues args = new ContentValues();
    	args.put(ACCOUNT_HOLDER, accHolder);
    	args.put(ACCOUNT_NAME, accName);
    	
    	return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    	
    }
    
    public boolean updateEntry(long rowId, int amount, int type){
    	ContentValues args = new ContentValues();
    	
        Cursor mCursor = 
           		mDb.query(true, DATABASE_TABLE, new String[] {AMOUNT}, KEY_ROWID + "=" + rowId, 
            			null, null, null, null, null);
    	if(mCursor != null){
    		mCursor.moveToFirst();
    	}
    		
    	if(type == ADD){
    			
    	    /*if I'm addin amounts, grab the old and add it to make a new*/
    	    String sAmount = mCursor.getString(0);//should be the first
    	    
    	    int iAmount = Integer.parseInt(sAmount); //parse the string
    	    	
    	    args.put(AMOUNT, amount+iAmount);
    	    	
    	    return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    	}
    	else if (type == SUB){
    		//must be subtract
    			
    	    /*if I'm addin amounts, grab the old and add it to make a new*/
    	    String sAmount = mCursor.getString(0); //should be the first
    	    	
    	    int iAmount = Integer.parseInt(sAmount); //parse the string
    	    
    	    args.put(AMOUNT, amount-iAmount); 
    	    	
    	    return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    	}
    	
    	else{
    		return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    	}
    		
    }
    
    //returns the number of rows deleted
    public int deleteAll(){
    	
    	//putting "1" on the where clause will show how many rows were deleted 
    	 return mDb.delete(DATABASE_NAME, "1", null);
    }
}
