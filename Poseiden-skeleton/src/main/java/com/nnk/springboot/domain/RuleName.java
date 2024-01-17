package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "rulename")
public class RuleName {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Size(max = 125, message = "Name is too long (max = 125)")
	private String name;

	@Size(max = 125, message = "Description is too long (max = 125)")
	private String description;

	@Size(max = 125, message = "Json is too long (max = 125)")
	private String json;

	@Size(max = 512, message = "Template is too long (max = 512)")
	private String template;

	@Size(max = 125, message = "SqlStr is too long (max = 125)")
	private String sqlStr;

	@Size(max = 125, message = "SqlPart is too long (max = 125)")
	private String sqlPart;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getSqlPart() {
		return sqlPart;
	}

	public void setSqlPart(String sqlPart) {
		this.sqlPart = sqlPart;
	}
}
