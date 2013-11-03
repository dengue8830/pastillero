package com.codequark.pastillero;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.codequark.pastillero.businesslogic.PastilleroManager;
import com.codequark.pastillero.lista.ListaAdaptador;
import com.codequark.pastillero.lista.ListaEntrada;
import com.codequark.pastillero.model.vo.PacienteVO;

public class MainActivity extends Activity{

    private ListView lista;
    ArrayList<ListaEntrada> datos;
    private PastilleroManager manager = new PastilleroManager(this);
    public static final long NORMAL = -1l;
    public static final long AGREGAR = -2l;
    public static final long INFORMACION = -3l;
    public static final long EDITAR = -4l;
    public static final long BORRAR = -5l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(this.getIntent().getExtras() != null){
            CharSequence texto = "Agregado con exito el paciente: " + this.getIntent().getExtras().getString("nuevoPaciente");

            if(texto.length() != 0){
                Toast toast = Toast.makeText(MainActivity.this, texto, Toast.LENGTH_LONG);
                toast.show();
            }
        }

        //Log.i("AAAAAQQQQQQUUUUUUUUIIIIIIIII", "Comenzando activity main");

        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Pacientes", null).setContent(R.id.tab1Layout));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Configuración", null).setContent(R.id.tab2Layout));

        manager.openDB();

        ArrayList<PacienteVO> pacientesVO = manager.getAllpacientes();

        manager.closeDB();
        datos = manager.cargarListaEntradas(pacientesVO, getString(R.string.opciones_agregar_paciente));


        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setAdapter(new ListaAdaptador(this, R.layout.item_paciente, datos){
            @Override
            public void onEntrada(Object item, View view, int posicion) {
                if (item == null) {
                    return;
                }

                ListaEntrada entrada = (ListaEntrada) item;

                TextView textoSuperiorEntrada = (TextView) view.findViewById(R.id.textView_superior);
                if (textoSuperiorEntrada != null)
                    textoSuperiorEntrada.setText(entrada.getTextoEncima());

                TextView textoInferiorEntrada = (TextView) view.findViewById(R.id.textView_inferior);
                if (textoInferiorEntrada != null)
                    textoInferiorEntrada.setText(entrada.getTextoDebajo());

                ImageView imagenEntrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                if (imagenEntrada != null)
                    imagenEntrada.setImageResource(entrada.getIdImagen());

                if(entrada.getTipo() != NORMAL){
                    return;
                }

                ImageView imagenInfo = (ImageView) view.findViewById(R.id.imageView_informacion);
                imagenInfo.setImageResource(R.drawable.icono_informacion);
                imagenInfo.setContentDescription("id: "+entrada.getPacienteVo().getIdPaciente()+", dni: "+entrada.getPacienteVo().getDni());

                imagenInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence texto = view.getContentDescription();
                        Toast toast = Toast.makeText(MainActivity.this, texto, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

                ImageView imagenEditar = (ImageView) view.findViewById(R.id.imageView_editar);
                imagenEditar.setImageResource(R.drawable.icono_editar_lapiz);
                imagenEditar.setContentDescription(entrada.getPacienteVo().getIdPaciente() + "");

                imagenEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*CharSequence texto = "editar pressed, id: "+view.getContentDescription().toString();
                        Toast toast = Toast.makeText(MainActivity.this, texto, Toast.LENGTH_SHORT);
                        toast.show();*/
                        Intent intent = new Intent(MainActivity.this, AltaPacienteActivity.class);
                        intent.putExtra("id", Long.parseLong(view.getContentDescription().toString()));
                        startActivity(intent);
                        finish();
                    }
                });

                ImageView imagenBorrar = (ImageView) view.findViewById(R.id.imageView_borrar);
                imagenBorrar.setImageResource(R.drawable.icono_borrar_tacho);
                imagenBorrar.setContentDescription(entrada.getPacienteVo().getIdPaciente() + "");
                imagenBorrar.setTag(posicion);

                imagenBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence texto;
                        Toast toast;

                        try {
                            manager.openDB();
                            manager.borrarPaciente(Long.parseLong(view.getContentDescription().toString()));
                            manager.closeDB();
                        }catch (Exception e){
                            texto = "Error al borrar... \n " + e.getStackTrace();
                            toast = Toast.makeText(MainActivity.this, texto, Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }

                        //Si ha ido bien, se muestra el siguiente mensaje.
                        texto = "Borrado con éxito ";
                        toast = Toast.makeText(MainActivity.this, texto, Toast.LENGTH_SHORT);
                        toast.show();

                        //Modificamos la lista de datos con la que alimentamos a el adaptador.
                        datos.remove(Integer.parseInt(view.getTag().toString()));
                        //Avisamos al adaptador de ese cambio.
                        notifyDataSetChanged();
                        }
                    }

                    );
                }
            });

        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                ListaEntrada elegido = (ListaEntrada) pariente.getItemAtPosition(posicion);

                //if(elegido.getTextoEncima().equalsIgnoreCase(getString(R.string.opciones_agregar_paciente))){
                if(elegido.getTipo() == AGREGAR){
                    Intent intent = new Intent(MainActivity.this, AltaPacienteActivity.class);
                    startActivity(intent);
                    finish();
                    /*CharSequence texto = "elegido new con id"+pariente.getId()+" y count:"+pariente.getCount();
                    Toast toast = Toast.makeText(MainActivity.this, texto, Toast.LENGTH_SHORT);
                    toast.show();*/
                }
            }
        });

       /* Button boton = (Button) view.findViewById(R.id.button2);
        boton.setBackgroundResource(R.drawable.info);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence texto = "boton info pressed";
                Toast toast = Toast.makeText(MainActivity.this, texto, Toast.LENGTH_SHORT);
                toast.show();
            }
        });*/

    }

}