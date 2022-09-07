package com.kh.helloffice.board.dao;

import java.util.List;

import com.kh.helloffice.board.entity.FileInfoDto;
import com.kh.helloffice.board.entity.PageVo;
import com.kh.helloffice.board.entity.PostDto;
import com.kh.helloffice.board.entity.ReplyDto;

public interface BoardDao {

	List<PostDto> getList(PageVo pageVo) throws Exception;

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

    int increaseViewCnt(long no) throws Exception;

    int uploadFiles(FileInfoDto file) throws Exception;

	List<FileInfoDto> getFiles(long no) throws Exception;

    FileInfoDto getFile(long fileNo) throws Exception;

	int increaseDownloadCnt(long fileNo) throws Exception;
}
