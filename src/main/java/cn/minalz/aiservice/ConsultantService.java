package cn.minalz.aiservice;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT, // 手动装配
        chatModel = "openAiChatModel" // 指定模型
)
//@AiService // 默认就可以使用，默认用的是自动装配，用上面的可以使得代码更为明晰，当然了，不加配置的代码更为简洁
public interface ConsultantService {

    String chat(String message);
}
