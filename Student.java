/**
 * Created by Binro on 2017/5/31.
 */
public class Student {
    private String name;
    private String id;
    private String path;
    private String mark;
    private Boolean select;

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    public Student(String name, String id, String path, String mark) {
        this.name = name;
        this.id = id;
        this.path = path;
        this.mark = mark;
        this.select = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
