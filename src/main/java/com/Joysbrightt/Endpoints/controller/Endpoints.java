package com.Joysbrightt.Endpoints.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
public class Endpoints {


    @GetMapping("/firstEndPoint")
    public ResponseEntity<Object> getSlackChannelInformation(@RequestParam("slack_name") String slackName, @RequestParam("track") String track){
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        String currentDay = dayOfWeek.toString();

        Instant currentInstant = Instant.now();
        OffsetDateTime utcTime = OffsetDateTime.ofInstant(currentInstant, ZoneOffset.UTC);

        // Validate within +/- minutes
Instant twoMinutesAgo = Instant.now().minusSeconds(120);
Instant twoMinutesLater = Instant.now().plusSeconds(120);
if (currentInstant.isBefore(twoMinutesAgo) || currentInstant.isAfter(twoMinutesLater)) {

    String errorMessage = "UTC time validation failed. Current time is not within the allowed window.";

    return ResponseEntity.badRequest().body(errorMessage);
}

//           Creating a POJO representing the reponse
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("slack_name", slackName);
            responseMap.put("currentDay", currentDay);
            responseMap.put("utc_time", utcTime.toString());
            responseMap.put("track", track);
            responseMap.put("github_file_url", "https://github.com/Joysbrightt/Endpoints");
            responseMap.put("github_repo_url", "https://github.com/Joysbrightt/Endpoints");
            responseMap.put("status_code", 200);

            try{
//                Serialize the Map to Json
                ObjectMapper objectMapper = new ObjectMapper();

                String json = objectMapper.writeValueAsString(responseMap);
//      Return the JSON response with a 200 status code
                return ResponseEntity.ok(json);
            } catch (JsonProcessingException jsonProcessingException) {
                throw new RuntimeException(jsonProcessingException);
            }
    }
}
