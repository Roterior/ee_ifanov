package com.accenture.flowershop.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringWebAppInitializer
        implements WebApplicationInitializer
{

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

//        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//        ctx.register(ApplicationContextConfig.class);
//        ctx.setServletContext(servletContext);
//
//        ServletRegistration.Dynamic servlet = servletContext.addServlet("springDispatcherServlet", new DispatcherServlet(ctx));
//
//        servlet.setLoadOnStartup(1);
//        servlet.addMapping("/");







        // Create the root Spring application context
//        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.register(AppConfig.class);
//
//        servletContext.addListener(new ContextLoaderListener(rootContext));
//
//        // Create the dispatcher servlet's application context
//        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
//        dispatcherContext.register(ApplicationContextConfig.class);
//
//        // Register and map the dispatcher servlet
//        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/");






//        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.register(ApplicationContextConfig.class);
//        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(rootContext);
//        servletContext.addListener(contextLoaderListener);
//        servletContext.addListener(new ContextLoaderListener(rootContext));

//        servletContext.setInitParameter("contextInitializerClasses", "mvctest.web.DemoApplicationContextInitializer");
//        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
//        webContext.register(MvcConfiguration.class);
//        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
//        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
//        dispatcher.addMapping("/");
//        rootContext.setServletContext(servletContext);






        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(ApplicationContextConfig.class);
//        servletContext.addListener(new ContextLoaderListener(appContext));

//        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//        ctx.register(ApplicationContextConfig.class);
        appContext.setServletContext(servletContext);




        // Dispatcher Servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        dispatcher.setInitParameter("contextClass", appContext.getClass().getName());


        // UTF8 Charactor Filter.
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);

        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
    }

}
