package be.vn.logic;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.vn.constant.CommonConstant;
import be.vn.model.LinkView;
import be.vn.model.TblNews;
import be.vn.model.YoiTVServer;
import be.vn.model.YoiTVToken;
import be.vn.repository.LinkViewRepository;
import be.vn.repository.TblNewsRepository;
import be.vn.repository.YoiTVServerRepository;
import be.vn.repository.YoitvTokenRepository;
import be.vn.util.DateUtil;
import be.vn.util.HttpClientUtil;
import be.vn.util.LogUtil;
import be.vn.util.StringUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * YoiTVLogic class
 *
 * @author TuanDV <tuandv@hospital.vn>
 */
@Service
public class YoiTVLogic {
    private static CloseableHttpClient client = new DefaultHttpClient();
    @Autowired
    private TblNewsRepository tblNewsRepository;
    @Autowired
    private YoitvTokenRepository yoitvTokenRepository;
    @Autowired
    private YoiTVServerRepository yoiTVServerRepository;

    @Autowired
    private LinkViewRepository linkViewRepository;

    public void updateLink() {
        try {
            for (TblNews news : tblNewsRepository.getLsYoiTV()) {
                if (StringUtil.isEmpty(news.getYoitvId())) {
                    continue;
                }

                List<LinkView> lsLinkView = new ArrayList<>();
                for (YoiTVToken yoitvToken : yoitvTokenRepository.getListYoitvToken()) {
                    for (YoiTVServer yoiTVServer : yoiTVServerRepository.findByStatus(1)) {
                        String link = getLink(news.getYoitvId(), yoitvToken.getToken(), yoiTVServer);
                        if (StringUtil.isEmpty(link)) {
                            continue;
                        }
                        LinkView linkView = new LinkView();
                        linkView.setNewsId(news.getIdNews());
                        linkView.setLink(link);
                        linkView.setStatus(1);
                        linkView.setTime(0);
                        linkView.setCount(0);
                        linkView.setActive(1);
                        linkView.setType(1);
                        lsLinkView.add(linkView);
                    }
                }
                if (lsLinkView.size() > 0) {
                    linkViewRepository.deleteAll(linkViewRepository.findByNewsIdAndType(news.getIdNews(), 1));
                }
                linkViewRepository.saveAll(lsLinkView);
                news.setActive(1);
                news.setYoitvDate(DateUtil.getNextHour(1));
                tblNewsRepository.save(news);
            }
            HttpClientUtil.sendGet("https://duytt.animehd.club/app_tv_rv/api/news/updateM3u");
        } catch (Exception ex) {
            LogUtil.error(ex);
        }
    }

    public String getLink(String id, String token, YoiTVServer yoiTVServer) {
        try {
            HttpGet request = new HttpGet(yoiTVServer.getHost() + "/query/s/" + id + ".M3U8?type=live&__cross_domain_user=" + URLEncoder.encode(token, StandardCharsets.UTF_8.toString()));
            request.addHeader("Origin", yoiTVServer.getReferer());
//            if (serverId == null || serverId == 1) {
//                request = new HttpGet("http://live.yoitv.com:9083/query/s/" + id + ".M3U8?type=live&__cross_domain_user=" + URLEncoder.encode(token, StandardCharsets.UTF_8.toString()));
//                request.addHeader("Origin", "http://play.yoitv.com");
//            } else if (serverId == 2) {
//                request = new HttpGet("http://livean.yhvserve.com:9083/query/s/" + id + ".M3U8?type=live&__cross_domain_user=" + URLEncoder.encode(token, StandardCharsets.UTF_8.toString()));
//                request.addHeader("Origin", "http://webtv.jpacnet.com");
//            } else {
//                request = new HttpGet("http://asian.ynnhcwdf.com:9083/query/s/" + id + ".M3U8?type=live&__cross_domain_user=" + URLEncoder.encode(token, StandardCharsets.UTF_8.toString()));
//                request.addHeader("Origin", "http://isakura.tv");
//            }

            // add request header
            request.addHeader(HttpHeaders.USER_AGENT, HttpHeaders.USER_AGENT);
            request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            HttpResponse response = client.execute(request);
            String body = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            if (body.contains("<html>")) {
                return CommonConstant.BLANK;
            }
            if (body.equals("Runs out of your paying connections quota!")) {
                Thread.sleep(1000);
                return getLink(id, token, yoiTVServer);
            }

            for (String line : body.split("\\n")) {
                if (line.startsWith("http")) {
                    return line;
                }
            }
        } catch (Exception ex) {
            LogUtil.error(ex);
        }
        return CommonConstant.BLANK;
    }

//    public void updateLink() {
//        try {
////            String token = getToken();
////            if (StringUtil.isEmpty(token)) {
////                return;
////            }
//
////            String text = new String(Files.readAllBytes(Paths.get("C:\\Users\\hnbv8873\\AppData\\Local\\YoiTV\\account\\AccountInfo")), StandardCharsets.UTF_8);
////            String token = new JSONObject(text).getString("cid_access_token");
////            String abcd = getLink("Hj8HzlXSQnqBFMUva4MA4g==", token);
//
//            for (YoiTVToken yoitvToken : yoitvTokenRepository.getListYoitvToken()) {
//                for (TblNews news : tblNewsRepository.getTblNews(Integer.valueOf(yoitvToken.getIp()))) {
//                    if (StringUtil.isEmpty(news.getYoitvId())) {
//                        continue;
//                    }
//
//                    String link = getLink(news.getYoitvId(), yoitvToken.getToken(), news.getServerId());
//                    if (StringUtil.isEmpty(link)) {
//                        continue;
//                    }
//
//                    news.setActive(1);
//                    news.setLinkM3u(link);
//                    news.setYoitvDate(DateUtil.getNextHour(1));
//                    tblNewsRepository.save(news);
//                }
//            }
//
//        } catch (Exception ex) {
//            LogUtil.error(ex);
//        }
//    }

//    private String getToken() {
//        for (YoiTVToken yoitvToken : yoitvTokenRepository.getListYoitvToken()) {
//            String link = getLink("R3W0ABMki1QGwd3TxPxjhA==", yoitvToken.getToken(), null);
//            if (!StringUtil.isEmpty(link)) {
//                return yoitvToken.getToken();
//            } else {
//                yoitvTokenRepository.delete(yoitvToken);
//            }
//        }
//        return CommonConstant.BLANK;
//    }
//
//    public String getLink(String id, String token, Integer serverId) {
//        try {
//            HttpGet request;
//            if (serverId == null || serverId == 1) {
//                request = new HttpGet("http://live.yoitv.com:9083/query/s/" + id + ".M3U8?type=live&__cross_domain_user=" + URLEncoder.encode(token, StandardCharsets.UTF_8.toString()));
//                request.addHeader("Origin", "http://play.yoitv.com");
//            } else if (serverId == 2) {
//                request = new HttpGet("http://livean.yhvserve.com:9083/query/s/" + id + ".M3U8?type=live&__cross_domain_user=" + URLEncoder.encode(token, StandardCharsets.UTF_8.toString()));
//                request.addHeader("Origin", "http://webtv.jpacnet.com");
//            } else {
//                request = new HttpGet("http://asian.ynnhcwdf.com:9083/query/s/" + id + ".M3U8?type=live&__cross_domain_user=" + URLEncoder.encode(token, StandardCharsets.UTF_8.toString()));
//                request.addHeader("Origin", "http://isakura.tv");
//            }
//
//            // add request header
//            request.addHeader(HttpHeaders.USER_AGENT, HttpHeaders.USER_AGENT);
//            request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
//            HttpResponse response = client.execute(request);
//            String body = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
//            if (body.contains("<html>")) {
//                return CommonConstant.BLANK;
//            }
//            if (body.equals("Runs out of your paying connections quota!")) {
//                Thread.sleep(1000);
//                return getLink(id, token, serverId);
//            }
//
//            for (String line : body.split("\\n")) {
//                if (line.startsWith("http")) {
//                    return line;
//                }
//            }
//        } catch (Exception ex) {
//            LogUtil.error(ex);
//        }
//        return CommonConstant.BLANK;
//    }

//    public String getLink(String id) {
//        try {
//            for (int i = 0; i < 100; i++) {
//                HttpGet request = new HttpGet("https://code.television4you.xyz/getlink/yoitv?id=" + id);
//
//                // add request header
//                request.addHeader(HttpHeaders.USER_AGENT, HttpHeaders.USER_AGENT);
//                request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
//                HttpResponse response = client.execute(request);
//                String body = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
//                if (body.contains("<html>")) {
//                    Thread.sleep(500);
//                    continue;
//                }
//
//                Matcher m = Pattern.compile("(?<=fplsegment.ts).*?(?=\\r\\n)", Pattern.MULTILINE).matcher(body);
//                if (!m.find()) {
//                    return CommonConstant.BLANK;
//                }
//                String param = m.group();
//                m = Pattern.compile("(?<=Path=).*?(?=])").matcher(response.toString());
//                if (!m.find()) {
//                    return CommonConstant.BLANK;
//                }
//                String path = m.group();
//                return "http://23.237.60.14:9083" + path + "live.livesegments.M3U8" + param;
//            }
//        } catch (Exception ex) {
//            LogUtil.error(ex);
//        }
//        return CommonConstant.BLANK;
//    }
}