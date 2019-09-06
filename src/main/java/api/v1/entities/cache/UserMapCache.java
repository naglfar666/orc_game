package api.v1.entities.cache;

import org.springframework.data.redis.core.RedisHash;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@RedisHash("UserMap")
public class UserMapCache {

    @Id
    private Integer id;

    private Integer xAxis;

    private Integer yAxis;

    private Long dateAdd;

    public UserMapCache(Integer id, Integer xAxis, Integer yAxis, Long dateAdd) {
        this.id = id;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.dateAdd = dateAdd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Long getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Long dateAdd) {
        this.dateAdd = dateAdd;
    }
}
