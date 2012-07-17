package bank.machine.app;

//import com.android.demo.notepad3.NotesDbAdapter;

//import com.android.demo.notepad3.NotesDbAdapter;
//import com.android.demo.notepad3.R;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class DisplayAccounts extends ListActivity {

	 private BankDbAdpater mDbHelper;
	 //private Cursor mNotesCursor;
	 private SQLiteDatabase database;
	 private CursorAdapter dataSource;

	 
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
	            String[] columns = new String[] { BankDbAdpater.ACCOUNT_NAME, BankDbAdpater.AMOUNT };
	            // the XML defined views which the data will be bound to
	            int[] to = new int[] { R.id.name_entry, R.id.number_entry };

	            // create the adapter using the cursor pointing to the desired data as well as the layout information
	            SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.display_account_ex_layout, data, columns, to);

	            // set this adapter as your ListActivity's adapter
	            this.setListAdapter(mAdapter);
	            
	            
	 }
}		
