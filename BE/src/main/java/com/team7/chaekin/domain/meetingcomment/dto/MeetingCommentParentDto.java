package com.team7.chaekin.domain.meetingcomment.dto;

import com.team7.chaekin.domain.meetingcomment.entity.MeetingComment;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Builder
@Data
public class MeetingCommentParentDto {
    private final static String DELETE_MESSAGE = "삭제된 댓글입니다.";

    private long meetingCommentId;
    private String writer;
    private String content;
    private String createdAt;
    private Boolean isRemoved;

    public static MeetingCommentParentDto toDto(MeetingComment mc) {
        return MeetingCommentParentDto.builder()
                .meetingCommentId(mc.isRemoved() ? 0L : mc.getId())
                .content(mc.isRemoved() ? DELETE_MESSAGE : mc.getContent())
                .writer(mc.isRemoved() ? "" : mc.getMember().getNickname())
                .createdAt(mc.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm")))
                .isRemoved(mc.isRemoved()).build();
    }
}
