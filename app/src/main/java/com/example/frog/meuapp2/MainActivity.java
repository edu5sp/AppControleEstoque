package com.example.frog.meuapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    TextView labelLogin;
    TextView labelSenha;

    TextView tLogin;
    TextView tSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelLogin = (TextView) findViewById(R.id.labelLogin);
        labelLogin.setTextColor(Color.BLACK);

        labelSenha = (TextView) findViewById(R.id.labelSenha);
        labelSenha.setTextColor(Color.BLACK);

        tLogin = (EditText) findViewById(R.id.txtLogin);
        tSenha = (EditText) findViewById(R.id.txtSenha);
    }

    public void clicouBotao(View botao) {
        Thread tr = new Thread() {
            @Override
            public void run() {
                final String resultado = loginGet();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = validaDadosJson(resultado);
                        if (r > 0) {
                            SharedLogin(tLogin.getText().toString());
                            Intent intent = new Intent(MainActivity.this, BemVindo.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuário ou senha incorreto!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };
        tr.start();
    }

    public String loginGet() {
        User user = new User(tLogin.getText().toString(), tSenha.getText().toString(), 1);
        return user.getAuthentication();
    }

    public int validaDadosJson(String response) {
        int res = 0;
            try {

                JSONArray json = new JSONArray(response);
                if (json.length() > 0) {

                    res = 1;
                }
            } catch (Exception e) {}
        return res;
    }

    public void IrPaginaSobre(View view) {
        Intent intent = new Intent(MainActivity.this, Sobre.class);
        startActivity(intent);
    }

    public void IrPaginaCadastro(View view) {
        Intent intent = new Intent(MainActivity.this, Cadastro.class);
        startActivity(intent);
    }

    public void SharedLogin(String login) {

        SharedPreferences prefs = getSharedPreferences("preferencias",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();

        ed.putString("login", login);
        ed.apply();
    }

}
