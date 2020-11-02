package it.mynaproject.gestprod.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class AboutJson {

	private String version;

	private String buildtime;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AboutJson [version=");
		builder.append(version);
		builder.append(", buildtime=");
		builder.append(buildtime);
		builder.append("]");

		return builder.toString();
	}
}
