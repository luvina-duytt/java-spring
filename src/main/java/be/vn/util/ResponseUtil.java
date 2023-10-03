package be.vn.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import be.vn.exception.ValidationException;
import be.vn.response.ApiResponse;

/**
 * ResponseUtil class
 *
 * @author TuanDV
 */
@Slf4j
public class ResponseUtil {
    private static final String MESSAGE_ERROR = "{\"status\":%s,\"message\":\"%s\"}";

    /**
     * Response ok
     *
     * @param data data
     * @return data
     * @throws Exception
     */
    public static String ok(Object data) throws Exception {
        return CommonUtil.MAPPER.writeValueAsString(new ApiResponse(HttpStatus.SC_OK, data));
    }

    public static String ok(Object data, String message) throws Exception {
        return CommonUtil.MAPPER.writeValueAsString(new ApiResponse(HttpStatus.SC_OK, data, message));
    }

    public static String ok(Object data, long totalRecord) throws Exception {
        return CommonUtil.MAPPER.writeValueAsString(new ApiResponse(HttpStatus.SC_OK, data, totalRecord));
    }

    public static String ok(String message) throws Exception {
        return CommonUtil.MAPPER.writeValueAsString(new ApiResponse(HttpStatus.SC_OK, message));
    }

    /**
     * Response create
     *
     * @param message message
     * @return data
     * @throws Exception
     */
    public static String create(String message) throws Exception {
        return CommonUtil.MAPPER.writeValueAsString(new ApiResponse(HttpStatus.SC_CREATED, message));
    }
    /**
     * Response delete
     *
     * @param message message
     * @return data
     * @throws Exception
     */
    public static String delete(String message) throws Exception {
        return CommonUtil.MAPPER.writeValueAsString(new ApiResponse(HttpStatus.SC_OK, message));
    }

    /**
     * Response error
     *
     * @param message message
     * @return data
     * @throws Exception
     */
    public static String error(Integer status, String message) {
        return String.format(MESSAGE_ERROR, status, message);
    }

    /**
     * Response error
     *
     * @param ex Exception
     */
    public static String error(Exception ex) {
        if (!(ex instanceof ValidationException)) {
            ex.getStackTrace();
            log.error(ex.getMessage(), ex);
        }
        ValidationException tempEx = (ValidationException) ex;
        if (tempEx.getData() != null) {
            try {
                return CommonUtil.MAPPER.writeValueAsString(new ApiResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, tempEx.getData()));
            } catch (JsonProcessingException e) {
                return String.format(MESSAGE_ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            return String.format(MESSAGE_ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    /**
     * Response error
     *
     * @param ex Exception
     */
    public static String error(Object request, Exception ex) {
        if (!(ex instanceof ValidationException)) {
            log.error(request.toString(), ex);
            ex.getStackTrace();
        }
        return String.format(MESSAGE_ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
