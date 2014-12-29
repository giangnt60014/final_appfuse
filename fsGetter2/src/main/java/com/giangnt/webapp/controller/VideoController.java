package com.giangnt.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/fsaccount111*")
public class VideoController extends BaseFormController {
	@RequestMapping(value = "/getLink1", method = RequestMethod.GET)
	@ResponseBody public void getPreview2(@PathVariable("id") String id, HttpServletResponse response) {
	    try {
	    	URL website = new URL("https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/10882338_484618718343509_4427203943746860088_n.jpg?oh=caa8ddc30be99c6b6ab2d04356b35433&oe=5541E36F&__gda__=1430479449_49fb99a08013ff8b117d10bad5cd8d6e");
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream("abc.jpg");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
	        File file = new File("abc.jpg");
	        response.setHeader("Content-Disposition", "attachment; filename="+file.getName().replace(" ", "_"));
	        InputStream iStream = new FileInputStream(file);
	        IOUtils.copy(iStream, response.getOutputStream());
	        response.flushBuffer();
	    } catch (java.nio.file.NoSuchFileException e) {
	        response.setStatus(HttpStatus.NOT_FOUND.value());
	    } catch (Exception e) {
	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	    }
	}
}
