package com.giangnt.webapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
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
import com.giangnt.webapp.util.RequestUtil;

@Controller
@RequestMapping("/fsaccount*")
public class FsaccountController extends BaseFormController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5256970539504686733L;
	private HttpClient client = new DefaultHttpClient();;
	private FsaccountManager fsaccountManager = null;
	private UserManager userManager;
	private String ipAddress;
	private List<String> cookies;
	CookieStore cookieStore = new BasicCookieStore();
	HttpContext httpContext = new BasicHttpContext();
	

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

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
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
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
			@RequestParam(value = "account", required = false) String accountId, HttpServletRequest request) {

		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
//		if (link.contains("folder")) {
//
//		} else {
//
//		}
		System.out.println("Client Ip: " + ipAddress);
		
		String directLink = downloadFile(request, Integer.parseInt(accountId),
				link.trim());
		if (directLink != null && !directLink.isEmpty()) {
			user.setFreeLink(user.getFreeLink() - 1);
			userManager.save(user);

			Fsaccount accountChosen = fsaccountManager.getById(Integer.parseInt(accountId));
			accountChosen.setUsing(accountChosen.getUsing() + 1);
			fsaccountManager.updateFsAccount(accountChosen);
		}
		System.out.println(user.getUsername() + " is logged in get " + directLink);
		return directLink;
	}

	private String downloadFile(HttpServletRequest request, long accChosenId, String link) {

		String url = "https://www.fshare.vn/";

		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());

		String username = fsaccountManager.getById(accChosenId).getAccount();
		String password = fsaccountManager.getById(accChosenId).getSecurity();
		String security = NumberUtil.decoded(password.substring(
				Constants.DECODE_VERSION, password.length()));

		try {
			List<NameValuePair> postParams = getFormParams(url, username,
					security);
			CookieStore cookieStore = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
			for (org.apache.http.cookie.Cookie cookie : cookieStore.getCookies()) {
				if(cookie.getName().equals("")){
					String directLink = sendGet(request, link);
					return directLink;
				}
			}
			sendPost(url, postParams, link);
			String directLink = sendGet(request, link);//, sendPost(url, postParams, link));
			return directLink;
		} catch (Exception e) {
			logout();
			client.getConnectionManager().shutdown();
			e.printStackTrace();
			return "Trùng phiên đăng nhập, vui lòng thử lại sau...";
		}
	}

	private List<NameValuePair> getFormParams(String url, String username,
			String password) throws IOException {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("fs_csrf", RequestUtil
				.getCRSF(url)));

		paramList.add(new BasicNameValuePair("LoginForm[email]", username));
		paramList.add(new BasicNameValuePair("LoginForm[password]", password));
		return paramList;
	}


	/**
	 *  Login to Fshare to get cookie header
	 */
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
		post.setHeader("Referer", "https://www.fshare.vn/login");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");

		post.setEntity(new UrlEncodedFormEntity(postParams));

		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
				true);

		HttpResponse response = client.execute(post, httpContext);
		post.abort();
		Header[] headers = response.getHeaders("Set-Cookie");
		for (int i = 0; i < headers.length; i++) {
			System.out.println(headers[i].getValue().toString());
		}
		return headers;
		
	}

	private String sendGet(HttpServletRequest request, String link){//, Header[] headers) {

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
		get.setHeader("X-FORWARDED-FOR", ipAddress);
		get.setHeader("Content-Type", "application/x-www-form-urlencoded");
		try {
			client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,
					false);

			HttpResponse response = client.execute(get, httpContext);
			ClientConnectionManager clientConnectionManager = client
					.getConnectionManager();

			if (response.getHeaders("Location") != null
					&& response.getHeaders("Location").length > 0) {
				direcLk = response.getHeaders("Location")[0].getValue()
						.toString();
			}

//			response.getEntity().consumeContent();
//			InputStream is = response.getEntity().getContent();
//			is.close();
//			get.abort();
//			response.setHeader(
//					"Set-Cookie",
//					"fshare_userpass=deleted; expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.fshare.vn; httponly"
//							+ "fshare_userid=-1; expires=Mon, 26-Jan-2015 07:31:16 GMT; path=/; domain=.fshare.vn; httponly"
//							+ "fshare_a_userid=deleted; expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.fshare.vn; httponly"
//							+ "fshare_a_userpass=deleted; expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.fshare.vn; httponly"
//							+ "fshare_a_sessionid=deleted; expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.fshare.vn; httponly");
			// clientConnectionManager.shutdown();\
//			for (Cookie cookie : request.getCookies()) {
//				RequestUtil.deleteCookie((HttpServletResponse) response, cookie, "/getLink");
//			}
//			cookieStore.clear();
//			httpContext.setAttribute(ClientContext.COOKIE_STORE,cookieStore);
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
