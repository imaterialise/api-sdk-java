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



public class UploadModelApi 
{	
	private String toolID;
	
	public UploadModelApi(String toolId)
	{
		this.toolID = toolId;
	}
		
	
	public void uploadModel(String filePath, String fileUnits) throws IOException, JSONException {

		HttpPost post = new HttpPost("https://imatsandbox.materialise.net/web-api/tool/"+this.toolID+"/model");
		post.addHeader("Accept", "text/json");
		String boundaryId = UUID.randomUUID().toString();
		post.addHeader("Content-Type", "multipart/form-data; boundary=" + boundaryId);
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setBoundary(boundaryId);
		
		File file = new File(filePath);
	    builder.addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
		
		builder.addTextBody("fileUnits", fileUnits, ContentType.TEXT_HTML);
		post.setEntity(builder.build());
		
		HttpClient hclient = new DefaultHttpClient();		
		HttpResponse resp = hclient.execute(post);
		
		System.out.println("response:" + IOUtils.toString(resp.getEntity().getContent()));
	}
}