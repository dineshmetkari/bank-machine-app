package bank.machine.app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class AppWidgetConfigure extends AppWidgetProvider {

	
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		
	}

	private static final String ACTION_CLICK = "ACTION_CLICK";

	  @Override
	  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	      int[] appWidgetIds) {

	        final int N = appWidgetIds.length;

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, WidgetDialog.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            intent.setAction("Configure Widget");

            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_account_dialog);
            views.setOnClickPendingIntent(R.id.textView1, pendingIntent);
            
	        // Perform this loop procedure for each App Widget that belongs to this provider
	        for (int i=0; i<N; i++) {
	            int appWidgetId = appWidgetIds[i];

	            // Tell the AppWidgetManager to perform an update on the current App Widget
	            appWidgetManager.updateAppWidget(appWidgetId, views);
	    }
	  }
}
