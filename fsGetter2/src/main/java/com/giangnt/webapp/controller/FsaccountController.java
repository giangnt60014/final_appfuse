package com.giangnt.webapp.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
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
import com.giangnt.webapp.model.Link;
import com.giangnt.webapp.service.FsaccountManager;
import com.giangnt.webapp.service.LinkManager;
import com.giangnt.webapp.util.NumberUtil;
import com.giangnt.webapp.util.RequestUtil;

@Controller
@RequestMapping("/fsaccount*")
public class FsaccountController extends BaseFormController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5256970539504686733L;
	private PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
	private RequestConfig reqConfig = RequestConfig.copy(RequestConfig.DEFAULT)
            .setConnectTimeout(30000)
            .setSocketTimeout(30000)
            .setConnectionRequestTimeout(60000)
            .build();
	ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
	    @Override
	    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
	        HeaderElementIterator it = new BasicHeaderElementIterator
	            (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
	        while (it.hasNext()) {
	            HeaderElement he = it.nextElement();
	            String param = he.getName();
	            String value = he.getValue();
	            if (value != null && param.equalsIgnoreCase
	               ("timeout")) {
	                return Long.parseLong(value) * 1000;
	            }
	        }
	        return 5 * 1000;
	    }
	};
	private CloseableHttpClient client = HttpClientBuilder.create().setKeepAliveStrategy(myStrategy).setDefaultRequestConfig(reqConfig)
			.disableRedirectHandling().setConnectionManager(poolingConnManager).build();
	private FsaccountManager fsaccountManager = null;
	private UserManager userManager;
	private LinkManager linkManager;
	private String ipAddress;
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
	
	@Autowired
	public void setLinkManager(LinkManager linkManager) {
		this.linkManager = linkManager;
	}

	public FsaccountController() {
		poolingConnManager.setMaxTotal(5);
		poolingConnManager.setDefaultMaxPerRoute(4);
		HttpHost host = new HttpHost("www.fshare.vn", 80);
		poolingConnManager.setMaxPerRoute(new HttpRoute(host), 5);
		setCancelView("redirect:/mainMenu");
		setSuccessView("redirect:/fsaccount");
	}

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public Fsaccount showForm(HttpServletRequest request,
			HttpServletResponse response) {
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
		Object principalStr = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if(principalStr instanceof String){
			return null;
		}else{
			User user = (User) principalStr;
			if (user.getUsername() != null && !user.getUsername().isEmpty()) {
				return user;
			} else {
				return null;
			}
		}
		

	}

	@RequestMapping(value = "/getLink", method = RequestMethod.GET)
	public @ResponseBody
	String getLink(@RequestParam(value = "link", required = false) String link,
			@RequestParam(value = "account", required = false) String accountId, HttpServletRequest request) {

		User user = this.getCurrentUser();
		if(accountId==null || "undefined".equals(accountId)){
			accountId = fsaccountManager.getAllAccount().get(0).getId().toString();
		}
		String directLink = downloadFile(request, Integer.parseInt(accountId),
				link.trim());
		if (directLink != null && !directLink.isEmpty()) {
			Link linkObj = new Link();
			if(user!=null){
				user.setFreeLink(user.getFreeLink() - 1);
				userManager.save(user);
				linkObj.setUser(user);
			}
			Fsaccount accountChosen = fsaccountManager.getById(Integer.parseInt(accountId));
			accountChosen.setUsing(accountChosen.getUsing() + 1);
			fsaccountManager.updateFsAccount(accountChosen);
			
			
			linkObj.setLink(link);
			linkObj.setDirectLink(directLink);
			linkObj.setUser(userManager.getUserByUsername("user1"));
			linkManager.saveLink(linkObj);
		}
		System.out.println((user!=null ? user.getUsername():"Anonymous") + " is logged in get " + directLink);
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
			sendPost(url, postParams, link);
			String directLink = sendGet(request, link);
			return directLink;
		} catch (Exception e) {
			logout();
			e.printStackTrace();
			System.out.println(e);
			return "Lỗi trong quá trình lấy link, vui lòng thử lại sau...";
		}
	}

	private List<NameValuePair> getFormParams(String url, String username,
			String password) throws IOException {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("LoginForm[email]", username));
		paramList.add(new BasicNameValuePair("LoginForm[password]", password));
		paramList.add(new BasicNameValuePair("LoginForm[checkloginpopup]", "0"));
		paramList.add(new BasicNameValuePair("LoginForm[rememberMe]", "0"));
		paramList.add(new BasicNameValuePair("yt0", "Đăng nhập"));
		return paramList;
	}


	/**
	 *  Login to Fshare to get cookie header
	 */
	private void sendPost(String url, List<NameValuePair> postParams,
			String link) throws Exception {
		System.out.println((getCurrentUser()!=null ? getCurrentUser().getUsername():"Anonymous") + " is trying to login...");
		
		Map<String, String> map = RequestUtil.getCRSF(url);
		postParams.add(new BasicNameValuePair("fs_csrf", map.get("csrf")));
		
		HttpPost post = new HttpPost("https://www.fshare.vn/login/");
		String body = URLEncodedUtils.format(postParams, StandardCharsets.UTF_8); // use encoding of request
		StringEntity entity = new StringEntity(body);
		post.setEntity(entity);
		// add header
		post.setHeader(HttpHeaders.HOST, "www.fshare.vn");
		post.setHeader(HttpHeaders.ACCEPT,
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader(HttpHeaders.REFERER, "https://www.fshare.vn/");
		post.setHeader(HttpHeaders.CONTENT_TYPE, URLEncodedUtils.CONTENT_TYPE);
		post.setHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
		
		post.setHeader("Cookie", "session_id="+ map.get("sessionid"));
		RequestConfig config = RequestConfig.copy(reqConfig).setCircularRedirectsAllowed(true).build();
		post.setConfig(config);
		client.execute(post);
		post.abort();
		post.releaseConnection();
		System.out.println((getCurrentUser()!=null ? getCurrentUser().getUsername():"Anonymous") + " is logged in!");
		
	}

	private String sendGet(HttpServletRequest request, String link){

		String direcLk = "Trùng phiên đăng nhập, vui lòng thử lại sau...";
		System.out.println((getCurrentUser()!=null ? getCurrentUser().getUsername():"Anonymous") + " is getting link: " + link);
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
		get.setConfig(reqConfig);
		try {
			CloseableHttpResponse  response = client.execute(get, httpContext);
			direcLk = response.getHeaders("Location")[0].getValue()
					.toString();
			get.releaseConnection();
			logout();
			response.close();
			return direcLk;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.out.println(e);
			logout();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
			logout();
		}catch (Exception e){
			e.printStackTrace();
			System.out.println(e);
			logout();
		}
		
		return null;
	}

	public String logout() {

		HttpGet get = new HttpGet("http://www.fshare.vn/logout");
		get.setHeader("Host", "www.fshare.vn");
		get.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
		get.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.setHeader("Accept-Language", "en-US,en;q=0.5");
		get.setHeader("Content-Type", "application/x-www-form-urlencoded");

		try {
			CloseableHttpResponse  response = client.execute(get);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			System.out.println((getCurrentUser()!=null ? getCurrentUser().getUsername():"Anonymous") + " is logged out of Fshare!");
			get.releaseConnection();
			response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.out.println(e);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return null;
	}
}
