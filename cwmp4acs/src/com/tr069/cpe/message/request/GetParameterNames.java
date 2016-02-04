package com.tr069.cpe.message.request;

public class GetParameterNames {

	private String parameterPath;
	private boolean nextLevel;
	public String getParameterPath() {
		return parameterPath;
	}
	public void setParameterPath(String parameterPath) {
		this.parameterPath = parameterPath;
	}
	public boolean isNextLevel() {
		return nextLevel;
	}
	public void setNextLevel(boolean nextLevel) {
		this.nextLevel = nextLevel;
	}
}
