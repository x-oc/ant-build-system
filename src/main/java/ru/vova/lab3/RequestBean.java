package ru.vova.lab3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class RequestBean implements Serializable {
    @Inject
    InputBean inputBean;
    @Inject
    AreaCheckBean areaChecker;
    @Inject
    ResultsRepository resultsRepository;

    public void process(boolean fromGraph) {
        Result result = areaChecker.checkArea(inputBean, fromGraph);
        resultsRepository.addResult(result);
    }
}