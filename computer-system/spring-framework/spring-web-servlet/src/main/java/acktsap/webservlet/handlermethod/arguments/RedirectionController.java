package acktsap.webservlet.handlermethod.arguments;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import acktsap.webservlet.Event;

/**
 * 리다이렉트 할 때 기본적으로 Model에 들어있는 primitive type 데이터는 URI 쿼리 매개변수에 추가된다.
 *
 * - 스프링 부트에서는 이 기능이 기본적으로 비활성화 되어 있다.
 *
 * - Ignore-default-model-on-redirect 프로퍼티를 사용해서 활성화 할 수 있다.
 *
 * 원하는 값만 리다이렉트 할 때 전달하고 싶다면 RedirectAttributes에 명시적으로 추가할 수 있다.
 *
 * 리다이렉트 요청을 처리하는 곳에서 쿼리 매개변수를 @RequestParam 또는 @ModelAttribute로 받을 수 있다.
 */
@Controller
@RequestMapping("/redirection")
public class RedirectionController {

    @GetMapping("/attribute")
    public String attribute(RedirectAttributes attributes) {
        attributes.addAttribute("name", "aaa");
        attributes.addAttribute("limit", "111");
        return "redirect:/redirection/list";
    }

    /**
     * FlashAttribute : 주로 리다이렉트시에 데이터를 전달할 때 사용한다.
     *
     * - 데이터가 URI에 노출되지 않는다.
     *
     * - 임의의 객체를 저장할 수 있다.
     *
     * - 보통 HTTP 세션을 사용한다.
     *
     * 사용법 : 리다이렉트 하기 전에 데이터를 HTTP 세션에 저장하고 리다이렉트 요청을 처리 한 다음 그 즉시 제거한다.
     *
     * RedirectAttributes를 통해 사용할 수 있다.
     */
    @GetMapping("/flashattribute")
    public String flashAttribute(RedirectAttributes attributes) {
        attributes.addFlashAttribute("name", "bbb");
        attributes.addFlashAttribute("limit", "222");
        return "redirect:/redirection/list";
    }

    @GetMapping("/list")
    @ResponseBody
    public String getEvents(@ModelAttribute("flashEvent") Event flashEvent,
                            @SessionAttribute LocalDateTime visitTime, Model model, SessionStatus status) {
        Event spring = new Event();
        spring.setName("spring");
        spring.setLimit(10);

        Event visit = new Event();
        spring.setName(visitTime.toString());

        List<Event> eventList = new ArrayList<>();
        eventList.add(spring);
        eventList.add(flashEvent);
        eventList.add(visit);

        status.setComplete(); // finish session
        model.addAttribute(eventList);

        return Integer.toString(eventList.size());
    }

}
