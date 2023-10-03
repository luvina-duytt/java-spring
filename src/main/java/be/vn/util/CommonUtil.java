package be.vn.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import be.vn.constant.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * CommonUtil class
 *
 * @author TuanDV
 */
@Slf4j
public class CommonUtil {
    public static final ObjectMapper MAPPER = getObjectMapper();

    /**
     * Get JavaSparkContext
     *
     * @return data
     */
    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(DateUtil.FORMAT_YEAR_MONTH_DAY_SEC_HYPHEN));
        mapper.setTimeZone(TimeZone.getDefault());
        return mapper;
    }

    public static boolean isAuthenticate(HttpServletRequest request) {
        String publicKey = request.getHeader("key");
        return CommonConstant.API_KEY.equals(publicKey);
    }
}
