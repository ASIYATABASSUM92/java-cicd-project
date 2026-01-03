package com.example;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleApp {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", ex -> {
            String res = "<html><body style='font-family:Arial;text-align:center;padding:50px;background:linear-gradient(135deg,#667eea,#764ba2);color:white'><h1>Java JAR - Jenkins CI/CD</h1><p>Pod: " + System.getenv("HOSTNAME") + "</p></body></html>";
            ex.sendResponseHeaders(200, res.length());
            ex.getResponseBody().write(res.getBytes());
            ex.getResponseBody().close();
        });
        server.createContext("/health", ex -> {
            String res = "OK";
            ex.sendResponseHeaders(200, res.length());
            ex.getResponseBody().write(res.getBytes());
            ex.getResponseBody().close();
        });
        server.start();
        System.out.println("Server started on 8080");
    }
}
