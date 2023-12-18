package com.ybe.mp10.domain.region.service;

import com.ybe.mp10.domain.category.model.Category;
import com.ybe.mp10.domain.region.dto.response.GetAllRegion;
import com.ybe.mp10.domain.region.model.Region;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class RegionService {
    @Transactional(readOnly = true)
    public GetAllRegion getRegions() {
        return GetAllRegion.builder().regions(Arrays.stream(Region.values()).map(r -> {
            return r.getValue();
        }).toList()).build();
    }
}
