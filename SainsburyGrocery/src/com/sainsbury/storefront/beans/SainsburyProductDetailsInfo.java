package com.sainsbury.storefront.beans;

import org.apache.log4j.Logger;

import com.granule.json.JSONException;
import com.granule.json.OrderedJSONObject;


/**
 * Custom POJO class for storing the details of the Product info object and converting it to JSON
 * 
 * @author manushi.bedi
 *
 */

public class SainsburyProductDetailsInfo
{
	 /**
	  * Logger.
	  */
	 private static final Logger LOG = Logger.getLogger(SainsburyProductDetailsInfo.class);
	 private String title;
	 private float size;
	 private double unitPrice;
	 private String description;

	 public SainsburyProductDetailsInfo(String title, float size, double unitPrice, String description)
	 {
		  this.title = title;
		  this.size = size;
		  this.unitPrice = unitPrice;
		  this.description = description;
	 }

	 /**
	  * Method to generate JSONObject using the ProductInfo object. 
	  * OrderedJSONObject used to maintain the order in
	  * JSON as title, size, unit_price, desc
	  * 
	  * @return JSONObject containing all the fields of the ProductInfo object.
	  */
	 public OrderedJSONObject convertToJSON()
	 {
		  OrderedJSONObject returnContent = new OrderedJSONObject();
		  String htmSize = null;
		  htmSize = size + "kb";
		  try
		  {
				returnContent.put("title", title);
				returnContent.put("size", htmSize);
				returnContent.put("unit_price", unitPrice);
				returnContent.put("description", description);
		  }
		  catch (JSONException ex)
		  {
				LOG.error("Error while converting json" + ex);
		  }


		  return returnContent;
	 }

	 public String getTitle()
	 {
		  return title;
	 }

	 public void setTitle(String title)
	 {
		  this.title = title;
	 }

	 public float getSize()
	 {
		  return size;
	 }

	 public void setSize(float size)
	 {
		  this.size = size;
	 }

	 public double getUnitPrice()
	 {
		  return unitPrice;
	 }

	 public void setUnitPrice(double unitPrice)
	 {
		  this.unitPrice = unitPrice;
	 }

	 public String getDescription()
	 {
		  return description;
	 }

	 public void setDescription(String description)
	 {
		  this.description = description;
	 }


}
