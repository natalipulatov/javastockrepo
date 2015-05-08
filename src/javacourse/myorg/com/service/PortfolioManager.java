package javacourse.myorg.com.service;

import java.util.Calendar;
import java.util.Date;

import javacourse.myorg.com.model.Portfolio;
import javacourse.myorg.com.model.Stock;
/** An instance if this class represents portFolio manger that managing the portFolio object 
 * @author Natali
 * @since 08/05/2015
 * date 08/05/2015  */
public class PortfolioManager {
	/**The method creates new portFolio.
	 * @return new portFolio of stocks */
	public Portfolio getPortfolio(){
		Portfolio portfolio = new Portfolio();
		portfolio.setTitle("Portfolio1");
		
		//Creating new Date instances to solve the problem of reference to the same Date object
		Calendar cal = Calendar.getInstance();
		cal.set(2014,10,15);
		Date d1 = cal.getTime();
		Date d2 = cal.getTime();
		Date d3 = cal.getTime();
		
		//Initializing instance of class Stock
		Stock stockPih = new Stock("PIH",(float)13.1, (float)12.4, d1);
		portfolio.addStock(stockPih);
		
		Stock stockAal = new Stock("AAL",(float)5.78, (float)5.5, d2);
		portfolio.addStock(stockAal);
		
		Stock stockCaas = new Stock("CAAS",(float)32.2, (float)31.5, d3);
		portfolio.addStock(stockCaas);
		return portfolio;
	}
}
