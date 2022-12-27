package com.carrot.market.member.ui

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/member")
class MemberController {

    @GetMapping("/join")
    fun getMemberJoinView(model: Model): String {
        return "member/join"
    }
}
