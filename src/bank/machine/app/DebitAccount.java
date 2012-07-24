package bank.machine.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class DebitAccount extends Activity {

		private BankDbAdpater mDbHelper;
		//private int selectedAmount;
		private ArrayAdapter<String> adapter;
		private ArrayList<Integer> listOfIds;
		private int globalPosition = 0;
		
		private static final int SUBTRACT = 2;
		private static final int ADD = 1;
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mDbHelper = new BankDbAdpater(this);
	        mDbHelper.open();
	        setContentView(R.layout.debit_layout);

	        //Button confirmButton = (Button) findViewById(R.id.button1);
	        Spinner myspinner = (Spinner)findViewById(R.id.spinner1);
	        
	        Button addButton = (Button) findViewById(R.id.button1);
	        Button subButton = (Button) findViewById(R.id.button2);
	        
	        
	        fillSpinner();
	        
	        myspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	            @Override
	            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	                @SuppressWarnings("unused")
					Object c = parent.getItemAtPosition(pos);
	                Log.d("debitAccount", "got here");
	                List<String> amountList = mDbHelper.fetchAllAmountsToString();
	                listOfIds = mDbHelper.fetchAllIds();
	                String selectedAmount = amountList.get(pos);
	                //selectedAmount = c.getInt(c.getColumnIndexOrThrow(BankDbAdpater.AMOUNT));
	                
	                EditText a = (EditText) findViewById(R.id.editText2);
	                a.setHint(selectedAmount);
	                
	                globalPosition = pos; //gets the id
	            }
	            
	            @Override
	                public void onNothingSelected(AdapterView<?> parent) {
	            }
	        });
	        
	        addButton.setOnClickListener(new View.OnClickListener() {

	            public void onClick(View view) {
	            	//if its null do nothing
	            	debitAccount(ADD);
	            	setResult(RESULT_OK);
	                finish();
	            }

	        });
	        
	        subButton.setOnClickListener(new View.OnClickListener() {

	            public void onClick(View view) {
	            	
	            	debitAccount(SUBTRACT);
	                setResult(RESULT_OK);
	                finish();
	            }

	        });
	    }
	    
	    private void debitAccount(int type){
        	EditText e = (EditText) findViewById(R.id.editText2);
        	if(e.getText() == null ){
        		Toast toast;
        		Context context = getApplicationContext();
        		CharSequence text = "YOU MUST ENTER A BALANCE OR EXIT";
        		int duration = Toast.LENGTH_SHORT;
        		
            	toast = Toast.makeText(context, text, duration);
            	toast.show();
        	}
        	else{
        		
        		int rowId = listOfIds.get(globalPosition);
        		 //f = view.getParent().getParent();
        		int amount = Integer.parseInt(e.getText().toString());
        		mDbHelper.updateEntry(rowId, amount, type);
        	}
	    }
	    private void fillSpinner(){
	    	 
	    	//Cursor c = mDbHelper.fetchAllAccounts();
	    	//startManagingCursor(c);
	    	 
	    	List<String>list = mDbHelper.fetchAllAccountsToString();

	    	
	    	// create an array to specify which fields we want to display
	    	String[] from = new String[]{BankDbAdpater.ACCOUNT_NAME };
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
	    	//adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
	    	s.setAdapter(adapter);
	    }
	    


}
