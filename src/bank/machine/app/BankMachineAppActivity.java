package bank.machine.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import bank.machine.app.BankDbAdpater;

public class BankMachineAppActivity extends Activity {
	
    private BankDbAdpater mDbHelper;
    private Cursor mNotesCursor;
    
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_DISPLAY = 1;
    private static final int ACTIVITY_DEBIT = 2;
    
    private Context context;
    private CharSequence text;
    private int duration;
    private Toast toast;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDbHelper = new BankDbAdpater(this);
        mDbHelper.open();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //Bundle extras = intent.getExtras();
        switch(requestCode) {
            case ACTIVITY_CREATE:
            	context = getApplicationContext();
            	text = "You have successfully created an account!";
            	duration = Toast.LENGTH_SHORT;

            	toast = Toast.makeText(context, text, duration);
            	toast.show();
            	/*
                String title = extras.getString(NotesDbAdapter.KEY_TITLE);
                String body = extras.getString(NotesDbAdapter.KEY_BODY);
                mDbHelper.createNote(title, body);
                fillData();*/
                break;
            case ACTIVITY_DEBIT:
            	context = getApplicationContext();
            	text = "You have successfully made a transaction!";
            	int duration = Toast.LENGTH_SHORT;

            	toast = Toast.makeText(context, text, duration);
            	toast.show();
                break;
        }
    }
    
    public void onClick(View v){
    	switch(v.getId()){
    	
    	//display account
    	case R.id.button1:
    		Intent dis = new Intent(this, DisplayAccounts.class);
    		startActivityForResult(dis, ACTIVITY_DISPLAY);
    		break;
    		
    	//debit button	
    	case R.id.button2:
    		Intent deb = new Intent(this, DebitAccount.class);
    		startActivityForResult(deb, ACTIVITY_DEBIT);
    		break;
    	//set dates button
    	case R.id.button3:
    		
    		break;
    	//createAccont
    	case R.id.button4:
    		Intent i = new Intent(this, CreateAccount.class);
    		startActivityForResult(i, ACTIVITY_CREATE);
    		break;
    	}
    }
}