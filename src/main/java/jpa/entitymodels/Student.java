package jpa.entitymodels;

import com.sun.istack.NotNull;
import org.hibernate.*;
import javax.persistence.*;
import java.util.List;

@Entity
public class Student {
    @NotNull
    @Column(name = "email")
    @Id
    private String sEmail;
    @NotNull
    @Column(name = "name")
    private String sName;
    @NotNull
    @Column(name = "password")
    private String sPass;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "studentcourse",
            joinColumns = {@JoinColumn(name = "sEmail", referencedColumnName = "email")})
    private List<Course> sCourses;

    public Student() {
    }

    public Student(String sEmail, String sName, String sPass, List<Course> sCourses) {
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = sPass;
        this.sCourses = sCourses;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsPass() {
        return sPass;
    }

    public void setsPass(String sPass) {
        this.sPass = sPass;
    }

    public List<Course> getsCourses() {
        return sCourses;
    }

    public void setsCourses(List<Course> sCourses) {
        this.sCourses = sCourses;
    }
}
