package acktsap.sample.demowebmvc;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class Event {

  public interface Group1 {
  }
  public interface Common {
  }

  private Integer id;

  @NotBlank(groups = {Group1.class, Common.class})
  private String name;

  @Min(value = 0, groups = Common.class) // @Valid, @Validated랑 같이 사용
  private Integer limit;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate startDate;
 
}
