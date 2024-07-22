package com.boot.board_240718.validator;

import com.boot.board_240718.model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator {
    private static final Logger log = LoggerFactory.getLogger(BoardValidator.class);

    @Override
    public boolean supports(Class clazz) {
//        return Board.class.isAssignableFrom(clazz);
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {
        log.info("@# validate()");

        Board b = (Board) obj;
        log.info("@# b=>"+b);

        if (b.getContent().equals("")) {
            e.rejectValue("content", "key", "내용을 입력하세요.");
        }
    }
}
