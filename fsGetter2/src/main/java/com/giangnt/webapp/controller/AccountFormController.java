package com.giangnt.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.giangnt.webapp.model.Fsaccount;
import com.giangnt.webapp.service.FsaccountManager;

@Controller
@RequestMapping("/admin/accountform*")
public class AccountFormController extends BaseFormController {

	private FsaccountManager fsaccountManager = null;
	
	@Autowired
	public void setFsaccountManager(FsaccountManager fsaccountManager) {
		this.fsaccountManager = fsaccountManager;
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public String onSubmit(Fsaccount fsaccount, BindingResult errors, HttpServletRequest request,
                           HttpServletResponse response){
		if (request.getParameter("save")!=null){
			fsaccountManager.saveFsAccount(fsaccount);
			System.out.println("Save successful");
		}
		
		return null;
				
	}
	
	@ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected Fsaccount showForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		return new Fsaccount();
	}
}
