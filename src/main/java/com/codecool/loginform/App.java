package com.codecool.loginform;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/login", new Static());
        server.createContext("/js/loginScript.js", new Static());
        server.createContext("/css/loginStyle.css", new Static());
        server.createContext("/handle", new LoginHandler());
        server.setExecutor(null);

        server.start();
    }
}
