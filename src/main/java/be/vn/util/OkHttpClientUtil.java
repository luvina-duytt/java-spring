package be.vn.util;


import okhttp3.*;
import okhttp3.internal.JavaNetCookieJar;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

/**
 * Date utility
 *
 * @author TuanDV
 */
public class OkHttpClientUtil {
    private static HashSet<String> cookies = new HashSet<>();
    private static OkHttpClient CLIENT = getOkHttpClient();

    private static OkHttpClient getOkHttpClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
//                .addInterceptor(new AddCookiesInterceptor())
//                .addInterceptor(new ReceivedCookiesInterceptor())
                .build();
        return okHttpClient;
    }

    public static class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                cookies.addAll(originalResponse.headers("Set-Cookie"));
            }
            return originalResponse;
        }
    }

    public static class AddCookiesInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            for (String cookie : cookies) {
                builder.addHeader("Cookie", cookie);
            }

            return chain.proceed(builder.build());
        }
    }

    public static String sendGet(String url) throws Exception {
        return send(url, null, "GET");
    }

    public static String sendGet(String url, Map<String, String> params) throws Exception {
        return send(url, params, "GET");
    }

    public static String sendPost(String url, Map<String, String> params) throws Exception {
        return send(url, params, "POST");
    }

    public static String sendPost1(String url, Map<String, String> params, String coookie) throws Exception {
        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .method("POST", body)
                .addHeader("Cookie", coookie)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private static String send(String url, Map<String, String> params, String method) throws Exception {
        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        RequestBody body = null;
        if ("POST".equals(method)) {
            MediaType mediaType = MediaType.parse("text/plain");
            body = RequestBody.create(mediaType, "");
        }
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .method(method, body)
                .build();
        Response response = CLIENT.newCall(request).execute();
        return response.body().string();
    }

}