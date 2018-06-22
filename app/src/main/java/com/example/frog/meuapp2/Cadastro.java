package com.example.frog.meuapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


public class Cadastro extends AppCompatActivity {

    TextView txtLogin;
    TextView txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
    }

    public void BotaoCadastra(View view) {
        Thread tr = new Thread() {
            @Override
            public void run() {
                final String resultado = setCadastratro();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (resultado) {
                            case "existe":
                                Toast.makeText(getApplicationContext(), "Esse login já existe!", Toast.LENGTH_LONG).show();
                                break;
                            case "ok":
                                Toast.makeText(getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Erro ao cadastrae!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };
        tr.start();
    }

    public String setCadastratro() {
        User user = new User(txtLogin.getText().toString(), txtSenha.getText().toString(), 1);
        String response = user.cadastraUsuario();

        try {
            JSONObject jsonObject = new JSONObject(response);
            response = jsonObject.getString("resposta");
        } catch (Exception e) {

        }

        return response;
    }

}
