package com.geekbrains.ru.springmarket.endpoint;

import com.geekbrains.ru.springmarket.service.soap.ProductService;
import com.geekbrains.ru.springmarket.soap.soap.GetAllProductsRequest;
import com.geekbrains.ru.springmarket.soap.soap.GetAllProductsResponse;
import com.geekbrains.ru.springmarket.soap.soap.GetProductByNameRequest;
import com.geekbrains.ru.springmarket.soap.soap.GetProductByNameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.geekbrains.com/springmarket/ws/products";
    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByNameRequest")
    @ResponsePayload
    public GetProductByNameResponse getProductByName(@RequestPayload GetProductByNameRequest request) {
        GetProductByNameResponse response = new GetProductByNameResponse();
        response.setProduct(productService.getByTitle(request.getName()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.getAllProducts().forEach(response.getProducts()::add);
        return response;
    }
}
