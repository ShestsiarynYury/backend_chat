package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.model.Message;
import app.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
	public Message saveOrUpdate(Message message) {
		return this.repository.save(message);
	}
}
