package io.github.ignamlrz.autotrader.service.status;

import io.github.ignamlrz.autotrader.service.commons.response.StatusResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Tag(name = "status", description = "Obtain status of the system")
@RestController
@RequestMapping("status")
public class StatusController {

    @GetMapping("system")
    public Mono<StatusResponse<String>> serviceStatus() {
        return Mono.just(new StatusResponse<>(new ArrayList<>(), "OK"));
    }

    @GetMapping("binance")
    public Mono<StatusResponse<String>> serviceBinance() {
        return Mono.just(new StatusResponse<>(null, "I don not know"));
    }
}
