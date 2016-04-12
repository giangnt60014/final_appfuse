package com.giangnt.webapp.controller;

import org.appfuse.Constants;
import org.appfuse.dao.SearchException;
import org.appfuse.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.giangnt.webapp.service.LinkFilmManager;


/**
 * Simple class to retrieve a list of users from the database.
 * <p/>
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/linkFilm*")
public class LinkFilmController {
    private LinkFilmManager linkFilmManager = null;

    @Autowired
    public void setLinkFilmManager(LinkFilmManager linkFilmManager) {
        this.linkFilmManager = linkFilmManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(required = false, value = "q") String query) throws Exception {
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(Constants.FILM_LIST, linkFilmManager.getAllLinkFilm());
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(linkFilmManager.getAllLinkFilm());
        }
        return new ModelAndView("linkList", model.asMap());
    }
}
