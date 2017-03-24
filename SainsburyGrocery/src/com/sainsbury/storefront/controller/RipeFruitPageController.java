package com.sainsbury.storefront.controller;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sainsbury.storefront.services.SainsburyProductService;


/**
 * Custom controller for fetching and displaying the expected JSON after parsing HTML and its relevant content.
 * 
 * @author manushi.bedi
 *
 */

@Controller
@RequestMapping("/")
public class RipeFruitPageController
{

	 /**
	  * Logger.
	  */
	 private static final Logger LOG = Logger.getLogger(RipeFruitPageController.class);
	 /**
	  * sainsburyProductService
	  */
	 @Resource(name = "sainsburyProductService")
	 private SainsburyProductService sainsburyProductService;

	 /**
	  * Parses the HTML and converts the required attributes like title,unit price, desc, size into JSON format Returns
	  * the model view at home.jsp for displaying the contents of JSON returned.
	  * 
	  * @param ModelMap
	  * @return ModelAndView
	  */
	 @RequestMapping(method =
	 { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	 public ModelAndView getProductInfo(ModelMap model)
	 {
		  String jsonContent = null;
		  LOG.info("In ripe fruit controller");
		  String sainsUrl = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";

		  try
		  {
				URL url = new URL(sainsUrl);
				//method for parsing the HTML
				jsonContent = getSainsburyProductService().parseHtml(url);

		  }
		  catch (MalformedURLException ex)
		  {
				LOG.error("The given web-address '" + sainsUrl + "' is not valid");
		  }
		  //maps the JSON model obtained to the view--home.jsp
		  return new ModelAndView("home", "jsonContent", jsonContent);
	 }

	 public SainsburyProductService getSainsburyProductService()
	 {
		  return sainsburyProductService;
	 }

	 public void setSainsburyProductService(SainsburyProductService sainsburyProductService)
	 {
		  this.sainsburyProductService = sainsburyProductService;
	 }
}
