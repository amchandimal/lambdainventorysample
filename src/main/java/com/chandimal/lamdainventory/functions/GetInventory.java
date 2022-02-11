package com.chandimal.lamdainventory.functions;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.chandimal.lamdainventory.dto.HttpRequest;
import com.chandimal.lamdainventory.dto.Product;
import com.chandimal.lamdainventory.clients.InventoryS3Client;
import com.chandimal.lamdainventory.dto.HttpResponse;

public class GetInventory extends InventoryS3Client implements
    RequestHandler<HttpRequest, HttpResponse> {

  @Override
  public HttpResponse handleRequest(HttpRequest request, Context context) {
    context.getLogger().log("Input: " + request);
    String idAsString = request.getQueryStringParameters().get("id");
    //Retrive All the Products
    if (idAsString.equalsIgnoreCase("all")) {
      return new HttpResponse(getAllProducts());
    }
    int id = Integer.parseInt(idAsString);

    Product product = getProductById(id);
    if (product != null) {
      return new HttpResponse(product);
    }
    return new HttpResponse();
  }

  private Product getProductById(int prodId) {
    Product[] products = getAllProducts();
    for (Product product : products) {
      if (product.getId() == prodId) {
        return product;
      }
    }
    return null;
  }
}
