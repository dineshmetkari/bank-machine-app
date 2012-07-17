package bank.machine.app;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class DebitAccount extends Activity {

		private BankDbAdpater mDbHelper;
		private EditText mNameText;
		private EditText mAccountText;
		private Long mRowId;
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mDbHelper = new BankDbAdpater(this);
	        mDbHelper.open();
	        setContentView(R.layout.debit_layout);
	        

	        mNameText = (EditText) findViewById(R.id.editText1);
	        mAccountText = (EditText) findViewById(R.id.editText3);

	        Button confirmButton = (Button) findViewById(R.id.button1);

	        
	        mRowId = (savedInstanceState == null) ? null :
	            (Long) savedInstanceState.getSerializable(BankDbAdpater.KEY_ROWID);
	        if (mRowId == null) {
	            Bundle extras = getIntent().getExtras();
	            mRowId = extras != null ? extras.getLong(BankDbAdpater.KEY_ROWID)
	                                    : null;
	        }
	        
	        //populateFields();

	        confirmButton.setOnClickListener(new View.OnClickListener() {

	            public void onClick(View view) {
	            	
	            	
	                setResult(RESULT_OK);
	                finish();
	            }

	        });
	    }
	    
	    
	    private void fillSpinner(){
	    	 
	    	Cursor c = mDbHelper.fetchAllAccounts();
	    	startManagingCursor(c);
	    	 
	    	// create an array to specify which fields we want to display
	    	String[] from = new String[]{BankDbAdpater.ACCOUNT_NAME};
	    	// create an array of the display item we want to bind our data to
	    	int[] to = new int[]{R.id.spinner1};
	    	// create simple cursor adapter
	    	SimpleCursorAdapter adapter =
	    	  new SimpleCursorAdapter(this, R.layout.debit_layout, c, from, to );
	    	adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
	    	// get reference to our spinner
	    	Spinner s = (Spinner) findViewById( R.id.spinner1 );
	    	s.setAdapter(adapter);
	    }
	    
	    @Override
	    protected void onPause() {
	        super.onPause();
	        saveState();
	    }
	    
	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        saveState();
	        outState.putSerializable(BankDbAdpater.KEY_ROWID, mRowId);
	    }
	    
	    private void saveState() {
	        String holderName = mNameText.getText().toString();
	        String accountName = mAccountText.getText().toString();

	        if (mRowId == null) {
	            long id = mDbHelper.createEntry(holderName, accountName, 0);
	            if (id > 0) {
	                mRowId = id;
	            }
	        } else {
	            mDbHelper.updateEntry(mRowId, holderName, accountName);
	        }
	    }

	}

}
