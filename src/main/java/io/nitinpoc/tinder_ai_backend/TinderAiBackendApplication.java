package io.nitinpoc.tinder_ai_backend;

import io.nitinpoc.tinder_ai_backend.profiles.Gender;
import io.nitinpoc.tinder_ai_backend.profiles.Profile;
import io.nitinpoc.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Profile profile = new Profile(
				"1","Nitin","Muley",40,"Indian", Gender.MALE,"Software Programmer",
				"foo.jpg","INTP"
		);
		profileRepository.save(profile);
		profileRepository.findAll().forEach(System.out::println);
	}
}
