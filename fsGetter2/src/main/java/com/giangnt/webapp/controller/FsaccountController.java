package com.giangnt.webapp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
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
	private FsaccountManager fsaccountManager = null;

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

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Fsaccount fsaccount, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameter("action").equals("Add")) {
			String age = request.getParameter("age");
			String  fsAccChosen = request.getParameter("id");
//			downloadFile();
			System.out.println(">>>>>>>>" + age);
			System.out.println(">>>>>>>>" + fsaccount.getId());
			System.out.println(">>>>" + fsAccChosen);
		}
		return null;

	}

	private void downloadFile() {
	
		String url = "http://www.fshare.vn/file/TJCXWFZC7T";
		String username = "giangnt60014@gmail.com";
	    String password = "kdm5ltus";
	    String host="https://www.fshare.vn/login.php";
		
		 
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
 
		// add request header
		request.addHeader("User-Agent", "Mozilla/5.0");
		request.addHeader("Host", host);
		request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.addHeader("Accept-Encoding", "gzip, deflate");
//		request.addHeader("Cookie", "__cfduid=d6d863697d1db09f750efc50dc91fc7251386247233966; fshare_userid=13634; fshare_userpass=e026be39d22ec528bee6f9792a57da22%3A4n90jj4v5rjb6; fshare_a_userpass=Ns5Hsq9EObIiqZAXvlOOt1zvDK%2FrBLI1rOAqWZK7kMU%3D; fshare_a_userid=13634");
//		request.addHeader("Cookie","__cfduid=da5d62fccb965438d604bc2093345d8a51384431782725; _ga=GA1.2.1188952325.1384431783; PHPSESSID=vg44uu800dflkb9qr3g1rba0c6; loc={%22province%22:29%2C%22zone%22:1%2C%22country%22:%22vn%22%2C%22ip%22:%22113.172.166.124%22}; fosp_gender=3; trueImp={%2224611%22:[3%2C0%2C1384438804529]%2C%2225340%22:[3%2C0%2C1384656161326]%2C%2225341%22:[3%2C0%2C1384656161326]%2C%2225494%22:[2%2C0%2C1384437887526]%2C%2225610%22:[1%2C0%2C1384438804529]%2C%2225624%22:[2%2C0%2C1385477289689]%2C%2225696%22:[1%2C0%2C1384431785240]%2C%2225715%22:[1%2C0%2C1384431785240]%2C%2225724%22:[3%2C0%2C1385800510950]%2C%2225729%22:[1%2C0%2C1385742414815]%2C%2225733%22:[1%2C0%2C1385800510950]%2C%2225748%22:[2%2C0%2C1384438804529]%2C%2225787%22:[3%2C0%2C1384605723879]%2C%2225790%22:[4%2C0%2C1384688715354]%2C%2225795%22:[4%2C0%2C1384605723879]%2C%2225821%22:[1%2C0%2C1384607052343]%2C%2225877%22:[2%2C0%2C1384529402824]%2C%2225878%22:[3%2C0%2C1384529402824]%2C%2225899%22:[1%2C0%2C1384581571809]%2C%2225906%22:[2%2C0%2C1384607052343]%2C%2225907%22:[1%2C0%2C1384688715354]%2C%2225908%22:[1%2C0%2C1384581571809]%2C%2225909%22:[1%2C0%2C1384581571809]%2C%2225910%22:[1%2C0%2C1384605770184]%2C%2225911%22:[1%2C0%2C1384688715354]%2C%2225912%22:[1%2C0%2C1384605770184]%2C%2225924%22:[3%2C0%2C1386165111536]%2C%2225954%22:[1%2C0%2C1384688715354]%2C%2226008%22:[1%2C0%2C1384688715354]%2C%2226015%22:[2%2C0%2C1384607052343]%2C%2226018%22:[1%2C0%2C1384438626636]%2C%2226027%22:[1%2C0%2C1384581571809]%2C%2226056%22:[1%2C0%2C1385556398433]%2C%2226072%22:[1%2C0%2C1385556157847]%2C%2226074%22:[1%2C0%2C1384438626636]%2C%2226095%22:[3%2C0%2C1384688715354]%2C%2226096%22:[2%2C0%2C1384607052343]%2C%2226099%22:[1%2C0%2C1384688715354]%2C%2226112%22:[4%2C0%2C1384656161326]%2C%2226136%22:[1%2C0%2C1384581571809]%2C%2226141%22:[1%2C0%2C1384605770184]%2C%2226167%22:[2%2C0%2C1384656025354]%2C%2226194%22:[1%2C0%2C1385866961635]%2C%2226197%22:[2%2C0%2C1385867093455]%2C%2226198%22:[1%2C0%2C1385867093455]%2C%2226210%22:[1%2C0%2C1385864706426]%2C%2226212%22:[1%2C0%2C1385864706426]%2C%2226218%22:[1%2C0%2C1385476261689]%2C%2226219%22:[7%2C0%2C1386165370828]%2C%2226220%22:[1%2C0%2C1385477289689]%2C%2226640%22:[1%2C0%2C1385556157847]%2C%2226712%22:[3%2C0%2C1386165106545]%2C%2226713%22:[2%2C0%2C1385875119443]%2C%2226716%22:[1%2C0%2C1385556157847]%2C%2226717%22:[1%2C0%2C1385802035093]%2C%2226767%22:[1%2C0%2C1385767257468]%2C%2226768%22:[1%2C0%2C1385767257468]%2C%2226776%22:[1%2C0%2C1385556157847]%2C%2226797%22:[1%2C0%2C1385800510950]%2C%2226815%22:[1%2C0%2C1385556157847]%2C%2226816%22:[1%2C0%2C1385477101794]%2C%2226871%22:[1%2C0%2C1385864735373]%2C%2226872%22:[1%2C0%2C1385864735373]%2C%2226888%22:[2%2C0%2C1385911245640]%2C%2226901%22:[1%2C0%2C1385875049667]%2C%2226920%22:[4%2C0%2C1386165568405]%2C%2226921%22:[5%2C0%2C1385911237421]%2C%2226926%22:[3%2C0%2C1385911245640]%2C%2226927%22:[3%2C0%2C1385911245640]%2C%2226939%22:[4%2C0%2C1385911245640]%2C%2226948%22:[1%2C0%2C1386165485357]%2C%2226950%22:[2%2C0%2C1386165485357]%2C%2226964%22:[2%2C0%2C1385875119443]%2C%2226972%22:[1%2C0%2C1385875049667]%2C%2226982%22:[2%2C0%2C1385802035093]%2C%2226983%22:[1%2C0%2C1385800510950]%2C%2226986%22:[2%2C0%2C1386246841180]%2C%2226990%22:[1%2C0%2C1386165485357]%2C%2226991%22:[1%2C0%2C1386165485357]%2C%2227013%22:[1%2C0%2C1386165485357]%2C%2227027%22:[5%2C0%2C1386246841180]%2C%2227038%22:[1%2C0%2C1386165106117]%2C%2227045%22:[2%2C0%2C1386165568405]%2C%2227047%22:[2%2C0%2C1386246841180]%2C%2227049%22:[2%2C0%2C1386165786602]%2C%2227050%22:[1%2C0%2C1386165111536]%2C%2227051%22:[1%2C0%2C1386165568405]%2C%2227055%22:[1%2C0%2C1386165568405]%2C%2227117%22:[2%2C0%2C1386165786602]%2C%2227119%22:[1%2C0%2C1386165485357]%2C%2227120%22:[1%2C0%2C1386165485357]%2C%2227161%22:[1%2C0%2C1386246841180]}; popupday=1386261074; fshare_a_sessionid=vg44uu800dflkb9qr3g1rba0c61386261081; fshare_a_userpass=Ns5Hsq9EObIiqZAXvlOOt1zvDK%2FrBLI1rOAqWZK7kMU%3D; fshare_a_userid=13634; fshare_userpass=e026be39d22ec528bee6f9792a57da22%3A4n90jj4v5rjb6; fshare_lastvisit=1386261081; fshare_userid=13634");
		request.addHeader("Connection", "keep-alive");
 
		HttpResponse response;
		try {
			client.getCredentialsProvider().setCredentials(new AuthScope(host, AuthScope.ANY_PORT), new UsernamePasswordCredentials(username, password));
			response = client.execute(request);
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + 
	                       response.getStatusLine().getStatusCode());
	 
			BufferedReader rd = new BufferedReader(
	                       new InputStreamReader(response.getEntity().getContent()));
	 
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
	 
			System.out.println(result.toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		
		
	}

}

