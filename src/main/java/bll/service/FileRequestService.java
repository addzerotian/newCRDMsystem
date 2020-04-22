package bll.service;

import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface FileRequestService {
    Map<String, Object> parseRequest(HttpServletRequest request);
    void parseImageBySpecifiedURL(byte[] image, String path, String URL);
    String calcMD5OfFile(byte[] file);
    String calMD5OfRequest(String key);
    void setResponse(HttpServletResponse response, JSONObject jsonObject);
}
