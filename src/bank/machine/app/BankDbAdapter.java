package bank.machine.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;


public class BankDbAdapter extends AbstractDbAdapter{
    
    private static final int ADD = 1;
    private static final int SUB = 2;
    //private static final int NONE = 0;
    
    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public BankDbAdapter(Context ctx) {
        super(ctx);
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
    
    public long createEntry(/*String accHolder,*/ String accName, double amount){
    	ContentValues initialValues = new ContentValues();
    	//initialValues.put(KEY_ROWID, id);
    	initialValues.put(ACCOUNT_NAME, accName);
    	//initialValues.put(ACCOUNT_HOLDER, accHolder);
    	initialValues.put(AMOUNT, amount);
    	
    		
    	return mDb.insert(ACCOUNT_TABLE, null, initialValues);
    	//return mDb.insert(CALENDAR_TABLE, null, initialValues);
    }

    /**
     * Return a Cursor over the list of all notes in the database
     * 
     * @return Cursor over all notes
     */
    
    public Cursor fetchAllAccounts(){
    	
    	return mDb.query(ACCOUNT_TABLE, new String[] {KEY_ROWID, ACCOUNT_NAME, AMOUNT}, 
    			null, null, null, null, "ACCOUNT_NAME");
    }
    
    /**
     * Delete the note with the given rowId
     * 
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */

    
    public boolean deleteEntry(long rowId){
    	
    	return mDb.delete(ACCOUNT_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }


    
    /**
     * Return a Cursor positioned at the note that matches the given rowId
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    
    public Cursor getEntry(long rowId) throws SQLException {
    	
    	Cursor mCursor = 
    		
    		mDb.query(true, ACCOUNT_TABLE, new String[] {KEY_ROWID,
    				ACCOUNT_NAME, AMOUNT }, KEY_ROWID + "=" + rowId, null, null, null, null, null);
    	
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
    
    public boolean updateEntry(long rowId,/*String accHolder,*/ String accName){
    	ContentValues args = new ContentValues();
    	//args.put(ACCOUNT_HOLDER, accHolder);
    	args.put(ACCOUNT_NAME, accName);
    	
    	return mDb.update(ACCOUNT_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    	
    }
    
    public boolean updateEntry(long rowId, double amount, int type){
    	ContentValues args = new ContentValues();
    	
        Cursor mCursor = 
           		mDb.query(true, ACCOUNT_TABLE, new String[] {AMOUNT}, KEY_ROWID + "=" + rowId, 
            			null, null, null, null, null);
    	if(mCursor != null){
    		mCursor.moveToFirst();
    	}
    		
    	if(type == ADD){
    			
    	    /*if I'm addin amounts, grab the old and add it to make a new*/
    	    String sAmount = mCursor.getString(0);//should be the first
    	    
    	    //Double.parseDouble(string)
    	    //int iAmount = Integer.parseInt(sAmount); //parse the string
    	    double iAmount = Double.parseDouble(sAmount);	
    	    args.put(AMOUNT, amount+iAmount);
    	    	
    	    return mDb.update(ACCOUNT_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    	}
    	else if (type == SUB){
    		//must be subtract
    			
    	    /*if I'm addin amounts, grab the old and add it to make a new*/
    	    String sAmount = mCursor.getString(0); //should be the first
    	    	
    	    //int iAmount = Integer.parseInt(sAmount); //parse the string
    	    double iAmount = Double.parseDouble(sAmount);
    	    args.put(AMOUNT, iAmount-amount); 
    	    	
    	    return mDb.update(ACCOUNT_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    	}
    	
    	else{
    		return mDb.update(ACCOUNT_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    	}
    		
    }
    
    //returns the number of rows deleted
    public int deleteAll(){
    	
    	//putting "1" on the where clause will show how many rows were deleted 
    	 return mDb.delete(ACCOUNT_TABLE, "1", null);
    }
}
