package be.vn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import be.vn.logic.YoiTVLogic;
import be.vn.util.ResponseUtil;

/**
 * YoiTVController class
 *
 * @author TuanDV
 */
@RestController
@RequestMapping(value = "/yoitv")
@Slf4j
public class YoiTVController {

    @Autowired
    private YoiTVLogic yoiTVLogic;

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.GET, headers = "Accept=application/json")
    public String update() {
        try {
            new Thread(() -> yoiTVLogic.updateLink()).start();
            return ResponseUtil.ok("Thành công");
        } catch (Exception ex) {
            return ResponseUtil.error(ex);
        }
    }

//    @ResponseBody
//    @RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json")
//    public String getTransferCommand(HttpServletRequest servletRequest) {
//        try {
//            if (!CommonUtil.isAuthenticate(servletRequest)) {
//                return ResponseUtil.error(HttpStatus.SC_UNAUTHORIZED, "Unauthorized");
//            }
//            return ResponseUtil.ok(bankLogic.getTransferCommand());
//        } catch (Exception ex) {
//            return ResponseUtil.error(ex);
//        }
//    }

//    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
//    public void method(HttpServletResponse httpServletResponse, @PathVariable("id") String id) {
//        httpServletResponse.setHeader("Location", yoiTVLogic.getLink(id));
//        httpServletResponse.setStatus(302);
//    }
}
