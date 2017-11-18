package frsf.isi.grupojf.lab03;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArraySet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import frsf.isi.grupojf.lab03.Dao.TrabajoDao;
import frsf.isi.grupojf.lab03.Dao.TrabajoDaoJson;
import frsf.isi.grupojf.lab03.Dao.TrabajoDaoSQLite;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lvListaTrabajos;
    TrabajoAdapter trabajoAdapter;
    FloatingActionButton btnNuevoTrabajo;
    TextView sin_trabajos;
    private TrabajoDao trabajoDaoSQLite;//cada dao se va a encargar de persistir los datos a su mandera
    private TrabajoDao trabajoDaoJson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //creo ambos dao, para leer la lista uso archivo (json) como pide el enunciado
//        trabajoDaoSQLite = new TrabajoDaoSQLite(this);
        trabajoDaoJson = new TrabajoDaoJson(this);
        List<Categoria> categorias = trabajoDaoJson.listaCategoria();


        btnNuevoTrabajo = (FloatingActionButton) findViewById(R.id.btnNuevoTrabajo);
        btnNuevoTrabajo.setOnClickListener(this);
        sin_trabajos = (TextView) findViewById(R.id.sin_trabajos);
        lvListaTrabajos = (ListView) findViewById(R.id.lvListaTrabajos);
        List<Trabajo> listaTrabajos = null;
        try {
            listaTrabajos = trabajoDaoJson.listaTrabajos();
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        List<Trabajo> listaTrabajos = new LinkedList<>(Arrays.asList(Trabajo.TRABAJOS_MOCK));

        if(listaTrabajos.isEmpty()){//si esta vacia muestro mensaje que dice que no hay trabajos
            sin_trabajos.setVisibility(View.VISIBLE);
        }
        trabajoAdapter = new TrabajoAdapter(this, listaTrabajos);
        lvListaTrabajos.setAdapter(trabajoAdapter);

        registerForContextMenu(lvListaTrabajos);
    }

    /* Creación del menú contextual */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        /* Esto se hace para mostrar un Toast con el elemento seleccionado de la lista
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        Trabajo trabajo = trabajoAdapter.getItem(info.position);
        Toast toast = Toast.makeText(getApplicationContext(), "Se hizo algo " + trabajo.getDescripcion(), Toast.LENGTH_SHORT);
        toast.show();
        */

        MenuInflater inflater = getMenuInflater();
        menu.setHeaderTitle("Acciones");
        inflater.inflate(R.menu.main_context_menu, menu);
    }

    /* Acciones del menú contextual */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.itBorrar:
                Trabajo trabajoEliminado = trabajoAdapter.removeItem(info.position);
                trabajoAdapter.notifyDataSetChanged();
                Toast toast = Toast.makeText(getApplicationContext(), "Se eliminó " + trabajoEliminado.getDescripcion(), Toast.LENGTH_SHORT);
                toast.show();
                return true;
            case R.id.itPostularse:
                Trabajo trabajoPostulado = trabajoAdapter.getItem(info.position);
                //Crear la tarea asincrona y enviar una notificación
                new AsyncPostulacion().execute(trabajoPostulado.getDescripcion());
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNuevoTrabajo:
                Intent intent = new Intent(this, AltaTrabajoActivity.class);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Si se canceló el alta de oferta
        if (resultCode == RESULT_CANCELED) {
        }

        else {//Si se confirmó la creación de la oferta

            Trabajo trabajo = data.getParcelableExtra("OFERTA");
            Log.d("trabajo_cread" ,trabajo.toString());

            //Toast toast = Toast.makeText(getApplicationContext(), oferta, Toast.LENGTH_SHORT);
            //toast.show();
            int id_trabajo = trabajoAdapter.getCount();
            trabajo.setId(id_trabajo);

            if(sin_trabajos.getVisibility() == View.VISIBLE){ //escondo mensaje si esta viisble porque ahora hay por lo menos un trabajo
                sin_trabajos.setVisibility(View.INVISIBLE);
            }

            trabajoDaoJson.crearOferta(trabajo);//guardado en archivo

//            trabajoDaoSQLite.crearOferta(trabajo); //guardado de base de datos y checkeo que haya guardado
            List<Trabajo> litaTrabajos = null;

            try {
                litaTrabajos = trabajoDaoJson.listaTrabajos();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for(Trabajo t: litaTrabajos){
                Log.d("myTag", t.toString());
            }

            trabajoAdapter.addItem(trabajo);
            trabajoAdapter.notifyDataSetChanged();

            Toast toast = Toast.makeText(getApplicationContext(), "Se agregó la oferta: " + trabajo.getDescripcion(), Toast.LENGTH_SHORT);
            toast.show();
        }


    }


    private class AsyncPostulacion extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Context contexto = MainActivity.this;
            NotificationManager mNotificationManager = (NotificationManager)contexto.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Builder builder = new Notification.Builder(contexto)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setAutoCancel(true)
                    .setContentTitle("Se ha postulado con éxito")
                    .setContentText(params[0]);

            //Show the notification
            mNotificationManager.notify(1, builder.build());

            return null;
        }
    }
}
