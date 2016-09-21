package lists;

public class StockList {

    private String stockCurrentName;
    private String stockCurrentPrice;
    private String stockImage;
    private String[] restPrices;

    public StockList(String stockCurrentName, String stockCurrentPrice, String stockImage, String[] restPrices) {
        this.stockCurrentName = stockCurrentName;
        this.stockCurrentPrice = stockCurrentPrice;
        this.stockImage = stockImage;
        this.restPrices = restPrices;
    }

    public String getStockCurrentName() {
        return stockCurrentName;
    }

    public void setStockCurrentName(String stockCurrentName) {
        this.stockCurrentName = stockCurrentName;
    }

    public String getStockCurrentPrice() {
        return stockCurrentPrice;
    }

    public void setStockCurrentPrice(String stockCurrentPrice) {
        this.stockCurrentPrice = stockCurrentPrice;
    }

    public String getStockImage() {
        return stockImage;
    }

    public void setStockImage(String stockImage) {
        this.stockImage = stockImage;
    }

    public String[] getRestPrices() {
        return restPrices;
    }

    public void setRestPrices(String[] restPrices) {
        this.restPrices = restPrices;
    }
}
