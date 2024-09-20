package org.example.servlet.dto;

public class SubjectDTO extends DTO {
    private int id;
    private String name;

    public SubjectDTO() {}

    public SubjectDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"id\": "+id+",\n" +
                "    \"name\": \""+name+"\"\n" +
                "}";
    }
}