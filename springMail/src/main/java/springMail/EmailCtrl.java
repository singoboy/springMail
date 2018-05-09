package springMail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/email")
public class EmailCtrl {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${welcome.message:test}")
	private String message = "Hello World";

	private static final String UPLOAD_DIR = "uploads/";

	@RequestMapping("/index")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome";
	}

	@RequestMapping("/goTestForm")
	public String goTestForm(Model model) {
		return "testForm";
	}

	@RequestMapping("/goFormalForm")
	public String goFormalForm(Model model) {
		return "formalForm";
	}

	@RequestMapping(value = "/handleTestForm", method = RequestMethod.POST)
	public String handleTestForm(Letter letter, @RequestParam("file") MultipartFile file, HttpServletRequest request,
			Model model) {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			model.addAttribute("error", "UnsupportedEncodingException");
			return "error";
		}

		String applicationPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}

		try {
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(uploadFilePath + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			model.addAttribute("error", "IOException");
			return "error";
		}

		ObjectMapper mapper = new ObjectMapper();

		// JSON from file to Object
		List<Person> personList;
		try {
			personList = mapper.readValue(new File(uploadFilePath + file.getOriginalFilename()),
					new TypeReference<List<Person>>() {
					});
		} catch (JsonParseException e) {
			model.addAttribute("error", "JsonParseException");
			return "error";
		} catch (JsonMappingException e) {
			model.addAttribute("error", "JsonMappingException");
			return "error";
		} catch (IOException e) {
			model.addAttribute("error", "IOException");
			return "error";
		}

		List<String> mailList = new ArrayList<String>() ;
		
		for(Person p : personList){
			mailList.add(p.getMailAddr());
		}
		
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;

		try {
			helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(letter.getSender());
			helper.setTo(mailList.toArray(new String[mailList.size()]));
			// helper.setCc(""); //副本
			// helper.setBcc(""); //密件副本
			helper.setSubject(letter.getTopic());
			helper.setText(letter.getContent(), true);   //代表寄送Html 
		} catch (MessagingException e) {
			model.addAttribute("error", "MessagingException");
			return "error";
		}

		/**
		 * 附件處理 
		 * FileSystemResource file = new FileSystemResource(new
		 * File("/Users/Gary/Desktop/sts_workspace/springMail/weixin.jpg"));
		 * helper.addInline("weixin", file); FileSystemResource file2 = new
		 * FileSystemResource(new
		 * File("/Users/Gary/Desktop/sts_workspace/springMail/yo.jpeg"));
		 * helper.addAttachment("妖尾.jpeg", file2);
		 */

		javaMailSender.send(mimeMessage);
		
		model.addAttribute("sender", letter.getSender()) ;
		model.addAttribute("receiverMail", letter.getReceiverMail()) ;
		model.addAttribute("receiverName", letter.getReceiverName()) ;
		model.addAttribute("topic", letter.getTopic()) ;
		model.addAttribute("content", letter.getContent()) ;
		model.addAttribute("formalLink", letter.getFormalLink()) ;
		
		return "testForm";

	}

	@RequestMapping(value = "/handleFormalForm", method = RequestMethod.POST)
	public String handleFormalForm(Letter letter, HttpServletRequest request,
			Model model) {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			model.addAttribute("error", "UnsupportedEncodingException");
			return "error";
		}

		if(letter.getFormalLink().isEmpty()){
			model.addAttribute("error", "沒給連結");
			return "error";
		}

		ObjectMapper mapper = new ObjectMapper();

		// JSON from file to Object
		List<Person> personList;
		try {
			personList = mapper.readValue(new URL(letter.getFormalLink()),
					new TypeReference<List<Person>>() {
					});
		} catch (JsonParseException e) {
			model.addAttribute("error", "JsonParseException");
			return "error";
		} catch (JsonMappingException e) {
			model.addAttribute("error", "JsonMappingException");
			return "error";
		} catch (IOException e) {
			model.addAttribute("error", "IOException");
			return "error";
		}

		List<String> mailList = new ArrayList<String>() ;
		
		for(Person p : personList){
			mailList.add(p.getMailAddr());
		}
		
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;

		try {
			helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(letter.getSender());
			helper.setTo(mailList.toArray(new String[mailList.size()]));
			// helper.setCc(""); //副本
			// helper.setBcc(""); //密件副本
			helper.setSubject(letter.getTopic());
			helper.setText(letter.getContent(), true);   //代表寄送Html 
		} catch (MessagingException e) {
			model.addAttribute("error", "MessagingException");
			return "error";
		}

		/**
		 * 附件處理 
		 * FileSystemResource file = new FileSystemResource(new
		 * File("/Users/Gary/Desktop/sts_workspace/springMail/weixin.jpg"));
		 * helper.addInline("weixin", file); FileSystemResource file2 = new
		 * FileSystemResource(new
		 * File("/Users/Gary/Desktop/sts_workspace/springMail/yo.jpeg"));
		 * helper.addAttachment("妖尾.jpeg", file2);
		 */

		javaMailSender.send(mimeMessage);       //發正式
		
		model.addAttribute("sender", letter.getSender()) ;
		model.addAttribute("receiverMail", letter.getReceiverMail()) ;
		model.addAttribute("receiverName", letter.getReceiverName()) ;
		model.addAttribute("topic", letter.getTopic()) ;
		model.addAttribute("content", letter.getContent()) ;
		model.addAttribute("formalLink", letter.getFormalLink()) ;
		
		return "testForm";

	}

}
