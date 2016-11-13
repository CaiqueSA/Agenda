package br.com.alura.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import br.com.alura.agenda.modelo.Aluno;

public class FormularioHelper {
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        aluno = new Aluno();
    }


    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setFoto(convertInByte(new File((String) campoFoto.getTag())));
        return aluno;
    }


    public void carregarImagem(String caminhoFoto) {
        Bitmap bm = BitmapFactory.decodeFile(caminhoFoto);
        bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
        campoFoto.setImageBitmap(bm);
        campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        campoFoto.setTag(caminhoFoto);
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getFoto());
        this.aluno = aluno;
    }

    private void carregaImagem(byte[] foto) {
        if (foto != null) {
            ByteArrayInputStream fotoStream = new ByteArrayInputStream(foto);
            Bitmap imagemFoto = BitmapFactory.decodeStream(fotoStream);
            campoFoto.setImageBitmap(imagemFoto);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    private byte[] convertInByte(File arquivoFoto) {
        try {
            FileInputStream is = new FileInputStream(arquivoFoto);
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayOutputStream bos = new ByteArrayOutputStream(500);
            int current = 0;
            while ((current = bis.read()) != -1) {
                bos.write((byte) current);
            }

            return bos.toByteArray();
        } catch (IOException e) {
            Log.d("convertInByte", "Error: " + e.toString());
        }
        return null;
    }
}
