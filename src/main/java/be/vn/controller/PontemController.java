package be.vn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import be.vn.logic.PontemLogic;
import be.vn.util.ResponseUtil;

/**
 * YoiTVController class
 *
 * @author TuanDV
 */
@RestController
@RequestMapping(value = "/pontem")
@Slf4j
public class PontemController {

    @Autowired
    private PontemLogic pontemLogic;

    @ResponseBody
    @RequestMapping(value = "/get-mail", method = RequestMethod.GET, headers = "Accept=application/json")
    public String getGmail() {
        try {
            return ResponseUtil.ok(pontemLogic.getGmail());
        } catch (Exception ex) {
            return ResponseUtil.error(ex);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get-address", method = RequestMethod.GET, headers = "Accept=application/json")
    public String getAddress() {
        try {
            return ResponseUtil.ok(pontemLogic.getAddress());
        } catch (Exception ex) {
            return ResponseUtil.error(ex);
        }
    }

    @RequestMapping(value = "/update/{email}/{address}", method = RequestMethod.GET)
    public String update(@PathVariable("email") String email, @PathVariable("address") Integer address) {
        try {
            return ResponseUtil.ok(pontemLogic.update(email, address));
        } catch (Exception ex) {
            return ResponseUtil.error(ex);
        }
    }

//    @RequestMapping(value = "/get/{id}/", method = RequestMethod.GET)
//    public void method(HttpServletResponse httpServletResponse, @PathVariable("id") String id) {
//        httpServletResponse.setHeader("Location", yoiTVLogic.getLink(id));
//        httpServletResponse.setStatus(302);
//    }
}
