package com.kh.helloffice.board.dao;

import java.util.List;
import java.util.Map;

import com.kh.helloffice.board.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    @Override
    public FileInfoDto getFile(long fileNo) throws Exception {
        return session.selectOne("board.getFile", fileNo);
    }

    @Override
    public int increaseDownloadCnt(long fileNo) throws Exception {
        return session.update("board.increaseDownloadCnt",fileNo);
    }

	@Override
	public int deleteFile(Long fileNo) throws Exception {
		return session.update("board.deleteFile",fileNo);
	}

	@Override
	public int postReply(PostDto post) throws Exception {
		return session.insert("board.insertReplyPost", post);
	}

	@Override
	public List<PostDto> getNoticeList() throws Exception {
		return session.selectList("board.getNoticeList");
	}

	@Override
	public List<BoardDto> getCategoryList(long depNo) throws Exception {
		return session.selectList("board.getCategoryList", depNo);
	}

	@Override
	public long getDepNo(long boardNo) throws Exception {
		return session.selectOne("board.getBoardDepNo", boardNo);
	}

	@Override
	public List<BoardDto> getCategoryListForUser(Map<String, Long> map) {
		return session.selectList("board.getCategoryListForUser", map);
	}
}
