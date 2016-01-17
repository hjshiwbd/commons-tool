package org.shinomin.commons.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.shinomin.commons.exception.URLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author hjin
 * @cratedate 2013-1-7 下午2:52:23
 * 
 */
public class UrlUtil {
    private static Logger logger = LoggerFactory.getLogger(UrlUtil.class);

    /**
     * 执行get方法
     * 
     * @author hjin
     * @cratedate 2013-11-4 上午8:47:08
     * @param url
     * @param encoding
     * @return
     * 
     */
    public static String get(String url, String encoding) {
        String responseBody = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            logger.debug("get url:" + url);
            SimpleResponseHandler responseHandler = new SimpleResponseHandler(encoding);
            responseBody = httpclient.execute(httpGet, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }

    public static String post(String url, List<NameValuePair> postParams, String encoding) {
        return post(url, postParams, null, encoding, createConfig());
    }

    public static String post(String url, String postString, String encoding) {
        return post(url, null, postString, encoding, createConfig());
    }

    private static RequestConfig createConfig() {
        Builder builder = RequestConfig.custom();
        builder.setConnectTimeout(10000);// 10s超时
        builder.setSocketTimeout(10000);
        builder.setConnectionRequestTimeout(10000);

        return builder.build();
    }

    /**
     * 提交post请求
     * 
     * @param url
     *            请求地址
     * @param postParams
     *            请求参数
     * @param postString
     *            POST内置字符串
     * @param encoding
     *            字符集
     * @return
     * @author hjin
     */
    public static String post(String url, List<NameValuePair> postParams, String postString, String encoding,
            RequestConfig config) {
        logger.debug("url:{}", url);
        String responseBody = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(config);

            if (postParams != null) {
                httpPost.setEntity(new UrlEncodedFormEntity(postParams, encoding));
            }

            if (!StringUtils.isEmpty(postString)) {
                StringEntity s = new StringEntity(postString, encoding);
                s.setContentEncoding(encoding);
                s.setContentType("application/json;charset=" + encoding);
                httpPost.setEntity(s);
            }

            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity, encoding).trim();
            // 关闭连接
            EntityUtils.consume(entity);

            response.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }

    /**
     * 内部类，处理response返回转为中文
     * 
     * @author hjin
     * @cratedate 2013-11-4 上午8:47:28
     * 
     */
    public static class SimpleResponseHandler implements ResponseHandler<String> {
        private String encoding;

        public SimpleResponseHandler(String encoding) {
            this.encoding = encoding;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity, encoding) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
    }

    public static void download(String remoteUrl, File localFile) throws IOException, URLException {
        download(remoteUrl, localFile, null);
    }

    /**
     * 下载文件
     * 
     * @param remoteUrl
     *            下载地址
     * @param localFile
     *            本地保存文件
     * @param maxSize
     *            最大文件大小
     * @throws IOException
     * @throws URLException
     * @author hjin
     */
    public static void download(String remoteUrl, File localFile, Integer maxSize) throws IOException, URLException {
        URL url = new URL(remoteUrl);
        URLConnection conn = url.openConnection();
        int size = conn.getContentLength();
        if (maxSize != null && maxSize.intValue() < size) {
            throw new URLException("size exceed:" + size + "/" + maxSize);
        }
        InputStream in = conn.getInputStream();
        FileUtils.copyInputStreamToFile(in, localFile);
    }

    public static void main(String[] args) {
        String s = get(
                "http://hb.189.cn/queryNumber.action?NUMTYPE=" + "&acc_nbr_head=&acc_nbr=&citycode=0127&pageNum=1",
                "utf-8");
        System.out.println(s);
    }

}
