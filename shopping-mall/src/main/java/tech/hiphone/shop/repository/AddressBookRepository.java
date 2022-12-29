package tech.hiphone.shop.repository;

import tech.hiphone.commons.domain.User;
import tech.hiphone.framework.jpa.support.JpaExtRepository;
import tech.hiphone.shop.domain.AddressBook;

public interface AddressBookRepository extends JpaExtRepository<AddressBook, Long> {

    void deleteByUser(User user);
}
