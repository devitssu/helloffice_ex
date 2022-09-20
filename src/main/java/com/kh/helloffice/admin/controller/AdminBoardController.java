package com.kh.helloffice.admin.controller;

import com.kh.helloffice.AdminLevel;
import com.kh.helloffice.Level;
import com.kh.helloffice.admin.service.AdminBoardService;
import com.kh.helloffice.board.entity.FileInfoDto;
import com.kh.helloffice.board.entity.PageVo;
import com.kh.helloffice.board.entity.PostDto;
import com.kh.helloffice.board.service.BoardService;
import com.kh.helloffice.hr.entity.DeptDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/board")
@Level(adminLevel = AdminLevel.ADMIN)
public class AdminBoardController {

    private final AdminBoardService adminService;
    private final BoardService boardService;

    @GetMapping
    public String boardAdminPage(Model model) throws Exception {
        List<DeptDto> boardList = adminService.getBoardList();
        model.addAttribute("boardList", boardList);
        return "admin/board/main";
    }

    @GetMapping("/{boardNo}")
    public String boardAdminDetail(@PathVariable long boardNo,
                                   @RequestParam(defaultValue = "전체") String category,
                                   @RequestParam(defaultValue = "") String search,
                                   @RequestParam(defaultValue = "1") String page,
                                   @RequestParam(defaultValue = "10") String count,
                                   Model model) throws Exception {

        int pageNum = 5;
        int totalRow = boardService.getTotalPostNum(boardNo);

        PageVo pageVo = new PageVo(page, count, pageNum, totalRow);
        pageVo.setBoardNo(boardNo);
        pageVo.setCategory(category);
        pageVo.setSearch(search);

        List<PostDto> list =  boardService.getList(pageVo, category);
        List<PostDto> noticeList = boardService.getNoticeList();

        model.addAttribute("page", pageVo);
        model.addAttribute("list", list);
        model.addAttribute("noticeList", noticeList);

        return "admin/board/board";
    }

    @GetMapping("/{boardNo}/{postNo}")
    public String adminPost(@PathVariable long postNo,
                            @PathVariable long boardNo,
                            Model model) throws Exception {
        PostDto post = boardService.getPost(postNo);
        List<FileInfoDto> fileList = boardService.getFiles(postNo);

        model.addAttribute("boardNo", boardNo);
        model.addAttribute("post", post);
        model.addAttribute("fileList",fileList);
        return "admin/board/post";
    }

    @GetMapping("/{boardNo}/post/{postNo}")
    public String adminEditPostPage(@PathVariable long postNo,
                                    Model model) throws Exception {
        PostDto post = boardService.getPost(postNo);
        Map<Long, String> fileMap = boardService.getFileMap(postNo);

        model.addAttribute("post", post);
        model.addAttribute("fileMap", fileMap);

        return "admin/board/edit-post";
    }

    @PatchMapping("/{boardNo}/post/{postNo}")
    public String editPost(@PathVariable long boardNo,
                           @PathVariable long postNo,
                           PostDto post) throws Exception {
        int result = adminService.editPost(post);
        if(result > 0) return "redirect:/admin/board/" + boardNo + "/" + postNo;
        else return "redirect:/admin/board/" + boardNo + "/post/" + postNo;
    }

    @DeleteMapping("/{boardNo}/{postNo}")
    public String deletePost(@PathVariable long boardNo,
                             @PathVariable long postNo) throws Exception {
        int result = boardService.deletePost(postNo);
        if(result > 0) return "redirect:/admin/board/" + boardNo;
        else return "redirect:/admin/board/" + boardNo + "/" + postNo;
    }

    @GetMapping("/post")
    public String notice(){
        return "admin/board/add-notice";
    }

    @PostMapping("/post")
    public String addNotice(PostDto post,
                            @RequestParam(required = false) List<MultipartFile> fileList) throws Exception {
        int result = adminService.addNotice(post, fileList);
        if(result > 0) return "redirect:/admin/board/1";
        else return "redirect:/admin/board/post";
    }

}
