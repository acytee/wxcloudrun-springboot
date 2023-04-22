package com.tencent.wxcloudrun.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String get(String url) throws Exception {
        final String charset = "utf-8";
        String result;

        CloseableHttpClient client = HttpClients.createDefault();
        try {

            HttpGet httpget = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).
                    setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();

            httpget.setConfig(requestConfig);

            HttpResponse response = client.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 400) {
                String errmsg = "请求状态码：" + statusCode;
                JSONObject json = new JSONObject();
                json.put("errCode", -1);
                json.put("errMsg", errmsg);
                result = json.toString();
            } else {
                InputStream is = response.getEntity().getContent();

                InputStreamReader isReader = new InputStreamReader(is, charset);
                BufferedReader reader = new BufferedReader(isReader);

                StringBuilder sb = new StringBuilder();
                String line = null;
                do {
                    line = reader.readLine();
                    if (line != null) {
                        sb.append(line);
                    }
                } while (line != null);
                result = sb.toString();
            }
        } finally {
            client.close();
        }

        return result;
    }

    public static String post(String url, List<NameValuePair> parameters) throws Exception {
        final String charset = "utf-8";
        String result;

        CloseableHttpClient client = HttpClients.createDefault();
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, charset);

            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(formEntity);

            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).
                    setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();

            httppost.setConfig(requestConfig);

            HttpResponse response = client.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 400) {
                String errMsg = "请求状态码：" + statusCode;
                JSONObject json = new JSONObject();
                json.put("errCode", -1);
                json.put("errMsg", errMsg);
                result = json.toString();
            } else {
                InputStream is = response.getEntity().getContent();

                InputStreamReader isReader = new InputStreamReader(is, charset);
                BufferedReader reader = new BufferedReader(isReader);

                StringBuilder sb = new StringBuilder();
                String line = null;
                do {
                    line = reader.readLine();
                    if (line != null) {
                        sb.append(line);
                    }
                } while (line != null);
                result = sb.toString();
            }
        } finally {
            client.close();
        }

        return result;
    }

    public static List<NameValuePair> createParam(Map<String, Object> param) {

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        if(param != null) {
            for(String k : param.keySet()) {
                pairs.add(new BasicNameValuePair(k, param.get(k).toString()));
            }
        }
        return pairs;
    }

}
