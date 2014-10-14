package imatsdk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class PricingByDimensionsApi {
	
	private String toolID;
	private String apiCode;
	
	public PricingByDimensionsApi(String toolId, String apiCode)
	{
		this.toolID = toolId;
		this.apiCode = apiCode;
	}
		
	
	private JSONObject getJson() throws JSONException {
		JSONObject j = new JSONObject();
		
		JSONObject itemJson = new JSONObject();
		itemJson.put("toolID", this.toolID);
		itemJson.put("modelReference", "my reference");
		itemJson.put("materialID", "035f4772-da8a-400b-8be4-2dd344b28ddb");
		itemJson.put("finishID", "bba2bebb-8895-4049-aeb0-ab651cee2597");
		itemJson.put("quantity", 1);
		itemJson.put("xDimMm", 1);
		itemJson.put("yDimMm", 1);
		itemJson.put("zDimMm", 1);
		itemJson.put("volumeCm3", 1);
		itemJson.put("surfaceCm2", 1);
		
		JSONObject shipmentJson = new JSONObject();
		shipmentJson.put("countryCode", "BE");
		shipmentJson.put("stateCode", "");
		shipmentJson.put("city", "Leuven");
		shipmentJson.put("zipCode", "3001");		
		
		List<JSONObject> itemList = new ArrayList<>();
		itemList.add(itemJson);
		
		j.put("models", itemList);
		j.put("shipmentInfo", shipmentJson);
		j.put("currency", "USD");
		
		return j;
	}
	
	public void calculatePrice() throws IOException, JSONException {

		HttpPost post = new HttpPost("https://imatsandbox.materialise.net/web-api/pricing");
		post.addHeader("Accept", "application/json");
		post.addHeader("Content-Type", "application/json");
		post.addHeader("APICode", this.apiCode);
		JSONObject json = getJson();
		
	
		EntityBuilder builder = EntityBuilder.create();
		builder.setText(json.toString());
		post.setEntity(builder.build());
		
		HttpClient hclient = new DefaultHttpClient();		
		HttpResponse resp = hclient.execute(post);
		
		System.out.println("response:" + IOUtils.toString(resp.getEntity().getContent()));
	}
}
