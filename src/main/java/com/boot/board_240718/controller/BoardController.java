package com.boot.board_240718.controller;

import com.boot.board_240718.model.Board;
import com.boot.board_240718.repository.BoardRepository;
import com.boot.board_240718.validator.BoardValidator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
//    public String list(Model model, Pageable pageable){
//    public String list(Model model, @PageableDefault(size = 2) Pageable pageable, @RequestParam String searchText){
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText){
        log.info("@# list");
        log.info("@# searchText=>"+searchText);

//        List<Board> boards = boardRepository.findAll();
//        Page<Board> boards = boardRepository.findAll(PageRequest.of(1, 20));
//        Page<Board> boards = boardRepository.findAll(PageRequest.of(0, 20));
//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContent(searchText, searchText, pageable);
        // 최종: 5개 이전 페이지 표시 + 5개 이후 페이지 표시
        // 105 페이지 일 때 101~110 페이지 표시
//        int startPage = Math.max(0, boards.getPageable().getPageNumber() - 4);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);

//        log.info("@# boards=>"+boards);
//        log.info("@# pageable=>"+pageable);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);

        return "board/list";
    }

    @GetMapping("/form")
//    public String form(Model model){
//    public String form(Model model, @RequestParam Long id){
    public String form(Model model, @RequestParam(required = false) Long id){
        log.info("@# GetMapping form()");
        log.info("@# GetMapping form() id=>"+id);
        if (id != null){
//            Optional<Board> board = boardRepository.findById(id);
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
            log.info("@# GetMapping form() board=>"+board);
        }else  {
            model.addAttribute("board",new Board());
        }
        return "board/form";
    }

    @PostMapping("/form")
//    public String form(@ModelAttribute Board board, Model model) {
    public String checkPersonInfo(@Valid Board board, BindingResult bindingResult) {
        boardValidator.validate(board, bindingResult);

        if (bindingResult.hasErrors()) {
            return "board/form";
        }

        boardRepository.save(board);

        return "redirect:/board/list";
    }
}
