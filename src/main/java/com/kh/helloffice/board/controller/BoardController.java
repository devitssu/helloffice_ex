package com.kh.helloffice.board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.helloffice.board.entity.FileInfoDto;
import com.kh.helloffice.board.entity.ReplyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kh.helloffice.board.entity.PageVo;
import com.kh.helloffice.board.entity.PostDto;
import com.kh.helloffice.board.service.BoardService;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("board/{boardNo}")
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@GetMapping
	public String board(@PathVariable long boardNo, 
						@RequestParam(defaultValue = "전체") String category, 
						@RequestParam(defaultValue = "") String search, 
						@RequestParam(defaultValue = "1") String page, 
						@RequestParam(defaultValue = "10") String count, 
						Model model) throws Exception {
		int pageNum = 5;
		int totalRow = service.getTotalPostNum(boardNo);
		
		PageVo pageVo = new PageVo(page, count, pageNum, totalRow);
		pageVo.setBoardNo(boardNo);
		pageVo.setCategory(category);
		pageVo.setSearch(search);
		
		List<PostDto> list =  service.getList(pageVo, category);
		
		model.addAttribute("page", pageVo);
		model.addAttribute("list", list);
		return "board/board";
	}

	@GetMapping("{no}")
	public String detail(@PathVariable long no,
                         @PathVariable long boardNo,
                         Model model) throws Exception {
		PostDto post = service.getPost(no);
		List<FileInfoDto> fileList = service.getFiles(no);

		model.addAttribute("boardNo", boardNo);
		model.addAttribute("post", post);
		model.addAttribute("fileList",fileList);
		return "board/post";
	}
	
	@GetMapping("post")
	public String post() {
		return "board/add-post";
	}
	
	
	@PostMapping("post")
	public String post(PostDto post,
                       @PathVariable String boardNo,
					   @RequestParam List<MultipartFile> fileList) throws Exception {

		int result = service.post(post, fileList);
		if(result > 0) {
			return "redirect:/board/" + boardNo;
		}else {
			return "error";
		}

	}
	
	@GetMapping("post/{no}")
	public String editPost(@PathVariable long no, Model model) throws Exception {
		PostDto post = service.getPost(no);
		model.addAttribute("post", post);
		return "board/edit-post";
	}
	
	@PutMapping("post/{no}")
	public String update(@PathVariable String boardNo, 
						 @PathVariable long no, 
						 PostDto post) throws Exception {
		int result = service.editPost(post);
		if(result > 0) {
			return "redirect:/board/" + boardNo + "/" + no;			
		}else {
			return "redirect:/board/" + boardNo + "/post/" + no;
		}
	}
	
	@DeleteMapping("{no}")
	public String delete(@PathVariable String boardNo,
                         @PathVariable long no) throws Exception {
		int result = service.deletePost(no);
		if(result > 0) {			
			return "redirect:/board/" + boardNo;
		}else {
			return "redirect:/board/" + boardNo + "/" + no;		
		}
	}

	@PostMapping("{no}/reply")
	@ResponseBody
	public ReplyDto addReply(@PathVariable long no,
							 @RequestBody ReplyDto reply) throws Exception {
		reply.setPostNo(no);
		int result = service.addReply(reply);
		if(result > 0) {
			return reply;
		}else {
			return null;
		}
	}

	@GetMapping("{no}/reply")
	@ResponseBody
	public List<ReplyDto> getReplies(@PathVariable String boardNo,
									 @PathVariable long no,
									 Model model) throws Exception {
		List<ReplyDto> replies = service.getReplyList(no);
		return replies;
	}

	@PatchMapping("{no}/reply/{replyNo}")
	@ResponseBody
	public String editReply(@PathVariable long replyNo,
							@RequestBody ReplyDto reply) throws Exception {
		int result = service.editReply(reply);
		if(result > 0) return "ok";
		else return "fail";
	}

	@DeleteMapping("{no}/reply/{replyNo}")
	@ResponseBody
	public String deleteReply(@PathVariable long replyNo) throws Exception{
		int result = service.deleteReply(replyNo);
		if(result > 0 ) return "200";
		return null;
	}

}
