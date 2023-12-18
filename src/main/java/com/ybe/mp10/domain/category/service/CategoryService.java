package com.ybe.mp10.domain.category.service;

import com.ybe.mp10.domain.category.dto.response.GetAllCategory;
import com.ybe.mp10.domain.category.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CategoryService {
    @Transactional(readOnly = true)
    public GetAllCategory getCategories() {
        return GetAllCategory.builder()
                .categories(Arrays.stream(Category.values()).map(c -> {
                    return c.getValue();
                }).toList()).build();
    }
}
