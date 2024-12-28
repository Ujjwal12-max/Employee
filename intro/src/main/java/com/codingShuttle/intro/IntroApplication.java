package com.codingShuttle.intro;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntroApplication implements CommandLineRunner {

	public static void main(String[] args)  {

		SpringApplication.run(IntroApplication.class, args);

	}
	@Autowired
	Apple obj1;
	@Autowired
	Apple obj2;


	public void run(String... args) throws Exception {
		obj1.eatApple();
		obj2.eatApple();
	//	obj1.setVal(12);
		//obj2.setVal(34);
	//	System.out.println(obj1.getVal());
		//System.out.println(obj2.getVal());

		int a=1;
		jod(a);
		int b = a;
		System.out.println("b = "+b);
	}

	public void jod (int x){

	};
}
