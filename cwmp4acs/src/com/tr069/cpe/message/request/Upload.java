package com.tr069.cpe.message.request;

public class Upload {

	private String commandKey;
	private String fileType;
	private String url;
	private String username;
	private String password;
	private long delaySeconds;
	
	public String getCommandKey() {
		return commandKey;
	}
	public void setCommandKey(String commandKey) {
		this.commandKey = commandKey;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getDelaySeconds() {
		return delaySeconds;
	}
	public void setDelaySeconds(long delaySeconds) {
		this.delaySeconds = delaySeconds;
	}
	
}
