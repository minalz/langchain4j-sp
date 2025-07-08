package cn.minalz.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT, // 手动装配
        chatModel = "openAiChatModel", // 指定模型
        streamingChatModel = "openAiStreamingChatModel", // 流式调用 非阻塞
//        chatMemory = "chatMemory", // 配置会话记忆对象
        chatMemoryProvider = "chatMemoryProvider", // 配置会话记忆提供者对象
        contentRetriever = "contentRetriever", // 配置向量数据库检索对象
        tools = "reservationTool"
)
//@AiService // 默认就可以使用，默认用的是自动装配，用上面的可以使得代码更为明晰，当然了，不加配置的代码更为简洁
public interface ConsultantService {

//    String chat(String message);

//    @SystemMessage("你是天下第一的智能小助手朱朱,人美心善又多金")
//    @SystemMessage(fromResource = "system.txt")
//    @UserMessage("你是天下第一的智能小助手朱朱,人美心善又多金!{{it}}")
//    @UserMessage("你是天下第一的智能小助手朱朱,人美心善又多金!{{msg}}")
//    Flux<String> chat(@V("msg") String message);

//    @SystemMessage(fromResource = "system.txt")
//    Flux<String> chat(String message);

    @SystemMessage(fromResource = "system.txt")
    Flux<String> chat(@MemoryId String memoryId, @UserMessage String message);
}
