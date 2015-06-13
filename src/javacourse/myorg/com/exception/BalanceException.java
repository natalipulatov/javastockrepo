package javacourse.myorg.com.exception;

import org.algo.exception.PortfolioException;

/** An instance of this class represents a balance exceptions
 * Empty instance- not enough balance
 * Integer parameter- advise to add more money
 * Float parameter- balance can't be negative
 * @author Natali  */
public class BalanceException extends PortfolioException{
	public BalanceException() {
		super("Not enough balance to complete purchase");
	}
	public BalanceException(int amount) {
		super("Not enough money to buy stock, you have to add some money to your balance");
	}
	public BalanceException(float amount) {
		super("You can not withdraw this amount because the acount balance can not be negative");
	}
}
