package be.vn.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ApiResponse class
 *
 * @author TuanDV
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ApiResponse {
    @JsonProperty("status")
    private Integer status;

    @JsonProperty("totalRecord")
    private Long totalRecord;

    @JsonProperty("data")
    private String data;

    @JsonProperty("result")
    private Object result;

    public ApiResponse(Integer status, String data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse(Integer status, Object result) {
        this.status = status;
        this.result = result;
    }

    public ApiResponse(Integer status, Object result, long totalRecord) {
        this.status = status;
        this.result = result;
        this.totalRecord = totalRecord;
    }

    public ApiResponse(Integer status, Object result, String data) {
        this.status = status;
        this.result = result;
        this.data = data;
    }
}
