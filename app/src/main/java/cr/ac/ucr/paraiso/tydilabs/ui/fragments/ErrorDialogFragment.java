package cr.ac.ucr.paraiso.tydilabs.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import cr.ac.ucr.paraiso.tydilabs.R;

/**
 * Project: Tydilabs
 * Date: 5/27/17
 *
 * @author ricardo
 */

public class ErrorDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // show an error dialog or something
        Bundle args = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMessage(args.getInt("message", R.string.generic_error))
                .setTitle(args.getInt("title", R.string.generic_error_title))
                .setCancelable(true);

        return builder.create();
    }

    public static ErrorDialogFragment newInstance(int message, int title) {
        ErrorDialogFragment f = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putInt("message", message);
        args.putInt("title", title);
        f.setArguments(args);
        return f;
    }

    public static ErrorDialogFragment newInstance() {
        return ErrorDialogFragment.newInstance(R.string.generic_error, R.string.generic_error_title);
    }

}
