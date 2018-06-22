package com.example.frog.meuapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class AlterarProduto extends AppCompatActivity {

    TextView tAlteraId;
    TextView tAlteraDescricao;
    TextView tAlteraValor;
    TextView tAlteraQuandidade;

    String loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_produto);

        tAlteraId = (EditText) findViewById(R.id.txtAlteraId);
        tAlteraId.setEnabled(false);
        tAlteraDescricao = (EditText) findViewById(R.id.txtAlteraDescricao);
        tAlteraValor = (EditText) findViewById(R.id.txtAlteraValor);
        tAlteraQuandidade = (EditText) findViewById(R.id.txtAlteraQuantidade);

        Intent intent = getIntent();
        String jsonProduto = intent.getStringExtra("jsonProduto");

        try {
            JSONObject jsonObject = new JSONObject(jsonProduto);
            tAlteraId.setText(jsonObject.getString("id"));
            tAlteraDescricao.setText(jsonObject.getString("descricao"));
            tAlteraValor.setText(jsonObject.getString("valor"));
            tAlteraQuandidade.setText(jsonObject.getString("quantidade"));
        } catch (Exception e) {

        }

        loginUser = getSharedLogin();
    }

    public String getSharedLogin() {
        SharedPreferences prefs = getSharedPreferences("preferencias",
                Context.MODE_PRIVATE);

        return prefs.getString("login", "sem login");
    }

    public void btnSalvarAlteracao(View view) {
        Thread tr = new Thread() {
            @Override
            public void run() {
                final String resultado = setAlteracaoProduto();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (resultado) {
                            case "ok":
                                Toast.makeText(getApplicationContext(), "Produto Alterado!", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Erro: " + resultado, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };
        tr.start();
    }

    public String setAlteracaoProduto() {
        Double valor = Double.valueOf(tAlteraValor.getText().toString());
        int quantidade = Integer.parseInt(tAlteraQuandidade.getText().toString());

        Produto produto = new Produto(loginUser, tAlteraDescricao.getText().toString(), valor, quantidade, 1);
        String response = produto.setAlteracaoProduto(tAlteraId.getText().toString());

        try {
            JSONObject jsonObject = new JSONObject(response);
            response = jsonObject.getString("response");
        } catch (Exception e) {

        }

        return response;
    }

}
