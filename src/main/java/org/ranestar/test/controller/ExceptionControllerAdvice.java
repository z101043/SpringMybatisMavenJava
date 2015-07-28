package org.ranestar.test.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(Exception.class)
    public ModelAndView exception(Exception e) {
         
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("name", e.getClass().getSimpleName());
        mav.addObject("message", e.getMessage());
 
        return mav;
    }
	
}
