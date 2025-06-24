package cn.minalz.controller;

import cn.minalz.aiservice.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    @Autowired
    private ConsultantService consultantService;

    @GetMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(String memoryId, String message) {
        return consultantService.chat(memoryId, message);
    }

    /*@GetMapping("/chat")
    public String chat(String message) {
        return consultantService.chat(message);
    }*/

    /*@Autowired
    private OpenAiChatModel chatModel;

    @GetMapping("/chat")
    public String chat(String message) {
        return chatModel.chat(message);
    }*/
}
