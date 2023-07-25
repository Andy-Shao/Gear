package com.github.andyshao.exception;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 27, 2017<br>
 * Encoding:UNIX UTF-8
 * @author andy.shao
 *
 */
public enum ResultCodeEnum implements ResultCode {
    /**Continue 100*/
    CONTINUE("100", "Continue"),
    /**Switching protocol 101*/
    SWITCHING_PROTOCOL("101", "Switching Protocols"),
    /**processing 102*/
    PROCESSING("102", "Processing"),
    /**success 200*/
    SUCCESS("200", "OK"),
    /**created 201*/
    CREATED("201", "Created"),
    /**accepted 202*/
    ACCEPTED("202","Accepted"),
    /**non-authoritative information 203*/
    NON_AUTHORITATIVE_INFORMATION("203", "Non-Authoritative Information"),
    /**no content 204*/
    NO_CONTENT("204", "No Content"),
    /**rest content 205*/
    REST_CONTENT("205", "Rest Content"),
    /**partial content 206*/
    PARTIAL_CONTENT("206", "Partial Content"),
    /**multi status 207*/
    MULTI_STATUS("207", "Multi-Status"),
    /**multiple choices 208*/
    MULTIPLE_CHOICES("208", "Multiple Choices"),
    /**moved permanently 301*/
    MOVED_PERMANENTLY("301", "Moved Permanently"),
    /**found 302*/
    FOUND("302", "Found"),
    /**see other 303*/
    SEE_OTHER("303", "See Other"),
    /**not modified 304*/
    NOT_MODIFIED("304", "Not Modified"),
    /**use proxy 305*/
    USE_PROXY("305", "Use Proxy"),
    /**temporary redirect 307*/
    TEMPORARY_REDIRECT("307", "Temporary Redirect"),
    /**bad request 400*/
    BAD_REQUEST("400", "Bad Request"),
    /**un-authorized 401*/
    UNAUTHORIZED("401", "Unauthorized"),
    /**payment required 402*/
    PAYMENT_REQUIRED("402", "Payment Required"),
    /**forbidden 403*/
    FORBIDDEN("403", "Forbidden"),
    /**not found 404*/
    NOT_FOUND("404", "Not Found"),
    /**method not allowed 405*/
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
    /**not acceptable 406*/
    NOT_ACCEPTABLE("406", "Not Acceptable"),
    /**proxy authentication required 407*/
    PROXY_AUTHENTICATION_REQUIRED("407", "Proxy Authentication Required"),
    /**request timeout 408*/
    REQUEST_TIMEOUT("408", "Request Timeout"),
    /**conflict 409*/
    CONFLICT("409", "Conflict"),
    /**gone 410*/
    GONE("410", "Gone"),
    /**length required 411*/
    LENGTH_REQUIRED("411", "Length Required"),
    /**precondition failed 412*/
    PRECONDITION_FAILED("412", "Precondition Failed"),
    /**request entity too large 413*/
    REQUEST_ENTITY_TOO_LARGE("413", "Request Entity Too Large"),
    /**request uri too long 414*/
    REQUEST_URI_TOO_LONG("414", "Request-URI Too Long"),
    /**unsupported media type 415*/
    UNSUPPORTED_MEDIA_TYPE("415", "Unsupported Media Type"),
    /**requested range not satisfiable 416*/
    REQUESTED_RANGE_NOT_SATISFIABLE("416", "Requested Range Not Satisfiable"),
    /**expectation failed 417*/
    EXPECTATION_FAILED("417", "Expectation Failed"),
    /**misdirected request 421*/
    MISDIRECTED_REQUEST("421", "Misdirected Request"),
    /**unprocessable entity 422*/
    UNPROCESSABLE_ENTITY("422", "Unprocessable Entity"),
    /**locked 423*/
    LOCKED("423", "Locked"),
    /**failed dependency 424*/
    FAILED_DEPENDENCY("424", "Failed Dependency"),
    /**unordered collection 425*/
    UNORDERED_COLLECTION("425", "Unordered Collection"),
    /**upgrade required 426*/
    UPGRADE_REQUIRED("426", "Upgrade Required"),
    /**precondition required 428*/
    PRECONDITION_REQUIRED("428", "Precondition Required"),
    /**too many requests 429*/
    TOO_MANY_REQUESTS("429", "Too Many Requests"),
    /**request header fields too large 431*/
    REQUEST_HEADER_FIELDS_TOO_LARGE("431", "Request Header Fields Too Large"),
    /**error 500*/
    ERROR("500", "Internal Server Error"),
    /**not implemented 501*/
    NOT_IMPLEMENTED("501", "Not Implemented"),
    /**bad gateway 502*/
    BAD_GATEWAY("502", "Bad Gateway"),
    /**service unavailable 503*/
    SERVICE_UNAVAILABLE("503", "Service Unavailable"),
    /**gateway timeout 504*/
    GATEWAY_TIMEOUT("504", "Gateway Timeout"),
    /**http version not supported 505*/
    HTTP_VERSION_NOT_SUPPORTED("505", "HTTP Version Not Supported"),
    /**variant also negotiates 506*/
    VARIANT_ALSO_NEGOTIATES("506", "Variant Also Negotiates"),
    /**insufficient storage 507*/
    INSUFFICIENT_STORAGE("507", "Insufficient Storage"),
    /**not extended 510*/
    NOT_EXTENDED("510", "Not Extended"),
    /**network authentication required 511*/
    NETWORK_AUTHENTICATION_REQUIRED("511", "Network Authentication Required")
    ;
    private String code;
    private String message;
    private ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public String getCode() {
        return this.code;
    }
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * Copy it's code and message to new result
     * @param result new result
     * @return new result
     * @param <DATA> data type
     */
    public <DATA> Result<DATA> wrap(Result<DATA> result) {
        result.setCode(this.getCode());
        result.setMessage(this.getMessage());
        return result;
    }
}