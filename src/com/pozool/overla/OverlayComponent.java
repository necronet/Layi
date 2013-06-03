package com.pozool.overla;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class OverlayComponent {

	public static void showOverlay(Activity activity) {
		ViewGroup rootView = (ViewGroup)activity.getWindow().getDecorView();
		
		FrameLayout overlay = new FrameLayout(activity);
		overlay.setBackgroundColor(Color.BLACK);
		
		rootView.addView(overlay);
	}
	
}
