package com.chandimal.lamdainventory.clients;

import com.chandimal.lamdainventory.dto.Product;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.google.gson.Gson;

import java.util.Set;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;

public class InventoryS3Client {

  private static final String BUCKET_NAME = "chandimal-inventory-data";
  private static final String INVENTORY_FILE = "inventory.json";

  protected Product[] getAllProducts() {
    Region region = Region.US_WEST_2;
    SdkHttpClient httpClient = ApacheHttpClient.builder().build();
    S3Client s3Client = S3Client.builder().httpClient(httpClient).region(region).build();
    ResponseInputStream<?> objectData = s3Client.getObject(GetObjectRequest.builder()
        .bucket(BUCKET_NAME)
        .key(INVENTORY_FILE)
        .build());
    InputStreamReader isr = new InputStreamReader(objectData);
    BufferedReader br = new BufferedReader(isr);
    Gson gson = new Gson();
    Product[] products = gson.fromJson(br, Product[].class);
    return products;
  }

  protected Set<Product> getAllProductsList() {
    return new HashSet<>(Arrays.asList(getAllProducts()));
  }

  protected boolean updateAllProducts(Product[] products) {

    Gson gson = new Gson();
    String jsonString = gson.toJson(products);

    Region region = Region.US_WEST_2;
    SdkHttpClient httpClient = ApacheHttpClient.builder().build();
    S3Client s3Client = S3Client.builder().httpClient(httpClient).region(region).build();

    PutObjectResponse putResponse = s3Client.putObject(PutObjectRequest.builder()
            .bucket(BUCKET_NAME)
            .key(INVENTORY_FILE)
            .build(),
        RequestBody.fromString(jsonString));

    return putResponse.sdkHttpResponse().isSuccessful();

  }

  protected boolean updateAllProducts(Set<Product> productList) {
    Product[] products = productList.toArray(new Product[productList.size()]);
    return updateAllProducts(products);
  }


}
