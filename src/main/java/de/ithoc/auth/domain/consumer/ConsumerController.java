package de.ithoc.auth.domain.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/consumers")
@CrossOrigin(origins = "*")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping("/fetch")
    public ResponseEntity<ConsumerResponseBody> fetch(@RequestBody ConsumerRequestBody consumerRequestBody) {
        log.trace(consumerRequestBody.toString());

        // TODO Check if client secret matches.
        // TODO Check if password matches.

        String apiKey = consumerService.read(
                consumerRequestBody.getRealm(),
                consumerRequestBody.getClientId(),
                consumerRequestBody.getUsername()
        );

        ConsumerResponseBody consumerResponseBody = new ConsumerResponseBody();
        consumerResponseBody.setApiKey(apiKey);

        return ResponseEntity.ok(consumerResponseBody);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody ConsumerRequestBody consumerRequestBody) {
        log.trace(consumerRequestBody.toString());

        // TODO Check if client secret matches.
        // TODO Check if password matches.

        consumerService.generate(
                consumerRequestBody.getRealm(),
                consumerRequestBody.getClientId(),
                consumerRequestBody.getUsername()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

