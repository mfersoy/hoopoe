package com.hoopoe.mapper;



import com.hoopoe.domain.ContactMessage;
import com.hoopoe.dto.ContactMessageDTO;
import com.hoopoe.dto.request.ContactMessageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMessageMapper {

    @Mapping(target = "id" , ignore = true)
    ContactMessage contactMessageRequestToContactMessage (ContactMessageRequest contactMessageRequest);

    List<ContactMessageDTO> map (List<ContactMessage> contactMessages);

    ContactMessageDTO contactMessageToContactMessageDTO(ContactMessage contactMessage);
}
