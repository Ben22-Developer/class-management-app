package com.example.classManagementAppServer.controller.mvc;

import com.example.classManagementAppServer.dto.ai.*;
import com.example.classManagementAppServer.dto.message.*;
import com.example.classManagementAppServer.dto.ui.*;
import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.service.rest.admin.*;
import com.example.classManagementAppServer.service.rest.feedback.*;
import com.example.classManagementAppServer.service.rest.statistics.*;
import com.example.classManagementAppServer.service.user.update.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ModelViewController {

    private final StatisticsService statisticsService;

    private final ReadUsersAdminService readUsersAdminService;

    private final UserUpdateService userUpdateService;

    private final ChatRatingService chatRatingService;

    private final UserFeedbackService userFeedbackService;

    @GetMapping("/login")
    public String loginPage (Model model) {
        return "html/login";
    }

    @GetMapping("/logout")
    public String logoutPage (Model model) {
        return "html/logout";
    }


    @GetMapping("/")
    @PreAuthorize("@preAuthorizeService.verifyAdminUserRole()")
    public String homePage (Model model) {

        model.addAttribute("adminWelcomeMessage", Messages.adminMessage());
        model.addAttribute(Messages.currentPage(), "Home");

        return "html/home";
    }

    @GetMapping("/admin/token-chart")
    public String tokenUsageChart (Model model) {

        model.addAttribute(Messages.currentPage(), "Chart");

        Dataset dataset = statisticsService.tokenUsageDataset();

        List<String> labels = dataset.getLabels();
        List<Integer> data = dataset.getData();
        List<String> backgroundColors = ColorSelector.colors(data.size());

        model.addAttribute("chart", new Chart(labels, data, backgroundColors, Messages.tokenChartString()));
        model.addAttribute(Messages.chartDateString(), new ChartDate());

        return "html/chart";
    }

    @GetMapping("/admin/token-chart/search")
    public String tokenUsageChart (Model model, @RequestParam String startingDate, @RequestParam String endingDate) {

        startingDate = getDateFormat(startingDate);
        endingDate = getDateFormat(endingDate);

        model.addAttribute(Messages.currentPage(), "Chart");

        Dataset dataset = statisticsService.tokenUsageDatasetByDate(startingDate, endingDate);

        List<String> labels = dataset.getLabels();
        List<Integer> data = dataset.getData();
        List<String> backgroundColors = ColorSelector.colors(data.size());

        model.addAttribute("chart", new Chart(labels, data, backgroundColors, Messages.tokenChartString()));
        model.addAttribute(Messages.chartDateString(), new ChartDate());

        return "html/chart";
    }

    private void setTokenUsageChartModel (String startingDate, String endingDate, Model model) {

        model.addAttribute(Messages.currentPage(), "Chart");

//        if (startingDate.is)
        Dataset dataset = statisticsService.tokenUsageDatasetByDate(startingDate, endingDate);

        List<String> labels = dataset.getLabels();
        List<Integer> data = dataset.getData();
        List<String> backgroundColors = ColorSelector.colors(data.size());

        model.addAttribute("chart", new Chart(labels, data, backgroundColors, Messages.tokenChartString()));
        model.addAttribute(Messages.chartDateString(), new ChartDate());
    }

    @GetMapping("/admin/chat-chart")
    public String chatRatingChart (Model model) {

        model.addAttribute(Messages.currentPage(), "Chart");

        Dataset dataset = statisticsService.chatRatingDataset();

        List<String> labels = dataset.getLabels();
        List<Integer> data = dataset.getData();
        List<String> backgroundColors = ColorSelector.colors(data.size());

        model.addAttribute("chart", new Chart(labels, data, backgroundColors, Messages.chatChartString()));
        model.addAttribute(Messages.chartDateString(), new ChartDate());

        return "html/chart";
    }

    @GetMapping("/admin/chat-chart/search")
    public String chatRatingChart (Model model, @RequestParam String startingDate, @RequestParam String endingDate) {

        startingDate = getDateFormat(startingDate);
        endingDate = getDateFormat(endingDate);

        model.addAttribute(Messages.currentPage(), "Chart");

        Dataset dataset = statisticsService.chatRatingDatasetByDate(startingDate, endingDate);

        List<String> labels = dataset.getLabels();
        List<Integer> data = dataset.getData();
        List<String> backgroundColors = List.of("red", "orange", "green");

        model.addAttribute("chart", new Chart(labels, data, backgroundColors, Messages.chatChartString()));
        model.addAttribute(Messages.chartDateString(), new ChartDate());

        return "html/chart";
    }

    private String getDateFormat (String rawDate) {
        String[] startingDateArray = rawDate.split("-");
        return startingDateArray[2] + "-"+ startingDateArray[1] + "-" + startingDateArray[0];
    }

    @GetMapping("/admin/users/username_id/mvc")
    public String allUsernames_id (Model model) {

        List<UserBuilder> userBuilders = readUsersAdminService.allUsername_id();

        setUsersPageModal(model, userBuilders);

        return "html/users";
    }

    @GetMapping("/admin/users/username_id/search/mvc")
    public String search (@RequestParam String search,Model model) {

        List<UserBuilder> userBuilders = readUsersAdminService.search(search);

        setUsersPageModal(model, userBuilders);

        return "html/users";
    }

    private void setUsersPageModal (Model model, List<UserBuilder> userBuilders) {
        model.addAttribute(Messages.currentPage(), "Users");
        model.addAttribute(Messages.allUsername_IDString(), userBuilders);
    }

    @GetMapping("/admin/user/update/mvc")
    public String username_id (@RequestParam int id, Model model)
    {
        UserBuilder userBuilder = readUsersAdminService.username_id(id);

        model.addAttribute(Messages.currentPage(), "Users");

        model.addAttribute("header", "Username update");
        model.addAttribute("userBuilder", userBuilder);
        model.addAttribute("updated", false);

        return "html/user-edit";
    }

    @PatchMapping("/admin/user/update/mvc")
    public String updateUsername (@ModelAttribute UserBuilder userBuilder, Model model)
    {

        ResponseEntity<String> response = (ResponseEntity<String>) userUpdateService.updateUsernameByID(userBuilder);

        model.addAttribute(Messages.currentPage(), "Users");

        model.addAttribute("header", response.getBody());
        model.addAttribute("updated", true);

        return "html/user-edit";
    }


    @GetMapping("/admin/chat-rating/mvc")
    public String chatRating (Model model)
    {

        ResponseEntity<List<ChatRating>> chatRatingResponseEntity = (ResponseEntity<List<ChatRating>>) chatRatingService.getAllChatRating();

        List<ChatRating> chatRatingList = chatRatingResponseEntity.getBody();

        setChatRatingModel(model,chatRatingList);

        return "html/user_chatMessages";
    }

    @DeleteMapping("/admin/chat-rating/mvc")
    public String chatRating (@ModelAttribute UIChatRating uiChatRating, Model model)
    {
        model.addAttribute(Messages.currentPage(), "Chat Rating");

        chatRatingService.deleteChatRatings(uiChatRating.getToDeleteChatRatingID().stream().toList());

        ResponseEntity<List<ChatRating>> chatRatingResponseEntity = (ResponseEntity<List<ChatRating>>) chatRatingService.getAllChatRating();

        setChatRatingModel(model,chatRatingResponseEntity.getBody());
        return "html/user_chatMessages";
    }

    private void setChatRatingModel (Model model, List<ChatRating> chatRatingList) {
        UIChatRating uiChatRating = new UIChatRating(chatRatingList, new HashSet<>());
        model.addAttribute("header", "User-Bot Chat Ratings");
        model.addAttribute("chatRatingList", chatRatingList);
        model.addAttribute("uiChatRating", uiChatRating);
        model.addAttribute(Messages.currentPage(), "Chat Rating");
    }


    @GetMapping("/admin/feedback/mvc")
    public String feedback(Model model) {

        setTeacherMessageModel(model);

        return "html/feedbacks";
    }

    @DeleteMapping("/admin/feedback/mvc/{id}")
    public String feedback(@PathVariable int id, Model model) {

        userFeedbackService.deleteUserFeedback(id);
        setTeacherMessageModel(model);

        return "redirect:/admin/feedback/mvc";
    }

    private void setTeacherMessageModel (Model model) {
        List<TeacherMessage> teacherMessageList = userFeedbackService.getUserFeedback();
        model.addAttribute("feedbacks", teacherMessageList);
    }
}
