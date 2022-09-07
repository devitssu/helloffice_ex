package com.kh.helloffice.board.dao;

import java.util.List;

import com.kh.helloffice.board.entity.FileInfoDto;
import com.kh.helloffice.board.entity.ReplyDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.helloffice.board.entity.PageVo;
import com.kh.helloffice.board.entity.PostDto;

@Repository
@Slf4j
public class BoardDaoImpl implements BoardDao{

	@Autowired
	private SqlSession session;
	
	@Override
	public List<PostDto> getList(PageVo pageVo) throws Exception {
		return session.selectList("board.getPostList", pageVo);
	}

	@Override
	public int post(PostDto post) throws Exception {
		return session.insert("board.insertPost", post);
	}

	@Override
	public PostDto getPost(long no) throws Exception {
		return session.selectOne("board.getPost", no);
	}

	@Override
	public int editPost(PostDto post) throws Exception {
		return session.update("board.updatePost", post);
	}

	@Override
	public int deletePost(long no) throws Exception {
		return session.delete("board.deletePost", no);
	}

	@Override
	public int getTotalPostNum(long boardNo) throws Exception {
		return session.selectOne("board.getTotalPostNum", boardNo);
	}

	@Override
	public List<PostDto> getRecentList() throws Exception {
		return session.selectList("board.getRecentList");
	}

	@Override
	public int addReply(ReplyDto reply) throws Exception {
		return session.insert("board.insertReply",reply);
	}

	@Override
	public List<ReplyDto> getReplyList(long no) throws Exception {
		return session.selectList("board.getReplyList", no);
	}

	@Override
	public int editReply(ReplyDto reply) throws Exception {
		return session.update("board.updateReply", reply);
	}

	@Override
	public int deleteReply(long replyNo) throws Exception {
		return session.update("board.deleteReply",replyNo);
	}

	@Override
	public int increaseViewCnt(long no) throws Exception {
		return session.update("board.increaseViewCnt", no);
	}

	@Override
	public int uploadFiles(FileInfoDto file) throws Exception {
		return session.insert("board.insertFile", file);
	}

	@Override
	public List<FileInfoDto> getFiles(long no) throws Exception {
		return session.selectList("board.getFiles", no);
	}

}
