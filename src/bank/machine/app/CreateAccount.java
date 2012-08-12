package bank.machine.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import bank.machine.app.BankDbAdpater;

public class CreateAccount extends Activity {

	private BankDbAdpater mDbHelper;
	//private EditText mNameText;
	private EditText mAccountText;
	private Long mRowId;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new BankDbAdpater(this);
        mDbHelper.open();
        setContentView(R.layout.create_account);
        setTitle(R.string.createAccount);
        

        //mNameText = (EditText) findViewById(R.id.editText1);
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
        //String holderName = mNameText.getText().toString();
        String accountName = mAccountText.getText().toString();

        if (mRowId == null) {
            long id = mDbHelper.createEntry(/*holderName,*/ accountName, 0);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateEntry(mRowId, /*holderName,*/ accountName);
        }
    }

}
