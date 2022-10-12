package com.kh.helloffice.workflow.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushData {
    private Long seq;
    private Long sender;
    private Long formSeq;
    private Long docSeq;
    private String formName;

    private Long receiver;
    private PushType pushType;

    private String senderName;
    private String senderDep;
}
