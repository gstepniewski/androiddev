package com.example.test;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		remoteView.setOnClickPendingIntent(R.id.widget_button, createButtonIntent(context));
		
		ComponentName myWidget = new ComponentName(context, WidgetProvider.class);
		appWidgetManager.updateAppWidget(myWidget, remoteView);
	}
	
	private PendingIntent createButtonIntent(Context context) {
		Intent i = new Intent(context, SecondActivity.class);
		i.putExtra("text", "widget clicked");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
		
		return pi;
	}
	
}
