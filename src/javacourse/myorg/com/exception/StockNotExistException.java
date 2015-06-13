package javacourse.myorg.com.exception;

import org.algo.exception.PortfolioException;
/** An instance of this class represents a stock not exist exception
 * Empty instance- if received null symbol of stock
 * String parameter- the stock not exist
 * Integer parameter- not correct quantity
 * Integer and string  parameter- not having quantity of stock to sell*/

public class StockNotExistException extends PortfolioException {
	public StockNotExistException() {
		super("Not correct symbol of stock was received");
	}
	public StockNotExistException(String stockSymbol) {
		super("The stock doesn't exist in the portfolio");
	}
	public StockNotExistException(int quantity) {
		super("Not correct quantity of stock was received");
	}
	public StockNotExistException(String stockSymbol, int quantity) {
		super("Not having stocks to sell");
	
    }
}
