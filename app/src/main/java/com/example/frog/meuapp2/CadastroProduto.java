package com.example.frog.meuapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class CadastroProduto extends AppCompatActivity {

    TextView tDescricao;
    TextView tValor;
    TextView tQuantidade;

    String loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        tDescricao = (EditText) findViewById(R.id.txtDescricao);
        tValor = (EditText) findViewById(R.id.txtValor);
        tQuantidade = (EditText) findViewById(R.id.txtQuantidade);

        loginUser = getSharedLogin();
    }

    public String getSharedLogin() {
        SharedPreferences prefs = getSharedPreferences("preferencias",
                Context.MODE_PRIVATE);

        return prefs.getString("login", "sem login");
    }

    public void btnCadastro(View view) {
        Thread tr = new Thread() {
            @Override
            public void run() {
                final String resultado = setCadastro();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (resultado) {
                            case "ok":
                                Toast.makeText(getApplicationContext(), "Produto Salvo!", Toast.LENGTH_LONG).show();
                                tDescricao.setText("");
                                tValor.setText("");
                                tQuantidade.setText("");
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

    public String setCadastro() {
        Double valor = Double.valueOf(tValor.getText().toString());
        int quantidade = Integer.parseInt(tQuantidade.getText().toString());

        Produto produto = new Produto(loginUser, tDescricao.getText().toString(), valor, quantidade, 1);
        String response = produto.cadastraProduto();

        try {
            JSONObject jsonObject = new JSONObject(response);
            response = jsonObject.getString("response");
        } catch (Exception e) {

        }

        return response;
    }
}
