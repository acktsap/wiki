/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.nullsafety;

import java.util.Objects;
import org.assertj.core.annotations.NonNull;
import org.springframework.stereotype.Service;

/**
 * NullSafety : (툴의 지원을 받아) 컴파일 시점에 최대한 NullPointerException을 방지하는 것
 * 
 * -> 설정 제대로 안되어 있으면 안될 수도 있음
 * 
 * > 그냥 null check해 {@link java.util.Objects#requireNonNull(Object)}.
 */
@Service
public class EventService {

  public String createEvent(@NonNull final String name) {
    // recommaned (by acktsap)
    String eventName = Objects.requireNonNull(name, "Name most not null");
    return "Hello " + eventName;
  }

}
