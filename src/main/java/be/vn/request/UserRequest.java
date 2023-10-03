package be.vn.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import be.vn.model.User;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRequest extends User {
    private Integer month;
    private Boolean isRegister;
}
