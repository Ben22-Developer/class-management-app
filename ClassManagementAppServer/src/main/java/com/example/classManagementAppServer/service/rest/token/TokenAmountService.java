package com.example.classManagementAppServer.service.rest.token;

import com.example.classManagementAppServer.repository.token.contract.*;
import lombok.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class TokenAmountService {

    @Value("${spring.ai.token.max-token-amount}")
    private int maxTokenAmount;

    private final TokenAmountRepositoryContract tokenAmountRepo;

    @Async
    public void deductTokenAmount (int tokens, int userID) {

        boolean deducted = tokenAmountRepo.deductTokenAmount(tokens, userID);

        int i = 0;

        while (!deducted && i < 5) {
            createTokenAmountRow(userID);
            deducted = tokenAmountRepo.deductTokenAmount(tokens, userID);
            i++;
        }
    }

    private void createTokenAmountRow (int userID) {
        try {
            tokenAmountRepo.resetTokenAmount(userID);
        } catch (Exception e) {}
    }

    public void tokenAmountCheck (int userID) {
        int amount = tokenAmountRepo.readTokenAmount(userID);

        if (amount >= (maxTokenAmount-100))
            throw new RuntimeException();
    }
}
