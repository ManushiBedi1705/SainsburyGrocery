package com.sainsbury.storefront.services;

import java.io.IOException;
import java.net.URL;

import org.json.simple.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.granule.json.JSONException;
import com.granule.json.OrderedJSONObject;
import com.sainsbury.storefront.beans.SainsburyProductDetailsInfo;


/**
 * Custom class parsing the HTML URL for Sainsbury, scrape the products Information as per requirement and conversion
 * into JSON.
 * 
 * @author manushi.bedi
 *
 */

public class SainsburyProductService
{
	 /**
	  * Logger.
	  */
	 private static final Logger LOG = Logger.getLogger(SainsburyProductService.class);

	 /**
	  * Parses the HTML using Jsoup makes connections and using
	  * <UL>
	  * and
	  * <LI>
	  * tags of HTML fetches the products, iterates over each of them and creates final JSON object to be dispalyed
	  * 
	  * @param URL
	  * @return String
	  */

	 @SuppressWarnings("unchecked")
	 public String parseHtml(URL url)
	 {
		  LOG.info("In parseHTML--SainsburyProductService");

		  String content = null;
		  OrderedJSONObject json = new OrderedJSONObject();
		  JSONArray results = new JSONArray();
		  // total unit price.
		  double total = 0.00;

		  Connection con = Jsoup.connect(url.toString());
		  if (con == null)
		  {
				// If we can't connect, we return an empty JSON document.
				return content;
		  }

		  try
		  {
				Element node = con.get().select("ul.productLister").first();
				if (node == null)
				{
					 // check to fetch if the products list is empty
					 return content;
				}

				//loop to iterate over products obtained on HTML
				Elements els = node.getElementsByTag("li");
				for (Element element : els)
				{
					 Element prdlink = element.select("div.productInfo").first();
					 Element prdanchor = prdlink.getElementsByTag("a").first();

					 String infoUrl = prdanchor.attr("href");
					 SainsburyProductDetailsInfo prdInfo = getProductInfo(infoUrl);

					 // Add JSON representation of the SainsburyProductDetailsInfo object to the array.
					 results.add(prdInfo.convertToJSON());
					 total += prdInfo.getUnitPrice();
				}
				json.put("results", results);
				json.put("total", Math.round(total * 100) / 100.0);
		  }
		  catch (JSONException ex)
		  {
				LOG.error("Error while converting json" + ex);
		  }

		  catch (IOException ex)
		  {
				Logger.getLogger(SainsburyProductService.class.getName()).log(Level.ERROR, null, ex);
		  }


		  //final JSON object to be displayed
		  return json.toString();
	 }

	 /**
	  * Using the div-productTitleDescriptionContainer fetches the products one by one and create attributes namely
	  * title, unit price, size of HTML and description as per the PDP links of the relevant products displayed
	  * 
	  * @param String
	  *            argUrl
	  * @return SainsburyProductDetailsInfo
	  */
	 public SainsburyProductDetailsInfo getProductInfo(String argUrl)
	 {

		  String title = "";
		  float size = 0.0f;
		  double unitPrice = 0.00;
		  String description = "";

		  LOG.info("In getProductInfo--SainsburyProductService");

		  try
		  {
				// converting the special characters in the PDP links used.
				String htmUrl = java.net.URLDecoder.decode(argUrl, "UTF-8");
				Document doc = Jsoup.connect(htmUrl).get();
				Element el = doc.select("div.productTitleDescriptionContainer").first();
				if (el == null)
				{
					 // no product found
					 return null;
				}
				else
				{
					 // get the product title
					 Element titleElement = el.getElementsByTag("h1").first();
					 title = titleElement.text();

					 // size of the web-page (in kb)
					 size = doc.toString().length() / 1024;
				}

				// get price per unit
				el = doc.select("p.pricePerUnit").first();
				if (el == null)
				{
					 return null;
				}
				else
				{
					 String ptxt = el.text();
					 ptxt = ptxt.replace("/unit", "");
					 ptxt = ptxt.replace("£", "");
					 unitPrice = Double.parseDouble(ptxt);
				}

				// get the description.
				el = doc.select("div.productText").first();
				if (el == null)
				{
					 return null;
				}
				else
				{
					 description = el.text();
				}
		  }
		  catch (IOException ex)
		  {
				Logger.getLogger(SainsburyProductService.class.getName()).log(Level.ERROR, null, ex);
		  }

		  return new SainsburyProductDetailsInfo(title, size, unitPrice, description);
	 }




}
