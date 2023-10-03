//package be.vn.util;
//
//
//import com.squareup.okhttp.*;
//import be.vn.okhttp.MyCookieManager;
//
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Arrays;
//
///**
// * Date utility
// *
// * @author TuanDV
// */
//public class OkHttpClient1Util {
//    public static String sendPost() throws Exception {
//        OkHttpClient client = new OkHttpClient();
////        client.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
////        client.setWriteTimeout(15000, TimeUnit.MILLISECONDS);
////        client.setReadTimeout(15000, TimeUnit.MILLISECONDS);
//        client.setCookieHandler(new MyCookieManager());
////        client.interceptors().add(new DCB_AddCookiesInterceptor());
////        client.interceptors().add(new DCB_ReceivedCookiesInterceptor());
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
//
////        Request request = new Request.Builder()
////                .url("https://ebanking.vietinbank.vn/efast/login.do")
////                .method("POST", body)
////                .build();
////        Response response = client.newCall(request).execute();
////        List<String> cookielist = new ArrayList<>();
////        for (String abc :response.headers().values("Set-Cookie")) {
////
////        }
////
////        String body11 = response.body().string();
////        Files.write(Paths.get("/var/tuandv.html"), Arrays.asList(body11), StandardCharsets.UTF_8);
//
//        Request request = new Request.Builder()
//                .url("https://ebanking.vietinbank.vn/efast/j_spring_security_check?j_username=bv.be&j_password=17bcb3e7a134dc48e0adc21e84e786a79fa80f9885895a7a8590f5d5037de37274d06bfcfd70e6d34897ea5ec4ee12e303ca5db11124449e91ef4066422c0308eb8828519775b962a9c24ccb3a7ffc47215b603ee0e1ef95b6ee45bd552dccb2f01b5992c906d54f62c15a9379b0fead53b58a78cd4a7df7f967df6cec9c6034")
//                .method("POST", body)
////                .addHeader("Cookie",String.join(";", cookielist))
//                .build();
//
//        Response response = client.newCall(request).execute();
//        String body11 = response.body().string();
//        Files.write(Paths.get("/var/tuandv.html"), Arrays.asList(body11), StandardCharsets.UTF_8);
//        return body11;
//    }
//
//}