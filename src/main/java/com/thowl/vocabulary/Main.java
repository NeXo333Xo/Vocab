package com.thowl.vocabulary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.entity.Vocabulary;

@SpringBootApplication
public class Main {

	

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		Users obj = new Users("admin", "nimda", true);
		System.out.println(obj.getId());
		System.out.println(obj.getUsername());
		System.out.println(obj.getPassword());



		Users obj2 = new Users("test", "hahhaah", false);
		System.out.println(obj2.getId());
		System.out.println(obj2.getUsername());
		System.out.println(obj2.getPassword());


		Vocabulary vocs = new Vocabulary();
	}


}
