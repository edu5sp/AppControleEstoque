package com.example.frog.meuapp2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by frog on 04/05/18.
 */

public class Produto {

    public String login;
    public String descricaoProduto;
    public Double valorProduto;
    public int quantidade;
    public int statusProduto;

    public Produto(String login, String descricaoProduto, Double valorProduto, int quantidade, int statusProduto) {
        this.login = login;
        this.descricaoProduto = descricaoProduto;
        this.valorProduto = valorProduto;
        this.quantidade = quantidade;
        this.statusProduto = statusProduto;
    }

    public String cadastraProduto() {
        return disparaWs("http://siwork.com.br/wscp/controller.php?type=cadastroProduto&login="+this.login+"&descricaoProduto="+this.descricaoProduto+"&valorProduto="+this.valorProduto+"&quantidade="+this.quantidade);
    }

    public String getProduto() {
        return disparaWs("http://siwork.com.br/wscp/controller.php?type=getProduto&login="+this.login);
    }

    public String getProdutoId(String idProduto) {
        return disparaWs("http://siwork.com.br/wscp/controller.php?type=getProdutoId&login="+this.login+"&idProduto="+idProduto);
    }

    public String setAlteracaoProduto(String idProduto) {
        return disparaWs("http://siwork.com.br/wscp/controller.php?type=setAlteracaoProduto&login="+this.login+"&idProduto="+idProduto+"&descricaoProduto="+this.descricaoProduto+"&valorProduto="+this.valorProduto+"&quantidade="+this.quantidade);
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
