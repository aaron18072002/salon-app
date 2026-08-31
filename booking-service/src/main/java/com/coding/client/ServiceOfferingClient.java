package com.coding.client;

import com.coding.dto.response.ApiResponse;
import com.coding.dto.response.ServiceOfferingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "service-offering", url = "${application.config.service-offering-url:http://localhost:8083}")
public interface ServiceOfferingClient {

    @GetMapping("/api/v1/service-offerings/list/{ids}")
    ApiResponse<Set<ServiceOfferingDTO>> getServiceOfferingsByIds(@PathVariable("ids") Set<Long> ids);

}
