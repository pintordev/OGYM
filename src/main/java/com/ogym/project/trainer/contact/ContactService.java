package com.ogym.project.trainer.contact;

import com.ogym.project.trainer.trainer.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public Contact create(String type, String content, Trainer trainer){
        Contact contact = new Contact();
        contact.setType(type);
        contact.setContent(content);
        contact.setTrainer(trainer);
        this.contactRepository.save(contact);
        return contact;
    }
    public List<Contact> getList(){
        return this.contactRepository.findAll();
    }
}
