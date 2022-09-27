package com.kh.helloffice.board.dao;

import java.util.List;
import java.util.Map;

import com.kh.helloffice.board.entity.*;

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

    int deleteFile(Long fileNo) throws Exception;

	int postReply(PostDto post) throws Exception;

    List<PostDto> getNoticeList() throws Exception;

    List<BoardDto> getCategoryList(long boardNo) throws Exception;

    long getDepNo(long boardNo) throws Exception;

    List<BoardDto> getCategoryListForUser(Map<String, Long> map);
}
