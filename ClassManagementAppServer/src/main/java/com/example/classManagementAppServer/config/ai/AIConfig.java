package com.example.classManagementAppServer.config.ai;

import com.example.classManagementAppServer.dto.ai.*;
import com.knuddels.jtokkit.api.*;
import org.springframework.ai.chat.client.*;
import org.springframework.ai.chat.client.advisor.*;
import org.springframework.ai.chat.client.advisor.vectorstore.*;
import org.springframework.ai.chat.memory.*;
import org.springframework.ai.chat.memory.repository.jdbc.*;
import org.springframework.ai.chat.model.*;
import org.springframework.ai.chat.prompt.*;
import org.springframework.ai.embedding.*;
import org.springframework.ai.google.genai.*;
import org.springframework.ai.google.genai.text.*;
import org.springframework.ai.transformer.splitter.*;
import org.springframework.ai.vectorstore.*;
import org.springframework.ai.vectorstore.mariadb.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.*;

@Configuration
public class AIConfig {





    public AIConfig ( @Value("${spring.mail.username}")String name)
    {
        System.out.println(name);
    }


    @Bean
    @Primary
    public ChatClient chatClient (ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, VectorStore vectorStore) {
        return chatClientBuilder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor
                                .builder(chatMemory)
                                .build(),
                        QuestionAnswerAdvisor
                                .builder(vectorStore)
                                .build())
                .build();
    }

    @Bean
    public ChatClient ratingChatClient (ChatModel chatModel, StructuredOutputValidationAdvisor structuredOutputValidationAdvisor) {

        return ChatClient
                .builder(chatModel)
                .defaultAdvisors(structuredOutputValidationAdvisor)
                .build();
    }

    @Bean
    public StructuredOutputValidationAdvisor ratingStructuredOutputValidationAdvisor  () {

        return StructuredOutputValidationAdvisor
                .builder()
                .outputType(ChatRatingPost.class)
                .maxRepeatAttempts(5)
                .build();
    }

    @Bean
    public ChatMemoryRepository chatMemoryRepository (JdbcTemplate jdbcTemplate) {
        return JdbcChatMemoryRepository
                .builder()
                .jdbcTemplate(jdbcTemplate)
                .dialect(new MysqlChatMemoryRepositoryDialect())
                .build();
    }

    @Bean
    public ChatMemory chatMemory (ChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory
                .builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(3)
                .build();
    }

    @Bean
    public MariaDBVectorStore mariaDBVectorStore (@Qualifier("mariaDBJdbcTemplate") JdbcTemplate jdbcTemplate, GoogleGenAiTextEmbeddingModel googleGenAiTextEmbeddingModel, BatchingStrategy batchingStrategy) {
        return MariaDBVectorStore
                .builder(jdbcTemplate,googleGenAiTextEmbeddingModel)
                .initializeSchema(true)
                .batchingStrategy(batchingStrategy)
                .build();
    }

    @Bean
    public GoogleGenAiTextEmbeddingModel googleGenAiTextEmbeddingModel (GoogleGenAiEmbeddingConnectionDetails googleGenAiEmbeddingConnectionDetails,
                                                                        GoogleGenAiTextEmbeddingOptions googleGenAiTextEmbeddingOptions)
    {
        return new GoogleGenAiTextEmbeddingModel(googleGenAiEmbeddingConnectionDetails, googleGenAiTextEmbeddingOptions);
    }

    @Bean
    public GoogleGenAiEmbeddingConnectionDetails googleGenAiEmbeddingConnectionDetails (@Value("${spring.ai.google.genai.api-key}") String apiKey) {

        return GoogleGenAiEmbeddingConnectionDetails
                .builder()
                .apiKey(apiKey)
                .build();
    }

    @Bean
    public GoogleGenAiTextEmbeddingOptions googleGenAiTextEmbeddingOptions () {

        return GoogleGenAiTextEmbeddingOptions
                .builder()
                .model(GoogleGenAiTextEmbeddingModelName.GEMINI_EMBEDDING_001)
                .taskType(GoogleGenAiTextEmbeddingOptions.TaskType.RETRIEVAL_DOCUMENT)
                .autoTruncate(false)
                .build();
    }

    @Bean
    public TokenCountBatchingStrategy tokenCountBatchingStrategy (@Value("${spring.ai.token.token-batching-strategy.max-input-token-count}") int maxInputTokenCount,
                                                                  @Value("${spring.ai.token.token-batching-strategy.reserve-percentage}") double reservePercentage) {
        return new TokenCountBatchingStrategy ( EncodingType.CL100K_BASE, maxInputTokenCount, reservePercentage);
    }

    @Bean
    public TokenTextSplitter tokenTextSplitter (@Value("${spring.ai.token.token-text-splitter.chunk-size}") int chunkSize,
                                                @Value("${spring.ai.token.token-text-splitter.min-chunk-size-chars}") int minChunkSizeChars)
    {
        return TokenTextSplitter
                .builder()
                .withChunkSize(chunkSize)
                .withMinChunkSizeChars(minChunkSizeChars)
                .build();
    }
}
