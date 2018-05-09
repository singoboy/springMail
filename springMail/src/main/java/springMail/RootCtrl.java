package springMail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootCtrl {
	
	
	@RequestMapping("/")
	public String welcome() {

		return "welcome";
	}

}
