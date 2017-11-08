package com.ben.smith.reader;

/**
 * Created by bensmith on 11/7/17.
 */
public class asset {

    public String name;
    public String title;
    public String cusip;
    public String excel_cusip;
    public String cash_value;
    public String num_shares;
    public String type;
    public String discretion;

    public asset(String name, String title, String cusip, String cash_value, String num_shares, String type, String discretion) {
        this.name = name;
        this.title = title;
        this.cusip = cusip;
        this.cash_value = cash_value;
        this.num_shares = num_shares;
        this.type = type;
        this.discretion = discretion;
    }

    public asset(String line) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCusip() {
        return cusip;
    }

    public void setCusip(String cusip) {
        this.cusip = cusip;
    }

    public String getExcel_cusip() {
        return excel_cusip;
    }

    public void setExcel_cusip(String excel_cusip) {
        this.excel_cusip = excel_cusip;
    }

    public String getCash_value() {
        return cash_value;
    }

    public void setCash_value(String cash_value) {
        this.cash_value = cash_value;
    }

    public String getNum_shares() {
        return num_shares;
    }

    public void setNum_shares(String num_shares) {
        this.num_shares = num_shares;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscretion() {
        return discretion;
    }

    public void setDiscretion(String discretion) {
        this.discretion = discretion;
    }
}
