package bank.machine.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class CalendarDbAdapter extends AbstractDbAdapter{
	
    
    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public CalendarDbAdapter(Context ctx) {
        super(ctx);
    }
    
    
    public long createEntry(String accName, String billName, String time, double amount){
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(ACCOUNT_NAME, accName);
    	initialValues.put(BILL_NAME, billName);
    	initialValues.put(AMOUNT, amount);
    	initialValues.put(TIME, time);

    	
    		
    	return mDb.insert(CALENDAR_TABLE, null, initialValues);
    }
    
    //returns the number of rows deleted
    public int deleteAll(){
    	
    	//putting "1" on the where clause will show how many rows were deleted 
    	 return mDb.delete(CALENDAR_TABLE, "1", null);
    }
    
    /**
     * Return a Cursor over the list of all notes in the database
     * 
     * @return Cursor over all notes
     */
    
    public Cursor fetchAllAccounts(){
    	
    	return mDb.query(CALENDAR_TABLE, new String[] {KEY_ROWID, ACCOUNT_NAME, AMOUNT, BILL_NAME, TIME}, 
    			null, null, null, null, "ACCOUNT_NAME");
    }

}
