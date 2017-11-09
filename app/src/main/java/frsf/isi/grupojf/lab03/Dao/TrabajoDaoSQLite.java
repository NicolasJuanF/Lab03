package frsf.isi.grupojf.lab03.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.grupojf.lab03.Categoria;
import frsf.isi.grupojf.lab03.Trabajo;


/**
 * Created by usuario on 8/11/2017.
 */

public class TrabajoDaoSQLite implements TrabajoDao {
    //atributos
    private SQLiteDatabase db;
    private final Context context;
    private final WorkFromHomeOpenHelper dbhelper;


    public TrabajoDaoSQLite(Context c){
        context = c;
        dbhelper = WorkFromHomeOpenHelper.getInstance(context);
    }

    public List<Categoria> listaCategoria() {
        List<Categoria> categorias = new ArrayList<>();
        db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM categoria" , null);

        // Por cada tubla retornada
        while(cursor.moveToNext()){
            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            categoria.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
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
        cv.put("descripcion", unTrabajo.getDescripcion());
        cv.put("fecha_entrega", unTrabajo.getFechaEntrega().toString());
        cv.put("horas_presupuestadas", unTrabajo.getHorasPresupuestadas());
        cv.put("moneda",unTrabajo.getMonedaPago());
        cv.put("precio", unTrabajo.getPrecioMaximoHora());
        cv.put("requiere_ingles", unTrabajo.getRequiereIngles());
        cv.put("id_categoria", unTrabajo.getCategoria().getId());
        db.insert("trabajo",null, cv);
        db.close();
    }


    @Override
    public List<Trabajo> listaTrabajos() {
        List<Trabajo> trabajos = new ArrayList<>();
        db = dbhelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM trabajo" , null);
        while(c.moveToNext()){
            Trabajo trabajo = new Trabajo();
            trabajo.setId(c.getInt(c.getColumnIndex("_id")));
            trabajo.setDescripcion(c.getString(c.getColumnIndex("descripcion")));
            trabajo.setPrecioMaximoHora(c.getDouble(c.getColumnIndex("precio"))); //precio mas por hora
            trabajo.setMonedaPago(c.getInt(c.getColumnIndex("moneda")));
            trabajo.setHorasPresupuestadas(c.getInt(c.getColumnIndex("horas_presupuestadas")));
            Integer requiereIngles = c.getInt(c.getColumnIndex("requiere_ingles"));
            trabajo.setRequiereIngles(requiereIngles == 1 ? true : false);

            trabajo.setFechaEntrega(c.getString(c.getColumnIndex("fecha_entrega")));//error recibe string requiere date ver!

            for(Categoria cat : this.listaCategoria()){
                if(cat.getId() == c.getInt(c.getColumnIndex("id_categoria"))){
                    trabajo.setCategoria(cat);
                    break;
                }
            }
            trabajos.add(trabajo);
        }
        c.close();
        db.close();

        return trabajos;
    }


    public void borrarOferta(){
        //falta implementar, no lo pide enunciado
    }
}

