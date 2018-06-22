package com.example.frog.meuapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class buscarProduto extends AppCompatActivity {

    TextView tIdProduto;
    String loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_produto);

        tIdProduto = (EditText) findViewById(R.id.txtIdProduto);
        loginUser = getSharedLogin();
    }


    public String getSharedLogin() {
        SharedPreferences prefs = getSharedPreferences("preferencias",
                Context.MODE_PRIVATE);

        return prefs.getString("login", "sem login");
    }

    public void btnBuscaProduto(View view) {

        Thread tr = new Thread() {
            @Override
            public void run() {
                final String resultado = buscaProduto();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (resultado) {
                            case "vazio":
                                Toast.makeText(getApplicationContext(), "ID não existe!", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Intent intent = new Intent(buscarProduto.this, AlterarProduto.class);
                                intent.putExtra("jsonProduto", resultado);
                                startActivity(intent);
                        }
                    }
                });
            }
        };
        tr.start();
    }

    public String buscaProduto() {
        Produto produto = new Produto(loginUser, "", 0.00, 0, 1);
        String response = produto.getProdutoId(tIdProduto.getText().toString());
        String responseAux = response;

        try {
            JSONObject jsonObject = new JSONObject(response);
            response = jsonObject.getString("response");
        } catch (Exception e) {
            response = responseAux;
        }

        return response;
    }

}

