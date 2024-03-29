package br.com.nivlabs.cliniv.config;

import br.com.nivlabs.cliniv.util.SecurityContextUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Filtro de auditoria de requisições
 * <p>
 * AuditLoggerFilter.java
 *
 * @author viniciosarodrigues
 * @since 23-08-2021
 */
@Component
public class AuditLoggerFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {
            doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
        }
    }

    protected void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (log.isInfoEnabled()) {
                log.info(request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY) + " - " + request.getRemoteAddr() + " - " + request.getRequestURI()
                        + " | REQUISICAO |>");
            }
            filterChain.doFilter(request, response);

            if (log.isInfoEnabled()) {
                logRequest(request, " | OBJETO DA REQUISICAO |>");
            }
        } finally {
            if (log.isInfoEnabled()) {
                logResponse(response, request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY) + " - " + request.getRemoteAddr() + " - " + request.getRequestURI()
                        + " | RESPOSTA |>");
            }
            response.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request, String prefix) {
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            logContent(content, request.getContentType(), request.getCharacterEncoding(), prefix);
        }
    }

    private void logResponse(ContentCachingResponseWrapper response, String prefix) {
        int status = response.getStatus();
        log.info("{} {} {}", prefix, status, HttpStatus.valueOf(status).getReasonPhrase());
        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            logContent(content, response.getContentType(), response.getCharacterEncoding(), prefix);
        }
    }

    private void logContent(byte[] content, String contentType, String contentEncoding, String prefix) {
        MediaType mediaType = MediaType.valueOf(contentType);
        boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
        if (visible) {
            try {
                String contentString = new String(content, contentEncoding);
                contentString = hidePrivateAndLongInformation(contentString, "password");
                contentString = hidePrivateAndLongInformation(contentString, "senha");
                contentString = hidePrivateAndLongInformation(contentString, "oldPassword");
                contentString = hidePrivateAndLongInformation(contentString, "newPassword");
                contentString = hidePrivateAndLongInformation(contentString, "confirmNewPassword");
                Stream.of(contentString.split("\r\n|\r|\n")).forEach(line -> log.info("{} {}", prefix, line));
            } catch (UnsupportedEncodingException e) {
                log.info("{} [{} bytes content]", prefix, content.length);
            }
        } else {
            log.info("{} [{} bytes content]", prefix, content.length);
        }
    }

    private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }

    private String hidePrivateAndLongInformation(String json, String keyword) {
        String regex = "(?:\\b" + keyword + ")\\W+\\w+";
        return json.replaceAll(regex, keyword.concat("\": \"Confidencial..."));
    }

}
