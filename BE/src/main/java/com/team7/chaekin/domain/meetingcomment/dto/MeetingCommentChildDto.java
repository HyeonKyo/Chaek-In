package com.team7.chaekin.domain.meetingcomment.dto;

import com.team7.chaekin.domain.meetingcomment.entity.MeetingComment;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Builder
@Data
public class MeetingCommentChildDto {
    private long meetingCommentId;
    private String writer;
    private String content;
    private String createdAt;

    public static MeetingCommentChildDto toDto(MeetingComment child) {
        return MeetingCommentChildDto.builder()
                .meetingCommentId(child.getId())
                .content(child.getContent())
                .writer(child.getMember().getNickname())
                .createdAt(child.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm")))
                .build();
    }
}
