//package com.example.config;
//
//import org.springframework.web.WebApplicationInitializer;
//
//public class MainWebAppInitializer implements WebApplicationInitializer {
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class<?>[] {};
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class<?>[] { WebConfig.class };
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[] { "/" };
//    }
//
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        //add specific encoding (e.g. UTF-8) via CharacterEncodingFilter
//        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
//        encodingFilter.setInitParameter("encoding", "UTF-8");
//        encodingFilter.setInitParameter("forceEncoding", "true");
//        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
//    }
//}