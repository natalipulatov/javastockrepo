package javacourse.myorg.com.service;

import java.util.Calendar;
import java.util.Date;

import javacourse.myorg.com.model.Portfolio;
import javacourse.myorg.com.model.Stock;
/** An instance if this class represents portFolio manger that managing the portFolio object 
 * @author Natali */
public class PortfolioManager {
	/**The method creates new portFolio.
	 * @return new portFolio of stocks */
	public Portfolio getPortfolio(){
		Portfolio myPortfolio = new Portfolio();
		//Exercise.2
		myPortfolio.setTitle("Exercise 7 portfolio");
		//Exercise.3
		myPortfolio.updateBalance(10000);
		
		//Creating new Date instances to solve the problem of reference to the same Date object
		Calendar cal = Calendar.getInstance();
		cal.set(2014,11,15);
		Date d1 = cal.getTime();
		Date d2 = cal.getTime();
		Date d3 = cal.getTime();
		
		//Initializing instance of class Stock
		//Exercise.4
		Stock stockPih = new Stock("PIH",(float)10.0, (float)8.5, d1);
		myPortfolio.buyStock(stockPih, 20);
		
		Stock stockAal = new Stock("AAL",(float)30.0, (float)25.5, d2);
		myPortfolio.buyStock(stockAal, 30);
		
		Stock stockCaas = new Stock("CAAS",(float)20.0, (float)15.5, d3);
		myPortfolio.buyStock(stockCaas, 40);
		
		//Exercise.5
		myPortfolio.sellStock("AAL", -1);
		//Exercise.6
		myPortfolio.removeStock("CAAS");
		return myPortfolio;
	}
}
