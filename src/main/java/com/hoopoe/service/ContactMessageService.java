package com.hoopoe.service;


import com.hoopoe.domain.ContactMessage;
import com.hoopoe.dto.ContactMessageDTO;
import com.hoopoe.dto.request.ContactMessageRequest;
import com.hoopoe.exception.ResourceNotFoundException;
import com.hoopoe.exception.message.ErrorMessage;
import com.hoopoe.mapper.ContactMessageMapper;
import com.hoopoe.repository.ContactMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactMessageService {

    private ContactMessageRepository contactMessageRepository;


    private ContactMessageMapper contactMessageMapper;


    public void saveMessage(ContactMessageRequest contactMessageRequest){
        ContactMessage contactMessage = contactMessageMapper.contactMessageRequestToContactMessage(contactMessageRequest);
        contactMessageRepository.save(contactMessage);
    }

    public List<ContactMessageDTO> getAll(){
        List<ContactMessage> contactMessagesList= contactMessageRepository.findAll();
       return contactMessageMapper.map(contactMessagesList);
    }

    public ContactMessage getContactMessage(Long id){
        ContactMessage contactMessage = contactMessageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
        return contactMessage;
    }

    public void deleteContactMessage(Long id){
        ContactMessage contactMessage= getContactMessage(id);
        contactMessageRepository.delete(contactMessage);
    }

    public void updateContactMessage(Long id, ContactMessage contactMessage){
        ContactMessage foundContactMessage1 = getContactMessage(id);

        foundContactMessage1.setName(contactMessage.getName());
        foundContactMessage1.setSubject(contactMessage.getSubject());
        foundContactMessage1.setEmail(contactMessage.getEmail());
        foundContactMessage1.setBody(contactMessage.getBody());
        contactMessageRepository.save(foundContactMessage1);

    }

    public Page<ContactMessage> getAll(Pageable pageable){
        return contactMessageRepository.findAll(pageable);
    }
}
