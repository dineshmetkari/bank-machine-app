package bank.machine.app;

//import com.android.demo.notepad3.NotesDbAdapter;

//import com.android.demo.notepad3.NotesDbAdapter;
//import com.android.demo.notepad3.R;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class DisplayAccounts extends ListActivity {

	 private BankDbAdpater mDbHelper;
	 private Cursor mNotesCursor;
	 
	 @Override
	 public void onCreate(Bundle savedInstance) {
		 
	     setContentView(R.layout.display_account_layout);

	            // some code

	            Cursor cursor = getContentResolver().query(People.CONTENT_URI, new String[] {People._ID, People.NAME, People.NUMBER}, null, null, null);
	            startManagingCursor(cursor);

	            // the desired columns to be bound
	            String[] columns = new String[] { People.NAME, People.NUMBER };
	            // the XML defined views which the data will be bound to
	            int[] to = new int[] { R.id.name_entry, R.id.number_entry };

	            // create the adapter using the cursor pointing to the desired data as well as the layout information
	            SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.list_example_entry, cursor, columns, to);

	            // set this adapter as your ListActivity's adapter
	            this.setListAdapter(mAdapter);
	      }
	}



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
