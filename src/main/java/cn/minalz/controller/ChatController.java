package cn.minalz.controller;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private OpenAiChatModel chatModel;

    @GetMapping("/chat")
    public String chat(String message) {
        return chatModel.chat(message);
    }
}
