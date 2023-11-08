package tech.hiphone.commons.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.commons.domain.OperationLog;
import tech.hiphone.commons.repository.OperationLogRepository;
import tech.hiphone.commons.security.SecurityUtils;
import tech.hiphone.commons.security.jwt.JWTFilter;

public class OperationLogFilter extends OncePerRequestFilter {

    public static List<String> LOG_METHOD_LIST = Arrays.asList("POST", "PUT", "DELETE");

    private OperationLogRepository operationLogRepository;

    public OperationLogFilter(OperationLogRepository operationLogRepository) {
        super();
        this.operationLogRepository = operationLogRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        filterChain.doFilter(request, response);

        if (needsLog(request, response)) {
            String method = request.getMethod();
            String path = request.getServletPath();
            OperationLog operationLog = new OperationLog();
            operationLog.setMethod(method);
            operationLog.setPath(path);

            operationLogRepository.save(operationLog);
        }
    }

    private boolean needsLog(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getMethod();

        if (LOG_METHOD_LIST.contains(method)) {
            if (SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.ADMIN,
                    AuthoritiesConstants.MANAGER)) {
                return true;
            } else {
                String authorization = response.getHeader(JWTFilter.AUTHORIZATION_HEADER);
                if (StringUtils.isNotEmpty(authorization)) {
                    return true;
                }
            }
        }
        return false;
    }

}
