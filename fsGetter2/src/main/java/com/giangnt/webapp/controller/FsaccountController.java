package com.giangnt.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.giangnt.webapp.model.Fsaccount;
import com.giangnt.webapp.service.FsaccountManager;

@Controller
@RequestMapping("/fsaccount*")
public class FsaccountController{
	private FsaccountManager fsaccountManager = null;

	@Autowired
	public void setFsaccountManager(FsaccountManager fsaccountManager) {
		this.fsaccountManager = fsaccountManager;
	}
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest()
    throws Exception {
		@SuppressWarnings("unused")
		List<Fsaccount> fsaccounts = fsaccountManager.getAll();
        return new ModelAndView().addObject(fsaccounts);
    }

}
