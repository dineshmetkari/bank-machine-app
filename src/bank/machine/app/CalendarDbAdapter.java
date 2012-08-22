package bank.machine.app;

import android.content.ContentValues;
import android.content.Context;


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

}
