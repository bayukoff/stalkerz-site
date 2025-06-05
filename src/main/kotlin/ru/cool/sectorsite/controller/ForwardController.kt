package ru.cool.sectorsite.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ForwardController {
    @GetMapping("/{path:^(?!api|static|css|assets|images|index\\.html).*}")
    fun forwardSingleLevel(): String {
        return "forward:/index.html"
    }

    @GetMapping("/{path:^(?!api|static|css|assets|images|index\\.html).*$}/**")
    fun forwardMultiLevel(): String {
        return "forward:/index.html"
    }
}