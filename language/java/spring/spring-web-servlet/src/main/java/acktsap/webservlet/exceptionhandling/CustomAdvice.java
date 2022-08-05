package acktsap.webservlet.exceptionhandling;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 예외 처리, 바인딩 설정, 모델 객체를 모든 컨트롤러 전반에 걸쳐 적용하고 싶은 경우에 사용한다.
 *
 * - @ExceptionHandler
 *
 * - @InitBinder
 *
 * - @ModelAttributes
 *
 * 적용할 범위를 지정할 수도 있다.
 *
 * - 특정 애노테이션을 가지고 있는 컨트롤러에만 적용하기
 *
 * - 특정 패키지 이하의 컨트롤러에만 적용하기
 *
 * - 특정 클래스 타입에만 적용하기
 */
@ControllerAdvice(assignableTypes = {CustomExceptionHandlerController.class})
public class CustomAdvice {

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public String global(GlobalException ex) {
        return "global";
    }

    @InitBinder("event")
    public void initEventBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @ModelAttribute
    public void categories(Model model) {
        model.addAttribute("categories", "hobby");
    }
}
