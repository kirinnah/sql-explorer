package org.example.dao;

import org.example.exception.DAOException;
import org.example.model.Grade;
import org.example.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDao {
    public void transferStudentToCourse(Long studentId, Long oldCourseId, Long newCourseId, Short score){
        if (oldCourseId.equals(newCourseId)) {
            throw new IllegalArgumentException("ERROR: same courses to transfer!");
        }

        String deleteSql = "DELETE FROM grades WHERE student_id = ? AND course_id = ?";
        String insertSql = "INSERT INTO grades (student_id, course_id, score) VALUES (?, ?, ?)";

        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);

            try(PreparedStatement deleteState = connection.prepareStatement(deleteSql))
            {
                deleteState.setLong(1, studentId);
                deleteState.setLong(2,oldCourseId);
                Long rowsDeleted = deleteState.executeLargeUpdate();

                if (rowsDeleted == 0) {
                    throw new IllegalArgumentException("ERROR: student wasn't added to old course!");
                }
            }

            try(PreparedStatement insertState = connection.prepareStatement(insertSql))
            {
                insertState.setLong(1, studentId);
                insertState.setLong(2,newCourseId);
                insertState.setShort(3, score);
                insertState.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e){
            rollbackQuietly(connection);
            throw new DAOException("ERROR of transfer student: " + e.getMessage());
        } catch (RuntimeException e){
            rollbackQuietly(connection);
            throw e;
        }
        finally {
            closeQuietly(connection);
        }
    }

    private void rollbackQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("ERROR of rolling back transaction: " + ex.getMessage());
            }
        }
    }

    private void closeQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("ERROR of closing the connection: " + e.getMessage());
            }
        }
    }

    public void saveStudentGrade(Grade grade) {
        String sql = "INSERT INTO grades (student_id, course_id, score) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement state = connection.prepareStatement(sql)) {
            state.setLong(1, grade.getStudentId());
            state.setLong(2, grade.getCourseId());
            state.setShort(3, grade.getScore());
            state.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERROR of saving grade: " + e.getMessage());
        }
    }

    public List<Grade> findGradesByStudentId(Long studentId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE student_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement state = connection.prepareStatement(sql)) {
            state.setLong(1, studentId);

            try (ResultSet resultSet = state.executeQuery()) {
                while (resultSet.next()) {
                    grades.add(new Grade(resultSet.getLong("id"),
                            resultSet.getLong("student_id"),
                            resultSet.getLong("course_id"),
                            resultSet.getShort("score")));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("ERROR of searching grades: " + e.getMessage());
        }
        return grades;
    }
}