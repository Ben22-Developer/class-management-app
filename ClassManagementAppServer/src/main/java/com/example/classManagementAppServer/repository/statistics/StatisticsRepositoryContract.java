package com.example.classManagementAppServer.repository.statistics;

import com.example.classManagementAppServer.dto.ui.*;

public interface StatisticsRepositoryContract {
    Dataset getTokenUsageDataset();
    Dataset getTokenUsageDatasetByDate(String startingDate, String endingDate);
    Dataset getChatRatingDataset();
    Dataset getChatRatingDatasetByDate(String startingDate, String endingDate);
}
