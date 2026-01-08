package com.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleApp {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String hostname = System.getenv().getOrDefault("HOSTNAME", "unknown");
                String response = "<!DOCTYPE html><html><head><title>Jenkins CI/CD</title>" +
                    "<style>body{font-family:Arial;background:linear-gradient(135deg,#667eea,#764ba2);" +
                    "color:white;text-align:center;padding:50px;min-height:100vh;margin:0}" +
                    "h1{font-size:3em;margin-bottom:20px}h2{font-size:1.5em}" +
                    ".container{background:rgba(255,255,255,0.1);padding:50px;border-radius:20px;" +
                    "max-width:700px;margin:0 auto}.badge{background:rgba(255,255,255,0.2);" +
                    "padding:8px 15px;border-radius:15px;margin:5px;display:inline-block}" +
                    ".info{background:rgba(255,255,255,0.15);padding:20px;border-radius:10px;margin:20px 0}</style>" +
                    "</head><body><div class='container'>" +
                    "<h1>☕ Java JAR Application</h1>" +
                    "<h2>🎉 Deployed via Jenkins CI/CD!</h2>" +
                    "<div class='info'><p><strong>Running on Pod:</strong> " + hostname + "</p>" +
                    "<p><strong>Status:</strong> Active</p></div>" +
                    "<div style='margin-top:30px'>" +
                    "<span class='badge'>✅ Jenkins</span>" +
                    "<span class='badge'>✅ Maven</span>" +
                    "<span class='badge'>✅ Docker</span>" +
                    "<span class='badge'>✅ Kubernetes</span>" +
                    "</div></div></body></html>";
                
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        
        server.createContext("/health", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "{\"status\":\"healthy\",\"service\":\"java-app\"}";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        
        server.start();
        System.out.println("✅ Server started on port " + port);
    }
}
