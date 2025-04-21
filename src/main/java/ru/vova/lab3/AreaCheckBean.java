package ru.vova.lab3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * Класс для проверки попадания точки (x, y) в заданную область при радиусе R.
 * Используется в веб-приложении для валидации координат.
 *
 * @Named("areaChecker") — бин в сессионном контексте.
 * @SessionScoped — состояние сохраняется между запросами пользователя.
 */
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
    
    /**
     * Проверяет, попадает ли точка (x, y) в область при заданном радиусе R.
     * Логика проверки:
     * - 1-й квадрант: треугольник (y ≤ R - x),
     * - 2-й квадрант: прямоугольник (-x ≤ R и y ≤ R/2),
     * - 3-й квадрант: окружность (x² + y² ≤ (R/2)²),
     * - 4-й квадрант: всегда false.
     *
     * @param resultBean объект с координатами (x, y, R) и результатом.
     * @return true, если точка попадает в область, иначе false.
     */
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