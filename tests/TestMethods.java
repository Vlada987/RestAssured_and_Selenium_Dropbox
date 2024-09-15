package tests;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Properties;
import com.fasterxml.jackson.core.JsonProcessingException;
import frontend.LoginPage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.CheckUser;
import pojos.CreateFolder;
import pojos.GetFolder;
import pojos.Photo;
import pojos.ProfilePhoto;
import pojos.Search;
import pojos.Upload;
import rest.Context;
import rest.EContentType;
import rest.Methods;
import restUtils.JacksonHelper;
import restUtils.UtilMethods;
import restUtils.myKeys;

public class TestMethods<T> extends BaseTest {

	static myKeys mykeys = new myKeys();

	public static void frontend_setCode() throws IOException {

		mykeys.code = LoginPage.get_code(giveProperty("appKey"), giveProperty("redirect_url"), giveProperty("email"),
				giveProperty("password"));

		System.out.println(mykeys.code);
	}

	public static Response get_BearerToken() throws IOException {

		Context context = new Context();
		context.URI = "/token";
		context.baseURL = "https://api.dropboxapi.com/oauth2";
		context.queryParams.put("grant_type", "authorization_code");
		context.queryParams.put("code", mykeys.code);
		context.queryParams.put("redirect_uri", giveProperty("redirect_url"));
		context.queryParams.put("client_id", giveProperty("appKey"));
		context.queryParams.put("client_secret", giveProperty("appSecret"));

		Response resp = Methods.POST(context);
		mykeys.access_token = resp.jsonPath().get("access_token").toString();
		return resp;
	}

	public static Response checkUser(String txt) throws JsonProcessingException {

		Context context = new Context();
		context.URI = "/user";
		context.baseURL = "https://api.dropboxapi.com/2/check";
		context.auth = () -> {
			return RestAssured.given().auth().oauth2(mykeys.access_token);
		};
		context.requestHeaderParams.put("Content-Type", "application/json");
		context.requestBodyString = JacksonHelper.ObjectToJson(new CheckUser(txt));

		return Methods.POST(context);
	}

	public static Response getFolder(String foldername) throws JsonProcessingException {

		foldername = foldername.startsWith("/") ? foldername : "/" + foldername;

		Context context = new Context();
		context.URI = "/list_folder";
		context.baseURL = "https://api.dropboxapi.com/2/files";
		context.auth = () -> {
			return RestAssured.given().auth().oauth2(mykeys.access_token);
		};
		context.requestHeaderParams.put("Content-Type", "application/json");
		context.requestBodyString = JacksonHelper
				.ObjectToJson(new GetFolder(false, false, false, true, true, foldername, false));

		return Methods.POST(context);

	}

	public static Response searchFiles(String foldername, String query) throws JsonProcessingException {

		foldername = foldername.startsWith("/") ? foldername : "/" + foldername;

		LinkedHashMap<String, Object> match_field_options = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> options = new LinkedHashMap<String, Object>();
		match_field_options.put("include_highlights", false);
		options.put("file_status", "active");
		options.put("filename_only", false);
		options.put("max_results", 20);
		options.put("path", foldername);

		Search se = new Search(match_field_options, options, query);

		Context context = new Context();
		context.URI = "/search_v2";
		context.baseURL = "https://api.dropboxapi.com/2/files";
		context.auth = () -> {
			return RestAssured.given().auth().oauth2(mykeys.access_token);
		};
		context.requestHeaderParams.put("Content-Type", "application/json");
		context.requestBodyString = JacksonHelper.ObjectToJson(se);

		return Methods.POST(context);

	}

	public static Response createFolder(String foldername) throws JsonProcessingException {

		foldername = foldername.startsWith("/") ? foldername : "/" + foldername;

		CreateFolder payload = new CreateFolder(false, foldername);

		Context context = new Context();
		context.URI = "/create_folder_v2";
		context.baseURL = "https://api.dropboxapi.com/2/files";
		context.auth = () -> {
			return RestAssured.given().auth().oauth2(mykeys.access_token);
		};
		context.requestHeaderParams.put("Content-Type", "application/json");
		context.requestBodyString = JacksonHelper.ObjectToJson(payload);

		return Methods.POST(context);

	}

	public static Response uploadFile(String dropboxpath, String filepath) throws JsonProcessingException {

		Context context = new Context();
		context.baseURL = "https://content.dropboxapi.com/2/files";
		context.URI = "/upload";
		context.contentTypeCharset = false;
		context.auth = () -> {
			return RestAssured.given().auth().oauth2(mykeys.access_token);
		};
		context.requestHeaderParams.put("Dropbox-API-Arg",
				"{\"path\": \"" + dropboxpath + "\",\"mode\": \"add\",\"autorename\": true,\"mute\": false}");
		context.requestContentType = EContentType.CHARSET_STREAM;
		context.fileBody = new File(filepath);

		return Methods.POST(context);
	}

	public static Response viewFile(String path) throws JsonProcessingException {

		Context context = new Context();
		context.baseURL = "https://content.dropboxapi.com/2/files";
		context.URI = "/download";
		context.auth = () -> {
			return RestAssured.given().auth().oauth2(mykeys.access_token);
		};
		context.requestHeaderParams.put("Dropbox-API-Arg", "{\"path\": \"" + path + "\"}");

		return Methods.GET(context);

	}

	public static Response deleteFile(String path) throws JsonProcessingException {

		Context context = new Context();
		context.baseURL = "https://api.dropboxapi.com/2/files";
		context.URI = "/delete_v2";
		context.requestBodyString = "{\"path\": \"" + path + "\"}";
		context.requestContentType = EContentType.JSON;
		context.auth = () -> {
			return RestAssured.given().auth().oauth2(mykeys.access_token);
		};

		return Methods.POST(context);

	}

	public static Response setProfilePhoto(String path) throws IOException, JsonProcessingException {

		Photo p = new Photo("base64_data", UtilMethods.encodetoB64(path));
		ProfilePhoto pp = new ProfilePhoto(p);

		Context context = new Context();
		context.baseURL = "https://api.dropboxapi.com/2/account";
		context.URI = "/set_profile_photo";
		context.requestBodyString = JacksonHelper.ObjectToJson(pp);
		context.requestContentType = EContentType.JSON;
		context.auth = () -> {
			return RestAssured.given().auth().oauth2(mykeys.access_token);
		};

		return Methods.POST(context);

	}

}
