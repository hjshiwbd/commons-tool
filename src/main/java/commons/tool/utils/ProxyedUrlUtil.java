package commons.tool.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author hjin
 * @cratedate 2013-1-7 下午2:52:23
 * 
 */
public class ProxyedUrlUtil {
    private final static String ENCODING_UTF8 = "utf-8";

    private static Logger logger = LoggerFactory.getLogger(ProxyedUrlUtil.class);

    public static void main(String[] args) throws ClientProtocolException, IOException {
        String server = "http://192.168.30.16:3000";
        // String url = server + "/api/org";
        // String url = server + "/api/dashboards/db";
        // String url = "http://1111.ip138.com/ic.asp";

        String slug = "fu-wu-jian-kong";
        String url = server + "/api/dashboards/db/" + slug;

        String encoding = "gbk";

        Map<String, String> headers = new HashMap<>();
        String key = "eyJrIjoiZnpjRXBwQ2cyUTdVcFZPekN4WjdLSmdnaHg4TTU4M3IiLCJuIjoieGlucWlhbl90ZXN0XzEiLCJpZCI6MX0=";
        headers.put("Authorization", "Bearer " + key);

        // String s = post(url, "gbk", headers);
        String s = get(url, encoding, headers);
        System.out.println(s);
    }

    public static String get(String url) {
        return get(url, null, null);
    }

    public static String get(String url, String encoding, Map<String, String> headers) {
        CloseableHttpClient httpclient = createHttpclient();

        InetSocketAddress socksaddr = new InetSocketAddress("127.0.0.1", 35321);
        HttpClientContext context = HttpClientContext.create();
        context.setAttribute("socks.address", socksaddr);

        encoding = encoding == null ? ENCODING_UTF8 : encoding;

        HttpGet get = new HttpGet(url);

        if (headers != null) {
            addHeaders(get, headers);
        }

        String s = null;
        try {
            CloseableHttpResponse response = httpclient.execute(get, context);
            s = EntityUtils.toString(response.getEntity(), encoding);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }

    private static void addHeaders(HttpRequestBase requestBase, Map<String, String> headers) {
        for (Map.Entry<String, String> en : headers.entrySet()) {
            String key = en.getKey();
            String value = en.getValue();
            requestBase.setHeader(key, value);
        }
    }

    public static String post(String url, String encoding, Map<String, String> headers)
            throws ClientProtocolException, IOException {
        logger.debug("url:{}", url);
        CloseableHttpClient httpclient = createHttpclient();

        InetSocketAddress socksaddr = new InetSocketAddress("127.0.0.1", 35321);
        HttpClientContext context = HttpClientContext.create();
        context.setAttribute("socks.address", socksaddr);

        HttpPost post = new HttpPost(url);
        addHeaders(post, headers);

        CloseableHttpResponse response = httpclient.execute(post, context);
        String s = EntityUtils.toString(response.getEntity(), encoding);

        return s;
    }

    private static CloseableHttpClient createHttpclient() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", new LocalConnectionSocket()).build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);

        cm.setMaxTotal(5);

        httpClientBuilder.setConnectionManager(cm);

        return httpClientBuilder.build();
    }

    private static class LocalConnectionSocket implements ConnectionSocketFactory {
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        public Socket connectSocket(final int connectTimeout, final Socket socket, final HttpHost host,
                final InetSocketAddress remoteAddress, final InetSocketAddress localAddress, final HttpContext context)
                        throws IOException, ConnectTimeoutException {
            Socket sock;
            if (socket != null) {
                sock = socket;
            } else {
                sock = createSocket(context);
            }
            if (localAddress != null) {
                sock.bind(localAddress);
            }
            try {
                sock.connect(remoteAddress, connectTimeout);
            } catch (SocketTimeoutException ex) {
                throw new ConnectTimeoutException(ex, host, remoteAddress.getAddress());
            }
            return sock;
        }
    }

}
