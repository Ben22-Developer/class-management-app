package com.example.classManagementAppServer.service.rest.ai;

import org.springframework.ai.reader.*;
import lombok.*;
import org.springframework.ai.transformer.splitter.*;
import org.springframework.ai.vectorstore.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ai.document.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class VectorStoreService {

    private final VectorStore vectorStore;

    @Value("classpath:templates/spring_ai/vector_source/intro.st")
    private Resource resource;

    private final TokenTextSplitter tokenTextSplitter;

    public ResponseEntity<?> store ()
    {
        JsonReader jsonReader = new JsonReader(resource, "title", "description");

        List<Document> documentList = jsonReader.get();

        int id = 1;

        for (Document document : documentList)
            document.getMetadata().put("id","intro " + id++);

        List<Document> splitDocument = tokenTextSplitter.split(documentList);
        vectorStore.add(splitDocument);

        return ResponseEntity.ok(splitDocument);
    }

    public ResponseEntity<?> delete ()
    {

        vectorStore.delete("id == 'intro1'");

        return ResponseEntity.ok().build();
    }

}
