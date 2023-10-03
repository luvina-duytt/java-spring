package be.vn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import be.vn.logic.MuJiLogic;
import be.vn.util.ResponseUtil;

/**
 * CommonController class
 */
@RestController
@RequestMapping(value = "/common")
@Slf4j
public class CommonController {

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.GET, headers = "Accept=application/json")
    public String update() {
    }
}
