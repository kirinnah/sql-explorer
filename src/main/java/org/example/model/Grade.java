package org.example.model;

public class Grade {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Short score;

    public Grade() {}

    public Grade(Long studentId, Long courseId, Short score) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
    }

    public Grade(Long id, Long studentId, Long courseId, Short score) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Short getScore() {
        return score;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setScore(Short score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Grade { id=" + id + ", studentId=" + studentId + ", courseId=" + courseId + ", score=" + score + " } ";
    }
}