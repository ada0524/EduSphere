package com.example.messageservice.service;

import com.example.messageservice.dao.MessageRepository;
import com.example.messageservice.dto.NewMessage;
import com.example.messageservice.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public List<Message> getAllMessages(){
        return this.messageRepository.findAllByOrderByDateCreatedDesc();
    }

    @Transactional
    public void postNewMessage(Integer userId, NewMessage newMessage){
        Message message = Message.builder().userId(userId).email(newMessage.getEmail()).messageText(newMessage.getText()).dateCreated(LocalDateTime.now()).status("Open").subject(newMessage.getSubject()).build();
        this.messageRepository.save(message);
    }

    @Transactional
    public void changeMessageStatus(Integer id, String status){
        System.out.println(id);
        Message message = this.messageRepository.findById(id).get();
        message.setStatus(status);
        this.messageRepository.save(message);
    }
}
