/*import net.haxx.curl.CurlGlue;


 * Test class to login to eBay
 * change the username and password to yours.
 
public class Main {
	public final static String username = "MY_EBAY_USERNAME";
	public final static String password = "MY_EBAY_PASSWORD";

	public int handleString(byte s[]) {
		 output everything 
		try {
			System.out.write(s);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) {
		CurlGlue cg;

		try {
			Main cw = new Main();

			// Register callback write function
			cg = new CurlGlue();

			// first, go to the login page to get the cookies.

			cg.setopt(CurlGlue.CURLOPT_URL,
					"https://signin.ebay.com/aw-cgi/eBayISAPI.dll?SignIn");
			cg.setopt(
					CurlGlue.CURLOPT_USERAGENT,
					"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.7) Gecko/2009030422 Ubuntu/8.10 (intrepid) Firefox/3.0.7");
			cg.setopt(CurlGlue.CURLOPT_FOLLOWLOCATION, 1);
			cg.setopt(CurlGlue.CURLOPT_COOKIEJAR, "cookie.txt");
			cg.setopt(CurlGlue.CURLOPT_COOKIEFILE, "cookie.txt");
			cg.perform();
			cg.close();

			// login using the username, password and the cookies we got from
			// the login page.
			cg.setopt(CurlGlue.CURLOPT_URL,
					"https://signin.ebay.com/aw-cgi/eBayISAPI.dll");
			cg.setopt(
					CurlGlue.CURLOPT_USERAGENT,
					"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.7) Gecko/2009030422 Ubuntu/8.10 (intrepid) Firefox/3.0.7");
			cg.setopt(CurlGlue.CURLOPT_POST, 1);
			cg.setopt(
					CurlGlue.CURLOPT_POSTFIELDS,
					"MfcISAPICommand=SignInWelcome&siteid=0&co_partnerId=2&UsingSSL=0&ru=&pp=&pa1=&pa2=&pa3=&i1=-1&pageType=-1&userid="
							+ username + "&pass=" + password);
			cg.setopt(CurlGlue.CURLOPT_FOLLOWLOCATION, 1);
			cg.setopt(CurlGlue.CURLOPT_COOKIEJAR, "cookie.txt");
			cg.setopt(CurlGlue.CURLOPT_COOKIEFILE, "cookie.txt");
			cg.perform();

			cg.close();

			// now, if you like you can fetch the page:
			// http://my.ebay.com/ws/eBayISAPI.dll?MyeBay and see that you're
			// logged in
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}*/