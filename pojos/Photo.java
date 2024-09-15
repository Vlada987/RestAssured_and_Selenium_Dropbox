package pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ ".tag", "base64_data" })
public class Photo {

	@JsonProperty(".tag")
	String tag;
	String base64_data;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getBase64_data() {
		return base64_data;
	}

	public void setBase64_data(String base64_data) {
		this.base64_data = base64_data;
	}

	public Photo(String tag, String base64_data) {

		this.tag = tag;
		this.base64_data = base64_data;
	}

	public Photo() {

	}

}
