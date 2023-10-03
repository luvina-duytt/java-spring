package be.vn.util;

import be.vn.constant.CommonConstant;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * TelegramUtil class
 *
 * @author TuanDV
 */
public class TelegramUtil {
    private static final String URL = PropertiesUtil.getString("telegram.url");
    private static final String TOKEN = PropertiesUtil.getString("telegram.token");

    /**
     * Thực hiện gửi tin nhắn cảnh báo
     */
    public static void sendMessage(String message) {
        sendMessage(PropertiesUtil.getString("telegram.chanel.id"), message);
    }

    /**
     * Thực hiện gửi tin nhắn lỗi
     */
    public static void sendError(String message) {
        sendMessage(PropertiesUtil.getString("telegram.chanel.id.error"), message);
    }

    private static void sendMessage(String chatId, String message) {
        try {
            String text = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
            String urlTelegram = String.format(URL, TOKEN, chatId, text);
            LogUtil.warn(HttpClientUtil.sendGet(urlTelegram));
        } catch (Exception ex) {
            LogUtil.error(ex);
        }
    }
}
