package javacourse.myorg.com;
import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class HomeworkServlet extends HttpServlet {
	int num1 = 4, num2 = 3, num3 = 7;
	int result =(num1+num2)*num3;
	String resultstr = new String ( "<h1> Result of ("+num1+"+"+num2+")"+"*"+num3+"="+result+"</h1>");
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.getWriter().println(resultstr);
	}
}
