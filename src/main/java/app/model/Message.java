package app.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(
    name = "messages"
)
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "message_id")
    private Long id;
    @Column(name = "content")
    private String content;
}
