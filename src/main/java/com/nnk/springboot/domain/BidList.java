package com.nnk.springboot.domain;

import java.security.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "bidlist")
public class BidList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer BidListId;

	@NotNull
	@NotBlank(message = "Account is mandatory")
	@Size(min = 1, max = 30, message = "Account must be between 1 and 30 characters")
	private String account;

	@NotNull
	@NotBlank(message = "Type is mandatory")
	@Size(min = 1, max = 30, message = "Type must be between 1 and 30 characters")
	private String type;

	private Double bidQuantity;

	private Double askQuantity;

	private Double bid;

	private Double ask;

	@Size(max = 125, message = "Benchmark must be less than 125 characters")
	private String benchmark;

	private Timestamp bidListDate;

	@Size(max = 125, message = "Commentary must be less than 125 characters")
	private String commentary;

	@Size(max = 125, message = "Security must be less than 125 characters")
	private String security;

	@Size(max = 10, message = "Status must be less than 10 characters")
	private String status;

	@Size(max = 125, message = "Trader must be less than 125 characters")
	private String trader;

	@Size(max = 125, message = "Book must be less than 125 characters")
	private String book;

	@Size(max = 125, message = "Creation Name must be less than 125 characters")
	private String creationName;

	private Timestamp creationDate;

	@Size(max = 125, message = "Revision Name must be less than 125 characters")
	private String revisionName;

	private Timestamp revisionDate;

	@Size(max = 125, message = "Deal Name must be less than 125 characters")
	private String dealName;

	@Size(max = 125, message = "Deal Type must be less than 125 characters")
	private String dealType;

	@Size(max = 125, message = "Source List Id must be less than 125 characters")
	private String sourceListId;

	@Size(max = 125, message = "Side must be less than 125 characters")
	private String side;

	public Integer getBidListId() {
		return BidListId;
	}

	public void setBidListId(Integer bidListId) {
		BidListId = bidListId;
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

	public Double getBidQuantity() {
		return bidQuantity;
	}

	public void setBidQuantity(Double bidQuantity) {
		this.bidQuantity = bidQuantity;
	}

	public Double getAskQuantity() {
		return askQuantity;
	}

	public void setAskQuantity(Double askQuantity) {
		this.askQuantity = askQuantity;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public Timestamp getBidListDate() {
		return bidListDate;
	}

	public void setBidListDate(Timestamp bidListDate) {
		this.bidListDate = bidListDate;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
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
