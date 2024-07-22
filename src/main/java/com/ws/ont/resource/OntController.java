package com.ws.ont.resource;


import com.ws.cvlan.resource.CvlanController;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ont")
public class OntController {

    private static final Logger logger = LoggerFactory.getLogger(CvlanController.class);

    @Autowired
    private OntRepository ontRepository;

    @PostMapping(path = "/bloqueio", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddOntBlockResponse> handleOntBlockRequest(@RequestBody @Valid AddOntBlockFilter input) {
        return null;
    }

    @DeleteMapping(path = "/bloqueio", produces = "application/json", consumes = "application/json")
    public ResponseEntity<RemoveOntBlockResponse> handleOntBlockRemovalRequest(@RequestBody @Valid RemoveOntBlockFilter input) {

        RemoveOntBlockResponse removedOntBlock = ontRepository.executeOntBlockRemove(input);
        return null;
    }

    @GetMapping("/listaBloqueios")
    public ResponseEntity<ListOntBlockResponse> getOntBlockList(@RequestBody @Valid ListOntBlockFilter input) {
        ListOntBlockResponse ontBlocks = ontRepository.getCvlanBlockList(input);
        if (ontBlocks.getOntBlockList().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ontBlocks);
    }
}
