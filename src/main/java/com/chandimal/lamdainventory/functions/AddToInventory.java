package com.chandimal.lamdainventory.functions;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.chandimal.lamdainventory.clients.InventoryS3Client;
import com.chandimal.lamdainventory.dto.HttpResponse;
import com.chandimal.lamdainventory.dto.HttpRequest;
import com.chandimal.lamdainventory.dto.Product;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Input & Output are the enerics Here
public class AddToInventory extends InventoryS3Client implements RequestHandler<HttpRequest, HttpResponse> {

  @Override
  public HttpResponse handleRequest(HttpRequest request, Context context) {
    context.getLogger().log("Input: " + request);
    String body = request.getBody();

    Gson gson = new Gson();
    Product newProduct = gson.fromJson(body,Product.class);

    //Adding New Product to the File
    Set<Product> allProducts = getAllProductsList();
    allProducts.add(newProduct);

    if(updateAllProducts(allProducts)){
      return new HttpResponse().withBody(newProduct).withResponseCode("201");
    }

    return new HttpResponse().withResponseCode("500");
  }


}
