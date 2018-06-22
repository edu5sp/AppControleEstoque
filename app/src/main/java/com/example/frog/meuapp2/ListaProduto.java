package com.example.frog.meuapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListaProduto extends AppCompatActivity {

    ListView lista;
    String loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produto);

        lista = (ListView) findViewById(R.id.lvProduto);
        loginUser = getSharedLogin();
        getListaProdutos();

    }

    public void getListaProdutos() {
        Thread tr = new Thread() {
            @Override
            public void run() {
                final String resultado = getListaProdutosJson();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            ArrayList<String> dados = new ArrayList<String>();

                            JSONObject jsonObject = new JSONObject(resultado);
                            JSONArray jsonArray = jsonObject.getJSONArray("produtos");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject produto = jsonArray.getJSONObject(i);
                                dados.add("ID: " + produto.getString("id") + " PRODUTO: " + produto.getString("descricao"));
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ListaProduto.this, android.R.layout.simple_list_item_1, dados);
                            lista.setAdapter(arrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        tr.start();
    }

    public String getListaProdutosJson() {
        Produto produto = new Produto(loginUser, "", 0.00, 0, 1);
        String response = produto.getProduto();

        return response;
    }

    public String getSharedLogin() {
        SharedPreferences prefs = getSharedPreferences("preferencias",
                Context.MODE_PRIVATE);

        return prefs.getString("login", "sem login");
    }

}
