package com.kh.helloffice.board.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.helloffice.board.entity.*;
import com.kh.helloffice.member.entity.DeptEmp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kh.helloffice.board.service.BoardService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("board/{boardNo}")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	private final BoardService service;
	@GetMapping
	public String board(@PathVariable long boardNo,
						@RequestParam(defaultValue = "전체") String category,
						@RequestParam(defaultValue = "") String search,
						@RequestParam(defaultValue = "1") String page,
						@RequestParam(defaultValue = "10") String count,
						Model model,
						HttpSession session) throws Exception {
		int pageNum = 5;
		int totalRow = service.getTotalPostNum(boardNo);
		DeptEmp loginEmp = (DeptEmp) session.getAttribute("loginEmp");

		PageVo pageVo = new PageVo(page, count, pageNum, totalRow);
		pageVo.setBoardNo(boardNo);
		pageVo.setCategory(category);
		pageVo.setSearch(search);

		List<BoardDto> categories = null;
		if(loginEmp.getAdminLevel() > 1){
			categories = service.getCategoryList(boardNo);
		}else{
			categories = service.getCategoryList(boardNo, loginEmp.getEmpNo());
		}
		List<PostDto> list =  service.getList(pageVo, category);
		List<PostDto> noticeList = service.getNoticeList();

		System.out.println(pageVo);

		model.addAttribute("page", pageVo);
		model.addAttribute("list", list);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("categories", categories);
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
					   @RequestParam(required = false) List<MultipartFile> fileList) throws Exception {
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
        Map<Long, String> fileMap = service.getFileMap(no);

		model.addAttribute("post", post);
		model.addAttribute("fileMap", fileMap);
		return "board/edit-post";
	}
	
	@PostMapping("post/{no}")
	public String update(@PathVariable String boardNo, 
						 @PathVariable long no,
						 @RequestParam(required = false) List<MultipartFile> fileList,
						 @RequestParam(required = false) String delFileList,
						 PostDto post) throws Exception {
		int delResult = 0;
		if(!delFileList.isEmpty()){
			List<Long> delFileNo = new ArrayList<>();
			ObjectMapper objectMapper = new ObjectMapper();
			Map<Long, Long> delFileMap = objectMapper.readValue(delFileList, new TypeReference<Map<Long, Long>>(){});

			for (Map.Entry<Long, Long> entry: delFileMap.entrySet()) {
				delFileNo.add(entry.getValue());
			}
			delResult = service.deleteFiles(delFileNo);
		}else delResult = 1;
		if(delResult != 0){
			int result = service.editPost(post, fileList);
			if(result > 0) {
				return "redirect:/board/" + boardNo + "/" + no;
			}else {
				return "redirect:/board/" + boardNo + "/post/" + no;
			}
		}else return "redirect:/board/" + boardNo + "/post/" + no;
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

	@GetMapping("{no}/file/{fileNo}")
	@ResponseBody()
	public ResponseEntity<Resource> downloadFile(@PathVariable long fileNo, HttpServletResponse response) throws Exception {
		FileInfoDto file = service.getFile(fileNo);
		if(file == null) throw new NotFoundException("첨부파일이 존재하지 않습니다.");

		String filePath = file.getSavePath();
		String fileName = file.getOriginName();
		fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		File f =new File(filePath);
		if(!f.isFile()) throw new NotFoundException("첨부파일이 존재하지 않습니다.");

		Resource resource = new FileSystemResource(f);

		service.increaseDownloadCnt(fileNo);
		return ResponseEntity.ok()
							 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName + "\"")
				.contentLength(f.length())
				.body(resource);
	}

	@GetMapping("post/{no}/re")
	public String replyPage(@PathVariable long no, Model model) throws Exception {
		PostDto refPost = service.getPost(no);
		model.addAttribute("refPost",refPost);
		return "board/reply";
	}

	@PostMapping("post/{no}/re")
	public String postReply(@PathVariable long boardNo,
							@PathVariable long no,
							@RequestParam(required = false) List<MultipartFile> fileList,
							PostDto post) throws Exception{
		post.setRef(no);
		post.setDepth(post.getDepth() + 1);
		int result = service.postReply(post, fileList);
		if(result > 0) {
			return "redirect:/board/" + boardNo;
		}else {
			return "error";
		}
	}

}
