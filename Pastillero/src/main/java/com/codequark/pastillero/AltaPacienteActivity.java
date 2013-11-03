package com.codequark.pastillero;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.codequark.pastillero.businesslogic.PastilleroManager;
import com.codequark.pastillero.model.vo.PacienteVO;

public class AltaPacienteActivity extends Activity {

    private PastilleroManager manager = new PastilleroManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_paciente);

        PacienteVO pacienteVO;

        if(this.getIntent().getExtras() != null){
            manager.openDB();
            pacienteVO = manager.getPacienteById(this.getIntent().getExtras().getLong("id"));
            manager.closeDB();

            EditText txtNombre = (EditText) findViewById(R.id.altaPacientes_editText_nombre);
            txtNombre.setText(pacienteVO.getNombre());
            EditText txtApellido = (EditText) findViewById(R.id.altaPacientes_editText_apellido);
            txtApellido.setText(pacienteVO.getApellido());
            EditText txtEdad = (EditText) findViewById(R.id.altaPacientes_editText_edad);
            txtEdad.setText(String.valueOf(pacienteVO.getEdad()));
            EditText txtDni = (EditText) findViewById(R.id.altaPacientes_editText_dni);
            txtDni.setText(pacienteVO.getDni());
        }else{
            pacienteVO = new PacienteVO();
            pacienteVO.setIdPaciente(-1l);
            pacienteVO.setIdImagen(R.drawable.icono_camara);
        }

        TabHost tabHost = (TabHost) findViewById(R.id.altaPacientes_tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("PACIENTE", null).setContent(R.id.altaPacientes_tab1Layout));

        ImageView foto = (ImageView) findViewById(R.id.altaPacientes_imageView_foto);
        foto.setImageResource(pacienteVO.getIdImagen());

        ImageButton botonOk = (ImageButton) findViewById(R.id.altaPacientes_imageButton_ok);
        botonOk.setImageResource(R.drawable.icono_aceptar_tilde);
        botonOk.setTag(pacienteVO.getIdPaciente());

        botonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtNombre = (EditText) findViewById(R.id.altaPacientes_editText_nombre);
                EditText txtApellido = (EditText) findViewById(R.id.altaPacientes_editText_apellido);
                EditText txtEdad = (EditText) findViewById(R.id.altaPacientes_editText_edad);
                EditText txtDni = (EditText) findViewById(R.id.altaPacientes_editText_dni);
                //TODO: recuperar iamgen de foto
                ImageView foto = (ImageView) findViewById(R.id.altaPacientes_imageView_foto);
                // foto.setimage(foto.getimage == icono_camara ? ic_launcher : foto.getimage);

                PacienteVO newPaciente = new PacienteVO(R.drawable.ic_launcher, Long.valueOf(view.getTag().toString()), txtNombre.getText().toString(),
                        txtApellido.getText().toString(), Short.parseShort(txtEdad.getText().toString()), txtDni.getText().toString());

                //PastilleroManager manager = new PastilleroManager(AltaPacienteActivity.this);//

                manager.openDB();

                if(newPaciente.getIdPaciente() == -1l){
                    manager.insertPaciente(newPaciente);
                }else{
                    manager.updatePaciente(newPaciente);
                }

                manager.closeDB();

                Intent intent = new Intent(AltaPacienteActivity.this, MainActivity.class);

                if(newPaciente.getIdPaciente() == -1l){
                    intent.putExtra("nuevoPaciente", newPaciente.getApellidoComaEspacioNombre());
                }

                //((Activity) view.getContext()).finish();//.getApplicationContext()
                startActivity(intent);
                finish();
            }
        });

        ImageButton botonCancel = (ImageButton) findViewById(R.id.altaPacientes_imageButton_cancel);
        botonCancel.setImageResource(R.drawable.icono_borrar_cruz);

        botonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AltaPacienteActivity.this, MainActivity.class);
                startActivity(intent);
                //((Activity) view.getContext()).finish();//.getApplicationContext()
                finish();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.alta_paciente, menu);
        return true;
    }
    
}
