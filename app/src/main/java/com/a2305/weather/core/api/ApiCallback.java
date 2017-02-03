package com.a2305.weather.core.api;

public interface ApiCallback {

	void onApiStart(String description);

	void onApiEnd();

	void onApiError(String error);

}