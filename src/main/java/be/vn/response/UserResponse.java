package be.vn.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class UserResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("is_vip")
    private Boolean isVip;

    @JsonProperty("time_vip")
    private Date timeVip;

    public UserResponse(Integer id, Boolean isVip, Date timeVip) {
        this.id = id;
        this.isVip = isVip;
        this.timeVip = timeVip;
    }
}
