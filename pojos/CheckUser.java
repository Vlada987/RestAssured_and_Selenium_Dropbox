package pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckUser {

	@JsonProperty("query")
	String property1;

	public CheckUser() {

	}

	public CheckUser(String property1) {

		this.property1 = property1;
	}

	public String getProperty1() {
		return property1;
	}

	public void setProperty1(String property1) {
		this.property1 = property1;
	}

}
