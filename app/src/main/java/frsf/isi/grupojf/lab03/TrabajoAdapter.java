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

import java.util.List;

/**
 * Created by Nicolas on 19/9/2017.
 */

public class TrabajoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<Trabajo> listaTrabajos;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Trabajo getItem(int i) {
        return listaTrabajos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public TrabajoAdapter(Context context,List<Trabajo> listaTrabajos) {
        inflater = LayoutInflater.from(context);
        this.listaTrabajos = listaTrabajos;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;

        if(row == null){
            row = inflater.inflate(R.layout.content_ofertalaboral,viewGroup,false);
        }

        ViewHolder holder = (ViewHolder) row.getTag();

        if (holder == null) {
            holder = new ViewHolder(row);
            row.setTag(holder);
        }

        Log.d("Entra?","SI");


        holder.tvCategoria.setText((String) this.getItem(i).getCategoria().getDescripcion());

        holder.tvDescripcion.setText((String) this.getItem(i).getDescripcion());
        switch (this.getItem(i).getMonedaPago()){
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

        holder.checkBox.setChecked((Boolean) this.getItem(i).getRequiereIngles());
        holder.tvFechaFin.setText((String) this.getItem(i).getFechaEntrega().toString());
        holder.tvHora.setText((String) this.getItem(i).getHorasPresupuestadas().toString());


        return row;
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
        }
    }


}
