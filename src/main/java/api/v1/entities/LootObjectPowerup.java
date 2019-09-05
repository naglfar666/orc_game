package api.v1.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LootObjectPowerup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer lootObjectId;

    private Integer powerupId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLootObjectId() {
        return lootObjectId;
    }

    public void setLootObjectId(Integer lootObjectId) {
        this.lootObjectId = lootObjectId;
    }

    public Integer getPowerupId() {
        return powerupId;
    }

    public void setPowerupId(Integer powerupId) {
        this.powerupId = powerupId;
    }
}
