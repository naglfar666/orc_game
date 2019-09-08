package api.v1.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
public class GameObjectMapLootObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer gameObjectMapId;

    private Integer lootObjectId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lootObjectId", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private LootObject lootObject;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameObjectMapId() {
        return gameObjectMapId;
    }

    public void setGameObjectMapId(Integer gameObjectMapId) {
        this.gameObjectMapId = gameObjectMapId;
    }

    public Integer getLootObjectId() {
        return lootObjectId;
    }

    public void setLootObjectId(Integer lootObjectId) {
        this.lootObjectId = lootObjectId;
    }

    public LootObject getLootObject() {
        return lootObject;
    }

    public void setLootObject(LootObject lootObject) {
        this.lootObject = lootObject;
    }
}
