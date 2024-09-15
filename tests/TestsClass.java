package tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.fasterxml.jackson.core.JsonProcessingException;

import extentReports.Setup;
import io.restassured.response.Response;
import restUtils.UtilMethods;
import restUtils.myKeys;

public class TestsClass extends Setup{

	SoftAssert softassert = new SoftAssert();
	myKeys mykeys = new myKeys();
	
	

	@Test(priority = 0, groups = "positive", description = "obtaining bearer token by sending code and other data")
	public void test00_Get_Token() throws IOException {

		Response resp = TestMethods.get_BearerToken();

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertEquals(resp.jsonPath().getString("token_type"), "bearer");
		Assert.assertTrue(resp.jsonPath().getLong("expires_in") > 12000L);
		Assert.assertEquals(resp.getContentType(), "application/json");

	}

	@Test(priority = 1, groups = "positive", description = "test the current user and valitidy of token")
	public void test01_checkUser() throws JsonProcessingException {

		String someText = "test12092024";
		Response resp = TestMethods.checkUser(someText);

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertEquals(resp.jsonPath().getString("result"), someText);
		Assert.assertTrue(resp.getTime() < 3000L);
		Assert.assertEquals(resp.getContentType(), "application/json");
	}

	@Test(priority = 2, groups = "positive", description = "get list of files in folder")
	public void Test02_Get_Folder() throws JsonProcessingException {

		List<String> expectedFiles = Arrays.asList("mytestOne", "file01Estam.txt", "mydropbox.csv", "README.txt");
		Response resp = TestMethods.getFolder("estam");

		softassert.assertEquals(resp.getStatusCode(), 200);
		softassert.assertTrue(resp.jsonPath().getList("entries.name").equals(expectedFiles));
		softassert.assertTrue(resp.jsonPath().getList("entries.id").stream().allMatch(id -> id != null));

	}

	@Test(priority = 3, groups = "positive", description = "search for files and folders")
	public void Test_03_SearchFolders_and_Files() throws JsonProcessingException {

		Response resp = TestMethods.searchFiles("estam", "README.txt");

		softassert.assertEquals(resp.getStatusCode(), 200);
		softassert.assertTrue(resp.jsonPath().getList("matches.metadata.metadata.name")
				.equals(Arrays.asList("README.txt", "file01Estam.txt")));
		softassert.assertTrue(resp.jsonPath().getList("matches.metadata.metadata.server_modified").stream()
				.allMatch(data -> ((String) data).contains("2024-09")));

	}

	@Test(priority = 4, groups = "positive", description = "create new folder")
	public void Test04_CreateFolder() throws JsonProcessingException {

		String myFoldername = UtilMethods.createFolderName();

		Response resp = TestMethods.createFolder(myFoldername);

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertEquals(resp.jsonPath().getString("metadata.name"), myFoldername);
		Assert.assertNotNull(resp.jsonPath().getString("metadata.id"));

	}

	@Test(priority = 5, groups = "positive", description = "upload a new file")
	public void Test05_UploadFile() throws JsonProcessingException {

		String filepath = "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\PaypalApi\\README.txt";
		String dropboxpath = "/estam/READEST.txt";

		Response resp = TestMethods.uploadFile(dropboxpath, filepath);

		softassert.assertEquals(resp.getStatusCode(), 200);
		softassert.assertTrue(dropboxpath.contains(resp.jsonPath().getString("name")));
		softassert.assertFalse(resp.jsonPath().getString("id").isEmpty());
		softassert.assertTrue(resp.jsonPath().getString("path_lower").equalsIgnoreCase(dropboxpath));

	}

	@Test(priority = 6, groups = "positive", description = "search for newly created file")
	public void Test06_Search_NewFile() throws JsonProcessingException {

		String fileName = "READEST.txt";
		Response resp = TestMethods.searchFiles("estam", fileName);

		softassert.assertEquals(resp.getStatusCode(), 200);
		softassert
				.assertTrue(resp.jsonPath().getString("matches.metadata.metadata.name").toString().contains(fileName));
		softassert.assertNotNull(resp.jsonPath().getString("matches.metadata.metadata.id"));
	}

	@Test(priority = 7, groups = "positive", description = "read file content")
	public void Test07_ViewFileContent() throws JsonProcessingException {

		String path = "/estam/READEST.txt";
		Response resp = TestMethods.viewFile(path);

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertTrue(resp.asString().startsWith("Purpose of this test is to make"));
		Assert.assertEquals(resp.contentType(), "application/octet-stream");

	}

	@Test(priority = 8, groups = "positive", description = "delete file")
	public void Test08_Delete_File() throws JsonProcessingException {

		String path = "/estam/READEST.txt";

		Response resp = TestMethods.deleteFile(path);

		softassert.assertEquals(resp.getStatusCode(), 200);
		softassert.assertTrue(resp.jsonPath().getString("metadata.path_display").equalsIgnoreCase(path));

	}

	@Test(priority = 9, groups = "positive", description = "set profile photo")
	public void Test09_SetProfile_photo() throws IOException, JsonProcessingException {

		String path = "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\PaypalApi\\phLarge.jpg";
		Response resp = TestMethods.setProfilePhoto(path);

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertTrue(
				resp.jsonPath().getString("profile_photo_url").contains("https://dl-web.dropbox.com/account_photo"));

	}

	@Test(priority = 10, groups = "negative", description = "passing old token")
	public void test10_checkUser() throws JsonProcessingException {

		mykeys.access_token = mykeys.old_access_token;
		String someText = "test12092024";
		Response resp = TestMethods.checkUser(someText);

		Assert.assertEquals(resp.getStatusCode(), 401);
		Assert.assertEquals(resp.jsonPath().getString("error_summary"), "expired_access_token/");
		Assert.assertTrue(resp.getStatusLine().contains("Unauthorized"));
	}

	@Test(priority = 10, groups = "negative", description = "try to gen non-existing folder")
	public void Test10_Get_Folder() throws IOException {

		TestMethods.get_BearerToken();
		Response resp = TestMethods.getFolder("somefolder...");

		Assert.assertEquals(resp.getStatusCode(), 409);
		Assert.assertTrue(resp.getStatusLine().contains("Conflict"));
		Assert.assertTrue(resp.jsonPath().getString("error_summary").contains("path/not_found"));

	}

	@Test(priority = 11, groups = "negative", description = "sending non valid data for folder creation")
	public void Test11_CreateFolder() throws JsonProcessingException {

		String myFoldername = "";

		Response resp = TestMethods.createFolder(myFoldername);

		Assert.assertEquals(resp.getStatusCode(), 400);
		Assert.assertTrue(resp.getStatusLine().contains("Bad Request"));
		Assert.assertTrue(resp.asString().startsWith("Error in call to API function"));

	}

	@Test(priority = 12, groups = "negative", description = "try to set small sized photo as profile picture")
	public void Test12_SetProfile_photo() throws IOException, JsonProcessingException {

		String path = "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\PaypalApi\\ph.jpg";
		Response resp = TestMethods.setProfilePhoto(path);

		softassert.assertEquals(resp.getStatusCode(), 409);
		softassert.assertTrue(resp.getStatusLine().contains("Conflict"));
		softassert.assertTrue(resp.asString().contains("try to set small sized photo")
				|| resp.jsonPath().getString("error_summary").contains("dimension_error"));

		softassert.assertAll();
	}

}
