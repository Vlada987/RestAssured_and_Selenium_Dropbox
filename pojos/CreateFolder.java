package pojos;

public class CreateFolder {

	Boolean autorename;
	String path;

	public CreateFolder() {

	}

	public CreateFolder(Boolean autorename, String path) {
		this.autorename = autorename;
		this.path = path;
	}

	public Boolean getAutorename() {
		return autorename;
	}

	public void setAutorename(Boolean autorename) {
		this.autorename = autorename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
