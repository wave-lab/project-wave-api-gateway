package com.wave.apigateway;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultRes<T> {

    private int status;

    private boolean success;

    private String message;

    public DefaultRes(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public static<T> DefaultRes<T> res(final int status, final boolean success, final String message) {
        return DefaultRes.<T>builder()
                .status(status)
                .success(success)
                .message(message)
                .build();
    }

    public static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(500, false, "INTERNAL SERVER ERROR");
}