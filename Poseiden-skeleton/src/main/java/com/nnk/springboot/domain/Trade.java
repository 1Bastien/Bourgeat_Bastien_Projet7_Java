package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "trade")
public class Trade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer tradeId;

	@NotNull
	@Size(max = 30, message = "Account is limited to 30 characters")
	private String account;

	@NotNull
	@Size(max = 30, message = "Type is limited to 30 characters")
	private String type;

	private Double buyQuantity;

	private Double sellQuantity;

	private Double buyPrice;

	private Double sellPrice;

	private String benchmark;

	private Timestamp tradeDate;

	@Size(max = 125, message = "Security is limited to 125 characters")
	private String security;

	@Size(max = 10, message = "Status is limited to 10 characters")
	private String status;

	@Size(max = 125, message = "Trader is limited to 125 characters")
	private String trader;

	@Size(max = 125, message = "Book is limited to 125 characters")
	private String book;

	@Size(max = 125, message = "Creation Name is limited to 125 characters")
	private String creationName;

	private Timestamp creationDate;

	@Size(max = 125, message = "Revision Name is limited to 125 characters")
	private String revisionName;

	private Timestamp revisionDate;

	@Size(max = 125, message = "Deal Name is limited to 125 characters")
	private String dealName;

	@Size(max = 125, message = "Deal Type is limited to 125 characters")
	private String dealType;

	@Size(max = 125, message = "Source List Id is limited to 125 characters")
	private String sourceListId;

	@Size(max = 125, message = "Side is limited to 125 characters")
	private String side;

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Double buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public Double getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(Double sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public Timestamp getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Timestamp tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getRevisionName() {
		return revisionName;
	}

	public void setRevisionName(String revisionName) {
		this.revisionName = revisionName;
	}

	public Timestamp getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Timestamp revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getSourceListId() {
		return sourceListId;
	}

	public void setSourceListId(String sourceListId) {
		this.sourceListId = sourceListId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}
}
