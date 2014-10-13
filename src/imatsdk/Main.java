package imatsdk;

import java.io.IOException;

import org.json.JSONException;

public class Main {

	private static String toolID = "[TOOL ID here]";
	private static String apiCode = "[API CODE here]";
	
	public static void main(String[] args) throws IOException, JSONException {
		
		//CartItemApi c = new CartItemApi(toolID, apiCode);
		//c.registerCartItem();
		
		UploadModelApi uploadModelApi = new UploadModelApi(toolID);
		uploadModelApi.uploadModel("[FilePath here]", "mm");

	}
}
