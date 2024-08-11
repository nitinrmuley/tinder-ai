package io.nitinpoc.tinder_ai_backend.conversations;

import io.nitinpoc.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/conversations")
    public Conversation createNewConversation(@RequestBody createConversationRequest request){
        profileRepository.findById(request.profileId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "unable to find profile id "+request.profileId()));
        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                request.profileId(),
                new ArrayList<>());
        conversationRepository.save(conversation);
        return conversation;
    }
    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversation(@PathVariable
                                        String conversationId){
        return conversationRepository.findById(conversationId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"unable to find conversation with ID"+ conversationId));
    }

    @PostMapping("/conversations/{conversationId}")
    public Conversation addMessageToCoversation(@PathVariable String conversationId , @RequestBody ChatMessage chatMessage){
     Conversation conversation=conversationRepository.findById(conversationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"unable to find conversation with ID"+ conversationId));
     profileRepository.findById(chatMessage.authorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"unable to find author ID"+chatMessage.authorId()));
     //TODO validate author of message
     ChatMessage messageWithTime = new ChatMessage(chatMessage.messageText(), chatMessage.authorId(), LocalDateTime.now());

     conversation.messages().add(messageWithTime);
     conversationRepository.save(conversation);
     return conversation;
    }

    public record  createConversationRequest(
            String profileId
    ){}
}
