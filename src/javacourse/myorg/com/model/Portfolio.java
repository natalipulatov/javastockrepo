package javacourse.myorg.com.model;
/** An instance if this class represents a portFolio of stocks
 * @author Natali
 * @since 08/05/2015
 * date 08/05/2015  */
public class Portfolio {
	
	private final static int MAX_PORTFOLIO_SIZE = 5;
	
	private String title;
	private Stock[] stocks;
	private int portfolioSize;
	
	/**This constructor creates a portFolio object*/	
	public Portfolio() {
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.portfolioSize = 0;
		this.title = null;
		}
	/**This constructor creates a portFolio object  
	 * @param gets title of portFolio and logical size of portFolio stocks */
	public Portfolio(String title, int portfolioSize) {
		this.title = title;
		this.portfolioSize=portfolioSize;
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		}
	/**The copy constructor copies given portFolio object by value 
	 * @param gets portFolio object */
	public Portfolio(Portfolio portfolio) {
		this(new String (portfolio.getTitle()), portfolio.getPortfolioSize());
		
		Stock[] copyied = portfolio.getStocks();
		for(int i = 0; i< this.portfolioSize; i++){
			this.stocks[i] = new Stock(copyied[i]);

		}
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
		return stocks;
	}
	public void setStocks(Stock[] stocks) {
		this.stocks = stocks;
	}
	/**The method enables to add a new stock to the portFolio 
	 * @param gets a stock and adds it to portFolio */
	public void addStock(Stock stock) {
		if(stock != null && portfolioSize < MAX_PORTFOLIO_SIZE){
			this.stocks[portfolioSize] = stock;
			portfolioSize++;
		}
		else {
			System.out.println("Sorry, portfolio is full, or the stock is null!");
		}
	}
	/**The method prints the stocks in the portFolio  
	 * @return stocks in the portFolio */
	public String getHtmlString() {
		String portfolioString = new String("<h1>" + getTitle() + "</h1>");
		
		for(int i = 0;  i < portfolioSize; i++)
		{
			portfolioString = portfolioString + this.stocks[i].getHtmlDescription() + "<br>";
		}
		return portfolioString;
	}
	
	/**The method removes the stock from the portFolio
	 * @param gets stock symbol */
	public void removeStock(String stockSymbol) {
		int index= findIndexBySymbol(stockSymbol);
		for(int i = index; i < this.portfolioSize; i++){
			this.stocks[i] = this.stocks[i+1];
		}
		this.portfolioSize--;
	}
	
	/**The method changes the stock bid
	 * @param gets stock symbol, a new bid value*/
	public void changeStockBid(String stockSymbol, float newBid) {
		this.stocks[findIndexBySymbol(stockSymbol)].setBid(newBid);
	}
	/**The method gets stock symbol and finds its' index in the stocks array.
	 * This method is made for inner use
	 * @param gets stock symbol
	 * @return index of stock symbol in the stocks array */
	private int findIndexBySymbol(String stockSymbol){
		int index;
		if(stockSymbol.equals(null)){
			System.out.print("Not correct name of stock was received");
		}
		for(index = 0; index< this.portfolioSize; index++){
			if(this.stocks[index].symbol.equals(stockSymbol)){
				break;
			}
		}
		if(index == this.portfolioSize){
			System.out.print("Not correct name of stock was received");
		}
		return index;
	}	
}
