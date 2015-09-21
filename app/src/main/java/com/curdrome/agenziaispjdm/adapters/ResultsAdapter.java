package com.curdrome.agenziaispjdm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.model.Property;

import java.util.List;

/**
 * Created by adria on 21/09/2015.
 */
public class ResultsAdapter extends ArrayAdapter<Property> {
    public ResultsAdapter(Context context, int resource, List<Property> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_row, null);

        TextView province = (TextView) convertView.findViewById(R.id.Provincia);
        TextView city = (TextView) convertView.findViewById(R.id.Citta);
        TextView zone = (TextView) convertView.findViewById(R.id.Zona);
        TextView rooms = (TextView) convertView.findViewById(R.id.NumeroStanze);
        TextView bath = (TextView) convertView.findViewById(R.id.NumeroBagni);
        TextView type = (TextView) convertView.findViewById(R.id.Tipo);
        TextView subtype = (TextView) convertView.findViewById(R.id.SottoTipo);
        TextView price = (TextView) convertView.findViewById(R.id.Prezzo);
        TextView sqm = (TextView) convertView.findViewById(R.id.Mq);
        TextView description = (TextView) convertView.findViewById(R.id.Descrizione);
        TextView foto_link = (TextView) convertView.findViewById(R.id.Foto);

        Property property = getItem(position);

        province.setText(property.getProvince());
        city.setText(property.getCity());
        zone.setText(property.getZone());
        rooms.setText("" + property.getRooms());
        bath.setText("" + property.getBath());
        type.setText(property.getType());
        subtype.setText(property.getSubtype());
        price.setText("" + property.getPrice());
        sqm.setText("" + property.getSqm());
        description.setText(property.getDescription());
        foto_link.setText(property.getFoto_link());
        return convertView;
    }
}
