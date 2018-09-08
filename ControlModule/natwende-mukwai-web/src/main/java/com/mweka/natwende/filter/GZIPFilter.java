package com.mweka.natwende.filter;

public class GZIPFilter {//implements Filter {

//    // custom implementation of the doFilter method
//    public void doFilter(ServletRequest req,
//            ServletResponse res,
//            FilterChain chain)
//            throws IOException, ServletException {
//
//        // make sure we are dealing with HTTP
//        if (req instanceof HttpServletRequest) {
//            HttpServletRequest request
//                    = (HttpServletRequest) req;
//            HttpServletResponse response
//                    = (HttpServletResponse) res;
//      // check for the HTTP header that
//            // signifies GZIP support
//            String ae = request.getHeader("accept-encoding");
//            if (ae != null && ae.indexOf("gzip") != -1) {
//                System.out.println("GZIP supported, compressing.");
//                GZIPResponseWrapper wrappedResponse
//                        = new GZIPResponseWrapper(response);
//                chain.doFilter(req, wrappedResponse);
//                wrappedResponse.finishResponse();
//                return;
//            }
//            chain.doFilter(req, res);
//        }
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void destroy() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}