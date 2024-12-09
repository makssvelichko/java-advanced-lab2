package examples;

import annotations.Column;
import annotations.Id;
import annotations.Table;

@Table(name = "messages")
public class Message {

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "content")
  private String content;

  @Column(name = "sender")
  private String sender;

  @Column(name = "receiver")
  private String receiver;

  public Message(int id, String content, String sender, String receiver) {
    this.id = id;
    this.content = content;
    this.sender = sender;
    this.receiver = receiver;
  }

  public Message(String content, String sender, String receiver) {
    this.content = content;
    this.sender = sender;
    this.receiver = receiver;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }
}
