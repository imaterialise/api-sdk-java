package imatsdk;

import java.io.IOException;

import org.json.JSONException;

public class Main {

	private static String toolID = "[TOOL ID HERE]";
	private static String apiCode = "[API CODE HERE]";
	
	public static void main(String[] args) throws IOException, JSONException {
		
		//UploadModelApi uploadModelApi = new UploadModelApi(toolID);
		//uploadModelApi.uploadModel("[PATH TO FILE HERE]", "mm");
		
		//CartItemApi cartItemApi = new CartItemApi(toolID, apiCode);
		//cartItemApi.registerCartItem("[CART ID HERE]");
		
		//PricingByDimensionsApi pricingByDimensionsApi = new PricingByDimensionsApi(toolID, apiCode);
		//pricingByDimensionsApi.calculatePrice();
		
		//PricingByModelApi pricingByModelApi = new PricingByModelApi(toolID, apiCode);
		//pricingByModelApi.calculatePrice("[MODELID HERE]");
		
		//CartRegistrationApi cartRegistrationApi = new CartRegistrationApi(toolID, apiCode);
		//cartRegistrationApi.registerCart("[CART ITEM ID HERE]");		
		
		//CartOrderingApi cartOrderingApi = new CartOrderingApi(apiCode);
		//cartOrderingApi.createOrder("[CART ID HERE]", ""[SHIPMENT SERVICE HERE]"", null, null);			
	}
}
