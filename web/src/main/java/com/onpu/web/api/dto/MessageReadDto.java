package com.onpu.web.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageReadDto {
    String id;
    String text;
    UserReadDto author;
    List<CommentDto> comments;
    String link;
    String linkTitle;
    String linkDescription;
    String linkCover;
    Long timestamp;
}
