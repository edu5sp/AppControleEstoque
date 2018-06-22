package com.example.frog.meuapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BemVindo extends AppCompatActivity {

    public TextView lLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);

        String loginUser = getSharedLogin();

        lLogin = (TextView) findViewById(R.id.labelLogin);
        lLogin.setText("Bem-vindo " + loginUser);
    }

    public String getSharedLogin() {
        SharedPreferences prefs = getSharedPreferences("preferencias",
                Context.MODE_PRIVATE);

        return prefs.getString("login", "sem login");
    }

    public void btnCadastroUser(View view) {
        Intent intent = new Intent(BemVindo.this, CadastroProduto.class);
        startActivity(intent);
    }

    public void btnListaProduto(View view) {
        Intent intent = new Intent(BemVindo.this, ListaProduto.class);
        startActivity(intent);
    }

    public void bntPesquisarProduto(View view) {
        Intent intent = new Intent(BemVindo.this, buscarProduto.class);
        startActivity(intent);
    }

    public void btnSair(View view) {
        Intent intent = new Intent(BemVindo.this, MainActivity.class);
        startActivity(intent);
    }

}
