package cn.minalz.config;

import dev.langchain4j.community.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.community.store.embedding.redis.spring.RedisEmbeddingStoreAutoConfiguration;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommonConfig {

    @Autowired
    private OpenAiChatModel chatModel;
    @Autowired
    private ChatMemoryStore redisChatMemoryStore;
    @Autowired
    private EmbeddingModel embeddingModel;
    @Autowired
    private RedisEmbeddingStore redisEmbeddingStore;

    /*@Bean
    public ConsultantService consultantService() {
        return AiServices.builder(ConsultantService.class)
                .chatModel(chatModel)
                .build();
    }*/

    // 构建会话记忆对象
    /*@Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
    }*/

    // 构建chatMemoryProvider对象 (配置会话记忆提供者对象)
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return new ChatMemoryProvider() {

            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(20)
                        .chatMemoryStore(redisChatMemoryStore)
                        .build();
            }
        };
    }

    // 构建向量数据库操作对象
    @Bean
    public EmbeddingStore myEmbeddingStore() {
        // 1.加载文档进内存
//        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content");
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content", new ApachePdfBoxDocumentParser());
        // 绝对路径 absolute path
//        List<Document> documents = FileSystemDocumentLoader.loadDocuments("D:\\BBBWorkSpace\\langchain4j-sp\\src\\main\\resources\\content");
        // 2.构建向量数据库操作对象
        // 构建文档分割器对象
        DocumentSplitter ds = DocumentSplitters.recursive(500, 100);
        // 这是内存向量分割 重启后就没了
//        InMemoryEmbeddingStore store = new InMemoryEmbeddingStore();
        // 3.构建一个EmbeddingStoreIngestor对象，完成文本数据切割，向量化，存储
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
//                .embeddingStore(store)
                .embeddingStore(redisEmbeddingStore)
                .documentSplitter(ds)
                .embeddingModel(embeddingModel)
                .build();
        ingestor.ingest(documents);
//        return store;
        return redisEmbeddingStore;
    }

    // 构建向量数据库检索对象
    @Bean
    public ContentRetriever contentRetriever(/*EmbeddingStore embeddingStore*/) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(redisEmbeddingStore)
                .minScore(0.5)
                .maxResults(3)
                .embeddingModel(embeddingModel)
                .build();
    }
}
