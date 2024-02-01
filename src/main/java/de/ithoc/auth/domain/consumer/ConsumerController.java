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
    public ResponseEntity<ConsumerFetchResponseBody> fetch(@RequestBody ConsumerFetchRequestBody consumerFetchRequestBody) {
        log.trace(consumerFetchRequestBody.toString());

        // TODO Check if client secret matches.
        // TODO Check if password matches.

        String apiKey = consumerService.read(
                consumerFetchRequestBody.getRealm(),
                consumerFetchRequestBody.getClientId(),
                consumerFetchRequestBody.getUsername()
        );

        ConsumerFetchResponseBody consumerFetchResponseBody = new ConsumerFetchResponseBody();
        consumerFetchResponseBody.setApiKey(apiKey);

        return ResponseEntity.ok(consumerFetchResponseBody);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody ConsumerCreateRequestBody consumerCreateRequestBody) {
        log.trace(consumerCreateRequestBody.toString());

        // TODO Check if the user is an admin.
        // TODO Check if password matches.

        consumerService.generate(
                consumerCreateRequestBody.getRealm(),
                consumerCreateRequestBody.getClientId(),
                consumerCreateRequestBody.getUser()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

