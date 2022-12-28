package tech.hiphone.weixin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.commons.domain.User;
import tech.hiphone.weixin.domain.WxUser;
import tech.hiphone.weixin.domain.id.WxUserId;

public interface WxUserRepository extends JpaRepository<WxUser, WxUserId> {

    WxUser findTopOneByUnionId(String unionId);

    WxUser findOneByUserAndKeyType(User user, String type);

    void deleteByUser(User user);

    Page<WxUser> findAll(Specification<WxUser> spec, Pageable pageable);

}
