package com.ohgiraffers.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@Slf4j
public class MainController {

    @GetMapping String defaultLocation() {

        String name = "박태근";
        String job = "강사";

        System.out.println("메인페이지로 이동합니다.");
        log.info("저는 {}이구요 {}이라고 합니다.",job,name);

        return "main";
    }
}
