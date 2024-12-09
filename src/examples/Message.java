package examples;

import annotations.Column;
import annotations.Id;
import annotations.Table;
/**
 * Represents a message entity mapped to the "messages" table in the database.
 */
@Table(name = "messages")
public class Message {
  /**
   * The unique identifier for the message.
   */
  @Id
  @Column(name = "id")
  private int id;
  /**
   * The content of the message.
   */
  @Column(name = "content")
  private String content;
  /**
   * The sender of the message.
   */
  @Column(name = "sender")
  private String sender;
  /**
   * The receiver of the message.
   */
  @Column(name = "receiver")
  private String receiver;
  /**
   * Constructs a new {@code Message} with the specified id, content, sender, and receiver.
   *
   * @param id       the unique identifier for the message
   * @param content  the content of the message
   * @param sender   the sender of the message
   * @param receiver the receiver of the message
   */
  public Message(int id, String content, String sender, String receiver) {
    this.id = id;
    this.content = content;
    this.sender = sender;
    this.receiver = receiver;
  }
  /**
   * Constructs a new {@code Message} with the specified content, sender, and receiver.
   *
   * @param content  the content of the message
   * @param sender   the sender of the message
   * @param receiver the receiver of the message
   */
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
