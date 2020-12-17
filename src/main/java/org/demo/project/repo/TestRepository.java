package org.demo.project.repo;

import org.demo.project.DataBase.DBUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;

//Класс нужен для тестирования расчета стоимости абонимента, исходя из количества посещений зала данным клиентом за последний год.
//Для расчета минимальной скидки в таблице посещаемости для выбранного клиента должно быть более 100 записей с датой посещения.
//Метод addVisits() добавляет требуемое кол-во посещений для клиента по его id, начиная с сегодняшнего дня в обратную сторону -
//т.е. если в параметрах метода указать 3 дня, то в таблице посещаемости добавятся 3 строки с одиноковым id и датами - сегодня, вчера и позавчера
//Класс нужен только для тестирования

@RequestScoped
public class TestRepository {
    @Inject
    DBUtils dbUtils;

    public void addVisits(int id, int days) {

        java.sql.Date setDate = new java.sql.Date(new java.util.Date().getTime());
        for (int i = 0; i < days; i++, setDate = new java.sql.Date(setDate.getTime() - 24 * 60 * 60 * 1000)) {
            String sql = new StringBuilder("INSERT INTO attendance (date, client_id)\n")
                    .append("SELECT '").append(setDate).append("' AS date, ").append(id).append(" AS client_id FROM attendance\n")
                    .append("WHERE NOT EXISTS(\n")
                    .append("SELECT id FROM attendance WHERE client_id = ").append(id).append(" AND date = '").append(setDate).append("'\n")
                    .append("    )\n")
                    .append("LIMIT 1;")
                    .toString();
            try (Connection connection = dbUtils.connect();
                 PreparedStatement preparedSt = connection.prepareStatement(sql)) {
                preparedSt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}