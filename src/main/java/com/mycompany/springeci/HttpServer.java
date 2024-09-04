package com.mycompany.springeci;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple HTTP server that dynamically invokes methods based on the URL path.
 * It registers services and extracts parameters from the query string.
 * The server also serves static files from the resources directory.
 */
public class HttpServer {
    private static final String STATIC_FILES_DIR = "src/main/resources";
    private static final Map<String, Method> services = new HashMap<>();
    private static Object serviceInstance;

    /**
     * Main method to start the HTTP server.
     *
     * @param args command line arguments, where args[0] is the fully qualified class name to be invoked
     * @throws Exception if an error occurs during server initialization or execution
     */
    public static void main(String[] args) throws Exception {
        initializeServices("com.mycompany.springeci.HelloService");

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is running on port 8080");
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleRequest(clientSocket);
                }
            }
        }
    }

    /**
     * Initializes the services by loading the specified class and its methods.
     *
     * @param className the fully qualified class name to be loaded
     * @throws Exception if an error occurs during class loading or method extraction
     */
    private static void initializeServices(String className) throws Exception {
        Class<?> c = Class.forName(className);
        if (c.isAnnotationPresent(RestController.class)) {
            serviceInstance = c.getDeclaredConstructor().newInstance();
            Method[] methods = c.getDeclaredMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(GetMapping.class)) {
                    String key = m.getAnnotation(GetMapping.class).value();
                    services.put(key, m);
                }
            }
        }
    }

    /**
     * Handles the incoming client request.
     *
     * @param clientSocket the client socket
     * @throws IOException if an I/O error occurs while handling the request
     */
    private static void handleRequest(Socket clientSocket) throws IOException {
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {

            String request = readRequest(inputStream);
            System.out.println("Request: " + request);

            RequestDetails requestDetails = parseRequest(request);

            System.out.println("Request Path: " + requestDetails.path);
            System.out.println("Query Parameters: " + requestDetails.queryParams);

            Method serviceMethod = services.get(requestDetails.path);
            if (serviceMethod != null) {
                invokeServiceMethod(outputStream, serviceMethod, requestDetails.queryParams);
            } else {
                serveStaticFile(outputStream, requestDetails.path);
            }
        }
    }

    /**
     * Reads the HTTP request from the input stream.
     *
     * @param inputStream the input stream
     * @return the HTTP request as a string
     * @throws IOException if an I/O error occurs while reading the request
     */
    private static String readRequest(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        return new String(buffer, 0, bytesRead);
    }

    /**
     * Parses the HTTP request to extract the path and query parameters.
     *
     * @param request the HTTP request as a string
     * @return a RequestDetails object containing the path and query parameters
     */
    private static RequestDetails parseRequest(String request) {
        String[] requestParts = request.split(" ");
        if (requestParts.length > 1) {
            String path = requestParts[1];
            if ("/".equals(path)) {
                path = "index.html";
            }
            String queryString = null;
            if (path.contains("?")) {
                int queryIndex = path.indexOf("?");
                queryString = path.substring(queryIndex + 1);
                path = path.substring(0, queryIndex);
            }
            Map<String, String> queryParams = parseQuery(queryString);
            return new RequestDetails(path, queryParams);
        }
        return new RequestDetails("index.html", new HashMap<>());
    }

    /**
     * Parses the query string into a map of parameter names and values.
     *
     * @param query the query string to parse
     * @return a map of parameter names and values
     */
    private static Map<String, String> parseQuery(String query) {
        Map<String, String> queryParams = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return queryParams;
    }

    /**
     * Invokes the service method with the extracted query parameters.
     *
     * @param outputStream the output stream to write the response
     * @param serviceMethod the service method to invoke
     * @param queryParams the query parameters to pass to the service method
     * @throws IOException if an I/O error occurs while writing the response
     */
    private static void invokeServiceMethod(OutputStream outputStream, Method serviceMethod, Map<String, String> queryParams) throws IOException {
        try {
            Object[] methodParams = extractArguments(serviceMethod, queryParams);
            String response = (String) serviceMethod.invoke(serviceInstance, methodParams);
            writeResponse(outputStream, "HTTP/1.1 200 OK", "text/plain", response);
        } catch (Exception e) {
            e.printStackTrace();
            writeResponse(outputStream, "HTTP/1.1 500 Internal Server Error", "text/plain", "Internal Server Error");
        }
    }

    /**
     * Serves a static file from the resources directory.
     *
     * @param outputStream the output stream to write the response
     * @param path the path of the static file to serve
     * @throws IOException if an I/O error occurs while reading or writing the file
     */
    private static void serveStaticFile(OutputStream outputStream, String path) throws IOException {
        String response = HelloService.getStaticFileContent(path);
        writeResponse(outputStream, "HTTP/1.1 200 OK", "text/html", response);
    }

    /**
     * Writes the HTTP response to the output stream.
     *
     * @param outputStream the output stream to write the response
     * @param status the HTTP status line
     * @param contentType the content type of the response
     * @param content the content of the response
     * @throws IOException if an I/O error occurs while writing the response
     */
    private static void writeResponse(OutputStream outputStream, String status, String contentType, String content) throws IOException {
        String response = status + "\r\nContent-Type: " + contentType + "\r\nContent-Length: " + content.length() + "\r\n\r\n" + content;
        outputStream.write(response.getBytes());
    }

    /**
     * Extracts arguments from the query parameters for the specified method.
     *
     * @param method the method for which to extract arguments
     * @param queryParams the query parameters
     * @return an array of arguments to be passed to the method
     */
    private static Object[] extractArguments(Method method, Map<String, String> queryParams) {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(RequestParam.class)) {
                RequestParam annotation = parameters[i].getAnnotation(RequestParam.class);
                String paramName = annotation.value();
                String value = queryParams.getOrDefault(paramName, annotation.defaultValue());
                args[i] = value;
            }
        }
        return args;
    }

    /**
     * A class to hold the details of an HTTP request.
     */
    private static class RequestDetails {
        String path;
        Map<String, String> queryParams;

        /**
         * Constructs a new RequestDetails object.
         *
         * @param path the path of the request
         * @param queryParams the query parameters of the request
         */
        RequestDetails(String path, Map<String, String> queryParams) {
            this.path = path;
            this.queryParams = queryParams;
        }
    }
}