package com.example.classManagementAppServer.service.rest.ai;

import lombok.*;
import org.springframework.ai.document.*;
import org.springframework.ai.reader.*;
import org.springframework.ai.transformer.splitter.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TokenSplitterTestService {

    @Value("classpath:templates/spring_ai/vector_source/chapter1.st")
    private Resource resource;

    private final TokenTextSplitter tokenTextSplitter;

    public ResponseEntity<?> tokenSplitterTest ()
    {
        JsonReader jsonReader = new JsonReader(resource, "title", "description");

        List<Document> documentList = jsonReader.get();

        int id = 1;

        for (Document document : documentList)
            document.getMetadata().put("id","chapter1 " + id++);

        List<Document> splittedDocumentList = tokenTextSplitter.split(documentList);

        return ResponseEntity.ok(splittedDocumentList);
    }
}
