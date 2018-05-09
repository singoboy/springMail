package springMail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestCse {

	public static void main(String[] args) throws Exception {
		
		ArrayList list = new ArrayList<Person>();
		
		Person qq = new Person();
		qq.setMailAddr("qq@gmail");
		qq.setName("qq");
		
		
		Person kk = new Person();
		kk.setMailAddr("kk@gmail");
		kk.setName("kk");
		
		list.add(qq);
		list.add(kk);
		
		ObjectMapper mapper = new ObjectMapper();


		//Object to JSON in file
		mapper.writeValue(new File("/Users/Gary/Desktop/file.json"), list);
		
		// TODO Auto-generated method stub

	}

}
