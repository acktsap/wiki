package acktsap;

public final class Spec {
  private final String description;

  public static Spec d(String description) {
    return new Spec(description);
  }


  private Spec(String description) {
    this.description = description;
  }

  public void s(DangerousRunnable spec) {
    try {
      System.out.printf("== Pattern(\"%s\") ==%n", description);
      spec.run();
      System.out.println();
    } catch (Exception e) {
      RuntimeException throughPass = new RuntimeException(e.getMessage());
      throughPass.setStackTrace(e.getStackTrace());
      throw throughPass;
    }
  }
}
