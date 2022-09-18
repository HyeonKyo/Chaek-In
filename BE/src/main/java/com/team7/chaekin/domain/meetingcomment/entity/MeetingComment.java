package com.team7.chaekin.domain.meetingcomment.entity;

import com.team7.chaekin.domain.common.entity.BaseTimeEntity;
import com.team7.chaekin.domain.meeting.entity.Meeting;
import com.team7.chaekin.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MeetingComment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MeetingComment parent;

    @OneToMany(mappedBy = "parent")
    private List<MeetingComment> children = new ArrayList<>();

    @Column(length = 500)
    private String content;

    private boolean isRemoved;
}
