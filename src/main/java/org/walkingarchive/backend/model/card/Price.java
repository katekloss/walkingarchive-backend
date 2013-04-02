package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.util.Date;

public class Price
{
	private Integer id;
	private Set set;
	private BigDecimal price;
	private String source;
	private Date date;

	public Price() {}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer value)
	{
		id = value;
	}

	public Set getSet()
	{
		return set;
	}

	public void setSet(Set value)
	{
		set = value;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal value)
	{
		price = value;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String value)
	{
		source = value;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date value)
	{
		date = value;
	}
}