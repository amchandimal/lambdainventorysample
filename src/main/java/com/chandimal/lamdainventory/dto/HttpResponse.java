package com.chandimal.lamdainventory.dto;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class HttpResponse<I> {

  private String body;
  private String statusCode = "404";
  private Map<String, String> headers = new HashMap<String, String>();

  public HttpResponse() {
    this.headers.put("Content-Type", "application/json");
  }

  public HttpResponse(I object) {
    this();
    Gson gson = new Gson();
    this.body = gson.toJson(object);
    this.statusCode = "200";
  }

  public HttpResponse withResponseCode(String responseCode){
    this.setStatusCode(responseCode);
    return this;
  }

  public HttpResponse withBody(I body){
    Gson gson = new Gson();
    this.body = gson.toJson(body);
    return this;
  }

  public HttpResponse addHeaders(String key,String value){
    this.headers.put(key, value);
    return this;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }
}
