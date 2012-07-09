package bank.machine.app;

//import com.android.demo.notepad3.NotesDbAdapter;

//import com.android.demo.notepad3.NotesDbAdapter;
//import com.android.demo.notepad3.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class DisplayAccounts extends Activity {

	 private BankDbAdpater mDbHelper;
	 private Cursor mNotesCursor;
	 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new BankDbAdpater(this);
        mDbHelper.open();
        setContentView(R.layout.debit_layout);
        //setTitle(R.string.createAccount);
        

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
	
    private void fillData() {
        // Get all of the rows from the database and create the item list
        mNotesCursor = mDbHelper.fetchAllAccounts();
        startManagingCursor(mNotesCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{NotesDbAdapter.KEY_TITLE};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter notes = 
            new SimpleCursorAdapter(this, R.layout.notes_row, mNotesCursor, from, to);
        setListAdapter(notes);
    }
}
