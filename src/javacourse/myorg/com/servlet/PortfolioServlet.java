package javacourse.myorg.com.servlet;

import java.io.IOException;

import javacourse.myorg.com.model.Portfolio;
import javacourse.myorg.com.model.Stock;
import javacourse.myorg.com.service.PortfolioManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**This class allows to show portFolios in the browser
 *  * @author Natali
 * @since 08/05/2015
 * date 08/05/2015 */
public class PortfolioServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
				resp.setContentType("text/html");
				PortfolioManager portfolioManger = new PortfolioManager();
				
				Portfolio portfolio = portfolioManger.getPortfolio();
				Portfolio portfolio2 = new Portfolio(portfolio);
				
				portfolio2.setTitle("portfolio #2");
				resp.getWriter().println(portfolio.getHtmlString());
				resp.getWriter().println(portfolio2.getHtmlString());
				
				portfolio.removeStock("PIH");
				resp.getWriter().println(portfolio.getHtmlString());
				resp.getWriter().println(portfolio2.getHtmlString());
				
				portfolio2.changeStockBid("CAAS", 55.55f);
				resp.getWriter().println(portfolio.getHtmlString());
				resp.getWriter().println(portfolio2.getHtmlString());
	}
}
