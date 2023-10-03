package be.vn.logic;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.vn.constant.CommonConstant;
import be.vn.exception.ValidationException;
import be.vn.model.User;
import be.vn.repository.UserRepository;
import be.vn.request.UserRequest;
import be.vn.response.UserResponse;
import be.vn.util.DateUtil;
import be.vn.util.EmailUtil;

import java.util.Date;
import java.util.Random;

/**
 * UserLogic class
 *
 * @author TuanDV <tuandv@hospital.vn>
 */
@Service
public class UserLogic {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private void test901() {
//        EmailUtil.sendEmail("Japan TV アカウント登録", "tuandv1994@gmail.com", "Code: 5265");
//        EmailUtil.sendEmail("Japan TV パスワードをお忘れの場合", "tuandv1994@gmail.com", "Code: 5265");
//    }

    public UserResponse login(UserRequest request) {
        User user = userRepository.findFirstByUserNameAndPassword(request.getUserName(), request.getPassword());
        if (user == null) {
            throw new ValidationException("Incorrect password");
        }
        return new UserResponse(user.getId(), user.getTimeVip().compareTo(DateUtil.getCurrentDateUTC()) > 0, user.getTimeVip());
    }

    public String getCode(UserRequest request) {
        boolean isUser = userRepository.existsByUserName(request.getUserName());
        if (request.getIsRegister() && isUser) {
            throw new ValidationException("Account already exists");
        } else if (!request.getIsRegister() && !isUser) {
            throw new ValidationException("Account not exists");
        }
        String codeConfirm = String.format("%04d", new Random().nextInt(10000));
        EmailUtil.sendEmail("Japan TV アカウント登録", request.getUserName(), "Code: " + codeConfirm + "<br/>ご不明な点がございましたら、メールでお問い合わせください: tmd.suport@gmail.com");
        return codeConfirm;
    }

    public User register(UserRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new ValidationException("Account already exists");
        }

        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setTimeVip(DateUtil.getCurrentDateUTC());
        userRepository.save(user);
        return user;
    }

    public String resetPassword(UserRequest request) {
        User user = userRepository.findFirstByUserName(request.getUserName());
        if (user == null) {
            throw new ValidationException("Account not exists");
        }
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return CommonConstant.SUCCESS;
    }

//    public String updatePassword(UserRequest request) {
//        User user = userRepository.findFirstByUserNameAndCodeConfirm(request.getUserName(), request.getCodeConfirm());
//        if (user == null) {
//            throw new ValidationException("Incorrect information");
//        }
//        user.setPassword(request.getPassword());
//        userRepository.save(user);
//        return CommonConstant.SUCCESS;
//    }

    public String updateVip(UserRequest request) {
        User user = userRepository.findFirstByUserName(request.getUserName());
        if (user == null) {
            throw new ValidationException("Account not exists");
        }
        Date timeVip = DateUtil.getCurrentDateUTC().compareTo(user.getTimeVip()) > 0 ? DateUtil.getCurrentDateUTC() : user.getTimeVip();
        user.setTimeVip(DateUtil.getNextMonth(timeVip, request.getMonth()));
        userRepository.save(user);
        return CommonConstant.SUCCESS;
    }
}