package bank.machine.app;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class DisplayAccounts extends ListActivity {

	 private BankDbAdpater mDbHelper;
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 //setContentView(R.layout.create_account);
	     setContentView(R.layout.display_account_layout);
	     
	     mDbHelper = new BankDbAdpater(this);
         mDbHelper.open();
	     //database = mDbHelper.getReadableDatabase();
	            // some code

	     Cursor data = mDbHelper.fetchAllAccounts();
	    		 startManagingCursor(data);

	            // the desired columns to be bound
	            String[] columns = new String[] {BankDbAdpater.ACCOUNT_NAME, BankDbAdpater.AMOUNT };
	            // the XML defined views which the data will be bound to
	            int[] to = new int[] {R.id.account_name_entry, R.id.number_entry };

	            // create the adapter using the cursor pointing to the desired data as well as the layout information
	            SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.display_account_ex_layout, data, columns, to);

	            // set this adapter as your ListActivity's adapter
	            this.setListAdapter(mAdapter);
	            
	            
	 }
}		
