package javacourse.myorg.com.model;

public class Portfolio {
	
	private final static int MAX_PORTFOLIO_SIZE = 5;
	
	private String title;
	private Stock[] stocks;
	private int portfolioSize;
	//constructor	
	public Portfolio() {
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.portfolioSize = 0;
		this.title = null;
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

	public void addStock(Stock stock) {
		if(stock != null && portfolioSize < MAX_PORTFOLIO_SIZE){
			this.stocks[portfolioSize] = stock;
			portfolioSize++;
		}
		else {
			System.out.println("Sorry, portfolio is full, or the stock is null!");
		}
	}
	
	public String getHtmlString() {
		String portfolioString = new String("<h1>" + getTitle() + "</h1>");
		
		for(int i = 0;  i < portfolioSize; i++)
		{
			portfolioString = portfolioString + this.stocks[i].getHtmlDescription() + "<br>";
		}
		return portfolioString;
	}

}
