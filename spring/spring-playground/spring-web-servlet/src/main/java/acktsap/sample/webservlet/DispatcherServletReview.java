package acktsap.sample.webservlet;

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
import org.springframework.web.servlet.support.SessionFlashMapManager;
import org.springframework.web.servlet.theme.FixedThemeResolver;

/**
 * {@link DispatcherServlet}.
 * 
 * See interfaces in {@link DispatcherServlet#initStrategies}.
 * 
 * 잡다
 * 
 * {@link MultipartResolver} : multi file upload, default is {@link StandardServletMultipartResolver}
 * 
 * {@link LocaleResolver} : locale, default is {@link AcceptHeaderLocaleResolver}
 * 
 * {@link ThemeResolver} : default is {@link FixedThemeResolver}
 * 
 * customizing의 핵심  셋, bean 여러개 사용함
 * 
 * {@link HandlerMapping} : find handler
 * 
 * {@link HandlerAdapter} : run handler. 
 * 
 * {@link HandlerExceptionResolver} : handler exception
 * 
 * view
 * 
 * {@link RequestToViewNameTranslator} : resolve undefined view name
 * 
 * {@link ViewResolver} : finding view
 * 
 * Redirection
 * 
 * {@link FlashMapManager} : {@link FlashMap}, default is {@link SessionFlashMapManager}
 */
public class DispatcherServletReview {

}
