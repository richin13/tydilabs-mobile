package cr.ac.ucr.paraiso.tydilabs.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cr.ac.ucr.paraiso.tydilabs.R;
import cr.ac.ucr.paraiso.tydilabs.models.Revision;

/**
 * Created by daniel on 5/28/17.
 */

public class RevisionAdapter extends ArrayAdapter<Revision> {

    public RevisionAdapter(Context context, List<Revision> revisions) {
        super(context, 0, revisions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Revision revision = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_revision, parent, false);
        }

        TextView revName = (TextView) convertView.findViewById(R.id.revisionName);
        TextView revDescription = (TextView) convertView.findViewById(R.id.revisionDescription);

        revName.setText(revision.getName());
        revDescription.setText(revision.getDescription() == null ? "-" : revision.getDescription());

        return convertView;
    }
}
