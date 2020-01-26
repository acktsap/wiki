package acktsap.sample.demowebmvc.handlermethod.arguments;

/**
 * 특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을 때 사용
 * 
 * 바인딩 설정
 * 
 * - webDataBinder.setDisallowedFields();
 * 
 * 포매터 설정
 * 
 * - webDataBinder.addCustomFormatter();
 * 
 * Validator 설정
 * 
 * - webDataBinder.addValidators();
 * 
 * 특정 모델 객체에만 바인딩 또는 Validator 설정을 적용하고 싶은 경우
 * 
 * - @InitBinder("event")
 *
 */
public class InitBinderController {

}
