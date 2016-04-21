package com.giangnt.webapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Convenience class for setting and retrieving cookies.
 */
public final class RequestUtil {
	private static final Log log = LogFactory.getLog(RequestUtil.class);
	static PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
	static RequestConfig reqConfig = RequestConfig.copy(RequestConfig.DEFAULT)
            .setConnectTimeout(30000)
            .setSocketTimeout(30000)
            .setConnectionRequestTimeout(60000)
            .build();
	static CloseableHttpClient client = HttpClientBuilder.create().setConnectionManager(poolingConnManager).build();
	static String sessionid = "";
	static String setCookie = "";
	
	/**
	 * Checkstyle rule: utility classes should not have public constructor
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private RequestUtil() {
	}

	// /**
	// * Convenience method to set a cookie
	// *
	// * @param response the current response
	// * @param name the name of the cookie
	// * @param value the value of the cookie
	// * @param path the path to set it on
	// */
	// public static void setCookie(HttpServletResponse response, String name,
	// String value, String path) {
	// if (log.isDebugEnabled()) {
	// log.debug("Setting cookie '" + name + "' on path '" + path + "'");
	// }
	//
	// Cookie cookie = new Cookie(name, value);
	// cookie.setSecure(false);
	// cookie.setPath(path);
	// cookie.setMaxAge(3600 * 24 * 30); // 30 days
	//
	// response.addCookie(cookie);
	// }
	//
	// /**
	// * Convenience method to get a cookie by name
	// *
	// * @param request the current request
	// * @param name the name of the cookie to find
	// *
	// * @return the cookie (if found), null if not found
	// */
	// public static Cookie getCookie(HttpServletRequest request, String name) {
	// Cookie[] cookies = request.getCookies();
	// Cookie returnCookie = null;
	//
	// if (cookies == null) {
	// return returnCookie;
	// }
	//
	// for (final Cookie thisCookie : cookies) {
	// if (thisCookie.getName().equals(name) &&
	// !"".equals(thisCookie.getValue())) {
	// returnCookie = thisCookie;
	// break;
	// }
	// }
	//
	// return returnCookie;
	// }

	/**
	 * Convenience method for deleting a cookie by name
	 *
	 * @param response
	 *            the current web response
	 * @param cookie
	 *            the cookie to delete
	 * @param path
	 *            the path on which the cookie was set (i.e. /appfuse)
	 */
	public static void deleteCookie(HttpServletResponse response, Cookie cookie, String path) {
		if (cookie != null) {
			// Delete the cookie by setting its maximum age to zero
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}

	/**
	 * Convenience method to get the application's URL based on request
	 * variables.
	 * 
	 * @param request
	 *            the current request
	 * @return URL to application
	 */
	public static String getAppURL(HttpServletRequest request) {
		if (request == null)
			return "";

		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		return url.toString();
	}

	public static Map<String, String> getCRSF(String url) throws IOException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		poolingConnManager.setMaxTotal(5);
		poolingConnManager.setDefaultMaxPerRoute(4);
		HttpHost host = new HttpHost("www.fshare.vn", 80);
		poolingConnManager.setMaxPerRoute(new HttpRoute(host), 5);
		HttpGet request = new HttpGet("https://www.fshare.vn");
		request.setConfig(reqConfig);
		CloseableHttpResponse response = client.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();
		if (response.getHeaders("Set-Cookie").length > 0){
			setCookie =	response.getHeaders("Set-Cookie")[0].getValue();
			sessionid = setCookie.substring(setCookie.indexOf("=") + 1, setCookie.indexOf(";"));
		}
		map.put("sessionid", sessionid);
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		HttpEntity entity = response.getEntity();
		
		InputStream body = entity.getContent();
		BufferedReader in = new BufferedReader(new InputStreamReader(body));
		String inputLine;
		request.releaseConnection();
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("csrf")) {
				Document doc = Jsoup.parse(inputLine);
				Elements inputElements = doc.getElementsByTag("input");
				for (Element inputElement : inputElements) {
					String value = inputElement.attr("value");
					System.out.println(value);
					map.put("csrf", value);
					try {
						if (entity != null)
							EntityUtils.consume(entity);
						request.releaseConnection();
					} catch (IOException e) {
						e.printStackTrace();
					}
					EntityUtils.consume(entity);
					return map;
				}
			}
		}
		EntityUtils.consume(entity);
		return null;

	}
}
