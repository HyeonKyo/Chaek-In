package com.team7.chaekin.domain.meetingcomment.service;

import com.team7.chaekin.domain.meeting.entity.Meeting;
import com.team7.chaekin.domain.meeting.repository.MeetingRepository;
import com.team7.chaekin.domain.meetingcomment.dto.*;
import com.team7.chaekin.domain.meetingcomment.entity.MeetingComment;
import com.team7.chaekin.domain.meetingcomment.repository.MeetingCommentRepository;
import com.team7.chaekin.domain.member.entity.Member;
import com.team7.chaekin.domain.member.repository.MemberRepository;
import com.team7.chaekin.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.team7.chaekin.global.error.errorcode.DomainErrorCode.*;

@RequiredArgsConstructor
@Service
public class MeetingCommentService {
    private final MeetingCommentRepository meetingCommentRepository;
    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MeetingCommentListResponse getMeetingComments(long meetingId, Pageable pageable) {
        Meeting meeting = getMeeting(meetingId);
        Page<MeetingComment> meetingCommentPages = meetingCommentRepository.findByMeeting(meeting, pageable);

        int totalPages = meetingCommentPages.getTotalPages();
        List<MeetingCommentListDto> comments = new ArrayList<>();

        meetingCommentPages.toList().stream()
                .forEach(mc -> {
                    if (mc.getParent() != null)
                        return;
                    MeetingCommentParentDto parent = MeetingCommentParentDto.toDto(mc);
                    List<MeetingCommentChildDto> children = mc.getChildren().stream()
                            .filter(child -> !child.isRemoved())
                            .map(MeetingCommentChildDto::toDto)
                            .collect(Collectors.toList());

                    saveComments(comments, parent, children);
                });
        return new MeetingCommentListResponse(totalPages, comments);
    }

    @Transactional
    public long createComment(long meetingId, long memberId, MeetingCommentCreateRequest meetingCommentCreateRequest) {
        Meeting meeting = getMeeting(meetingId);
        Member member = getMember(memberId);

        MeetingComment meetingComment = MeetingComment.builder()
                .member(member)
                .meeting(meeting)
                .content(meetingCommentCreateRequest.getContent()).build();

        MeetingComment parent = meetingCommentCreateRequest.getParentId() == 0 ?
                null : getMeetingComment(meetingCommentCreateRequest.getParentId());
        meetingComment.addParent(parent);

        return meetingCommentRepository.save(meetingComment).getId();
    }

    @Transactional
    public long updateMeetingComment(long meetingId, long meetingCommentId,
                                     long memberId, MeetingCommentUpdateRequest meetingCommentUpdateRequest) {
        MeetingComment meetingComment = getMeetingComment(meetingCommentId);
        if (!meetingComment.getMeeting().getId().equals(meetingId)) {
            throw new CustomException(WRONG_LOCATION_COMMENT);
        }
        if (!meetingComment.getMember().getId().equals(memberId)) {
            throw new CustomException(DO_NOT_HAVE_AUTHORIZATION);
        }

        meetingComment.update(meetingCommentUpdateRequest.getContent());
        return meetingComment.getId();
    }

    @Transactional
    public void deleteMeetingComment(long meetingId, long memberId, long meetingCommentId) {
        MeetingComment meetingComment = getMeetingComment(meetingCommentId);
        if (!meetingComment.getMeeting().getId().equals(meetingId)) {
            throw new CustomException(WRONG_LOCATION_COMMENT);
        }
        if (!meetingComment.getMember().getId().equals(memberId)) {
            throw new CustomException(DO_NOT_HAVE_AUTHORIZATION);
        }
        meetingComment.delete();
    }

    private void saveComments(List<MeetingCommentListDto> comments, MeetingCommentParentDto parent, List<MeetingCommentChildDto> children) {
        if (!parent.getIsRemoved() || children.size() > 0) {
            comments.add(new MeetingCommentListDto(parent, children));
        }
    }

    private Meeting getMeeting(long meetingId) {
        return meetingRepository.findByIdAndIsRemovedIsFalse(meetingId)
                .orElseThrow(() -> new CustomException(MEETING_IS_NOT_EXIST));
    }
    private Member getMember(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_IS_NOT_EXIST));
    }

    private MeetingComment getMeetingComment(long meetingCommentId) {
        return meetingCommentRepository.findById(meetingCommentId)
                .orElseThrow(() -> new CustomException(COMMENT_IS_NOT_EXIST));
    }

}
