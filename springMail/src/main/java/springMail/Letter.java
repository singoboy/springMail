package springMail;

public class Letter {

	public String sender;
	public String receiverMail;
	public String receiverName;
	public String isReplaceName;
	public String cc;
	public String bcc;
	public String topic;
	public String content;
	public String formalLink;
	public String isHtml;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiverMail() {
		return receiverMail;
	}

	public void setReceiverMail(String receiverMail) {
		this.receiverMail = receiverMail;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getIsReplaceName() {
		return isReplaceName;
	}

	public void setIsReplaceName(String isReplaceName) {
		this.isReplaceName = isReplaceName;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFormalLink() {
		return formalLink;
	}

	public void setFormalLink(String formalLink) {
		this.formalLink = formalLink;
	}

	public String getIsHtml() {
		return isHtml;
	}

	public void setIsHtml(String isHtml) {
		this.isHtml = isHtml;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Letter [sender=").append(sender).append(", receiverMail=").append(receiverMail)
				.append(", receiverName=").append(receiverName).append(", isReplaceName=").append(isReplaceName)
				.append(", cc=").append(cc).append(", bcc=").append(bcc).append(", topic=").append(topic)
				.append(", content=").append(content).append(", formalLink=").append(formalLink).append(", isHtml=")
				.append(isHtml).append("]");
		return builder.toString();
	}

}
