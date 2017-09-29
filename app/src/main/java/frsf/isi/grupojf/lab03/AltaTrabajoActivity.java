package frsf.isi.grupojf.lab03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AltaTrabajoActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnGuardarOferta;
    Button btnCancelarOferta;
    EditText etOferta;
    Spinner spCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_trabajo);

        btnGuardarOferta = (Button) findViewById(R.id.btnGuardarOferta);
        btnCancelarOferta = (Button) findViewById(R.id.btnCancelarOferta);
        btnCancelarOferta.setOnClickListener(this);
        btnGuardarOferta.setOnClickListener(this);

        etOferta = (EditText) findViewById(R.id.etOferta);

        spCategoria = (Spinner) findViewById(R.id.spCategoria);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Arrays.asList(Categoria.CATEGORIAS_MOCK));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCategoria.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGuardarOferta:
                // Si el EditText no está vacío recogemos el resultado.
                if(etOferta.getText().length()!=0) {
                    String oferta = etOferta.getText().toString();
                    // Recogemos el intent que ha llamado a esta actividad.
                    Intent i = getIntent();
                    // Le metemos el resultado que queremos mandar a la
                    // actividad principal.
                    i.putExtra("OFERTA", oferta);
                    setResult(RESULT_OK, i);

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
