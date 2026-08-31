package com.coding.mapper;

import com.coding.dto.request.CreateBookingRequest;
import com.coding.dto.request.UpdateBookingRequest;
import com.coding.dto.response.BookingResponse;
import com.coding.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "totalServices", ignore = true)
    Booking toEntity(CreateBookingRequest request);

    BookingResponse toResponse(Booking booking);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "salonId", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "totalServices", ignore = true)
    void updateBookingFromRequest(UpdateBookingRequest request, @MappingTarget Booking booking);

}


