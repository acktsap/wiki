/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.task;

import lombok.Getter;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;

public class ReadTask implements Step {

  @Getter
  protected final String name = "read";

  @Getter
  protected final boolean allowStartIfComplete = false;

  @Getter
  protected final int startLimit = 10;

  @Override
  public void execute(final StepExecution stepExecution) throws JobInterruptedException {

  }

}
