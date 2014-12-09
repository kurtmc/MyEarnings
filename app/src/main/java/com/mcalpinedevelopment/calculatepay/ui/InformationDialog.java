package com.mcalpinedevelopment.calculatepay.ui;

import com.mcalpinedevelopment.calculatepay.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class InformationDialog extends DialogFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    // Inflate the layout to use as dialog or embedded fragment
	    View infoDialog =  inflater.inflate(R.layout.information_dialog, container, false);
	    TextView mInfoText = (TextView) infoDialog.findViewById(R.id.informationTextView);
	    mInfoText.setText(savedInstanceState.getString("information"));
	    
	    return infoDialog;
	}
	
	
	/** The system calls this only when creating the layout in a dialog. */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    // The only reason you might override this method when using onCreateView() is
	    // to modify any dialog characteristics. For example, the dialog includes a
	    // title by default, but your custom layout might not need it. So here you can
	    // remove the dialog title, but you must call the superclass to get the Dialog.
	    Dialog dialog = super.onCreateDialog(savedInstanceState);
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    return dialog;
	}
}
