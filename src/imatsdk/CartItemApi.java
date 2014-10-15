package imatsdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;



public class CartItemApi 
{	
	private String toolID;
	private String apiCode;
	 
	public CartItemApi(String toolId, String apiCode)
	{
		this.toolID = toolId;
		this.apiCode = apiCode;
	}

	private JSONObject getJson(String modelId) throws JSONException {
		JSONObject j = new JSONObject();
		
		JSONObject itemJson = new JSONObject();
		itemJson.put("myCartItemReference", UUID.randomUUID().toString());
		itemJson.put("toolID", this.toolID);
		itemJson.put("modelID", modelId);
		itemJson.put("materialID", "035f4772-da8a-400b-8be4-2dd344b28ddb");
		itemJson.put("finishID", "bba2bebb-8895-4049-aeb0-ab651cee2597");
		itemJson.put("fileScaleFactor", 1);
		itemJson.put("fileUnits", "mm");
		itemJson.put("quantity", 1);
		itemJson.put("xDimMm", 1);
		itemJson.put("yDimMm", 1);
		itemJson.put("zDimMm", 1);
		itemJson.put("volumeCm3", 1);
		itemJson.put("surfaceCm2", 1);
		itemJson.put("iMatAPIPrice", 0);
		itemJson.put("mySalesPrice", 0);
		
		List<JSONObject> itemList = new ArrayList<>();
		itemList.add(itemJson);
		
		j.put("cartItems", itemList);
		j.put("currency", "USD");
		
		return j;
	}
	
	
	public void registerCartItem(String modelId) throws IOException, JSONException {
		JSONObject json = getJson(modelId);

		HttpPost post = new HttpPost("https://imatsandbox.materialise.net/web-api/cartitems/register");
		post.addHeader("ApiCode", this.apiCode);
		post.addHeader("Accept", "text/json");
		String boundaryId = UUID.randomUUID().toString();
		post.addHeader("Content-Type", "multipart/form-data; boundary=" + boundaryId);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setBoundary(boundaryId);
		builder.addTextBody("request", json.toString(), ContentType.APPLICATION_JSON);
		post.setEntity(builder.build());
		
		
		HttpClient hclient = new DefaultHttpClient();		
		HttpResponse resp = hclient.execute(post);
		
		System.out.println("response:" + IOUtils.toString(resp.getEntity().getContent()));
	}
}
