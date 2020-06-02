package acktsap.sample.webservlet.handlermethod.arguments;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 
 * MultipartFile
 * 
 * - 파일 업로드시 사용하는 메소드 아규먼트
 * 
 * - MultipartResolver 빈이 설정 되어 있어야 사용할 수 있다. (스프링 부트 자동 설정이 해 줌)
 * 
 * - POST multipart/form-data 요청에 들어있는 파일을 참조할 수 있다.
 * 
 * - List<MultipartFile> 아큐먼트로 여러 파일을 참조할 수도 있다.
 */
@Controller
public class FileController {

  // open localhost:8080/file
  // see files/index.html

  @GetMapping("/file")
  public String fileUploadForm(Model model) {
    return "files/index";
  }

  @PostMapping("/file")
  public String fileUpload(@RequestParam MultipartFile file,
      RedirectAttributes attributes) {

    // save file to repository

    System.out.println("file name: " + file.getName());
    System.out.println("file original name: " + file.getOriginalFilename());
    String message = file.getOriginalFilename() + " is uploaded";
    attributes.addFlashAttribute("message", message);
    return "redirect:/file";
  }

}
