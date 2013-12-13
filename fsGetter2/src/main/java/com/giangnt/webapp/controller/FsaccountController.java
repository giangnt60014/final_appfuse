package com.giangnt.webapp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.giangnt.webapp.model.Fsaccount;
import com.giangnt.webapp.service.FsaccountManager;

@Controller
@RequestMapping("/fsaccount*")
public class FsaccountController {

	private String cookies;
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
		return user;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Fsaccount fsaccount, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response) {
		
		if (request.getParameter("action").equals("Add")) {
			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String link = request.getParameter("link");
			String fsAccChosen = request.getParameter("id");
			
			if (downloadFile(Integer.parseInt(fsAccChosen), link)){
				user.setFreeLink(user.getFreeLink()-1);
				userManager.save(user);
			}
			System.out.println(user.getUsername() + " is logged in");
		}
		return null;

	}

	private boolean downloadFile(long accChosenId, String link) {

		String url = "https://www.fshare.vn/login.php";
//		String gmail = "http://www.fshare.vn/file/TJCXWFZC7T";
		
//		String url = "http://hc1-appvm01:8090/login.jsp";
//		String gmail = "http://hc1-appvm01:8090/secure/Dashboard.jspa";

		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());

		String page = GetPageContent(url);

		List<NameValuePair> postParams = getFormParams(page,
				fsaccountManager.get(accChosenId).getAccount(), fsaccountManager.get(accChosenId).getSecurity());

		try {
			sendPost(url, postParams, link);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		String result = GetPageContent(gmail);
//		System.out.println(result);

		System.out.println("Done");
		return true;

	}

	private String GetPageContent(String url) {
		HttpGet request = new HttpGet(url);

		request.setHeader("User-Agent", "Mozilla/5.0");
		request.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-Language", "en-US,en;q=0.5");

		HttpResponse response;
		try {
			response = client.execute(request);
			int responseCode = response.getStatusLine().getStatusCode();

			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			try {
				BufferedReader rd;
				rd = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				// set cookies
				setCookies(response.getFirstHeader("Set-Cookie") == null ? ""
						: response.getFirstHeader("Set-Cookie").toString());

				return result.toString();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return url;
	}

	// /////////////////////////////////////////////
	private List<NameValuePair> getFormParams(String page, String username,
			String password) {
		System.out.println("Extracting form's data...");

		Document doc = Jsoup.parse(page);

//		// Google form id
//		Elements loginform = doc.getElementsByClass("loginField");
//		Elements inputElements = loginform.get(0).getElementsByTag("input");
//
//		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//
//		for (Element inputElement : inputElements) {
//			String key = inputElement.attr("name");
//			String value = inputElement.attr("value");
//
//			if(key.equals("login_useremail") || key.equals("login_password")){
//				if (key.equals("login_useremail"))
//					value = username;
//				else if (key.equals("login_password"))
//					value = password;
//
//				paramList.add(new BasicNameValuePair(key, value));
//			}
//			
//
//		}

		// Jira form id
				Element loginform = doc.getElementById("login-form");
				Elements inputElements = loginform.getElementsByTag("input");

				List<NameValuePair> paramList = new ArrayList<NameValuePair>();

				for (Element inputElement : inputElements) {
					String key = inputElement.attr("name");
					String value = inputElement.attr("value");

					if(key.equals("os_username") || key.equals("os_password")){
						if (key.equals("os_username"))
							value = username;
						else if (key.equals("os_password"))
							value = password;

						paramList.add(new BasicNameValuePair(key, value));
					}
					

				}
		return paramList;
	}
	private Header[] sendPost(String url, List<NameValuePair> postParams, String link)
			throws Exception {

		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("Host", "hc1-appvm01:8090");
		post.setHeader("User-Agent", "Mozilla/5.0");
		post.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.5");
		// post.setHeader("Cookie", getCookies());
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Referer", "http://hc1-appvm01:8090/secure/Dashboard.jspa");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");

		post.setEntity(new UrlEncodedFormEntity(postParams));

		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		
		HttpResponse response = client.execute(post);

		int responseCode = response.getStatusLine().getStatusCode();

		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + postParams);
		System.out.println("Response Code : " + responseCode);

		return response.getHeaders("Cookie");
	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}
}
