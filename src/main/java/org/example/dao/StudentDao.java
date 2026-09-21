package org.example.dao;

import org.example.exception.EntityNotFoundException;
import org.example.model.Student;
import org.example.util.ConnectionManager;
import org.example.exception.DAOException;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class StudentDao {


    public void saveStudent(Student student){
        String sql = "INSERT INTO students (full_name, email) VALUES (?, ?)";

        try(Connection connection = ConnectionManager.getConnection()){
            PreparedStatement state = connection.prepareStatement(sql);

            state.setString(1, student.getFullName());
            state.setString(2, student.getEmail());

            state.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERROR of saving student: " + e.getMessage());
        }
    }

    public List<Student> findAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet results = state.executeQuery()){
                while (results.next()){
                    students.add(new Student(results.getLong("id"),
                            results.getString("full_name"),
                            results.getString("email")));
                }
            }
        catch(SQLException e){
            throw new DAOException("ERROR of searching all students " + e.getMessage());
        }
        return students;
    }

    public Optional<Student> findStudentById(Long id){
        String sql = "SELECT * FROM students WHERE id = ?";

        try(Connection connection = ConnectionManager.getConnection();
        PreparedStatement state = connection.prepareStatement(sql)){
            state.setLong(1, id);

            try(ResultSet results = state.executeQuery()){
                if (results.next()){
                    return Optional.of(new Student(results.getLong("id"),
                            results.getString("full_name"),
                            results.getString("email")));
                }
            } catch (SQLException e){
                throw new DAOException("ERROR of searching of student: " + e.getMessage());
            }
        } catch(SQLException e){
            throw new DAOException("ERROR of searching of student: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void updateStudent(Student student){
        String sql = "UPDATE students SET full_name = ?, email = ? WHERE id = ?";

        try(Connection connection = ConnectionManager.getConnection();
        PreparedStatement state = connection.prepareStatement(sql)){
            state.setString(1, student.getFullName());
            state.setString(2, student.getEmail());
            state.setLong(3, student.getId());

            Long updatedRows = state.executeLargeUpdate();

            if (updatedRows ==0){
                throw new EntityNotFoundException("Course", student.getId());
            }
        } catch(SQLException e){
            throw new DAOException("ERROR of updating student: " + e.getMessage());
        }
    }

    public void deleteStudentById(Long id){
        String sql = "DELETE FROM students WHERE id = ?";

        try(Connection connection = ConnectionManager.getConnection();
        PreparedStatement state = connection.prepareStatement(sql)){
            state.setLong(1, id);

            Long deletedRows = state.executeLargeUpdate();

            if (deletedRows ==0){
                throw new EntityNotFoundException("Course", id);
            }

        } catch (SQLException e){
            throw new DAOException("ERROR of deleting student: " + e.getMessage());
        }
    }
}
