package com.estu.wusic.api.controllers;


import com.estu.wusic.business.abstracts.CommentService;
import com.estu.wusic.business.requests.commentRequests.CreateCommentRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    private CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCommentRequest createCommentRequest) throws BusinessException {
        return this.commentService.add(createCommentRequest);
    }

    @GetMapping("/getCommentDtoByCommentId")
    public DataResult getCommentDtoByCommentId(@RequestParam int id){
        return this.commentService.getCommentDtoByCommentId(id);
    }

    @GetMapping("/getCommentsByCommentRecieversId")
    public DataResult getCommentsByCommentRecieversId(@RequestParam int id){
        return this.commentService.getCommentsByCommentRecieversId(id);
    }
}
