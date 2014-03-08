package com.giangnt.webapp.controller;

import java.io.IOException;
import java.io.InputStream;
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
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.appfuse.Constants;
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

	private HttpClient client;
	private FsaccountManager fsaccountManager = null;
	private UserManager userManager;
	private String ipAddress;

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
		if (user.getUsername() != null && !user.getUsername().isEmpty()) {
			return user;
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/getLink", method = RequestMethod.GET)
	public @ResponseBody
	String getLink(@RequestParam(value = "link", required = false) String link,
			@RequestParam(value = "account", required = false) String accountId) {

		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (link.contains("folder")) {

		} else {

		}
		System.out.println("Client Ip: " + ipAddress);
		client = new DefaultHttpClient();
		String directLink = downloadFile(Integer.parseInt(accountId),
				link.trim());
		if (directLink != null && !directLink.isEmpty()) {
			user.setFreeLink(user.getFreeLink() - 1);
			userManager.save(user);

			Fsaccount accountChosen = fsaccountManager.getById(Integer
					.parseInt(accountId));
			accountChosen.setUsing(accountChosen.getUsing() + 1);
			fsaccountManager.updateFsAccount(accountChosen);
		}
		System.out.println(user.getUsername() + " is logged in get "
				+ directLink);

		return directLink;
	}

	private String downloadFile(long accChosenId, String link) {

		String url = "https://www.fshare.vn/login.php";

		// make sure cookies is turn on
//		CookieHandler.setDefault(new CookieManager());

		String username = fsaccountManager.getById(accChosenId).getAccount();
		String password = fsaccountManager.getById(accChosenId).getSecurity();
		String security = NumberUtil.decoded(password.substring(
				Constants.DECODE_VERSION, password.length()));

		List<NameValuePair> postParams = getFormParams(username, security);

		try {
			String directLink = sendGet(link, sendPost(url, postParams, link));
			logout();
			return directLink;
		} catch (Exception e) {
			logout();
			client.getConnectionManager().shutdown();
			e.printStackTrace();
			return "Trùng phiên đăng nhập, vui lòng thử lại sau...";
		}
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
		post.setHeader("Referer", "http://www.fshare.vn");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");

		post.setEntity(new UrlEncodedFormEntity(postParams));

		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
				true);

		HttpResponse response = client.execute(post);
		post.abort();
		Header[] headers = response.getHeaders("Set-Cookie");
		for (int i = 0; i < headers.length; i++) {
			System.out.println(headers[i].getValue().toString());
		}
		// response.getEntity().consumeContent();
		return headers;
	}

	private String sendGet(String link, Header[] headers) {

		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		String direcLk = "Trùng phiên đăng nhập, vui lòng thử lại sau...";
		System.out.println(user.getUsername() + " is getting link: " + link);
		HttpGet get = new HttpGet(link);
		get.setHeader("Host", "www.fshare.vn");
		get.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
		get.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.setHeader("Accept-Language", "en-US,en;q=0.5");
		get.setHeader("Connection", "close");
		// String headerString = "";
		// for (Header header : headers) {
		// headerString += header.getValue();
		// }
		// get.setHeader("Cookie",headerString);
		get.setHeader("Content-Type", "application/x-www-form-urlencoded");
		try {
			client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,
					false);

			HttpResponse response = client.execute(get);
			ClientConnectionManager clientConnectionManager = client
					.getConnectionManager();

			if(response.getHeaders("Location") !=null && response.getHeaders("Location").length>0){
				direcLk = response.getHeaders("Location")[0].getValue()
						.toString();
			}
			
			response.getEntity().consumeContent();
			InputStream is = response.getEntity().getContent();
			is.close();
			get.abort();
			response.setHeader(
					"Set-Cookie",
					"fshare_userpass=deleted; expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.fshare.vn; httponly"
							+ "fshare_userid=-1; expires=Mon, 26-Jan-2015 07:31:16 GMT; path=/; domain=.fshare.vn; httponly"
							+ "fshare_a_userid=deleted; expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.fshare.vn; httponly"
							+ "fshare_a_userpass=deleted; expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.fshare.vn; httponly"
							+ "fshare_a_sessionid=deleted; expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.fshare.vn; httponly");
			// clientConnectionManager.shutdown();
			return direcLk;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logout();
		} catch (IOException e) {
			e.printStackTrace();
			logout();
		}
		logout();
		return null;
	}

	public String logout() {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		HttpGet get = new HttpGet("http://www.fshare.vn/logout.php");
		get.setHeader("Host", "www.fshare.vn");
		get.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
		get.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.setHeader("Accept-Language", "en-US,en;q=0.5");
		get.setHeader("Content-Type", "application/x-www-form-urlencoded");

		try {
			HttpResponse response = client.execute(get);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			System.out
					.println(user.getUsername() + " is logged out of Fshare!");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
