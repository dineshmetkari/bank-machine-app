package bank.machine.app;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import bank.machine.app.BankDbAdapter;

public class BankMachineAppActivity extends Activity {
	
    private BankDbAdapter mDbHelper_bank;
    private CalendarDbAdapter mDbHelper_calendar;
    
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_DISPLAY = 1;
    private static final int ACTIVITY_DEBIT = 2;
    private static final int ACTIVITY_WIDTHDRAWAL = 3;
    private static final int ACTIVITY_DIS_WIDTHDRAWAL = 4;
    
    
    private static final int DELETE_ACCOUNT_ID = Menu.FIRST;
    private static final int DELETE_CALENDAR_ID = Menu.FIRST+1;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDbHelper_bank = new BankDbAdapter(this);
        mDbHelper_calendar = new CalendarDbAdapter(this);
        mDbHelper_bank.open();
        mDbHelper_calendar.open();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //Bundle extras = intent.getExtras();
        switch(requestCode) {
            case ACTIVITY_CREATE:
            	showShortToast("You have successfully created an account!");
                break;
            case ACTIVITY_DEBIT:
            	showShortToast("You have successfully made a transaction!");
                break;
                
            case ACTIVITY_WIDTHDRAWAL:
            	showShortToast("You have successfully made a widthdrawal date");
            	break;
            	
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, DELETE_ACCOUNT_ID, 0, R.string.menuDelete);
        menu.add(0, DELETE_CALENDAR_ID, 1, R.string.menuDelete2);
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	int howMany = 0;
        switch(item.getItemId()) {
            case DELETE_ACCOUNT_ID:
                howMany = mDbHelper_bank.deleteAll();
                showShortToast("You have successfully deleted "+howMany+" accounts");
                return true;
                
            case DELETE_CALENDAR_ID:
            	howMany = mDbHelper_calendar.deleteAll();
                showShortToast("You have successfully deleted "+howMany+" accounts");
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
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
    		Intent width = new Intent(this, Widthdrawal.class);
    		startActivityForResult(width, ACTIVITY_WIDTHDRAWAL);
    		break;
    	//createAccont
    	case R.id.button4:
    		Intent i = new Intent(this, CreateAccount.class);
    		startActivityForResult(i, ACTIVITY_CREATE);
    		break;
    		
    	case R.id.button5:
    		Intent disWidth = new Intent(this, DisplayWidthdrawals.class);
    		startActivityForResult(disWidth, ACTIVITY_DIS_WIDTHDRAWAL);
    		break;
    	}
    }
    
    private void showShortToast(String message){
       Context context;
       CharSequence text;
       int duration;
       Toast toast;
       
   	   context = getApplicationContext();
   	   text = message;
   	   duration = Toast.LENGTH_SHORT;
   	   
   	   toast = Toast.makeText(context, text, duration);
   	   toast.show();
    }
}