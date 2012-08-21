package bank.machine.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Widthdrawal extends Activity {

	private CalendarDbAdapter mDbHelper;
	private ArrayAdapter<String> adapter;
	private EditText billName;
	private EditText amount;
	private static int cDay = -1;
	private static int cMonth = -1;
	private static int cYear = -1;
	private int globalPosition = 0;
	private ArrayList<Integer> listOfIds;
	private String selectedAccount;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        mDbHelper = new CalendarDbAdapter(this);
        mDbHelper.open();
        setContentView(R.layout.widthdrawal_layout);
        
        Button confirmButton = (Button) findViewById(R.id.button1);
        Spinner myspinner = (Spinner)findViewById(R.id.spinner2);
        billName = (EditText)findViewById(R.id.editText5);
        amount = (EditText)findViewById(R.id.editText6);


        fillSpinner();
        
        myspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //@SuppressWarnings("unused")
				Object c = parent.getItemAtPosition(pos);
                Log.d("debitAccount", "got here");
                List<String> amountList = mDbHelper.fetchAllAccountsToString();
                listOfIds = mDbHelper.fetchAllIds();
                selectedAccount = amountList.get(pos);
                
                globalPosition = pos; //gets the id
            }
            
            @Override
                public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	
            	if(!billName.getText().toString().equals("") &&
            	    !amount.getText().toString().equals("") && cMonth != -1 ||
            	    cDay != -1 || cYear != -1){

            		setWidthdrawalDate(selectedAccount, billName.getText().toString(), 
            				cYear+"-"+cMonth+"-"+cDay, 
            				Double.parseDouble(amount.getText().toString()));
            	}
            		
                setResult(RESULT_OK);
                finish();
            }

        });
    }
    
    public void setWidthdrawalDate(String accName, String billName, String time, double amount){
    	
    	mDbHelper.createEntry(accName, billName, time, amount);
    	
    }
    
    
    public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

    	@Override
    	public Dialog onCreateDialog(Bundle savedInstanceState) {
    		// Use the current date as the default date in the picker
    		final Calendar c = Calendar.getInstance();
    		int year = c.get(Calendar.YEAR);
    		int month = c.get(Calendar.MONTH);
    		int day = c.get(Calendar.DAY_OF_MONTH);

    		// Create a new instance of DatePickerDialog and return it
    		return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
			
			cDay = day;
			cMonth = month;
			cYear = year;
			
		}
	}
    
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    
    private void fillSpinner(){
    	 
    	List<String>list = mDbHelper.fetchAllAccountsToString();

    	// get reference to our spinner
    	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
    	Spinner s = (Spinner) findViewById( R.id.spinner2 );
    	s.setAdapter(adapter);
    }
}
