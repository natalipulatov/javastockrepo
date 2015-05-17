package javacourse.myorg.com.model;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.algo.model.StockInterface;

import javacourse.myorg.com.model.Portfolio.ALGO_RECOMMENDATION;
/** An instance if this class represents a stock
 * @author Natali  */
public class Stock implements StockInterface{
String symbol;
private float ask; 
private float bid;
private Date date;
private ALGO_RECOMMENDATION recommendation;
private int stockQuantity;
/**This constructor creates a stock object  
 * @param gets symbol of stock, ask, bid and date */
public Stock (String symbol, float ask, float bid, Date date) {
	super();
	this.symbol = symbol;
	this.ask = ask;
	this.bid = bid;
	this.date = date;
	this.stockQuantity = 0;
}
/**The copy constructor copies given stock object by value 
 * @param gets stock object */
public Stock(Stock stock){
	this(new String (stock.getSymbol()), stock.getAsk(), stock.getBid(), new Date(stock.getDate(). getTime() ));	
} 
//Adding to all members getters and setters
public String getSymbol() {
	return symbol;
}
public void setSymbol(String symbol) {
	this.symbol = symbol;
}
public float getAsk() {
	return ask;
}
public void setAsk(float ask) {
	this.ask = ask;
}
public float getBid() {
	return bid;
}
public void setBid(float bid) {
	this.bid = bid;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public ALGO_RECOMMENDATION getRecommendation() {
	return recommendation;
}
public void setRecommendation(ALGO_RECOMMENDATION recommendation) {
	this.recommendation = recommendation;
}
public int getStockQuantity() {
	return stockQuantity;
}
public void setStockQuantity(int stockQuantity) {
	this.stockQuantity = stockQuantity;
}

/**The method prints a stock  
 * @return stock's details(symbol, ask, bid and date) */
public String getHtmlDescription(){
	SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY");//Format date
	String stocksDetails = "<b>Stock symbol</b>: " +getSymbol()+","+"<b> ask</b>: "+getAsk()+","+"<b> bid</b>: "+getBid()+","+"<b> date</b>: "+df.format(getDate())+", "+"<b> quantity</b>: "+ getStockQuantity(); 
	return stocksDetails;
}
}