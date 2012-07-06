package bank.machine.app;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import bank.machine.app.BankDbAdpater;

public class BankMachineAppActivity extends Activity {
	
    private BankDbAdpater mDbHelper;
    private Cursor mNotesCursor;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDbHelper = new BankDbAdpater(this);
        mDbHelper.open();
    }
    
    /** Called when the user touches the button */
    public void createAccountView(View view) {
        // Do something in response to button click
    }
    
    public void onClick(View v){
    	switch(v.getId()){
    	
    	//display account
    	case R.id.button1:
    		
    		break;
    	//debit button	
    	case R.id.button2:
    		
    		break;
    	//set dates button
    	case R.id.button3:
    		
    		break;
    	//createAccont
    	case R.id.button4:
    		
    		break;
    	}
    }
}