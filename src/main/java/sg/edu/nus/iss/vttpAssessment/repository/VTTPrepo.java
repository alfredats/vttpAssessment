package sg.edu.nus.iss.vttpAssessment.repository;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VTTPrepo {
    private static final Logger logger = LoggerFactory.getLogger(VTTPrepo.class);

    @Autowired
    private RedisTemplate redisTemplate;
    
    public String findById(String ID) {
        return null;
    }

    public Set<String> findKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
