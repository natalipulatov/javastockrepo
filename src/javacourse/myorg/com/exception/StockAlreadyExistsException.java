package javacourse.myorg.com.exception;

import org.algo.exception.PortfolioException;
/** An instance of this class represents a stock already exists exception*/
public class StockAlreadyExistsException extends PortfolioException {
	public StockAlreadyExistsException(String  ifExists) {
		super("The stock has been already added");
	}

}
