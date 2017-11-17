package frsf.isi.grupojf.lab03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AltaTrabajoActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnGuardarOferta;
    private Button btnCancelarOferta;
    private EditText etOferta;
    private EditText etFecha;
    private EditText etHoras;
    private Spinner spCategoria;
    private EditText etPrecio;
    private RadioGroup rgMoneda;
    private CheckBox checkIngles;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_trabajo);

        btnGuardarOferta = (Button) findViewById(R.id.btnGuardarOferta);
        btnCancelarOferta = (Button) findViewById(R.id.btnCancelarOferta);


        btnCancelarOferta.setOnClickListener(this);
        btnGuardarOferta.setOnClickListener(this);

        etOferta = (EditText) findViewById(R.id.etOferta);
        etFecha= (EditText) findViewById(R.id.etFecha);
        etHoras = (EditText) findViewById(R.id.etHoras);
        etOferta = (EditText) findViewById(R.id.etOferta);
        etPrecio = (EditText) findViewById(R.id.etPrecio);
        rgMoneda = (RadioGroup) findViewById(R.id.rbMoneda);
        spCategoria = (Spinner) findViewById(R.id.spCategoria);
        checkIngles = (CheckBox) findViewById(R.id.cbIngles);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Arrays.asList(Categoria.CATEGORIAS_MOCK));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCategoria.setAdapter(dataAdapter);

        intent = getIntent();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGuardarOferta:
                // Si el EditText no está vacío recogemos el resultado.
                if(etOferta.getText().length()!=0) {
                    String oferta = etOferta.getText().toString();
                    Trabajo nuevoTrabajo = new Trabajo(0,oferta);
                    nuevoTrabajo.setDescripcion(oferta);
                    nuevoTrabajo.setCategoria((Categoria) spCategoria.getSelectedItem());
                    nuevoTrabajo.setHorasPresupuestadas(Integer.parseInt(etHoras.getText().toString()));
                    nuevoTrabajo.setPrecioMaximoHora(Double.parseDouble(etHoras.getText().toString()));
                    nuevoTrabajo.setRequiereIngles(checkIngles.isChecked());
                    nuevoTrabajo.setMonedaPago(rgMoneda.getCheckedRadioButtonId());
                    SimpleDateFormat formato=  new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha = null;
                    try {
                        fecha = formato.parse(etFecha.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    nuevoTrabajo.setFechaEntrega(fecha);
                    // Recogemos el intent que ha llamado a esta actividad.

                    // Le metemos el resultado que queremos mandar a la
                    // actividad principal.
                    intent.putExtra("OFERTA", nuevoTrabajo);
                    setResult(RESULT_OK, intent);

                    // Finalizamos la Activity para volver a la anterior
                    finish();
                } else {
                    // Si no tenía nada escrito el EditText lo avisamos.
                    Toast.makeText(this,"Debe escribir el nombre de la oferta", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCancelarOferta:
                // Si se pulsa el botón, establecemos el resultado como cancelado.
                setResult(RESULT_CANCELED);
                // Finalizamos la Activity para volver a la anterior
                finish();
        }
    }
}
