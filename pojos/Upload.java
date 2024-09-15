package pojos;

public class Upload {

	String path;
	String url;

	public Upload() {

	}

	public Upload(String path, String url) {

		this.path = path;
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
