package restUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;
import java.util.StringJoiner;

import org.testng.annotations.Test;

public class UtilMethods {

	public static String encodetoB64(String path) throws IOException {

		File file = new File(path);

		byte[] fileBytes = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			fileBytes = fileInputStream.readAllBytes();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(fileBytes);

	}

	public static void toBinary(String path) throws IOException {

		FileInputStream inputData = new FileInputStream(path);
		ByteArrayOutputStream fileData = new ByteArrayOutputStream();
		inputData.transferTo(fileData);

		StringJoiner binaryData = new StringJoiner(" ");

		for (Byte data : fileData.toByteArray()) {
			binaryData.add(Integer.toBinaryString(data));
		}

		FileOutputStream outputData = new FileOutputStream(
				"C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\PaypalApi\\binaryFiles\\fo");
		outputData.write(binaryData.toString().getBytes());

		fileData.close();
		outputData.close();
		inputData.close();

	}

	public static String createFolderName() {

		Random r = new Random();
		String a = "myFolder";
		String b = "qwertyuiopasdfghjklzxcvbnm";

		return a + b.charAt(r.nextInt(b.length() - 1)) + b.charAt(r.nextInt(b.length() - 1))
				+ b.charAt(r.nextInt(b.length() - 1));
	}

}
