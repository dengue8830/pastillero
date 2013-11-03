package com.codequark.pastillero.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codequark.pastillero.R;
import com.codequark.pastillero.model.vo.PacienteVO;

import java.util.ArrayList;

/**
 * Created by dengue8830 on 10/6/13.
 */
public abstract class PacienteListAdapter extends BaseAdapter{
    private int idLayout;
    private ArrayList<PacienteVO> pacientes;
    private Context context;

    public PacienteListAdapter(Context context, ArrayList<PacienteVO> pacientes, int idLayout) {
        super();

        this.context = context;
        this.pacientes = pacientes;
        this.idLayout = idLayout;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup pariente) {

        /*LayoutInflater inflater;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(idLayout, pariente, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.textView_ape_nombre);
        textView.setText(pacientes.get(posicion).getApellidoComaEspacioNombre());

        ImageView foto = (ImageView) view.findViewById(R.id.imageView_foto);
        foto.setImageResource(pacientes.get(posicion).getIdImagen());*/

        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(idLayout, null);
        }
        onEntrada (pacientes.get(posicion), view);

        return view;
    }

    public abstract void onEntrada (Object entrada, View view);
}
