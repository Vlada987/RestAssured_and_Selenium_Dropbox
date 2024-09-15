package pojos;

public class GetFolder {

	boolean include_deleted;
	boolean include_has_explicit_shared_members;
	boolean include_media_info;
	boolean include_mounted_folders;
	boolean include_non_downloadable_files;
	String path;
	boolean recursive;

	public GetFolder() {

	}

	public GetFolder(boolean include_deleted, boolean include_has_explicit_shared_members, boolean include_media_info,
			boolean include_mounted_folders, boolean include_non_downloadable_files, String path, boolean recursive) {

		this.include_deleted = include_deleted;
		this.include_has_explicit_shared_members = include_has_explicit_shared_members;
		this.include_media_info = include_media_info;
		this.include_mounted_folders = include_mounted_folders;
		this.include_non_downloadable_files = include_non_downloadable_files;
		this.path = path;
		this.recursive = recursive;
	}

	public boolean isInclude_deleted() {
		return include_deleted;
	}

	public void setInclude_deleted(boolean include_deleted) {
		this.include_deleted = include_deleted;
	}

	public boolean isInclude_has_explicit_shared_members() {
		return include_has_explicit_shared_members;
	}

	public void setInclude_has_explicit_shared_members(boolean include_has_explicit_shared_members) {
		this.include_has_explicit_shared_members = include_has_explicit_shared_members;
	}

	public boolean isInclude_media_info() {
		return include_media_info;
	}

	public void setInclude_media_info(boolean include_media_info) {
		this.include_media_info = include_media_info;
	}

	public boolean isInclude_mounted_folders() {
		return include_mounted_folders;
	}

	public void setInclude_mounted_folders(boolean include_mounted_folders) {
		this.include_mounted_folders = include_mounted_folders;
	}

	public boolean isInclude_non_downloadable_files() {
		return include_non_downloadable_files;
	}

	public void setInclude_non_downloadable_files(boolean include_non_downloadable_files) {
		this.include_non_downloadable_files = include_non_downloadable_files;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

}
