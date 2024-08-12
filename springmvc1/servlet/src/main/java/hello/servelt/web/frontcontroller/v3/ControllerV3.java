package hello.servelt.web.frontcontroller.v3;

import hello.servelt.web.frontcontroller.ModelAndView;

import java.util.Map;

public interface ControllerV3 {
    ModelAndView process(Map<String, String> paramMap);

}
