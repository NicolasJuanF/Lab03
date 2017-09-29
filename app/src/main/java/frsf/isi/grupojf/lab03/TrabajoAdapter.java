package frsf.isi.grupojf.lab03;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 19/9/2017.
 */

public class TrabajoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    List<Trabajo> listaTrabajos;

    public TrabajoAdapter(Context context, List<Trabajo> listaTrabajos) {
        this.context = context;
        this.listaTrabajos = listaTrabajos;
    }

    @Override
    public int getCount() {
        return listaTrabajos.size();
    }

    @Override
    public Trabajo getItem(int position) {
        return listaTrabajos.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Trabajo removeItem(int i) { return this.listaTrabajos.remove(i);}

    public Boolean addItem(Trabajo trabajo) { return this.listaTrabajos.add(trabajo);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.content_ofertalaboral, parent, false);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        if (holder == null) {
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Log.d("Entra?","SI");

        Trabajo trabajo = getItem(position);


        holder.tvCategoria.setText(trabajo.getCategoria().getDescripcion());

        holder.tvDescripcion.setText(trabajo.getDescripcion());

        switch (trabajo.getMonedaPago()){
            case 1 :
                holder.bandera.setImageResource(R.drawable.us);
                break;
            case 2 :
                holder.bandera.setImageResource(R.drawable.eu);
                break;
            case 3 :
                holder.bandera.setImageResource(R.drawable.ar);
                break;
            case 4 :
                holder.bandera.setImageResource(R.drawable.uk);
                break;
            case 5 :
                holder.bandera.setImageResource(R.drawable.br);
                break;
        }

        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");

        holder.checkBox.setChecked(trabajo.getRequiereIngles());
        holder.tvFechaFin.setText("Fecha entrega: " + simpleDate.format(trabajo.getFechaEntrega()));
        holder.tvHora.setText("Horas: " + trabajo.getHorasPresupuestadas().toString()
                + " - Máx $/Hora: " + String.format( "%.2f", trabajo.getPrecioMaximoHora() ));


        return convertView;
    }

    class ViewHolder {
        ImageView bandera = null;
        TextView tvCategoria = null;
        TextView tvDescripcion = null;
        TextView tvHora = null;
        TextView tvFechaFin = null;
        CheckBox checkBox = null;

        ViewHolder(View base) {
            this.bandera = (ImageView) base.findViewById(R.id.bandera);
            this.tvCategoria = (TextView) base.findViewById(R.id.tvCategoria);
            this.tvDescripcion = (TextView) base.findViewById(R.id.tvDescripcion);
            this.tvFechaFin= (TextView) base.findViewById(R.id.tvFechaFin);
            this.tvHora= (TextView) base.findViewById(R.id.tvHora);
            this.checkBox = (CheckBox) base.findViewById(R.id.checkBox);
            checkBox.setEnabled(false);
        }
    }


}
