package com.onpu.web.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageReadDto {
    String id;
    String text;
    UserReadDto author;
    @Builder.Default
    List<CommentDto> comments = new ArrayList<>();
    String link;
    String linkTitle;
    String linkDescription;
    String linkCover;
    Long timestamp;
}
