package org.example.dao;

import org.example.exception.DAOException;
import org.example.exception.EntityNotFoundException;
import org.example.model.Course;
import org.example.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDao {
    public void saveCourse(Course course){
        String sql = "INSERT INTO courses (title) VALUES (?)";

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement state = connection.prepareStatement(sql))
        {
            state.setString(1, course.getTitle());
            state.executeUpdate();
        } catch(SQLException e){
            throw new DAOException("ERROR of saving course: " + e.getMessage());
        }
    }

    public List<Course> findAllCourses(){
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet results = state.executeQuery()) {
            while (results.next()){
                courses.add(new Course(results.getLong("id"),
                        results.getString("title")));
            }
        } catch(SQLException e){
            throw new DAOException("ERROR of searching all courses: " + e.getMessage());
        }

        return courses;
    }

    public Optional<Course> findCourseById(Long id){
        String sql = "SELECT * FROM courses WHERE id = ?";

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement state = connection.prepareStatement(sql);
        ){
            state.setLong(1, id);

            try(ResultSet results = state.executeQuery()) {
                if (results.next()) {
                    return Optional.of(new Course(results.getLong("id"),
                            results.getString("title")));
                }
            } catch (SQLException e){
                throw new DAOException("ERROR of searching course: " + e.getMessage());
            }
        } catch (SQLException e){
            throw new DAOException("ERROR of searching course: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void updateCourse(Course course){
        String sql = "UPDATE courses SET title = ? WHERE id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement state = connection.prepareStatement(sql)) {
            state.setString(1, course.getTitle());
            state.setLong(2, course.getId());

            Long updatedRows = state.executeLargeUpdate();

            if (updatedRows == 0) {
                throw new EntityNotFoundException("Course", course.getId());
            }
        } catch (SQLException e) {
            throw new DAOException("ERROR of updating course: " + e.getMessage());
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement state = connection.prepareStatement(sql)) {
            state.setLong(1, id);

            Long deletedRows = state.executeLargeUpdate();

            if (deletedRows == 0) {
                throw new EntityNotFoundException("Course", id);
            }

        } catch (SQLException e) {
            throw new DAOException("ERROR of deleting course: " + e.getMessage());
        }
    }
}