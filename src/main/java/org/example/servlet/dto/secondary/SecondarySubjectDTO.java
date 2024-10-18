package org.example.servlet.dto.secondary;

public class SecondarySubjectDTO {
    private int id;
    private String name;

    public SecondarySubjectDTO() {}

    public SecondarySubjectDTO(int id, String name) {
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
        if (!(o instanceof SecondarySubjectDTO)) return false;
        if (this == o) return true;

        SecondarySubjectDTO s = (SecondarySubjectDTO) o;

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