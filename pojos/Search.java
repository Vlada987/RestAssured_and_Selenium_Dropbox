package pojos;

import java.util.LinkedHashMap;

public class Search {

	LinkedHashMap<String, Object> match_field_options;
	LinkedHashMap<String, Object> options;
	String query;

	public LinkedHashMap<String, Object> getMatch_field_options() {
		return match_field_options;
	}

	public void setMatch_field_options(LinkedHashMap<String, Object> match_field_options) {
		this.match_field_options = match_field_options;
	}

	public LinkedHashMap<String, Object> getOptions() {
		return options;
	}

	public void setOptions(LinkedHashMap<String, Object> options) {
		this.options = options;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Search(LinkedHashMap<String, Object> match_field_options, LinkedHashMap<String, Object> options,
			String query) {
		this.match_field_options = match_field_options;
		this.options = options;
		this.query = query;
	}

	public Search() {

	}

}
