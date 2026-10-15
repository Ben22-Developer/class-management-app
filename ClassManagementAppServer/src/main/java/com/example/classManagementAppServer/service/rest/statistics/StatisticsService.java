package com.example.classManagementAppServer.service.rest.statistics;

import com.example.classManagementAppServer.annotation.network.*;
import com.example.classManagementAppServer.dto.ui.*;
import com.example.classManagementAppServer.repository.statistics.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepositoryContract statisticsRepositoryContract;

    @NetworkFailureAnnotation
    public Dataset tokenUsageDataset() {
        return statisticsRepositoryContract.getTokenUsageDataset();
    }

    @NetworkFailureAnnotation
    public Dataset tokenUsageDatasetByDate(String startingDate, String endingDate) {
        return statisticsRepositoryContract.getTokenUsageDatasetByDate(startingDate, endingDate);
    }

    @NetworkFailureAnnotation
    public Dataset chatRatingDataset() {
        return statisticsRepositoryContract.getChatRatingDataset();
    }

    @NetworkFailureAnnotation
    public Dataset chatRatingDatasetByDate(String startingDate, String endingDate) {
        return statisticsRepositoryContract.getChatRatingDatasetByDate(startingDate, endingDate);
    }
}
