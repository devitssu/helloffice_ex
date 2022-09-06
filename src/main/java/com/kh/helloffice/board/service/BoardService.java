package com.kh.helloffice.board.service;

import java.util.List;

import com.kh.helloffice.board.entity.PageVo;
import com.kh.helloffice.board.entity.PostDto;
import com.kh.helloffice.board.entity.ReplyDto;

public interface BoardService {

	List<PostDto> getList(PageVo pageVo, String category) throws Exception;

	int post(PostDto post) throws Exception;

	PostDto getPost(long no) throws Exception;

	int editPost(PostDto post) throws Exception;

	int deletePost(long no) throws Exception;

	int getTotalPostNum(long boardNo) throws Exception;

	List<PostDto> getRecentList() throws Exception;

    int addReply(ReplyDto reply) throws Exception;

    List<ReplyDto> getReplyList(long no) throws Exception;

	int editReply(ReplyDto reply) throws Exception;

	int deleteReply(long replyNo) throws Exception;
}
