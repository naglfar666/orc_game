package api.v1.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
public class GameObjectMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer gameObjectId;

    private Integer xAxis;

    private Integer yAxis;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameObjectId", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private GameObject gameObject;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameObjectId() {
        return gameObjectId;
    }

    public void setGameObjectId(Integer gameObjectId) {
        this.gameObjectId = gameObjectId;
    }

    public Integer getxAxis() {
        return xAxis;
    }

    public void setxAxis(Integer xAxis) {
        this.xAxis = xAxis;
    }

    public Integer getyAxis() {
        return yAxis;
    }

    public void setyAxis(Integer yAxis) {
        this.yAxis = yAxis;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
