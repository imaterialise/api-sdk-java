package imatsdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import components.HttpDeleteWithBody;



public class CartRegistrationApi 
{	
	private String toolID;
	private String apiCode;
	 
	public CartRegistrationApi(String toolId, String apiCode)
	{
		this.toolID = toolId;
		this.apiCode = apiCode;
	}

	private JSONObject getJson(String cartItemId) throws JSONException {
		JSONObject cartJson = new JSONObject();
		
		cartJson.put("myCartReference", UUID.randomUUID().toString());
		cartJson.put("toolID", this.toolID);
		cartJson.put("returnUrl", "http://mysite.com/success.html");
		cartJson.put("orderConfirmationUrl", "http://mysite.com/confirm.html");
		cartJson.put("failureUrl", "http://mysite.com/failure.html");
		
		JSONObject itemJson = new JSONObject();	
		itemJson.put("cartItemID", cartItemId);
		List<JSONObject> itemList = new ArrayList<>();
		itemList.add(itemJson);
		
		cartJson.put("cartItems", itemList);
		cartJson.put("currency", "USD");
		
		JSONObject billingJson = new JSONObject();	
		billingJson.put("FirstName", "John");
		billingJson.put("LastName", "Smith");
		billingJson.put("Email", "test@test.com");
		billingJson.put("Phone", "1234567");
		billingJson.put("Company", "No company");
		billingJson.put("Line1", "North Street");
		billingJson.put("CountryCode", "US");
		billingJson.put("StateCode", "NY");
		billingJson.put("ZipCode", "10001");
		billingJson.put("City", "New York");
		cartJson.put("ShippingInfo", billingJson);
		
		JSONObject shippingJson = new JSONObject();	
		shippingJson.put("FirstName", "John");
		shippingJson.put("LastName", "Smith");
		shippingJson.put("Email", "test@test.com");
		shippingJson.put("Phone", "1234567");
		shippingJson.put("Company", "No company");
		shippingJson.put("Line1", "North Street");
		shippingJson.put("CountryCode", "US");
		shippingJson.put("StateCode", "NY");
		shippingJson.put("ZipCode", "10001");
		shippingJson.put("City", "New York");
		shippingJson.put("VatNumber", "BE0999999922");
		cartJson.put("BillingInfo", shippingJson);		
		
		return cartJson;
	}
	
	
	public void registerCart(String cartItemId) throws IOException, JSONException {

		HttpPost post = new HttpPost("https://imatsandbox.materialise.net/web-api/cart/post");
		post.addHeader("Accept", "application/json");
		post.addHeader("Content-Type", "application/json");
		post.addHeader("APICode", this.apiCode);
		JSONObject json = getJson(cartItemId);
		
	
		EntityBuilder builder = EntityBuilder.create();
		builder.setText(json.toString());
		post.setEntity(builder.build());
		
		HttpClient hclient = new DefaultHttpClient();		
		HttpResponse resp = hclient.execute(post);
		
		System.out.println("response:" + IOUtils.toString(resp.getEntity().getContent()));
	}
	
	
	public void addCartItemToCart(String cartItemId, String cartId) throws IOException, JSONException {
		JSONObject cartItemsJson = new JSONObject();
		JSONObject itemJson = new JSONObject();
		itemJson.put("CartItemID", cartItemId);
		
		List<JSONObject> itemList = new ArrayList<>();
		itemList.add(itemJson);
		cartItemsJson.put("cartItems", itemList);

		HttpPost post = new HttpPost("https://imatsandbox.materialise.net/web-api/cart/" + cartId + "/items");
		post.addHeader("Accept", "application/json");
		post.addHeader("Content-Type", "application/json");
		
		EntityBuilder builder = EntityBuilder.create();
		builder.setText(cartItemsJson.toString());
		post.setEntity(builder.build());
		
		HttpClient hclient = new DefaultHttpClient();		
		HttpResponse resp = hclient.execute(post);
		
		System.out.println("response:" + IOUtils.toString(resp.getEntity().getContent()));
	}	
	
	public void removeCartItemFromCart(String cartItemId, String cartId) throws IOException, JSONException {
		JSONObject cartItemsJson = new JSONObject();
		JSONObject itemJson = new JSONObject();
		itemJson.put("CartItemID", cartItemId);
		
		List<JSONObject> itemList = new ArrayList<>();
		itemList.add(itemJson);
		cartItemsJson.put("cartItems", itemList);

		HttpDeleteWithBody httpDelete  = new HttpDeleteWithBody("https://imatsandbox.materialise.net/web-api/cart/" + cartId + "/items");
		httpDelete.addHeader("Accept", "application/json");
		httpDelete.addHeader("Content-Type", "application/json");
		
		EntityBuilder builder = EntityBuilder.create();
		builder.setText(cartItemsJson.toString());
		httpDelete.setEntity(builder.build());
		
		HttpClient hclient = new DefaultHttpClient();		
		HttpResponse resp = hclient.execute(httpDelete);
		
		System.out.println("response:" + IOUtils.toString(resp.getEntity().getContent()));
	}		
}