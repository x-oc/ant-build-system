package ru.vova.lab3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalTime;

@Named("areaChecker")
@SessionScoped
public class AreaCheckBean implements Serializable {
    public Result checkArea(InputBean inputBean, boolean fromGraph) {
        Result resultBean = new Result();
        long startTime = System.nanoTime();
        if (!fromGraph) {
            resultBean.setX(inputBean.getX());
            resultBean.setY(inputBean.getY());
        } else {
            resultBean.setX(inputBean.getXGraph());
            resultBean.setY(inputBean.getYGraph());
        }
        resultBean.setR(inputBean.getR());
        resultBean.setCreatedAt(LocalTime.now());
        boolean hit = check(resultBean);
        resultBean.setResult(hit);
        resultBean.setExecutionTime(System.nanoTime() - startTime);
        return resultBean;
    }

    private boolean check(Result resultBean) {
        var x = resultBean.getX();
        var y = resultBean.getY();
        var r = resultBean.getR();
        if (x >= 0 && y >= 0) return y <= r - x;
        if (x < 0 && y > 0) return -x <= r && (y <= r / 2);
        if (x < 0 && y < 0) return ((x * x + y * y) <= (r / 2 * r / 2));
        return false;
    }
}