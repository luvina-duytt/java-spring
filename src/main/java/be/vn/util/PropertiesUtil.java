package be.vn.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * PropertiesUtil class
 *
 * @author TuanDV <tuandv@hospital.vn>
 */
public class PropertiesUtil {
    private static final List<String> LIST_PROPERTIES = Arrays.asList("env.properties");

    private static final Properties props = getProperties();


    /**
     * Đọc file properties theo tên
     *
     * @return data
     */
    private static Properties getProperties() {
        Properties prop = new Properties();
        for (String filename : LIST_PROPERTIES) {
            try (InputStream inStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(filename)) {
                Properties propTmp = new Properties();
                propTmp.load(new InputStreamReader(inStream, StandardCharsets.UTF_8));
                prop.putAll(propTmp);
            } catch (Exception e) {
                LogUtil.error(e);
            }
        }
        return prop;
    }

    /**
     * Lấy thông tin application.properties theo key
     *
     * @param key The key to get message string
     * @return string in properties file.
     * @author TuanDV
     */
    public static String getString(String key) {
        return props.getProperty(key);
    }
}
