package acktsap.webservlet.handlermethod.arguments;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import acktsap.webservlet.Event;

/**
 * ModelAttribute의 다른 용법
 *
 * - @RequestMapping을 사용한 핸들러 메소드의 아규먼트에 사용하기 (이미 살펴 봤습니다.)
 *
 * - @Controller 또는 @ControllerAdvice (이 애노테이션은 뒤에서 다룹니다.)를 사용한 클래스에서 모델 정보를 초기화 할 때 사용한다.
 *
 * - @RequestMapping과 같이 사용하면 해당 메소드에서 리턴하는 객체를 모델에 넣어 준다.
 */
@Controller
@RequestMapping("/modelattribute2")
public class CommonModelAttributeController {

    @ModelAttribute
    public void categories(Model model) {
        List<String> list = new ArrayList<>();
        list.add("study");
        list.add("seminar");
        list.add("hobby");
        list.add("social");
        model.addAttribute("categories", list);
    }

    @GetMapping("/list")
    public String getEvents(Model model) {
        System.out.println("=== : " + model);
        Event spring = new Event();
        spring.setName("spring");
        spring.setLimit(10);

        Event newEvent = (Event) model.asMap().get("newEvent");

        List<Event> eventList = new ArrayList<>();
        eventList.add(spring);
        eventList.add(newEvent);

        model.addAttribute(eventList);

        return "/events/list";
    }

}
