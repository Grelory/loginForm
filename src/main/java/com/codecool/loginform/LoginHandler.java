package com.codecool.loginform;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class LoginHandler implements HttpHandler {
    private final int LOGIN_INDEX = 0;
    private final int PASSWORD_INDEX = 1;
    private final int VALUE_INDEX = 1;
    Dao dao = new Dao();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        String[] formData = br.readLine().split("&");
        String login = formData[LOGIN_INDEX].split("=")[VALUE_INDEX];
        String password = formData[PASSWORD_INDEX].split("=")[VALUE_INDEX];

        String response = dao.getNameFromLogin(login, password);

        int status = 200;
        if(response.length() == 0) {
            status = 401;
            response = "Invalid login or password!";
        } else {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
            exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
            response = String.format("{ \"name\": \"%s\" }", response);
        }

        exchange.sendResponseHeaders(status, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}
