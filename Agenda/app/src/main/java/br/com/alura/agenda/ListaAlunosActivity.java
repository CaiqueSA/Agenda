package br.com.alura.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.alura.agenda.adapter.AlunoAdapter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);


        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);

                Intent intentVaiPtoFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentVaiPtoFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiPtoFormulario);
            }
        });

        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(vaiProFormulario);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_notas:
                new EnviaAlunosTask(this).execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);

        return true;
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoAdapter adapter = new AlunoAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem itemDeletar = menu.add("Deletar");
        carregarLista(aluno, itemDeletar);
        MenuItem itemSite = menu.add("Visitar site");
        abrirSite(aluno, itemSite);
        MenuItem itemSMS = menu.add("Enviar SMS");
        enviarSMS(aluno, itemSMS);
        MenuItem itemMapa = menu.add("Localização");
        abrirMapa(aluno, itemMapa);
        MenuItem itemLigar = menu.add("Ligar para aluno");
        ligar(aluno, itemLigar);
    }

    private void abrirMapa(final Aluno aluno, MenuItem itemMapa) {
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);
    }

    private void enviarSMS(final Aluno aluno, MenuItem itemSMS) {
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);
    }

    private void carregarLista(final Aluno aluno, MenuItem itemDeletar) {
        itemDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();

                carregaLista();
                return false;
            }
        });
    }

    private void abrirSite(Aluno aluno, MenuItem itemSite) {
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = aluno.getSite();
        if (!site.startsWith("http://") || !site.startsWith("https://")) {
            site = "http://" + site;
        }
        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);
    }

    private void ligar(Aluno aluno, MenuItem itemLigar) {
        if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, 123);
        } else {
            Intent intentLigar = new Intent(Intent.ACTION_CALL);
            intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
            startActivity(intentLigar);
        }
    }
}
