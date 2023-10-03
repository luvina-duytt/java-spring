package be.vn.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Date utility
 *
 * @author TuanDV
 */
public class HttpClientUtil {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36";
    private static CloseableHttpClient client = new DefaultHttpClient();

    // HTTP GET request
    public static String sendGet(String url) throws Exception {
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = client.execute(request);
        return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
    }

    // HTTP POST request
    public static String sendPost(String url, List<NameValuePair> urlParameters) throws Exception {
        HttpPost post = new HttpPost(url);

        // add header
        post.addHeader("User-Agent", USER_AGENT);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        Header[] Cookielist = response.getHeaders("Set-Cookie");
        return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);

    }

    // HTTP POST request
    public static String sendPost(String url) throws Exception {
        HttpPost post = new HttpPost(url);

        // add header
        post.addHeader("Host", "ebanking.vietinbank.vn");
        post.addHeader("User-Agent", USER_AGENT);
        post.addHeader("X-Requested-With", "XMLHttpRequest");
        post.addHeader("sec-ch-ua-platform", "Windows");
        post.addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        HttpResponse response = client.execute(post);
        return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);

    }
}