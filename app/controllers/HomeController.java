package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.*;
import sun.awt.FwDispatcher;
import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	/**
	 * An action that renders an HTML page with a welcome message.
	 * The configuration in the <code>routes</code> file means that
	 * this method will be called when the application receives a
	 * <code>GET</code> request with a path of <code>/</code>.
	 */

	private WSClient ws;
	private static final String fileName = "/home/ec2-user/java-play-projects/kiss-files/skudetails.csv";
	//private static final String fileName = "/Users/shyamrajsampath/Documents/work/java-play-workspace/kiss-jewls/skudetails.csv";

	@Inject
	private FormFactory formFactory;

	@Inject
	private HomeController(WSClient ws) 
	{
		this.ws = ws;
	}

	public Result getHome()
	{
		return ok(views.html.home.render());
	}

	public Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public Result getAPIResponse() throws IOException
	{
		String apiEndPoint = "http://api.lkn.hk/product.aspx?sku=";
		DynamicForm requestData = formFactory.form().bindFromRequest();
		String skuIds = requestData.get("sku");
		Map<String, String> apiResponse;
		List<Map<String, String>> apiResponses = new ArrayList<Map<String, String>>();
		FileWriter fw;
		File newFile;

		String[] skus = skuIds.split(",");

		for(String sku : skus)
		{
			String url = apiEndPoint + sku;
			apiResponse = processGetRequest(url);
			apiResponses.add(apiResponse);
		}

		String[] header = new String[] { "Category", "ProductName", "SKU", "Weight", "Price",
				"Stock", "Description", "Detail", "Images1", "Images2", "Images3", "Images4", "Images5"
				, "Images6", "Images7", "Images8", "Images9", "Images10"};

		ICsvMapWriter mapWriter = null;
		try 
		{

			newFile = new File(fileName);
			if(newFile.exists())
			{
				newFile.delete();
			}

			fw = new FileWriter(fileName);
			mapWriter = new CsvMapWriter(fw, CsvPreference.STANDARD_PREFERENCE);


			// write the header
			mapWriter.writeHeader(header);

			for(Map<String, String> row : apiResponses)
			{
				mapWriter.write(row, header);
			}

		}
		catch(Exception ex)
		{
			System.out.println("Exception Occured : " + ex.getMessage());
		}
		finally 
		{
			if( mapWriter != null ) 
			{
				mapWriter.close();
			}
		}

		return redirect("downloadfile");
	}

	private Map<String, String> processGetRequest(String url)
	{
		String jsonString = "";
		Map<String, String> fieldList = new HashMap<String, String>();

		try
		{
			WSRequest request = ws.url(url);
			int imageCounter = 1;
			CompletionStage<WSResponse> responsePromise = request.get();
			CompletionStage<String> jsonPromise = responsePromise.thenApply(response -> response.getBody());
			jsonString = jsonPromise.toCompletableFuture().get();
			String[] fields = jsonString.split(",");

			for(String field : fields)
			{
				String[] keyAndValue = field.split("\":\"");
				String key = keyAndValue[0];
				String value = keyAndValue[1];
				key = key.replace("\"", "").replace("{", "");
				value = value.replace("\"", "").replace("}", "");
				
				if(key.equalsIgnoreCase("Images"))
				{
					String[] images = value.split(";");
					for(String image : images)
					{
						if(imageCounter < 11)
						{
							fieldList.put(key + imageCounter, image);
							imageCounter = imageCounter + 1;
						}
					}
				}
				else
				{
					fieldList.put(key, value);
				}
				
				
			}
		}
		catch(Exception ex)
		{
			Logger.error("An Exceptation Occured During Get Request : " + ex.getMessage());
		}

		return fieldList;
	}

	public Result getFile()
	{
		return ok(new File(fileName));
	}

}
