package frsf.isi.grupojf.lab03.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import frsf.isi.grupojf.lab03.Categoria;
import frsf.isi.grupojf.lab03.Trabajo;



public class TrabajoDaoSQLite implements TrabajoDao {
    //atributos
    private SQLiteDatabase db;
    private final Context context;
    private final WorkFromHomeOpenHelper dbhelper;


    public TrabajoDaoSQLite(Context ctx){
        context = ctx;
        dbhelper = WorkFromHomeOpenHelper.getInstance(context);
    }

    public List<Categoria> listaCategoria() {
        List<Categoria> categorias = new ArrayList<>();
        db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM" + Constants.TABLA_CATEGORIA, null);

        // Por cada tubla retornada
        while(cursor.moveToNext()){
            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(cursor.getColumnIndex(Constants.ID_CATEGORIA)));
            categoria.setDescripcion(cursor.getString(cursor.getColumnIndex(Constants.DESCRIPCION_CATEGORIA)));
            categorias.add(categoria);
        }

        cursor.close();
        db.close();
        return categorias;
    }


    @Override
    public void crearOferta(Trabajo unTrabajo){
        db = dbhelper.getWritableDatabase();
        //clase java, usa para insertar y actualizar
        ContentValues cv = new ContentValues();

        //put ('columna, valor)
        cv.put(Constants.DESCRIPCION_TRABAJO, unTrabajo.getDescripcion());
        cv.put(Constants.FECHA_ENTREGA, unTrabajo.getFechaEntrega().toString());
        cv.put(Constants.HORAS_PRESUPUESTADAS, unTrabajo.getHorasPresupuestadas());
        cv.put(Constants.MONEDA,unTrabajo.getMonedaPago());
        cv.put(Constants.PRECIO_MAXIMO, unTrabajo.getPrecioMaximoHora());
        cv.put(Constants.REQUIERE_INGLES, unTrabajo.getRequiereIngles());
        cv.put(Constants.CATEGORIA_FK, unTrabajo.getCategoria().getId());
        db.insert(Constants.TABLA_TRABAJO,null, cv);
        db.close();
    }


    @Override
    public List<Trabajo> listaTrabajos(){
        List<Trabajo> trabajos = new ArrayList<>();
        db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM" + Constants.TABLA_TRABAJO , null);
        while(cursor.moveToNext()){
            Trabajo trabajo = new Trabajo();
            trabajo.setId(cursor.getInt(cursor.getColumnIndex(Constants.ID_TRABAJO)));
            trabajo.setDescripcion(cursor.getString(cursor.getColumnIndex(Constants.DESCRIPCION_TRABAJO)));
            trabajo.setPrecioMaximoHora(cursor.getDouble(cursor.getColumnIndex(Constants.PRECIO_MAXIMO))); //precio mas por hora
            trabajo.setMonedaPago(cursor.getInt(cursor.getColumnIndex(Constants.MONEDA)));
            trabajo.setHorasPresupuestadas(cursor.getInt(cursor.getColumnIndex(Constants.HORAS_PRESUPUESTADAS)));
            Integer requiereIngles = cursor.getInt(cursor.getColumnIndex(Constants.REQUIERE_INGLES));
            trabajo.setRequiereIngles(requiereIngles == 1);

            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            String stringFecha = cursor.getString(cursor.getColumnIndex(Constants.FECHA_ENTREGA));


            try {
                trabajo.setFechaEntrega(formatoFecha.parse(stringFecha));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            for(Categoria cat : this.listaCategoria()){
                if(cat.getId() == cursor.getInt(cursor.getColumnIndex(Constants.CATEGORIA_FK))){
                    trabajo.setCategoria(cat);
                    break;
                }
            }
            trabajos.add(trabajo);
        }
        cursor.close();
        db.close();

        return trabajos;
    }

}

