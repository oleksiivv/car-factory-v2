package org.carfactory.service.pipeline;

import org.carfactory.model.transport.Pipeline;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class PipelineService {

    @Bean("enginePipeline")
    public Pipeline enginePipeline(){
        return new Pipeline();
    }

    @Bean("bodyPipeline")
    public Pipeline bodyPipeline(){
        return new Pipeline();
    }

    @Bean("accessoryPipeline")
    public Pipeline accessoryPipeline(){
        return new Pipeline();
    }

    @Bean("carPipeline")
    public Pipeline carPipeline(){
        return new Pipeline();
    }

    @Bean("dealerPipeline")
    public Pipeline dealerPipeline(){
        return new Pipeline();
    }
}
