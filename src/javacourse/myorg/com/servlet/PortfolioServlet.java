package javacourse.myorg.com.servlet;

import java.io.IOException;

import javacourse.myorg.com.model.Portfolio;
import javacourse.myorg.com.service.PortfolioManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PortfolioServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
				resp.setContentType("text/html");
				PortfolioManager portfolioManger = new PortfolioManager();
				Portfolio portfolio = portfolioManger.getPortfolio();
				resp.getWriter().println(portfolio.getHtmlString());
	}
}