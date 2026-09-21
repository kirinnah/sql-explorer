package org.example.model;

public class Course {
    private Long id;
    private String title;

    public Course(){}

    public Course(String title){
        this.title = title;
    }

    public Course(Long id, String title){
        this.id = id;
        this.title = title;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    @Override
    public String toString(){
        return "Course { id=" + id + ", title=" + title + " }";
    }
}
