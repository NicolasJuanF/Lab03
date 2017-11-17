package frsf.isi.grupojf.lab03.Dao;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.AsynchronousFileChannel;
import java.text.ParseException;
import java.util.List;

import frsf.isi.grupojf.lab03.Categoria;
import frsf.isi.grupojf.lab03.Trabajo;

/**
 * Created by usuario on 10/11/2017.
 */

public class TrabajoDaoJson implements TrabajoDao {

    private List<Categoria> categorias;
    private Context context;


    public TrabajoDaoJson(){

    }

    public List<Categoria> listaCategoria() {
        if(this.categorias == null || this.categorias.isEmpty()) {
            try {

                String categoriasJsonString = readJsonFile("categoria.json");

                JSONObject object = (JSONObject) new JSONTokener(categoriasJsonString).nextValue();

                JSONArray categoriasJsonArray = object.getJSONArray("categorias");

                for(int i=0; i<categoriasJsonArray.length(); i++) {

                    JSONObject categoriaJson = (JSONObject) categoriasJsonArray.get(i);

                    Categoria nuevaCategoria = new Categoria();

                    nuevaCategoria.setId(categoriaJson.getInt("id"));
                    nuevaCategoria.setDescripcion(categoriaJson.getString("descripcion"));

                    this.categorias.add(nuevaCategoria);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this.categorias;
    }

    @Override
    public void crearOferta(Trabajo t) {

    }

    @Override
    public void borrarOferta(Trabajo t) {

    }

    @Override
    public List<Trabajo> listaTrabajos() throws ParseException {
        return null;
    }



    /** USA ASSETS, TODAVIA NO SE CUAL ES LA CARPETA CORRESPIENDTE
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

    private String readJsonFile(String fileName){
        // Uso string builder porque es mas eficiente
        StringBuilder sb = new StringBuilder();
        try {
            // Creo FileInputStream del archivo con nombre pasado por parametro
            FileInputStream mInput = context.openFileInput(fileName);
            // Arreglo de bytes donde se almacenaran las lecturas. Lee de a 128 bytes.
            byte[] data = new byte[128];
            // Mientras que haya algo por leer, leo en data
            while(mInput.read(data)!=-1){
                // Paso los bytes de data a string y los concateno al string builder
                sb.append(new String(data));
            }
            // Cierro el stream
            mInput.close();
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace();}
        // Devuelvo el string generado con el string builder.
        return sb.toString();
    }


}
