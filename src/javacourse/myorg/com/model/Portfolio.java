package javacourse.myorg.com.model;
import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;

/** An instance if this class represents a portFolio of stocks
 * @author Natali  */
public class Portfolio implements PortfolioInterface {
	
	private final static int MAX_PORTFOLIO_SIZE = 5;
	public enum ALGO_RECOMMENDATION{
		 BUY, SELL, REMOVE, HOLD;}
	
	private String title;
	private StockInterface[] stocks;
	private int portfolioSize;
	private float balance;
	
	/**This constructor creates a portFolio object*/	
	public Portfolio() {
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.portfolioSize = 0;
		this.title = null;
		this.balance = 0;
		}
	/**This constructor creates a portFolio object  
	 * @param gets title of portFolio, logical size of portFolio stocks and the balance*/
	public Portfolio(String title, int portfolioSize, float balance) {
		this.title = title;
		this.portfolioSize=portfolioSize;
		this.balance = balance;
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		}
	/**The copy constructor copies given portFolio object by value 
	 * @param gets portFolio object */
	public Portfolio(Portfolio portfolio) {
		this(new String (portfolio.getTitle()), portfolio.getPortfolioSize(), portfolio.getBalance());
		
		Stock[] copyied = portfolio.getStocks();
		for(int i = 0; i< this.portfolioSize; i++){
			this.stocks[i] = new Stock(copyied[i]);
		}
	}
	public Portfolio(Stock[] stockArray) {
		// TODO Auto-generated constructor stub
		Stock[] copyied = stockArray;
		for(int i = 0; i< stockArray.length; i++){
			this.stocks[i] = new Stock(copyied[i]);
			this.portfolioSize++;
		}
	}
	public float getBalance() {
		return balance;
	}
	public int getPortfolioSize() {
		return portfolioSize;
	}
	public void setPortfolioSize(int PortfolioSize) {
		this.portfolioSize = PortfolioSize;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Stock[] getStocks() {
		return (Stock[]) stocks;
	}
	public void setStocks(Stock[] stocks) {
		this.stocks = stocks;
	}
	/**The method enables to add a new stock to the portFolio 
	 * @param gets a stock and adds it to portFolio */
	public void addStock(Stock stock) {
		if(stock == null){
			System.out.print("Not correct stock was received");
		}
		else if(this.portfolioSize == MAX_PORTFOLIO_SIZE){
			System.out.println("Can't add new stock, portfolio can have only " + MAX_PORTFOLIO_SIZE + " stocks.");
		}
		else{
		int index = findIndexBySymbol(stock.symbol);
			if (index == this.portfolioSize){//if not exists
				this.stocks[this.portfolioSize] = stock;
				((Stock) this.stocks[this.portfolioSize]).setStockQuantity(0);
				this.portfolioSize++;
			}
			else{
				System.out.println("The stock has been already added");
			}
		}
	}
	/**The method prints the stocks in the portFolio  
	 * @return stocks in the portFolio */
	public String getHtmlString() {
		String portfolioString = new String("<h1>" + getTitle() + "</h1>"+"<br>"+ "<b>Total poertfolio value</b>: "+getTotalValue()+"$"+ ", "+"<b>Total stock value</b>: "+getStockValue()+"$"+ ", "+"<b>Balance</b>: "+getBalance()+"$" + "<br>");
		
		for(int i = 0;  i < portfolioSize; i++)
		{
			portfolioString = portfolioString + ((Stock) this.stocks[i]).getHtmlDescription() + "<br>";
		}
		return portfolioString;
	}

	/**The method removes the stock from the portFolio
	 * @param gets stock symbol */
	public void removeStock(String stockSymbol) {
		if(stockSymbol.equals(null)){
			System.out.print("Not correct symbol of stock was received");
		}
		else{
		int index= findIndexBySymbol(stockSymbol);
			if(index < this.portfolioSize){//exist
				sellStock(stockSymbol, -1);
				this.stocks[index] = this.stocks[this.portfolioSize-1];
				this.stocks[this.portfolioSize-1] = null;
				this.portfolioSize--;
			}
			else{
				System.out.print("Not correct symbol of stock was received");//if the stock symbol not exsits in the array
			}
	    }
	}
	/**The method enables to sell stock from the portFolio 
	 * @param gets stock symbol
	 * @return boolean- if sale succeeded so true, if failed returns false  */
	public boolean sellStock(String stockSymbol, int quantity){
		if(stockSymbol.equals(null)){
			System.out.print("Not correct symbol of stock was received");
			return false;
		}
		else if(quantity == 0 || quantity < -1){
			System.out.print("Not correct quantity of stock was received");
			return false;
		}
		else{
			int index= findIndexBySymbol(stockSymbol);
			if(index == this.portfolioSize){
				System.out.print("The stock doesn't exist in the portfolio");
				return false;
			}
			else if(((Stock) this.stocks[index]).getStockQuantity() == 0){
				System.out.print("Not having stocks to sell");
				return false;
			}
			else if(quantity > ((Stock) this.stocks[index]).getStockQuantity()){
				System.out.print("Not enough stocks to sell");
			}
			else if (quantity == -1){
				this.balance =this.balance + (((Stock) this.stocks[index]).getStockQuantity()* this.stocks[index].getBid());
				((Stock) this.stocks[index]).setStockQuantity(0);
				return true;
			}
			else{
				int stockQuantity = ((Stock) this.stocks[index]).getStockQuantity();
				this.balance =this.balance + (stockQuantity* this.stocks[index].getBid());
				((Stock) this.stocks[index]).setStockQuantity(stockQuantity - quantity);
				return true;
			}
		}
		return false;
	}
	/**The method enables to buy new or exists stock for the portFolio 
	 * @param gets stock and quantity
	 * @return boolean- if sale succeeded so true, if failed returns false*/
	public boolean buyStock(Stock stock, int quantity){
		if(stock == null){
			System.out.print("Not correct stock was received");
			return false;
		}
		else if(quantity == 0 || quantity < -1){
			System.out.print("Not correct quantity of stock was received");
			return false;
		}
		else if (this.balance == 0){
			System.out.print("Not money to buy stock, you have to add some money to your balance");
		}
		else{
			int index = findIndexBySymbol(stock.symbol);
			if(index == this.portfolioSize){//not exists
				addStock(stock);
				if(quantity == -1){
					int quantityOfStock = (int)(this.balance / stock.getAsk());
					if(quantityOfStock > 0){
						float tempBalnce = quantityOfStock*stock.getAsk();
						if(tempBalnce <= this.balance){
							updateBalance(-tempBalnce);
							stock.setStockQuantity(((Stock) this.stocks[index]).getStockQuantity() + quantityOfStock);
							return true;
						}
						else{
							System.out.print("Not enough balance to complete purchase");
							return false;
						}
					}
				}
				else{
				float tempCountOfSell = quantity * stock.getAsk(); 
					if(tempCountOfSell <= this.balance){
						updateBalance(-tempCountOfSell);
						stock.setStockQuantity(((Stock) this.stocks[index]).getStockQuantity() + quantity);
						return true;
					}
					else{
						System.out.print("Not enough balance to complete purchase");
						return false;
					}
				}
			}
			else{//if exists
				if(quantity == -1){
					int quantityOfStock = (int)(this.balance / stock.getAsk());
					if(quantityOfStock > 0){
						float tempBalnce = quantityOfStock * stock.getAsk();
						if(tempBalnce <= this.balance){
							updateBalance(-tempBalnce);
							stock.setStockQuantity(((Stock) this.stocks[index]).getStockQuantity() + quantityOfStock);
							return true;
						}
						else{
							System.out.print("Not enough balance to complete purchase");
							return false;
						}
					}
				}
				else{
				float tempCountOfSell = quantity * stock.getAsk(); 
					if(tempCountOfSell <= this.balance){
						updateBalance(-tempCountOfSell);
						stock.setStockQuantity(((Stock) this.stocks[index]).getStockQuantity() + quantity);
						return true;
					}
					else{
						System.out.print("Not enough balance to complete purchase");
						return false;
					}
				}
			}
		}
		return false;
	}
	/**The method shows the total value of all stocks
	 * @return float amount of all stocks*/
	public float getStockValue(){
		float totalValueOfStocksNotLiquid = 0;
		for(int i = 0; i < this.portfolioSize; i++){
			totalValueOfStocksNotLiquid = totalValueOfStocksNotLiquid + (((Stock) this.stocks[i]).getStockQuantity() * this.stocks[i].getBid());
		}
		return totalValueOfStocksNotLiquid;
	}
	/**The method shows the total value of portFolio
	 * @return float amount of all stocks*/
	public float getTotalValue(){
		float totalValueOfStocks = getStockValue() + getBalance();
		return totalValueOfStocks;
	}
	/**The method changes the stock bid
	 * @param gets stock symbol, a new bid value*/
	public void changeStockBid(String stockSymbol, float newBid) {
		if(stockSymbol.equals(null)){
			System.out.print("Not correct symbol of stock was received");
		}
		else{
		int index = findIndexBySymbol(stockSymbol);
			if(index == this.portfolioSize){//not exists
				System.out.print("Not correct symbol of stock was received");
			}
			else{
				((Stock) this.stocks[index]).setBid(newBid);
			}
		}
	}
	/**The method gets stock symbol and finds its' index in the stocks array.
	 * This method is made for inner use
	 * @param gets stock symbol
	 * @return index of stock symbol in the stocks array */
	private int findIndexBySymbol(String stockSymbol){
		int index = -2;
		if(stockSymbol.equals(null)){
			System.out.print("Not correct symbol of stock was received");
		}
		else if(this.portfolioSize == 0){
			return 0;
		}
		else{
			for(index = 0; index< this.portfolioSize; index++){
				if(this.stocks[index].getSymbol().equals(stockSymbol)){
				break;
				}
			}
		}
		return index;	
	}
	public Stock findStock(String stockSymbol){
		int index = 0;
		if(stockSymbol.equals(null)){
			System.out.print("Not correct symbol of stock was received");
			return null;
		}
		else if(this.portfolioSize == 0){
			return null;
		}
		else{
			for(index = 0; index < this.portfolioSize; index++){
				if(this.stocks[index].getSymbol().equals(stockSymbol)){
				break;
				}
			}
		}
		return (Stock) stocks[index];	
	}

	/**The method updates porFilios' balance.
	 * @param gets amount of money */
	public void updateBalance(float amount){
		float tempSumOfBalnce= this.balance + (amount);
		if(tempSumOfBalnce >= 0){
			this.balance = tempSumOfBalnce;
		}
		else{
			System.out.print("You can not withdraw this amount because the acount balance can not be negative");
		}
	}
	public static int getMaxSize() {
		// TODO Auto-generated method stub
		return MAX_PORTFOLIO_SIZE;
	}
	
	
}
