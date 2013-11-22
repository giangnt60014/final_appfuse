package com.giangnt.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.giangnt.webapp.service.FSAccountManager;

@Controller
@RequestMapping("user/getterform*")
public class GetLinkController {

	@Autowired
	private FSAccountManager fsAccountManager;

    
//    /*public void setFSAccountManager(FSAccountManager fsAccountManager) {
//        this.fsAccountManager = fsAccountManager;
//    }*/
    
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest()
    throws Exception {
        return new ModelAndView().addObject(fsAccountManager.getAll());
    }
}
