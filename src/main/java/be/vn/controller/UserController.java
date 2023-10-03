package be.vn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import be.vn.logic.UserLogic;
import be.vn.request.UserRequest;
import be.vn.util.ResponseUtil;

/**
 * UserController class
 *
 * @author TuanDV
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {
    @Autowired
    private UserLogic userLogic;

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET, headers = "Accept=application/json")
    public String test() {
        try {
            return ResponseUtil.ok("OK");
        } catch (Exception ex) {
            return ResponseUtil.error(ex);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
    public String login(@RequestBody UserRequest request) {
        try {
            return ResponseUtil.ok(userLogic.login(request));
        } catch (Exception ex) {
            return ResponseUtil.error(ex);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get-code", method = RequestMethod.POST, headers = "Accept=application/json")
    public String getCode(@RequestBody UserRequest request) {
        try {
            return ResponseUtil.ok(userLogic.getCode(request));
        } catch (Exception ex) {
            return ResponseUtil.error(ex);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
    public String register(@RequestBody UserRequest request) {
        try {
            return ResponseUtil.ok(userLogic.register(request));
        } catch (Exception ex) {
            return ResponseUtil.error(ex);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST, headers = "Accept=application/json")
    public String resetPassword(@RequestBody UserRequest request) {
        try {
            return ResponseUtil.ok(userLogic.resetPassword(request));
        } catch (Exception ex) {
            return ResponseUtil.error(ex);
        }
    }

//    @ResponseBody
//    @RequestMapping(value = "/update-password", method = RequestMethod.POST, headers = "Accept=application/json")
//    public String updatePassword(@RequestBody UserRequest request) {
//        try {
//            return ResponseUtil.ok(userLogic.updatePassword(request));
//        } catch (Exception ex) {
//            return ResponseUtil.error(ex);
//        }
//    }

    @ResponseBody
    @RequestMapping(value = "/update-vip", method = RequestMethod.POST, headers = "Accept=application/json")
    public String updateVip(@RequestBody UserRequest request) {
        try {
            return ResponseUtil.ok(userLogic.updateVip(request));
        } catch (Exception ex) {
            return ResponseUtil.error(ex);
        }
    }
}
