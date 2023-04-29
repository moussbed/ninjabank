package com.mb.ninjabank.httpclients.decoder;


import com.mb.ninjabank.httpclients.exceptions.ExceptionMessage;
import com.mb.ninjabank.shared.common.exceptions.BadRequestException;
import com.mb.ninjabank.shared.common.exceptions.ForbiddenException;
import com.mb.ninjabank.shared.common.exceptions.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();
    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage exception = null;

        try(final InputStream body = response.body().asInputStream()) {
            exception = new ExceptionMessage((String) response.headers().get("date").toArray()[0],
                    response.status(),
                    HttpStatus.resolve(response.status()).getReasonPhrase(),
                    IOUtils.toString(body, StandardCharsets.UTF_8),
                    response.request().url()
                    );
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        return switch (response.status()) {
            case 400 -> new BadRequestException(exception.message() != null ? exception.message() : "Bad Request");
            case 404 -> new NotFoundException(exception.message() != null ? exception.message(): "Not found");
            case 403 -> new ForbiddenException(exception.message()!= null ? exception.message() : "Forbidden");
            default -> errorDecoder.decode(methodKey, response);
        };

    }
}
