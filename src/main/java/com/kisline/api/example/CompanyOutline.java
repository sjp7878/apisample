package com.kisline.api.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kisline.api.example.CompanyOutlineObject;

public class CompanyOutline {
	private static final Logger LOGGER = LogManager.getLogger("CompanyOutline");

	private static final String UID = "발급해 드린 UID를 사용하세요.";
	private static final String CLIENTID = "발급해 드린 clientId를 사용하세요.";
	private static final String CLIENTSECRET = "발급해 드린 clientSecret을 사용하세요.";

	// XML 또는 JSON으로 응답을 선택합니다.
	private static final String RESPONSETYPE = "application/json";
	// private static final String RESPONSETYPE = "application/xml";

	private static final String APIBASEURL = "https://api.kisline.com/nice/sb/api";
	private static final String APIURL = "/companyOutlineIfo/companyOutline";

	public static void main(String[] args) {

		try {

			Map<String, String> paramMap = new HashMap<>();
			StringBuilder sb = new StringBuilder();
			String key = "";
			String value = "";
			String json = "";

			Gson gson = new Gson();
			JsonParser parser = new JsonParser();

			paramMap.put("kiscode", "380725");

			for (Iterator<String> it = paramMap.keySet().iterator(); it.hasNext();) {
				key = String.valueOf(it.next());
				value = String.valueOf(paramMap.get(key));
				sb.append("&");
				sb.append(key);
				sb.append("=");
				sb.append(value);
			}

			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder().url(APIBASEURL + APIURL + "?uid=" + UID + sb).get()
					.addHeader("x-ibm-client-id", CLIENTID).addHeader("x-ibm-client-secret", CLIENTSECRET)
					.addHeader("accept", RESPONSETYPE).build();

			Response response = client.newCall(request).execute();

			json = response.body().string();

			LOGGER.info(response.headers().toString());
			LOGGER.info(json);

			JsonElement element = parser.parse(json).getAsJsonObject().get("items").getAsJsonObject().get("item");

			CompanyOutlineObject[] example = gson.fromJson(element, CompanyOutlineObject[].class);

			for (CompanyOutlineObject temp : example) {
				LOGGER.info("bizno = " + temp.getBizno());
				LOGGER.info("opt_entrnm = " + temp.getOpt_entrnm());
			}
		} catch (IOException ioe) {
			LOGGER.error(ioe);
		}
	}

}
