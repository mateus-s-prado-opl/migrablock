package com.ws.ont.resource;


import com.ws.ont.filter.AddOntBlockFilter;
import com.ws.ont.filter.ListOntBlockFilter;
import com.ws.ont.filter.RemoveOntBlockFilter;
import com.ws.ont.pojo.response.AddOntBlockResponse;
import com.ws.ont.pojo.response.ListOntBlockResponse;
import com.ws.ont.pojo.response.RemoveOntBlockResponse;
import com.ws.ont.repository.OntRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ont")
public class OntController {

    private static final Logger logger = LoggerFactory.getLogger(OntController.class);

    @Autowired
    private OntRepository ontRepository;

    @PostMapping(path = "/bloqueio", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddOntBlockResponse> handleOntBlockRequest(@RequestBody @Valid AddOntBlockFilter input) {
        AddOntBlockResponse addOntBlock = ontRepository.executeOntBlockAdd(input);

//        if (addOntBlock.hasError()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addOntBlock);
//        }
        return ResponseEntity.status(HttpStatus.OK).body(addOntBlock);
    }

    @DeleteMapping(path = "/bloqueio", produces = "application/json", consumes = "application/json")
    public ResponseEntity<RemoveOntBlockResponse> handleOntBlockRemovalRequest(@RequestBody @Valid RemoveOntBlockFilter input) {
        logger.info("\n\n\n[API-MIGRABLOCK-LOG] Received request to remove ONT block");
        long startTime = System.currentTimeMillis();

        RemoveOntBlockResponse removedOntBlock = ontRepository.executeOntBlockRemove(input);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("[API-MIGRABLOCK-LOG] Request completed in {} ms", duration);

        if (removedOntBlock.hasError()) {
            logger.error("[API-MIGRABLOCK-LOG] Error removing ONT block: {}", removedOntBlock.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(removedOntBlock);
        }

        logger.info("[API-MIGRABLOCK-LOG] Successfully removed ONT block");
        return ResponseEntity.status(HttpStatus.OK).body(removedOntBlock);
    }

    @GetMapping("/listaBloqueios")
    public ResponseEntity<ListOntBlockResponse> getOntBlockList(@RequestBody @Valid ListOntBlockFilter input) {
        ListOntBlockResponse ontBlocks = ontRepository.getOntBlockList(input);
        if (ontBlocks.getOntBlockList().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ontBlocks);
    }
}
