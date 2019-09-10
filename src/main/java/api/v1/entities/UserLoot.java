package api.v1.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
public class UserLoot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer lootObjectId;

    private Integer userId;

    private Integer amount;

    private Long dateAdd;

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

    public Integer getLootObjectId() {
        return lootObjectId;
    }

    public void setLootObjectId(Integer lootObjectId) {
        this.lootObjectId = lootObjectId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Long dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LootObject getLootObject() {
        return lootObject;
    }

    public void setLootObject(LootObject lootObject) {
        this.lootObject = lootObject;
    }
}
