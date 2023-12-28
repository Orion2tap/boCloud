package demo.guaServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class GuaResponse {
    public HashMap<String, String> headers;
    public Integer status;
    public byte[] body;

    public GuaResponse() {
        this.headers = new HashMap<>();
        this.status = null;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setBody(String body) {
        this.body = body.getBytes(StandardCharsets.UTF_8);
    }

    public void setHeaders(String key, String value) {
        this.headers.put(key, value);
    }

    public byte[] raw() {
        StringBuilder sb = new StringBuilder();
        String responseHeader = String.format("HTTP/1.1 %s\r\n", this.status);
        sb.append(responseHeader);
        for (String key:this.headers.keySet()) {
            String value = this.headers.get(key);
            sb.append(String.format("%s: %s\r\n", key, value));
        }
        sb.append("\r\n");

        ByteArrayOutputStream response = new ByteArrayOutputStream();
        try {
            response.write(sb.toString().getBytes(StandardCharsets.UTF_8));
            response.write(body);
            return response.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
