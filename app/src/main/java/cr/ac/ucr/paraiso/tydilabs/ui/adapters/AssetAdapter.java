package cr.ac.ucr.paraiso.tydilabs.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cr.ac.ucr.paraiso.tydilabs.R;
import cr.ac.ucr.paraiso.tydilabs.models.Asset;

/**
 * Created by daniel on 6/11/17.
 */

public class AssetAdapter extends ArrayAdapter<Asset> {

    public AssetAdapter(Context context, List<Asset> assets) {
        super(context, 0, assets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Asset asset = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_asset, parent, false);
        }

        TextView assetTitle = (TextView) convertView.findViewById(R.id.assetTitle);
        assetTitle.append(" " + asset.getId());

        TextView assetInfo = (TextView) convertView.findViewById(R.id.assetInfo);
        String plate_or_quantity = asset.getPlateNumber() == 0 ? "Cant. " + asset.getQuantity() : "Placa. " + asset.getPlateNumber();
        assetInfo.setText(asset.getTypeNice() + " | " + plate_or_quantity);

        return convertView;
    }
}
