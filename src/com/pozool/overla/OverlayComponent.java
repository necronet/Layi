package com.pozool.overla;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class OverlayComponent extends FrameLayout{

	private boolean hasCloseButton;
	private OnClickListener closeCallback;
	/**
	 * Instantiates a new overlay menu.
	 *
	 * @param context the associated Context
	 */
	public OverlayComponent(Context context) {
		this(context, null);
	}
	
	/**
	 * Instantiates a new Overlay .
	 *
	 * @param context the associated Context
	 * @param attrs the attrs
	 */
	public OverlayComponent(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	/**
	 * Instantiates a new Overlay .
	 *
	 * @param context the associated Context
	 * @param attrs the attrs
	 * @param defStyle
	 */
	public OverlayComponent(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setBackgroundColor(context.getResources().getColor(R.color.overlay_default_background));
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.TOP | Gravity.RIGHT;
		params.setMargins(0, getContext().getResources().getDimensionPixelSize(R.dimen.padding_top), 0, 0);
		TextView buttonClose = new TextView(getContext());
		buttonClose.setTextSize(getResources().getDimension(R.dimen.text_size));
		buttonClose.setTextColor(getResources().getColor(android.R.color.white));
		buttonClose.setId(R.id.button_close);
		buttonClose.setText(R.string.close);
		this.addView(buttonClose, params);
	}

	/**
	 * This will overlay a black background to the activity with a callback
	 * */
	public void wrap(Activity activity, OverlayCallback callback) {
		if (getParent() != null) {
			Log.d("OverlayComponent", "parent is not null");
			throw new IllegalStateException("There is already a overlay tutorial wrapping the activity");
		}

		if (this.hasCloseButton) {
			
		
		this.findViewById(R.id.button_close).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (closeCallback != null) {
					closeCallback.onClick(v);
					setVisibility(View.GONE);
				}
			}
		});
		} else {
			this.findViewById(R.id.button_close).setVisibility(View.GONE);
		}
		
		ViewGroup rootView = (ViewGroup)activity.getWindow().getDecorView();
		Log.d("OverlayComponent", "wraping activity " + rootView.getChildCount());
		rootView.addView(this, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setOnClickListener(new OverlayClickListener(callback));
		Log.d("OverlayComponent", "root was added");
	}
	
	static class OverlayClickListener implements OnClickListener {
		
		private OverlayCallback callback;
		
		public OverlayClickListener(OverlayCallback callback) {
			this.callback = callback;
		}
		
		@Override
		public void onClick(View v) {
			Log.d("OverlayComponent", "overla close " + v);
			if (this.callback != null) {
				callback.onClick(v);
			}
			v.setVisibility(View.GONE);
		}
		
	};
	
	public void withCloseButton(OnClickListener closeCallback) {
		this.hasCloseButton = true;
		this.closeCallback = closeCallback;
	}
	
	interface OverlayCallback extends OnClickListener{}
	
}
