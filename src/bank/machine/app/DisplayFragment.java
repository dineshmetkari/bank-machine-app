package bank.machine.app;

import android.app.ListFragment;
import android.os.Bundle;
//import android.support.v4.content.Loader;
import android.app.LoaderManager;
import android.database.Cursor;

public class DisplayFragment extends ListFragment implements
	LoaderManager.LoaderCallbacks<Cursor>{

	@Override
	public android.content.Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(android.content.Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(android.content.Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

}
