package com.example.frog.meuapp2;

import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class User {

    public String login;
    public String senha;
    public int status;

    public User(String login, String senha, int status) {
        this.login = login;
        this.senha = senha;
        this.status = status;
    }

    public String getAuthentication() {
        return disparaWs("http://siwork.com.br/wscp/controller.php?type=login&login="+this.login+"&senha="+this.senha);
    }

    public String cadastraUsuario() {
        return disparaWs("http://siwork.com.br/wscp/controller.php?type=cadastro&login="+this.login+"&senha="+this.senha);
    }

    public String getSessionWs() {
        return disparaWs("http://siwork.com.br/wscp/controller.php?type=getSession");
    }

    private String disparaWs(String strUrl) {

        URL url = null;
        String linha = null;
        int resposta = 0;
        StringBuilder result = null;

        try {
            url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            resposta = connection.getResponseCode();

            result = new StringBuilder();

            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while ((linha = reader.readLine()) != null) {
                    result.append(linha);
                }

            }
        } catch (Exception e) {

        }

        return result.toString();
    }


}
