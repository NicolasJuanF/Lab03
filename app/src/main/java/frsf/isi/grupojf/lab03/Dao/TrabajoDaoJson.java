package frsf.isi.grupojf.lab03.Dao;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import frsf.isi.grupojf.lab03.Categoria;
import frsf.isi.grupojf.lab03.Trabajo;

/**
 * Created by usuario on 10/11/2017.
 */

public class TrabajoDaoJson implements TrabajoDao {

    private Context context;

    public TrabajoDaoJson(Context ctx) {
        context = ctx;
        File archivoCategoria = new File("categorias.json");
        File archivoTrabajo = new File("trabajos.json");

        if(!archivoTrabajo.exists()){//si no existe, es porque no hay trabajos, creo archivo vacío
            try {
                archivoTrabajo.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        // Si no existe un archivo de categorias
        if(!archivoCategoria.exists()) try {
            JSONArray categoriasJsonArray = new JSONArray();
            categoriasJsonArray.put(new JSONObject().put("id", 1).put("descripcion", "Arquitecto"));
            categoriasJsonArray.put(new JSONObject().put("id", 2).put("descripcion", "Desarrollador"));
            categoriasJsonArray.put(new JSONObject().put("id", 3).put("descripcion", "Tester"));
            categoriasJsonArray.put(new JSONObject().put("id", 4).put("descripcion", "Analista"));
            categoriasJsonArray.put(new JSONObject().put("id", 5).put("descripcion", "Mobile Developer"));
            // Lo creo y lo abro en MODE PRIVATE por las dudas (sobreescribe lo que haya)
            FileOutputStream mOutput = context.openFileOutput("categorias.json", Activity.MODE_PRIVATE);
            // Y escribo el JSON de categorias como string.
            mOutput.write(categoriasJsonArray.toString().getBytes());
            mOutput.flush();
            mOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public List<Categoria> listaCategoria() {
        List<Categoria> categorias = new ArrayList<>();
            try {
                String categoriasString = readJsonFile("categorias.json");

                //En el archivo tenemos guardados json arryas, los traemos
                JSONArray categoriasJSONArray = new JSONArray(categoriasString);

                for(int i=0; i<categoriasJSONArray.length(); i++) {

                    JSONObject categoriaJson = (JSONObject) categoriasJSONArray.get(i);

                    Categoria unaCategoria = new Categoria();

                    unaCategoria.setId(categoriaJson.getInt("id"));
                    unaCategoria.setDescripcion(categoriaJson.getString("descripcion"));

                    categorias.add(unaCategoria);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return categorias;
    }

    @Override
    public List<Trabajo> listaTrabajos() {
        List<Trabajo> trabajos = new ArrayList<>();
        try {
            // Leo el archivo
            String trabajosString= readJsonFile("trabajos.json");
            if(!trabajosString.isEmpty()) {

                JSONArray trabajosJsonArray = new JSONArray(trabajosString);
                // Obtengo el arreglo de trabajos que esta en el objeto

                for (int i = 0; i < trabajosJsonArray.length(); i++) {
                    //cada posicion es un json, lo asigno y creo el trabajo
                    JSONObject trabajoJson = (JSONObject) trabajosJsonArray.get(i);


                    Trabajo nuevoTrabajo = new Trabajo();
                    nuevoTrabajo.setId(trabajoJson.getInt("id"));
                    nuevoTrabajo.setDescripcion(trabajoJson.getString("descripcion"));
                    nuevoTrabajo.setHorasPresupuestadas(trabajoJson.getInt("horas-presupuestadas"));
                    nuevoTrabajo.setPrecioMaximoHora(trabajoJson.getDouble("precio-max-hora"));
                    nuevoTrabajo.setMonedaPago(trabajoJson.getInt("moneda"));
                    nuevoTrabajo.setRequiereIngles(trabajoJson.getBoolean("requiere-ingles"));
                    // Obtengo el json object de la categoria, cargo los datos al objeto categoria y lo pongo al trabajo
                    JSONObject categoriaJson = trabajoJson.getJSONObject("categoria");
                    Categoria categoria = new Categoria();
                    categoria.setDescripcion(categoriaJson.getString("descripcion"));
                    categoria.setId(categoriaJson.getInt("id"));
                    nuevoTrabajo.setCategoria(categoria);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String fechaString = trabajoJson.getString("fecha-entrega");
                    Date fechaEntrega = sdf.parse(fechaString);
                    nuevoTrabajo.setFechaEntrega(fechaEntrega);

                    trabajos.add(nuevoTrabajo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return trabajos;
    }

    /**
       Esta funcion tiene que leer todo el archivo,
        agregar el nuevo trabajo y luego sobreescribir.
        No puedo agregar solo uno, (si se puede, no se como)
      */
    @Override
    public void crearOferta(Trabajo trabajo) {
        try {
            FileOutputStream mOutput = context.openFileOutput("trabajos.json", Activity.MODE_PRIVATE);//NO SE USA MODE APPEND PORQUE HAY QUE SOBREESCRIBIR EL ARCHIVO

            List<Trabajo> trabajos = this.listaTrabajos();
            trabajos.add(trabajo);
            JSONArray trabajoJsonArray = trabajosToJson(trabajos);
            mOutput.write(trabajoJsonArray.toString().getBytes());
            mOutput.flush();
            mOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //toma lista de trabajos, y los tranforma a json par ser guardados
    private JSONArray trabajosToJson(List<Trabajo> trabajos) throws JSONException {
        JSONArray trabajosJsonArray = new JSONArray();

        for (Trabajo trabajo: trabajos) {
            JSONObject trabajoJson = new JSONObject();
            trabajoJson.put("id", trabajo.getId());
            trabajoJson.put("descripcion", trabajo.getDescripcion());
            trabajoJson.put("horas-presupuestadas", trabajo.getHorasPresupuestadas());
            trabajoJson.put("precio-max-hora", trabajo.getPrecioMaximoHora());
            trabajoJson.put("moneda", trabajo.getMonedaPago());
            trabajoJson.put("requiere-ingles", trabajo.getRequiereIngles());
            // Formateo el Date a dd/MM/yyyy
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            trabajoJson.put("fecha-entrega", sdf.format(trabajo.getFechaEntrega()));
            // Creo el JSONObject Categoria para añadir al trabajo
            JSONObject categoria = new JSONObject();
            categoria.put("id", trabajo.getCategoria().getId());
            categoria.put("descripcion", trabajo.getCategoria().getDescripcion());
            trabajoJson.put("categoria", categoria);

            trabajosJsonArray.put(trabajoJson);
        }

        return trabajosJsonArray;
    }

    /** Si usamos ASSETS,
     *
    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("file_name.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
     */

    private String readJsonFile(String archivo){

        StringBuilder sb = new StringBuilder();
        try {

            FileInputStream mInput = context.openFileInput(archivo);

            byte[] data = new byte[128];

            while(mInput.read(data)!=-1){

                sb.append(new String(data));
            }

            mInput.close();
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace();}

        return sb.toString();
    }


}
