package bank.machine.app;

import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class DebitAccount extends Activity {

		private BankDbAdpater mDbHelper;
		private int selectedAmount;
		private ArrayAdapter<String> adapter;
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mDbHelper = new BankDbAdpater(this);
	        mDbHelper.open();
	        setContentView(R.layout.debit_layout);

	        //Button confirmButton = (Button) findViewById(R.id.button1);
	        Spinner myspinner = (Spinner)findViewById(R.id.spinner1);
	        
	        
	        fillSpinner();
	        
	        myspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	            @Override
	            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	                Cursor c = (Cursor)parent.getItemAtPosition(pos);
	                selectedAmount = c.getInt(c.getColumnIndexOrThrow(BankDbAdpater.AMOUNT));
	                
	                EditText a = (EditText) findViewById(R.id.editText1);
	                //a.setHint(selectedAmount);
	            }
	            @Override
	                public void onNothingSelected(AdapterView<?> parent) {
	            }
	        });
	    }
	    
	    
	    private void fillSpinner(){
	    	 
	    	//Cursor c = mDbHelper.fetchAllAccounts();
	    	//startManagingCursor(c);
	    	 
	    	List<String> list = mDbHelper.fetchAllAccountsToString();
	    	
	    	// create an array to specify which fields we want to display
	    	String[] from = new String[]{BankDbAdpater.ACCOUNT_NAME};
	    	// create an array of the display item we want to bind our data to
	    	int[] to = new int[]{R.id.spinner1};
	    	// create simple cursor adapter
	    	/*
	    	SimpleCursorAdapter adapter =
	    	  new SimpleCursorAdapter(this, R.layout.debit_layout, c, from, to );
	    	adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );*/
	    	// get reference to our spinner
	    	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
	    	Spinner s = (Spinner) findViewById( R.id.spinner1 );
	    	adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
	    	s.setAdapter(adapter);
	    }
	    


}
