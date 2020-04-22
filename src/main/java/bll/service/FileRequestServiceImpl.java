package bll.service;

import dal.model.MultiEnvStandardFormat;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

public class FileRequestServiceImpl implements FileRequestService {
    @Override
    public Map<String, Object> parseRequest(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String tempPathDir = "";
        File tempPathDirFile = new File(tempPathDir);

        // 创建工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置缓冲区大小，这里是400kb
        factory.setSizeThreshold(4096 * 100);
        // 设置缓冲区目录
        factory.setRepository(tempPathDirFile);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置上传文件的大小 12M
        upload.setFileSizeMax(4194304 * 3);
        // 创建解析器
        // 得到所有的文件
        try {
            List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
            for (FileItem item : items) {
                if ("request-data".equals(item.getFieldName())) {
                    JSONObject jsonObject = new JSONObject(item.getString("utf-8"));
                    map.put("request-data", jsonObject);
                }
                else if("img".equals(item.getFieldName())){
                    byte[] avatar;
                    if(item.getSize() == 0) avatar = null;
                    else {
                        avatar = new byte[(int)item.getSize()];
                        item.getInputStream().read(avatar);
                    }

                    map.put("avatar", avatar);
                }
                else if("request-type".equals(item.getFieldName())){
                    map.put("request-type", item.getString());
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }

        //将应用绝对地址加入参数中
        map.put("web-path", request.getServletContext().getRealPath(""));

        return map;
    }

    @Override
    public void parseImageBySpecifiedURL(byte[] image, String path, String URL) {
        File dir = new File(path);
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        dir = new File(path + MultiEnvStandardFormat.getInstance().getFileSeparator() + URL);
        if(!dir.exists()) {
            try {
                FileOutputStream fout = new FileOutputStream(path + MultiEnvStandardFormat.getInstance().getFileSeparator() + URL);
                fout.write(image);
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String calcMD5OfFile(byte[] file) {
        return DigestUtils.md5Hex(file);
    }

    @Override
    public String calMD5OfRequest(String key) {
        return DigestUtils.md5Hex(key);
    }

    @Override
    public void setResponse(HttpServletResponse response, JSONObject jsonObject) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter send = null;

        try {
            send = response.getWriter();
            send.append(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert send != null;
            send.close();
        }
    }
}
