package javacourse.myorg.com.exception;

import org.algo.exception.PortfolioException;
/** An instance of this class represents a portfolio full exception*/
public class PortfolioFullException extends PortfolioException{
	public PortfolioFullException(int  maxPortfolioSize) {
		super("Can't add new stock, portfolio can have only " + maxPortfolioSize + " stocks.");
	}
}
