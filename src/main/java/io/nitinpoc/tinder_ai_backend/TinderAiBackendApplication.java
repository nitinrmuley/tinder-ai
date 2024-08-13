package io.nitinpoc.tinder_ai_backend;

import io.nitinpoc.tinder_ai_backend.conversations.ChatMessage;
import io.nitinpoc.tinder_ai_backend.conversations.Conversation;
import io.nitinpoc.tinder_ai_backend.conversations.ConversationRepository;
import io.nitinpoc.tinder_ai_backend.profiles.Gender;
import io.nitinpoc.tinder_ai_backend.profiles.Profile;
import io.nitinpoc.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ConversationRepository conversationRepository;

	@Autowired
	private OpenAiChatModel  chatClient;

	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Prompt prompt= new Prompt("who is Google founder");
		 ChatResponse response = chatClient.call(prompt);
		System.out.println(response.getResult().getOutput().getContent());
		//clean all data from DB
		profileRepository.deleteAll();
		conversationRepository.deleteAll();
		Profile profile = new Profile(
				"1","Nitin","Muley",40,"Indian", Gender.MALE,"Software Programmer",
				"foo.jpg","INTP"
		);
		profileRepository.save(profile);
		Profile profile2 = new Profile(
				"2","Foo","bar",40,"Indian", Gender.MALE,"Software Programmer",
				"foo.jpg","INTP"
		);
		profileRepository.save(profile2);
		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation = new Conversation(
				"1",profile.id(),
				List.of( new ChatMessage("Hello",profile.id(),
				LocalDateTime.now())));
		conversationRepository.save(conversation);
		conversationRepository.findAll().forEach(System.out::println);
	}
}
