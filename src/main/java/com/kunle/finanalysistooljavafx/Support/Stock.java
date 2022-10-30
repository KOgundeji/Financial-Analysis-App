package com.kunle.finanalysistooljavafx.Support;

public class Stock {
    final private String ticker, companyName, price, change_per, change_abs, shares, avg_cost, total_cost, market_value, gain, days_gain, return_per;

    public Stock(String ticker, String companyName, String price, String change_per, String change_abs,
                 String shares, String avg_cost, String total_cost, String market_value, String gain,
                 String days_gain, String return_per) {
        this.ticker = ticker;
        this.companyName = companyName;
        this.price = price;
        this.change_per = change_per;
        this.change_abs = change_abs;
        this.shares = shares;
        this.avg_cost = avg_cost;
        this.total_cost = total_cost;
        this.market_value = market_value;
        this.gain = gain;
        this.days_gain = days_gain;
        this.return_per = return_per;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPrice() {
        return price;
    }

    public String getChange_per() {
        return change_per;
    }

    public String getChange_abs() {
        return change_abs;
    }

    public String getShares() {
        return shares;
    }

    public String getAvg_cost() {
        return avg_cost;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public String getMarket_value() {
        return market_value;
    }

    public String getGain() {
        return gain;
    }

    public String getDays_gain() {
        return days_gain;
    }

    public String getReturn_per() {
        return return_per;
    }
}
