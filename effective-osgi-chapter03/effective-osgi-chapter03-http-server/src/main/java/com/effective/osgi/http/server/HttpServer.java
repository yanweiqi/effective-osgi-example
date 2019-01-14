package com.effective.osgi.http.server;

import com.effective.osgi.http.api.RequestHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 简单的http server处理器
 */
public class HttpServer implements AutoCloseable {

    private int port = 8080;
    private Map<String, RequestHandler> map = new ConcurrentHashMap<>();
    private volatile boolean running = true;
    private ServerSocket serverSocket = null;

    public void addHandler(String path, RequestHandler handler) {
        map.put(path, handler);
    }

    public void removeHandler(String path) {
        map.remove(path);
    }

    public boolean exists(String path) {
        return map.containsKey(path);
    }

    public void start() throws Exception {
        serverSocket = new ServerSocket(port);
        System.out.println("server start");
        while (running) {
            Socket socket = serverSocket.accept();
            InputStream input = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(isr);
            OutputStream out = null;
            try {
                String firstLine = br.readLine();
                if (firstLine != null) {
                    String[] info = firstLine.split(" ");
                    String path = info[1];
                    out = socket.getOutputStream();
                    if (path == null || "".equals(path) || "/".equals(path)) {
                        out.write(getHeaderContent(200).getBytes());
                        out.write("\r\n".getBytes());
                        out.write(getHtmlContent("Welcome to OSGi server/1.0.0").getBytes());
                    } else if (map.containsKey(path)) {
                        RequestHandler handler = map.get(path);
                        out.write(getHeaderContent(200).getBytes());
                        out.write("\r\n".getBytes());
                        out.write(getHtmlContent(handler.handler()).getBytes());
                    } else {
                        out.write(getHeaderContent(404).getBytes());
                        out.write("\r\n".getBytes());
                        out.write(getHtmlContent("404 Page Not found").getBytes());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (br != null) br.close();
                if (isr != null) isr.close();
                if (input != null) input.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            }
        }
    }

    private String getHeaderContent(int status) {
        StringBuilder buf = new StringBuilder();
        buf.append("HTTP/1.1 " + status + " OK\r\n");
        buf.append("Content-type: text/html\r\n");
        buf.append("Server: OSGi-Server/1.0.0\r\n");
        return buf.toString();
    }

    private String getHtmlContent(String content) {
        StringBuilder buf = new StringBuilder();
        buf.append("<html><body>\r\n");
        buf.append(content);
        buf.append("\r\n");
        buf.append("</body></html>");
        return buf.toString();
    }

    public void close() throws Exception {
        running = false;
        serverSocket.close();
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();
        server.addHandler("/admin", () -> "<h1>admin page</h1>");
        server.start();
    }
}
