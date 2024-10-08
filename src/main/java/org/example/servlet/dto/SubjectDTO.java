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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof SubjectDTO)) return false;
        if (this == o) return true;

        SubjectDTO s = (SubjectDTO) o;

        return id == s.id
                && name.equals(s.name);
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + name.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"id\": "+id+",\n" +
                "    \"name\": \""+name+"\"\n" +
                "}";
    }
}