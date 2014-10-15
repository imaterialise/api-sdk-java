package imatsdk;


import java.io.File;
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



public class CartOrderingApi 
{	
	private String apiCode;
	 
	public CartOrderingApi(String apiCode)
	{
		this.apiCode = apiCode;
	}

	private JSONObject getJson(String cartId, String shipmentService) throws JSONException {
		
		JSONObject itemJson = new JSONObject();
		itemJson.put("cartID", cartId);
		itemJson.put("myOrderReference", UUID.randomUUID().toString());
		itemJson.put("directMailingAllowed", false);
		itemJson.put("shipmentService", shipmentService);
		itemJson.put("myInvoiceLink", "");
		itemJson.put("myDeliveryNoteLink", "");
		itemJson.put("remarks", "");
		
		return itemJson;
	}
	
	
	public void createOrder(String cartId, String shipmentService, String invoiceFilePath, String deliveryNoteFilePath) throws IOException, JSONException {
		JSONObject json = getJson(cartId, shipmentService);

		HttpPost post = new HttpPost("https://imatsandbox.materialise.net/web-api/order/post");
		post.addHeader("ApiCode", this.apiCode);
		post.addHeader("Accept", "text/json");
		String boundaryId = UUID.randomUUID().toString();
		post.addHeader("Content-Type", "multipart/form-data; boundary=" + boundaryId);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setBoundary(boundaryId);
		builder.addTextBody("request", json.toString(), ContentType.APPLICATION_JSON);
		post.setEntity(builder.build());

		if(invoiceFilePath != null) 
		{
			File invoiceFile = new File(invoiceFilePath);
			builder.addBinaryBody("MyInvoiceFile", invoiceFile, ContentType.APPLICATION_OCTET_STREAM, invoiceFile.getName());
		}
		
		if(deliveryNoteFilePath != null)
		{
			File deliveryNoteFile = new File(deliveryNoteFilePath);
			builder.addBinaryBody("MyDeliveryNoteFile", deliveryNoteFile, ContentType.APPLICATION_OCTET_STREAM, deliveryNoteFile.getName());
		}
	    
		HttpClient hclient = new DefaultHttpClient();		
		HttpResponse resp = hclient.execute(post);
		
		System.out.println("response:" + IOUtils.toString(resp.getEntity().getContent()));
	}
}
