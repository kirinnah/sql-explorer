package org.example.model;

public class Student {
    private Long id;
    private String fullName;
    private String email;

    public Student(){}

    public Student(String fullName, String email){
        this.fullName = fullName;
        this.email = email;
    }

    public Student(Long id, String fullName, String email){
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public Long getId(){
        return id;
    }

    public String getFullName(){
            return fullName;
    }

    public String getEmail(){
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return "Student { id=" + id + ", fullName=" + fullName + ", email=" + email + " }";
    }
}
