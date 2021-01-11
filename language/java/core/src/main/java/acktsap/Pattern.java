package acktsap;

public final class Pattern {
  private final String description;

  public static Pattern d(String description) {
    return new Pattern(description);
  }


  private Pattern(String description) {
    this.description = description;
  }

  public void p(DangerousRunnable pattern) {
    try {
      System.out.printf("== Pattern(\"%s\") ==%n", description);
      pattern.run();
      System.out.println();
    } catch (Exception e) {
      RuntimeException throughPass = new RuntimeException(e.getMessage());
      throughPass.setStackTrace(e.getStackTrace());
      throw throughPass;
    }
  }
}
