package acktsap.sample.webservlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filter
 *
 * A filter is an object that performs filtering tasks on either the request to a resource (a
 * servlet or static content), or on the response from a resource, or both.
 */
public class MyFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("Filter Init");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    System.out.println("Filter: " + getClass());

    // necessary, for filter chain
    // last filter connect this to servelt
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    System.out.println("Filter Destroy");
  }
}
