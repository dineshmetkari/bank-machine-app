package bank.machine.app;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Widthdrawal extends Activity {

	private BankDbAdpater mDbHelper;
	private ArrayAdapter<String> adapter;
	private EditText billName;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        mDbHelper = new BankDbAdpater(this);
        mDbHelper.open();
        setContentView(R.layout.widthdrawal_layout);
        
        Spinner spinner = (Spinner)findViewById(R.id.spinner2);
        billName = (EditText)findViewById(R.id.editText5);
        
        fillSpinner();
    }
    private void fillSpinner(){
   	 
    	//Cursor c = mDbHelper.fetchAllAccounts();
    	//startManagingCursor(c);
    	 
    	List<String>list = mDbHelper.fetchAllAccountsToString();

    	
    	// create an array to specify which fields we want to display
    	//String[] from = new String[]{BankDbAdpater.ACCOUNT_NAME };
    	// create an array of the display item we want to bind our data to
    	//int[] to = new int[]{R.id.spinner1};
    	// create simple cursor adapter
    	/*
    	SimpleCursorAdapter adapter =
    	  new SimpleCursorAdapter(this, R.layout.debit_layout, c, from, to );
    	adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );*/
    	// get reference to our spinner
    	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
    	Spinner s = (Spinner) findViewById( R.id.spinner1 );
    	//adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
    	s.setAdapter(adapter);
    }
}
