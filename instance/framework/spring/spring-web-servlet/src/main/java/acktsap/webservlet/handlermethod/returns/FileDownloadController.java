package acktsap.webservlet.handlermethod.returns;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 파일 리소스를 읽어오는 방법
 *
 * - 스프링 ResourceLoader 사용하기 파일 다운로드 응답 헤더에 설정할 내용
 *
 * - Content-Disposition: 사용자가 해당 파일을 받을 때 사용할 파일 이름
 *
 * - Content-Type: 어떤 파일인가
 *
 * - Content-Length: 얼마나 큰 파일인가
 *
 * 파일의 종류(미디어 타입) 알아내는 방법
 *
 * - http://tika.apache.org/
 *
 * ResponseEntity
 *
 * - 응답 상태 코드
 *
 * - 응답 헤더
 *
 * - 응답 본문
 */
@Controller
public class FileDownloadController {

    // open localhost:8080/file/test.jpg

    @Autowired
    private ResourceLoader resourceLoader; // also ApplicationContext

    @GetMapping("/file/{filename:.+}") // .+ 없으면 '.jpg'가 짤림
    // @ResponseBody // 생략가능
    public ResponseEntity<Resource> fileDownload(@PathVariable String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filename);
        System.out.println("Resource: " + resource);
        File file = resource.getFile();

        Tika tika = new Tika();
        String mediaType = tika.detect(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachement; filename=\"" + resource.getFilename() + "\"") // 파일 이름
                .header(HttpHeaders.CONTENT_TYPE, mediaType) // 파일 타입
                .header(HttpHeaders.CONTENT_LENGTH, file.length() + "") // 파일 사이즈
                .body(resource);
    }

}
