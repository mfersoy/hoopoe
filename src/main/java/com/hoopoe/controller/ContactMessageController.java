package com.hoopoe.controller;


import com.hoopoe.domain.ContactMessage;
import com.hoopoe.dto.ContactMessageDTO;
import com.hoopoe.dto.request.ContactMessageRequest;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.mapper.ContactMessageMapper;
import com.hoopoe.service.ContactMessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contactmessage")
@AllArgsConstructor
public class ContactMessageController {
    private ContactMessageService contactMessageService;

    private ContactMessageMapper contactMessageMapper;


    @PostMapping("/visitors")
    public ResponseEntity<HResponse> createMessage(@Valid @RequestBody ContactMessageRequest contactMessageRequest){

        contactMessageService.saveMessage(contactMessageRequest);

        //as an example of HARD CODING
        HResponse response = new HResponse(ResponseMessage.CONTACT_MESSAGE_SAVE_RESPONSE,true);

        //TODO please read about SQL-INJECTION

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ContactMessageDTO>>  getAllContactMessage(){

        List<ContactMessageDTO> contactMessageDTOList= contactMessageService.getAll();

        return ResponseEntity.ok(contactMessageDTOList);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<HResponse> deleteContactMessage(@PathVariable Long id){
        contactMessageService.deleteContactMessage(id);
        HResponse response = new HResponse(ResponseMessage.CONTACT_MESSAGE_DELETE_RESPONSE,true);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/request")
    public ResponseEntity<ContactMessageDTO> getRequestWithRequestParam(@RequestParam("id") Long id){

        ContactMessage contactMessage = contactMessageService.getContactMessage(id);

        ContactMessageDTO contactMessageDTO= contactMessageMapper.contactMessageToContactMessageDTO(contactMessage);
        return ResponseEntity.ok(contactMessageDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<ContactMessageDTO> getRequestWithPath(@PathVariable Long id){
        ContactMessage contactMessage = contactMessageService.getContactMessage(id);
        ContactMessageDTO contactMessageDTO= contactMessageMapper.contactMessageToContactMessageDTO(contactMessage);
        return ResponseEntity.ok(contactMessageDTO);
    }
    @PutMapping("{id}")
    public ResponseEntity<HResponse> updateContactMessage(@PathVariable Long id
            , @Valid @RequestBody ContactMessageRequest contactMessageRequest){
        ContactMessage contactMessage= contactMessageMapper.contactMessageRequestToContactMessage(contactMessageRequest);
        contactMessageService.updateContactMessage(id,contactMessage);

        HResponse response = new HResponse(ResponseMessage.CONTACT_MESSAGE_UPDATED,true);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/pages")
    public ResponseEntity<Page<ContactMessageDTO>> getAllContactMessageWithPage(@RequestParam("page") int page,
                                                                                @RequestParam("size") int size,
                                                                                @RequestParam("sort") String prop,
                                                                                @RequestParam (value = "direction",
                                                                                        required = false,
                                                                                        defaultValue = "DESC") Sort.Direction direction){
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<ContactMessage> contactMessagesPage = contactMessageService.getAll(pageable);
        Page<ContactMessageDTO> contactMessageDTOS= getPageDTO(contactMessagesPage);
        return ResponseEntity.ok(contactMessageDTOS);
    }

    public Page<ContactMessageDTO> getPageDTO(Page<ContactMessage> contactMessagesPage){
        return contactMessagesPage.map(contactMessage -> contactMessageMapper.contactMessageToContactMessageDTO((contactMessage)));
    }
}
