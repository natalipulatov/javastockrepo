package javacourse.myorg.com;

import java.io.IOException;
import java.util.Date;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class StockDetailsSevlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
				resp.setContentType("text/html");
				//Creating new Date instances to solve the problem of reference to the same Date object  
				Calendar cal = Calendar.getInstance();
				cal.set(2014,10,15);
				Date d1 = cal.getTime();
				Date d2 = cal.getTime();
				Date d3 = cal.getTime();
				//Initializing instance of class Stock
				Stock stockPih = new Stock("PIH",(float)13.1, (float)12.4, d1);
				Stock stockAal = new Stock("AAL",(float)5.78, (float)5.5, d2);
				Stock stockCaas = new Stock("CAAS",(float)32.2, (float)31.5, d3);
				//Printing the stocks
				resp.getWriter().println(stockPih.getHtmlDescription());
				resp.getWriter().println("<br>");
				resp.getWriter().println(stockAal.getHtmlDescription());
				resp.getWriter().println("<br>");
				resp.getWriter().println(stockCaas.getHtmlDescription());
}
}