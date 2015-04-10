package javacourse.myorg.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class NewServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		resp.setContentType("text/html");
		
		//Calculation 1:
		int radius = 50;
		double result1Area = radius*(Math.PI);
		
		//Calculation 2:
		int angleB = 30;
		int hypotenuseLength = 50;
		double angleBinRadians = Math.toRadians(angleB);
		double angleBinsin = Math.sin(angleBinRadians);
		double reasult2Oppsite = angleBinsin * hypotenuseLength;
		
		//Calculation 3:
		double base = 20;
		double exp = 13;
		double result3= Math.pow(base, exp);
		
		String line1 = new String("Calculation 1 : Area of circle with radius "+radius+" is: "+result1Area+" square-cm \n");
		String line2 = new String("Calculation 2 : Length of opposite where angle B is "+angleB+" degrees and Hypotenuse length is  "+hypotenuseLength+" cm is: "+reasult2Oppsite+" cm\n");
		String line3 = new String("Calculation 3 : Power of  "+base+" with exp of  "+exp+" is: "+result3+"\n");
		String resultStr = line1+ "<br>"+line2+ "<br>"+line3;
		resp.getWriter().println(resultStr);
	}
}

