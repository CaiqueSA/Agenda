package br.com.alura.agenda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Aluno;
import br.com.alura.agenda.util.ImageUtil;

/**
 * Created by caique on 13/11/16.
 */

public class AlunoAdapter extends BaseAdapter {

    private final Context context;
    private final List<Aluno> alunos;


    public AlunoAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Aluno getItem(int i) {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alunos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Aluno aluno = alunos.get(i);

        LayoutInflater inflater = LayoutInflater.from(context);

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_agenda, viewGroup, false);
        }

        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());

        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoTelefone.setText(aluno.getTelefone());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        campoFoto.setImageBitmap(ImageUtil.convertInBitmap(aluno.getFoto()));
        campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);

        return view;
    }
}
