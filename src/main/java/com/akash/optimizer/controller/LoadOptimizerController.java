package com.akash.optimizer.controller;

import com.akash.optimizer.model.OptimizeRequest;
import com.akash.optimizer.model.OptimizeResponse;
import com.akash.optimizer.service.LoadOptimizerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/load-optimizer")
public class LoadOptimizerController {
    private final LoadOptimizerService service;
    public LoadOptimizerController(LoadOptimizerService service) {
        this.service = service;
    }
    @PostMapping("/optimize")
    public OptimizeResponse optimize(@RequestBody OptimizeRequest request){
        return service.optimize(request);
    }

}
