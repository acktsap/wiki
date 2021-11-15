package acktsap.webservlet;

import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.support.SessionFlashMapManager;
import org.springframework.web.servlet.theme.FixedThemeResolver;

/**
 * {@link DispatcherServlet}. : 전통적인 Servlet에 대한 FrontController 구현체
 *
 * Core code
 *
 * - Servlet 일을하는 곳 : {@link DispatcherServlet#doService}
 *
 * - FrontController역할 : {@link DispatcherServlet#doDispatch}.
 *
 *
 * 중요한 인터페이스들 : See interfaces in {@link DispatcherServlet#initStrategies}.
 *
 * 잡다
 *
 * - {@link MultipartResolver} : multi file upload, default is
 * - {@link StandardServletMultipartResolver}
 *
 * - {@link LocaleResolver} : locale, default is {@link AcceptHeaderLocaleResolver}
 *
 * - {@link ThemeResolver} : default is {@link FixedThemeResolver}
 *
 * customizing의 핵심 셋
 *
 * - {@link HandlerMapping} : find handler. see {@link RequestMappingHandlerMapping}.
 *
 * - {@link HandlerAdapter} : run handler. see {@link RequestMappingHandlerAdapter}.
 *
 * - {@link HandlerExceptionResolver} : handler exception
 *
 * view
 *
 * - {@link RequestToViewNameTranslator} : resolve undefined view name
 *
 * - {@link ViewResolver} : finding view
 *
 * Redirection
 *
 * - {@link FlashMapManager} : {@link FlashMap}, default is {@link SessionFlashMapManager}
 */
public class DispatcherServletReview {

}
