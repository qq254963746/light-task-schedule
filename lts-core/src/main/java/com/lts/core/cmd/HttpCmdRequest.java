package com.lts.core.cmd;

import com.lts.core.commons.utils.StringUtils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Robert HG (254963746@qq.com) on 10/26/15.
 */
public class HttpCmdRequest {

    private String command;
    private String nodeIdentity;

    private Map<String, String> params;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getNodeIdentity() {
        return nodeIdentity;
    }

    public void setNodeIdentity(String nodeIdentity) {
        this.nodeIdentity = nodeIdentity;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getParam(String key) {
        if (params != null) {
            return params.get(key);
        }
        return null;
    }

    public String getParam(String key, String defaultValue) {
        if (params != null) {
            String value = params.get(key);
            if (StringUtils.isEmpty(value)) {
                return defaultValue;
            }
            return value;
        }
        return null;
    }

    public void addParam(String key, String value) {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.put(key, value);
    }

    public Map<String, String> getParams() {
        return params;
    }

    /**
     * GET /nodeIdentity/xxxCommand?xxx=yyyyy HTTP/1.1
     */
    protected static HttpCmdRequest parse(String url) throws Exception {

        HttpCmdRequest request = new HttpCmdRequest();

        if (StringUtils.isEmpty(url)) {
            return request;
        }
        int start = url.indexOf('/');
        int ask = url.indexOf('?') == -1 ? url.lastIndexOf(' ') : url.indexOf('?');
        int space = url.lastIndexOf(' ');
        String path = url.substring(start != -1 ? start + 1 : 0, ask != -1 ? ask : url.length());
        String nodeIdentity = path.substring(0, path.indexOf('/'));
        String command = path.substring(path.indexOf('/') + 1, path.length());
        request.setCommand(command);
        request.setNodeIdentity(nodeIdentity);

        if (ask == -1 || ask == space) {
            return request;
        }

        String paramStr = url.substring(ask + 1, space != -1 ? space : url.length());

        for (String param : paramStr.split("&")) {
            if (StringUtils.isEmpty(param)) {
                continue;
            }
            String[] kvPair = param.split("=");
            if (kvPair.length != 2) {
                continue;
            }

            String key = StringUtils.trim(kvPair[0]);
            String value = StringUtils.trim(kvPair[1]);
            value = URLDecoder.decode(value, "UTF-8");

            request.addParam(key, value);
        }
        return request;
    }
}
