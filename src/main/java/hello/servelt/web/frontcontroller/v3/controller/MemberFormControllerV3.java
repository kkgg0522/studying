package hello.servelt.web.frontcontroller.v3.controller;

import hello.servelt.web.frontcontroller.ModelAndView;
import hello.servelt.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

    @Override
    public ModelAndView process(Map<String, String> paramMap) {
        return new ModelAndView("new-form");
    }
}
