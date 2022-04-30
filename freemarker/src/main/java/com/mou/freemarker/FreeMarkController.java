package com.mou.freemarker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author cheng
 * @ClassName: FreemarkerAction
 * @Description: freemarker控制层
 * @date 2018年1月22日 下午8:19:39
 */
@Controller
@RequestMapping(value = "/freemarker")
public class FreeMarkController {

    /**
     * 日志管理
     */
    private static Logger log = LoggerFactory.getLogger(FreeMarkController.class);

    @RequestMapping(value = "/toDemo")
    public ModelAndView toDemo(ModelAndView mv) {
        log.info("====>>跳转freemarker页面");
        mv.addObject("name", "jack");
        mv.setViewName("freemarker");
        return mv;
    }

    public static void main(String[] args) {
        System.out.println(8 >> 2);
        System.out.println(8 << 2);
        System.out.println(8 >>> 1);
    }

}