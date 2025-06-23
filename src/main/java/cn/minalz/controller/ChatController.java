package cn.minalz.controller;

import cn.minalz.aiservice.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private ConsultantService consultantService;

    @GetMapping("/chat")
    public String chat(String message) {
        return consultantService.chat(message);
    }

    /*@Autowired
    private OpenAiChatModel chatModel;

    @GetMapping("/chat")
    public String chat(String message) {
        return chatModel.chat(message);
    }*/
}
