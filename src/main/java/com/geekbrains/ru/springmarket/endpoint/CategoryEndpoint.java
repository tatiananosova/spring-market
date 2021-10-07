package com.geekbrains.ru.springmarket.endpoint;

import com.geekbrains.ru.springmarket.service.soap.CategoryService;
import com.geekbrains.ru.springmarket.soap.soap.GetCategoryByNameRequest;
import com.geekbrains.ru.springmarket.soap.soap.GetCategoryByNameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class CategoryEndpoint {
    private static final String NAMESPACE_URI = "http://www.geekbrains.com/springmarket/ws/categories";
    private final CategoryService categoryService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoryByNameRequest")
    @ResponsePayload
    @Transactional
    public GetCategoryByNameResponse getCategoryByAlias(@RequestPayload GetCategoryByNameRequest request) {
        GetCategoryByNameResponse response = new GetCategoryByNameResponse();
        response.setCategory(categoryService.getByAlias(request.getName()));
        return response;
    }
}
