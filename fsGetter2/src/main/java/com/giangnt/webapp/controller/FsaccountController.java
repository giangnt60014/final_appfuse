package com.giangnt.webapp.controller;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.giangnt.webapp.model.Fsaccount;
import com.giangnt.webapp.service.FsaccountManager;
import com.giangnt.webapp.util.NumberUtil;

@Controller
@RequestMapping("/fsaccount*")
public class FsaccountController extends BaseFormController {

	private HttpClient client = new DefaultHttpClient();
	private FsaccountManager fsaccountManager = null;
	private UserManager userManager;

	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Autowired
	public void setFsaccountManager(FsaccountManager fsaccountManager) {
		this.fsaccountManager = fsaccountManager;
	}

	public FsaccountController() {
		setCancelView("redirect:/mainMenu");
		setSuccessView("redirect:/fsaccount");
	}
	
	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public Fsaccount showForm(HttpServletRequest request,
			HttpServletResponse response) {
		return new Fsaccount();
	}

	@ModelAttribute("fsaccountList")
	public Collection<Fsaccount> getAllAccount() {
		List<Fsaccount> fsaccounts = fsaccountManager.getAllAccount();
		return fsaccounts;
	}

	@ModelAttribute("user")
	public User getCurrentUser() {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if(user.getUsername()!=null && !user.getUsername().isEmpty()){
			return user;
		}else{
			return null;
		}
		
	}

	
	@RequestMapping(value = "/getLink", method = RequestMethod.GET)
	public @ResponseBody
	String getLink(
			@RequestParam(value = "link", required = false) String link,
			@RequestParam(value = "account", required = false) String accountId) {
		
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if(link.contains("folder")){
			
		}else{
			
		}

		String directLink = downloadFile(Integer.parseInt(accountId), link);
		if (directLink!=null && !directLink.isEmpty()) {
			user.setFreeLink(user.getFreeLink() - 1);
			userManager.save(user);

			Fsaccount accountChosen = fsaccountManager.getById(Integer.parseInt(accountId));
			accountChosen.setUsing(accountChosen.getUsing()+1);
			fsaccountManager.saveFsAccount(accountChosen);
		}
		System.out.println(user.getUsername() + " is logged in");
		
		return directLink;
	}

	private String downloadFile(long accChosenId, String link) {

		String url = "https://www.fshare.vn/login.php";
//		link = "http://www.fshare.vn/file/TJCXWFZC7T";

		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());

		String username = fsaccountManager.getById(accChosenId).getAccount();
		String password = fsaccountManager.getById(accChosenId).getSecurity();
		String security = NumberUtil.decoded(password.substring(0, password.length()-4));
		
		List<NameValuePair> postParams = getFormParams(username,security);

		try {
			String directLink = sendGet(link,sendPost(url, postParams, link));
			return directLink;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private List<NameValuePair> getFormParams(String username, String password) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("login_useremail", username));
		paramList.add(new BasicNameValuePair("login_password", password));
		return paramList;
	}

	private Header[] sendPost(String url, List<NameValuePair> postParams,
			String link) throws Exception {

		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("Host", "www.fshare.vn");
		post.setHeader("User-Agent", "Mozilla/5.0");
		post.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.5");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Referer", link);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");

		post.setEntity(new UrlEncodedFormEntity(postParams));

		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
				true);

		HttpResponse response = client.execute(post);
		Header[] headers = response.getHeaders("Set-Cookie");
		
		return headers;
	}

	private String sendGet(String link, Header[] headers){
		
		HttpGet get = new HttpGet(link);
		get.setHeader("Host", "www.fshare.vn");
		get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
		get.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.setHeader("Accept-Language", "en-US,en;q=0.5");
		get.setHeader("Connection", "keep-alive");
		String headerString = "";
		for (Header header : headers) {
			headerString += header.getValue();
		}
		get.setHeader("Cookie",headerString);
		get.setHeader("Content-Type", "application/x-www-form-urlencoded");
		try {
			client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,
					false);
			
			HttpResponse response = client.execute(get);
			System.out.println(response.getHeaders("Location"));
			return response.getHeaders("Location")[0].getValue().toString();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
