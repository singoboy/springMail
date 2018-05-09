package springMail;

public class Person {
	
	public String mailAddr ;
	public String name ;
	public String getMailAddr() {
		return mailAddr;
	}
	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [mailAddr=").append(mailAddr).append(", name=").append(name).append("]");
		return builder.toString();
	}
	
	
	
	
}
