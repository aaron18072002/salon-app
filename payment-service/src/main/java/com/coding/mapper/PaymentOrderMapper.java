package com.coding.mapper;

import com.coding.dto.request.CreatePaymentOrderRequest;
import com.coding.dto.response.PaymentOrderResponse;
import com.coding.model.PaymentOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentOrderMapper {

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentLinkId", ignore = true)
    PaymentOrder toEntity(CreatePaymentOrderRequest request);

    @Mapping(target = "paymentUrl", ignore = true)
    PaymentOrderResponse toResponse(PaymentOrder paymentOrder);

}
