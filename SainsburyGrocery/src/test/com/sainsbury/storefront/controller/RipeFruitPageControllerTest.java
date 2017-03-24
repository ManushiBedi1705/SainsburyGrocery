package test.com.sainsbury.storefront.controller;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.sainsbury.storefront.controller.RipeFruitPageController;
import com.sainsbury.storefront.services.SainsburyProductService;


/**
 * Test class for controller for fetching and 
 * displaying the expected JSON after parsing HTML and its relevant content.
 * 
 * @author manushi.bedi
 *
 */
public class RipeFruitPageControllerTest
{
	 /**
	  * Logger.
	  */
	 private static final Logger LOG = Logger.getLogger(RipeFruitPageControllerTest.class);
	 private RipeFruitPageController ripeFruitPageController;
	 private ModelMap model;
	 private SainsburyProductService sainsburyProductService;

	 @BeforeClass
	 public static void init() throws Exception
	 {
		  // Log4J junit configuration.
		  BasicConfigurator.configure();
	 }


	 @Before
	 public void setUp()
	 {
		  //Initialise objects before calling
		  ripeFruitPageController = new RipeFruitPageController();
		  sainsburyProductService = new SainsburyProductService();
		  ripeFruitPageController.setSainsburyProductService(sainsburyProductService);
	 }

	 /**
	  * Test to check the normal work flow and fetching the correct JSON.
	  * 
	  * @return JSONObject containing all the fields of the ProductInfo object.
	  */
	 @Test
	 public void testJSONNotEmpty()
	 {
		  ModelAndView json = ripeFruitPageController.getProductInfo(model);
		  LOG.info("JSON obtained" + json.toString());
		  assertTrue(!json.isEmpty());
	 }

	 /**
	  * Test to check the if URL is not correct then JSON will be empty
	  * 
	  */
	 @Test
	 public void testJSONEmpty()
	 {
		  URL url = null;
		  try
		  {
				url = new URL("http://www.google.com");
				String json = sainsburyProductService.parseHtml(url);
				LOG.info("JSON obtained" + "" + json);
				assertNull(json);
		  }
		  catch (MalformedURLException e)
		  {
				LOG.error("The given web-address '" + url + "' is not valid.");
		  }

	 }

	 /**
	  * Test to check if the URL is null, expected will be MalformedURLException
	  * 
	  * @return JSONObject containing all the fields of the ProductInfo object.
	  */
	 @Test(expected = MalformedURLException.class)
	 public void testInvalidURL() throws MalformedURLException
	 {
		  URL url = new URL("");
		  sainsburyProductService.parseHtml(url);

	 }


}
