package javacourse.myorg.com.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.algo.dto.PortfolioDto;
import org.algo.dto.PortfolioTotalStatus;
import org.algo.dto.StockDto;
import org.algo.exception.PortfolioException;
import org.algo.exception.SymbolNotFoundInNasdaq;
import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;
import org.algo.service.MarketService;
import org.algo.service.PortfolioManagerInterface;
import org.algo.service.ServiceManager;
import org.algo.service.DatastoreService;

import javacourse.myorg.com.exception.BalanceException;
import javacourse.myorg.com.exception.PortfolioFullException;
import javacourse.myorg.com.exception.StockAlreadyExistsException;
import javacourse.myorg.com.exception.StockNotExistException;
import javacourse.myorg.com.model.Portfolio;
import javacourse.myorg.com.model.Portfolio.ALGO_RECOMMENDATION;
import javacourse.myorg.com.model.Stock;
//import javacourse.myorg.com.exception.BalanceException;

/** An instance if this class represents portFolio manger that managing the portFolio object 
 * @author Natali */
public class PortfolioManager implements PortfolioManagerInterface {
	private DatastoreService datastoreService = ServiceManager.datastoreService();
	
	/**The method creates new portFolio.
	 * @return new portFolio of stocks */
	@Override

	public PortfolioInterface getPortfolio() {
		PortfolioDto portfolioDto =  datastoreService.getPortfolilo();
		return fromDto(portfolioDto);
	}
	@Override
	public void update() {
		StockInterface[] stocks = getPortfolio().getStocks();
		List<String> symbols = new ArrayList<>(Portfolio.getMaxSize());
		for (StockInterface si : stocks) {
			symbols.add(si.getSymbol());
		}

		List<Stock> update = new ArrayList<>(Portfolio.getMaxSize());
		List<Stock> currentStocksList = new ArrayList<Stock>();
		try {
			List<StockDto> stocksList = MarketService.getInstance().getStocks(symbols);
			for (StockDto stockDto : stocksList) {
				Stock stock = fromDto(stockDto);			
				currentStocksList.add(stock);
			}

			for (Stock stock : currentStocksList) {
				update.add(new Stock(stock));
			}

			 datastoreService.saveToDataStore(toDtoList(update));

		} catch (SymbolNotFoundInNasdaq e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * get portfolio totals
	 */
	@Override
	public PortfolioTotalStatus[] getPortfolioTotalStatus () {

		Portfolio portfolio = (Portfolio) getPortfolio();
		Map<Date, Float> map = new HashMap<>();

		//get stock status from db.
		StockInterface[] stocks = portfolio.getStocks();
		for (int i = 0; i < stocks.length; i++) {
			StockInterface stock = stocks[i];

			if(stock != null) {
				List<StockDto> stockHistory = null;
				try {
					stockHistory =  datastoreService.getStockHistory(stock.getSymbol(),30);
				} catch (Exception e) {
					return null;
				}
				for (StockDto stockDto : stockHistory) {
					Stock stockStatus = fromDto(stockDto);
					float value = stockStatus.getBid()*stockStatus.getStockQuantity();

					Date date = stockStatus.getDate();
					Float total = map.get(date);
					if(total == null) {
						total = value;
					}else {
						total += value;
					}

					map.put(date, value);
				}
			}
		}

		PortfolioTotalStatus[] ret = new PortfolioTotalStatus[map.size()];

		int index = 0;
		//create dto objects
		for (Date date : map.keySet()) {
			ret[index] = new PortfolioTotalStatus(date, map.get(date));
			index++;
		}

		//sort by date ascending.
		Arrays.sort(ret);

		return ret;
	}
	/**
	 * Add stock to portfolio 
	 * @throws StockNotExistException 
	 */
	@Override
	public void addStock(String symbol) throws PortfolioFullException, StockAlreadyExistsException, StockNotExistException{
		Portfolio portfolio = (Portfolio) getPortfolio();
		try 
		{
			StockDto stockDto = ServiceManager.marketService().getStock(symbol);
			
			//get current symbol values from nasdaq.
			Stock stock = fromDto(stockDto);
			
			//first thing, add it to portfolio.
			try {
				portfolio.addStock(stock);
			}catch(PortfolioFullException e) {
				System.out.println(e.getMessage());
				throw e;
			}catch(StockAlreadyExistsException e) {
				System.out.println(e.getMessage());
				throw e;
			}
			//second thing, save the new stock to the database.
			try{
			datastoreService.saveStock(toDto(portfolio.findStock(symbol)));
			}catch(StockNotExistException e) {
				System.out.println(e.getMessage());
				throw e;
			}
			
			flush(portfolio);
		} 
		catch (SymbolNotFoundInNasdaq e) 
		{
			System.out.println("Stock Not Exists: "+symbol);
		}
	}

	/**
	 * Set portfolio title
	 */
	@Override
	public void setTitle(String title) {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.setTitle(title);
		flush(portfolio);
	}
	/**
	 * update portfolio balance
	 */
	public void updateBalance(float value) throws BalanceException{ 
		Portfolio portfolio = (Portfolio) getPortfolio();
		try {
			portfolio.updateBalance(value);
		}catch(BalanceException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		flush(portfolio);
	}
	
	/**
	 * Buy stock
	 */
	@Override
	public void buyStock(String symbol, int quantity) throws PortfolioException, BalanceException, PortfolioFullException, StockAlreadyExistsException, StockNotExistException{
		try 
		{
			Portfolio portfolio = (Portfolio) getPortfolio();
			
			Stock stock = (Stock) portfolio.findStock(symbol);
			if(stock == null) 
			{
				stock = fromDto(ServiceManager.marketService().getStock(symbol));				
			}
			portfolio.buyStock(stock, quantity);
			flush(portfolio);
		}
		catch(PortfolioFullException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		catch(BalanceException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		catch(StockAlreadyExistsException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		catch(StockNotExistException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		catch (Exception e) 
		{
			System.out.println("Exception: "+e);
		}
	}

	/**
	 * Sell stock
	 */
	@Override
	public void sellStock(String symbol, int quantity) throws StockNotExistException {
		Portfolio portfolio = (Portfolio) getPortfolio();
		try{
		portfolio.sellStock(symbol, quantity);
		}catch(StockNotExistException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		flush(portfolio);
	}
	

	/**
	 * Remove stock
	 * @throws StockNotExistException 
	 */
	@Override
	public void removeStock(String symbol) throws StockNotExistException { 
		Portfolio portfolio = (Portfolio) getPortfolio();
		try{
			portfolio.removeStock(symbol);
			}catch(StockNotExistException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		flush(portfolio);
	}

	/**
	 * update database with new portfolio's data
	 * @param portfolio
	 */
	private void flush(Portfolio portfolio) {
		datastoreService.updatePortfolio(toDto(portfolio));
	}

	/**
	 * fromDto - get stock from Data Transfer Object
	 * @param stockDto
	 * @return Stock
	 */
	private Stock fromDto(StockDto stockDto) 
	{
		Stock newStock = new Stock();

		newStock.setSymbol(stockDto.getSymbol());
		newStock.setAsk(stockDto.getAsk());
		newStock.setBid(stockDto.getBid());
		newStock.setDate(stockDto.getDate().getTime());
		newStock.setStockQuantity(stockDto.getQuantity());
		if(stockDto.getRecommendation() != null) 
		{
			newStock.setRecommendation(Portfolio.ALGO_RECOMMENDATION.valueOf(stockDto.getRecommendation()));
		}
		
		else/*TODO : take care of recommendation's return value*/
		{
			newStock.setRecommendation(Portfolio.ALGO_RECOMMENDATION.valueOf("HOLD"));
		}

		return newStock;
	}

	/**
	 * toDto - covert Stock to Stock DTO
	 * @param inStock
	 */
	private StockDto toDto(StockInterface inStock) 
	{
		if (inStock == null) 
		{
			return null;
		}
		
		Stock stock = (Stock) inStock;
		return new StockDto(stock.getSymbol(), stock.getAsk(), stock.getBid(), 
				stock.getDate(), stock.getStockQuantity(), stock.getRecommendation().name());
	}

	/**
	 * toDto - converts Portfolio to Portfolio DTO
	 * @param portfolio
	 * @return
	 */
	private PortfolioDto toDto(Portfolio portfolio) {
		StockDto[] array = null;
		StockInterface[] stocks = portfolio.getStocks();
		if(stocks != null) {
			array = new StockDto[stocks.length];
			for (int i = 0; i < stocks.length; i++) {
				array[i] = toDto(stocks[i]);
			}
		}
		return new PortfolioDto(portfolio.getTitle(), portfolio.getBalance(), array);
	}

	/**
	 * fromDto - converts portfolioDto to Portfolio
	 * @param dto
	 * @return portfolio
	 */
	private Portfolio fromDto(PortfolioDto dto) {
		StockDto[] stocks = dto.getStocks();
		Portfolio ret;
		if(stocks == null) {
			ret = new Portfolio();			
		}else {
			List<Stock> stockList = new ArrayList<Stock>();
			for (StockDto stockDto : stocks) {
				stockList.add(fromDto(stockDto));
			}

			Stock[] stockArray = stockList.toArray(new Stock[stockList.size()]);
			ret = new Portfolio(stockArray);
		}

		ret.setTitle(dto.getTitle());
		try {
			ret.updateBalance(dto.getBalance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}	


	/**
	 * toDtoList - convert List of Stocks to list of Stock DTO
	 * @param stocks
	 * @return stockDto
	 */
	private List<StockDto> toDtoList(List<Stock> stocks) {

		List<StockDto> ret = new ArrayList<StockDto>();

		for (Stock stockStatus : stocks) {
			ret.add(toDto(stockStatus));
		}

		return ret;
	}
	/**
	 * A method that returns a new instance of Portfolio copied from another instance.
	 * @param portfolio		Portfolio to copy.
	 * @return a new Portfolio object with the same values as the one given.
	 */
	public Portfolio duplicatePortfolio(Portfolio portfolio) {
		Portfolio copyPortfolio = new Portfolio(portfolio);
		return copyPortfolio;
	}
}
