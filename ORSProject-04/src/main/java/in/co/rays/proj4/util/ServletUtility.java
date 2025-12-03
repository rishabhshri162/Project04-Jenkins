package in.co.rays.proj4.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.controller.BaseCtl;
import in.co.rays.proj4.controller.ORSView;

/**
 * ServletUtility is a helper class that provides common utility methods
 * for forwarding, redirecting, setting messages, and storing data
 * in the request scope while working with Servlets and JSPs.
 */
public class ServletUtility {

    /**
     * Forwards the request to a given JSP/Controller.
     *
     * @param page    target JSP or controller
     * @param request HTTP request
     * @param response HTTP response
     */
    public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    /**
     * Redirects the client to another page.
     *
     * @param page    target URL
     * @param request HTTP request
     * @param response HTTP response
     */
    public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.sendRedirect(page);
    }

    /**
     * Retrieves error message stored under given key.
     */
    public static String getErrorMessage(String property, HttpServletRequest request) {
        String val = (String) request.getAttribute(property);
        return val == null ? "" : val;
    }

    /**
     * Retrieves generic message stored under given key.
     */
    public static String getMessage(String property, HttpServletRequest request) {
        String val = (String) request.getAttribute(property);
        return val == null ? "" : val;
    }

    /**
     * Stores an error message in request scope.
     */
    public static void setErrorMessage(String msg, HttpServletRequest request) {
        request.setAttribute(BaseCtl.MSG_ERROR, msg);
    }

    /**
     * Returns global error message.
     */
    public static String getErrorMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
        return val == null ? "" : val;
    }

    /**
     * Stores a success message in request scope.
     */
    public static void setSuccessMessage(String msg, HttpServletRequest request) {
        request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
    }

    /**
     * Returns global success message.
     */
    public static String getSuccessMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
        return val == null ? "" : val;
    }

    /**
     * Stores a bean object in request scope.
     */
    public static void setBean(BaseBean bean, HttpServletRequest request) {
        request.setAttribute("bean", bean);
    }

    /**
     * Retrieves the bean stored in request scope.
     */
    public static BaseBean getBean(HttpServletRequest request) {
        return (BaseBean) request.getAttribute("bean");
    }

    /**
     * Retrieves request parameter safely.
     */
    public static String getParameter(String property, HttpServletRequest request) {
        String val = request.getParameter(property);
        return val == null ? "" : val;
    }

    /**
     * Sets a list in request scope.
     */
    public static void setList(List list, HttpServletRequest request) {
        request.setAttribute("list", list);
    }

    /**
     * Returns list stored in request scope.
     */
    public static List getList(HttpServletRequest request) {
        return (List) request.getAttribute("list");
    }

    /**
     * Stores page number for list controllers.
     */
    public static void setPageNo(int pageNo, HttpServletRequest request) {
        request.setAttribute("pageNo", pageNo);
    }

    /**
     * Returns page number.
     */
    public static int getPageNo(HttpServletRequest request) {
        return (Integer) request.getAttribute("pageNo");
    }

    /**
     * Stores page size in request.
     */
    public static void setPageSize(int pageSize, HttpServletRequest request) {
        request.setAttribute("pageSize", pageSize);
    }

    /**
     * Returns page size.
     */
    public static int getPageSize(HttpServletRequest request) {
        return (Integer) request.getAttribute("pageSize");
    }

    /*
     * Exception handler (disabled)
     *
     * public static void handleException(Exception e, HttpServletRequest request,
     * HttpServletResponse response) throws IOException, ServletException {
     *     request.setAttribute("exception", e);
     *     response.sendRedirect(ORSView.ERROR_CTL);
     * }
     */
}
