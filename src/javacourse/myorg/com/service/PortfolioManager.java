package javacourse.myorg.com.service;

import java.util.Calendar;
import java.util.Date;

import org.algo.dto.PortfolioTotalStatus;
import org.algo.exception.PortfolioException;
import org.algo.model.PortfolioInterface;
import org.algo.service.PortfolioManagerInterface;

import javacourse.myorg.com.model.Portfolio;
import javacourse.myorg.com.model.Stock;
/** An instance if this class represents portFolio manger that managing the portFolio object 
 * @author Natali */
public class PortfolioManager implements PortfolioManagerInterface {
	/**The method creates new portFolio.
	 * @return new portFolio of stocks */
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PortfolioInterface getPortfolio() {
		// TODO Auto-generated method stub
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

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBalance(float value) throws PortfolioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PortfolioTotalStatus[] getPortfolioTotalStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStock(String symbol) throws PortfolioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyStock(String symbol, int quantity) throws PortfolioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sellStock(String symbol, int quantity)
			throws PortfolioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeStock(String symbol) throws PortfolioException {
		// TODO Auto-generated method stub
		
	}
}
