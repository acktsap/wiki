package acktsap.webservlet.handlermethod.arguments;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import acktsap.webservlet.Event;

/**
 * SessionAttributes : 모델 정보를 HTTP 세션에 저장해주는 애노테이션
 *
 * - HttpSession을 직접 사용할 수도 있지만
 *
 * - 이 애노테이션에 설정한 이름에 해당하는 모델 정보를 자동으로 세션에 넣어준다.
 *
 * - @ModelAttribute는 세션에 있는 데이터도 바인딩한다.
 *
 * - 여러 화면(또는 요청)에서 사용해야 하는 객체를 공유할 때 사용한다 (eg. 장바구니같은거 동일하게 유지할 때)
 *
 * SessionStatus를 사용해서 세션 처리 완료를 알려줄 수 있다.
 *
 * - 폼 처리 끝나고 세션을 비울 때 사용한다.
 */
@Controller
@RequestMapping("/session")
@SessionAttributes("event") // model에 attribute로 값을 넣으면 session에 유지해 줌
public class SessionAttributesController {

    // open localhost:8080/session/events/form/name
    @GetMapping("/events/name")
    public String eventsFormName(Model model) {
        model.addAttribute("event", new Event()); // session에 event 넣음
        return "/events/form-name";
    }

    @PostMapping("/events/name")
    public String eventsFormNameSubmit(@Validated(Event.Common.class) @ModelAttribute Event event,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult);
            return "/events/form-name";
        }
        return "redirect:/session/events/limit";
    }

    @GetMapping("/events/limit")
    public String eventsFormLimit(@ModelAttribute Event event, Model model) {
        model.addAttribute("event", event);
        return "/events/form-limit";
    }

    @PostMapping("/events/limit")
    public String eventsFormLimitSubmit(@Validated(Event.Common.class) @ModelAttribute Event event,
                                        BindingResult bindingResult, SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult);
            return "/events/form-limit";
        }

        // @ModelAttribute는 session에서도 값을 가져옴. 그래서 name이 있음!!
        System.out.println("==== event: " + event);

        // session 죽여버림
        sessionStatus.setComplete();

        return "redirect:/session/events/list";
    }

    @GetMapping("/events/list")
    public String getEvents(Model model) {
        Event event = new Event();
        event.setName("spring");
        event.setLimit(10);

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

        model.addAttribute(eventList);

        return "/events/list";
    }

}
