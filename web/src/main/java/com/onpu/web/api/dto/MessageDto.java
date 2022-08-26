package com.onpu.web.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {
    String id;
    String text;
    UserSubscribersDto author;
    Long timestamp;
    EventType eventType;
    List<CommentDto> comments;
    String link;
    String linkTitle;
    String linkDescription;
    String linkCover;
}
